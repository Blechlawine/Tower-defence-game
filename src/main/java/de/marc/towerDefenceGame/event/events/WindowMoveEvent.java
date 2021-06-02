package de.marc.towerDefenceGame.event.events;

import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.utils.Vector2;

public class WindowMoveEvent extends Event {

    private Vector2 pos;

    public WindowMoveEvent(double x, double y) {
        this.pos = new Vector2(x, y);
    }

    public Vector2 getPos() {
        return this.pos;
    }

}
