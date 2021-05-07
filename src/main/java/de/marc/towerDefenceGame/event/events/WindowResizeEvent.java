package de.marc.towerDefenceGame.event.events;

import de.marc.towerDefenceGame.event.Event;

public class WindowResizeEvent extends Event {

    private double windowHeight, windowWidth;

    public WindowResizeEvent(double windowWidth, double windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public double[] getWindowSize() {
        return new double[] {this.windowWidth, this.windowHeight};
    }
}
