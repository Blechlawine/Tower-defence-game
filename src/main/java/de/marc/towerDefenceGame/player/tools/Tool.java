package de.marc.towerDefenceGame.player.tools;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.utils.*;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;

public abstract class Tool implements Renderable, Listener {

    private String name;
    private boolean active;

    protected double mapPosX, mapPosY;
    private Keybinding buildBinding, destroyBinding;

    public Tool(String name) {
        this.name = name;
//        this.activate();
    }

    public void activate() {
        TowerDefenceGame.theGame.getRenderer().getLayerByName("tools").addElement(this);
        this.active = true;
        this.buildBinding = new Keybinding(Settings.KeyBindings.TOOLS_BUILD, new KeyAction[]{DOWN}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                Tile target = TowerDefenceGame.theGame.getGameManager().getCurrentGame().getLevel().getTileFromCoords(mapPosX, mapPosY);
                if (target != null) {
                    build(target);
                    event.cancel();
                }
            }
        };
        this.destroyBinding = new Keybinding(Settings.KeyBindings.TOOLS_DESTROY, new KeyAction[] {DOWN}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                Tile target = TowerDefenceGame.theGame.getGameManager().getCurrentGame().getLevel().getTileFromCoords(mapPosX, mapPosY);
                destroy(target);
                event.cancel();
            }
        };
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MouseMoveEvent) {
            MouseMoveEvent mme = (MouseMoveEvent) event;
            this.mapPosX = mme.getMapPosX();
            this.mapPosY = mme.getMapPosY();
        }
        if (this.active) {
            this.buildBinding.onEvent(event);
            this.destroyBinding.onEvent(event);
        }
    }

    public String getName() {
        return this.name;
    }

    public abstract void renderInUI(Vector2 pos);
    public abstract void build(Tile target);
    public abstract void destroy(Tile target);

    public void deactivate() {
        TowerDefenceGame.theGame.getRenderer().getLayerByName("tools").removeElement(this);
        this.active = false;
    }
}
