package de.marc.towerDefenceGame.player;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.*;
import de.marc.towerDefenceGame.player.tools.BasicTowerTool;
import de.marc.towerDefenceGame.player.tools.SelectTool;
import de.marc.towerDefenceGame.player.tools.SniperTowerTool;
import de.marc.towerDefenceGame.player.tools.Tool;
import de.marc.towerDefenceGame.render.Camera;
import de.marc.towerDefenceGame.utils.KeyAction;
import de.marc.towerDefenceGame.utils.Keybinding;
import de.marc.towerDefenceGame.utils.Settings;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;
import static de.marc.towerDefenceGame.utils.KeyAction.UP;

public class Player extends Camera implements Listener {

    private final Vector2 motion;
    private double fastSpeed = 5;
    private double normalSpeed = 2;
    private double currentSpeed = this.normalSpeed;
    private double zoomSpeed = 1.5;

    private boolean dragMove = false;

    private ArrayList<Tool> tools;
    private Tool activeTool;
    private int activeToolIndex;

    // Starting values for the player should depend on the level
    private int wallet = 50;
    private int health = 100;
    private final int maxHealth = health;

    //Movement Vectors
    private Vector2 up = new Vector2(0D, 1);
    private Vector2 left = new Vector2(1, 0D);
    private Vector2 down = up.invert();
    private Vector2 right = left.invert();

    //Keybindings
    private Keybinding moveUpBinding, moveDownBinding, moveRightBinding, moveLeftBinding, moveFastBinding, moveMouseBinding, moveResetBinding;
    private ArrayList<Keybinding> bindings = new ArrayList<>();

    public Player() {
        super(new Vector2(0D, 0D));
        this.motion = new Vector2(0D, 0D);

        this.tools = new ArrayList<Tool>();
        this.tools.add(new SelectTool());
        this.tools.add(new BasicTowerTool());
        this.tools.add(new SniperTowerTool());

        this.activeToolIndex = 0;

        this.bindings.add(new Keybinding(Settings.KeyBindings.PLAYER_MOVEUP, new KeyAction[]{DOWN, UP}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (action == DOWN) motion.add(up);
                if (action == UP) motion.subtract(up);
            }
        });
        this.bindings.add(new Keybinding(Settings.KeyBindings.PLAYER_MOVEDOWN, new KeyAction[]{DOWN, UP}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (action == DOWN) motion.add(down);
                if (action == UP) motion.subtract(down);
            }
        });
        this.bindings.add(new Keybinding(Settings.KeyBindings.PLAYER_MOVELEFT, new KeyAction[]{DOWN, UP}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (action == DOWN) motion.add(left);
                if (action == UP) motion.subtract(left);
            }
        });
        this.bindings.add(new Keybinding(Settings.KeyBindings.PLAYER_MOVERIGHT, new KeyAction[]{DOWN, UP}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (action == DOWN) motion.add(right);
                if (action == UP) motion.subtract(right);
            }
        });
        this.bindings.add(new Keybinding(Settings.KeyBindings.PLAYER_MOVEFAST, new KeyAction[]{DOWN, UP}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (action == DOWN) currentSpeed = fastSpeed;
                if (action == UP) currentSpeed = normalSpeed;
            }
        });
        this.bindings.add(new Keybinding(Settings.KeyBindings.PLAYER_MOVEMOUSE, new KeyAction[]{DOWN, UP}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (action == DOWN) dragMove = true;
                if (action == UP) dragMove = false;
            }
        });
        this.bindings.add(new Keybinding(Settings.KeyBindings.PLAYER_MOVERESET, new KeyAction[]{UP}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (action == UP) {
                    Vector2 middle = TowerDefenceGame.theGame.getGameManager().getCurrentGame().getLevel().getMiddlePos();
                    TowerDefenceGame.theGame.getLogger().debug(middle);
                    pos.setX(middle.getX());
                    pos.setY(middle.getY());
                }
            }
        });
    }

    public void update(long partialMS) {
        this.pos.add(Vector2.duplicate(this.motion).normalize().multiply(this.currentSpeed * (partialMS / 10d)));
        this.setCameraPos(this.pos);
    }

    public void addMoney(int amount) {
        TowerDefenceGame.theGame.getSoundSourceManager().getSoundSourceFromName("coin").play();
        this.wallet += amount;
    }

    public int getWallet() {
        return this.wallet;
    }

    public int getHealth() {
        return this.health;
    }

    public void removeHealth(int amount) {
        this.health -= Math.min(amount, this.health);
        if (this.getHealth() <= 0) {
            TowerDefenceGame.theGame.getGameManager().getCurrentGame().end();
        }
    }

    public boolean pay(int amount) {
        if (amount <= this.wallet) {
            this.wallet -= amount;
            return true;
        } else {
            return false;
        }
    }

    public void onEvent(Event event) {
        for(Keybinding binding : this.bindings) {
            binding.onEvent(event);
        }
        for(Tool tool : this.tools) {
            tool.onEvent(event);
        }
        if (event instanceof MouseScrollEvent) {
            MouseScrollEvent e = (MouseScrollEvent) event;
            if (e.getY() > 0) {
                // scroll up / zoom in
                this.multiplyScale(this.zoomSpeed);
            } else if (e.getY() < 0) {
                // scroll down / zoom out
                this.divideScale(this.zoomSpeed);
            }
//            TowerDefenceGame.theGame.getLogger().debug(this.scale);
        } else if (event instanceof MouseMoveEvent) {
            MouseMoveEvent e = (MouseMoveEvent) event;
            if (this.dragMove) {
                // move player with mouse
                this.pos.add(new Vector2(e.getDX() / this.scale, e.getDY() / this.scale));
            }
        } else if (event instanceof UpdateEvent) {
            UpdateEvent e = (UpdateEvent) event;
            this.update(e.partialMS);
        }
    }

    public void setActiveTool(int toolIndex) {
        for (Tool tool : this.tools) {
            tool.deactivate();
        }
        this.activeToolIndex = toolIndex;
        this.activeTool = this.tools.get(this.activeToolIndex);
        this.activeTool.activate();
    }

    public void deactivateActiveTool() {
        this.setActiveTool(0);
    }

    public void updateCameraOrigin() {
        this.setOrigin(new Vector2(TowerDefenceGame.theGame.getWindowSize()[0] / 2, TowerDefenceGame.theGame.getWindowSize()[1] / 2));
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public ArrayList<Tool> getTools() {
        return this.tools;
    }

    public int getActiveToolIndex() {
        return this.activeToolIndex;
    }

    public void setPos(Vector2 pos) {
        this.pos = new Vector2(pos);
    }
}
