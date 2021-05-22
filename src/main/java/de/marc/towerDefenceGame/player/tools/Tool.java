package de.marc.towerDefenceGame.player.tools;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.TileClickEvent;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.utils.Renderable;
import de.marc.towerDefenceGame.utils.Vector2;

public abstract class Tool implements Renderable, Listener {

    private String name;

    public Tool(String name) {
        this.name = name;
//        this.activate();
    }

    public void activate() {
        TowerDefenceGame.theGame.getRenderer().getLayerByName("towers").addElement(this);
        TowerDefenceGame.theGame.getEventManager().addListener(this);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof TileClickEvent) {
            TileClickEvent e = (TileClickEvent) event;
            this.use(e.getTarget(), e.getMouseButton());
            event.cancel();
        }
    }

    public String getName() {
        return this.name;
    }

    public abstract void use(Tile target, int mouseButton);
    public abstract void renderInUI(Vector2 pos);

    public void deactivate() {
        TowerDefenceGame.theGame.getRenderer().getLayerByName("towers").removeElement(this);
        TowerDefenceGame.theGame.getEventManager().removeListener(this);
    }
}
