package de.marc.towerDefenceGame.player.tools;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.TileClickEvent;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.utils.Renderable;

public abstract class Tool implements Renderable, Listener {

    public Tool() {
        this.activate();
    }

    protected void activate() {
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

    public abstract void use(Tile target, int mouseButton);

    public void deactivate() {
        TowerDefenceGame.theGame.getRenderer().getLayerByName("towers").removeElement(this);
        TowerDefenceGame.theGame.getEventManager().removeListener(this);
    }
}
