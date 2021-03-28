package de.marc.towerDefenceGame.event.events;

import de.marc.towerDefenceGame.event.Event;

public class MouseScrollEvent extends Event {

    private final double xDir, yDir;

    public MouseScrollEvent(double xDir, double yDir) {
        this.xDir = xDir;
        this.yDir = yDir;
    }

    public double getX() {
        return this.xDir;
    }

    public double getY() {
        return this.yDir;
    }


}
