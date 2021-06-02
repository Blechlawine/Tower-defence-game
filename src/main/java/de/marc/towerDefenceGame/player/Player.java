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
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;

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
    private int health = 20;
    private final int maxHealth = health;

    public Player() {
        super(new Vector2(0D, 0D));
        TowerDefenceGame.theGame.getEventManager().addListener(this);
        this.motion = new Vector2(0D, 0D);

        this.tools = new ArrayList<Tool>();
        this.tools.add(new SelectTool());
        this.tools.add(new BasicTowerTool());
        this.tools.add(new SniperTowerTool());

        this.activeToolIndex = 0;
    }

    public void update(long partialMS) {
        this.pos.add(Vector2.duplicate(this.motion).normalize().multiply(this.currentSpeed * (partialMS / 10d)));
        this.setCameraPos(this.pos);
    }

    public void addMoney(int amount) {
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
            // Spiel zu ende
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
        if (event instanceof KeyEvent) {
            KeyEvent e = (KeyEvent) event;
            Vector2 up = new Vector2(0D, 1);
            Vector2 left = new Vector2(1, 0D);
            Vector2 down = up.invert();
            Vector2 right = left.invert();
            switch (e.getKey()) {
                case W:
                    switch (e.getAction()) {
                        case DOWN:
                            this.motion.add(up);
                            break;
                        case UP:
                            this.motion.subtract(up);
                            break;
                    }
                    break;
                case A:
                    switch (e.getAction()) {
                        case DOWN:
                            this.motion.add(left);
                            break;
                        case UP:
                            this.motion.subtract(left);
                            break;
                    }
                    break;
                case S:
                    switch (e.getAction()) {
                        case DOWN:
                            this.motion.add(down);
                            break;
                        case UP:
                            this.motion.subtract(down);
                            break;
                    }
                    break;
                case D:
                    switch (e.getAction()) {
                        case DOWN:
                            this.motion.add(right);
                            break;
                        case UP:
                            this.motion.subtract(right);
                            break;
                    }
                    break;
                case L_SHIFT:
                    switch (e.getAction()) {
                        case DOWN:
                            this.currentSpeed = this.fastSpeed;
                            break;
                        case UP:
                            this.currentSpeed = this.normalSpeed;
                            break;
                    }
                    break;
                case SPACE:
                    switch (e.getAction()) {
                        case DOWN:
                            break;
                        case UP:
                            this.pos.setX(0);
                            this.pos.setY(0);
                            break;
                    }
                    break;
            }
        } else if (event instanceof MouseButtonEvent) {
            MouseButtonEvent e = (MouseButtonEvent) event;
            if (e.getButton() == 2) { // 0 is the left mouse button
                switch(e.getAction()) {
                    case DOWN:
                        this.dragMove = true;
                        break;
                    case UP:
                        this.dragMove = false;
                        break;
                }
            }
        } else if (event instanceof MouseScrollEvent) {
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
}
