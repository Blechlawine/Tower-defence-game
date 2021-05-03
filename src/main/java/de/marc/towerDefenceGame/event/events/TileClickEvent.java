package de.marc.towerDefenceGame.event.events;

import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.level.Tile;

public class TileClickEvent extends Event {

    private final Tile target;
    private final int mouseButton;

    public TileClickEvent(Tile target, int mouseButton) {
        this.target = target;
        this.mouseButton = mouseButton;
    }

    public Tile getTarget() {
        return this.target;
    }

    public int getMouseButton() {
        return this.mouseButton;
    }

}
