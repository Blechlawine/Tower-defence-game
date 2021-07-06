package de.marc.towerDefenceGame.render;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.events.MouseScrollEvent;
import de.marc.towerDefenceGame.utils.Vector2;

public class Camera {

    protected Vector2 pos, origin;
    protected double scale = 1;
    protected double maxScale = 7, minScale = 0.5;

    public Camera(Vector2 pos) {
        double[] windowSize = TowerDefenceGame.theGame.getWindowSize();
        this.origin = new Vector2(windowSize[0] / 2, windowSize[1] / 2);
        this.pos = pos;
    }

    public Camera(Vector2 pos, Vector2 origin) {
        this.pos = pos;
        this.origin = origin;
    }

    public Camera(Vector2 pos, Vector2 origin, double scale) {
        this.pos = pos;
        this.origin = origin;
        this.scale = scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    protected void multiplyScale(double amount) {
        if (this.scale < this.maxScale)
            this.scale *= amount;
    }

    protected void divideScale(double amount) {
        if (this.scale > this.minScale)
            this.scale /= amount;
    }

    protected void setCameraPos(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getPos() {
        return this.pos;
    }

    public double getScale() {
        return this.scale;
    }

    public Vector2 getOrigin() {
        return this.origin;
    }

    public void setOrigin(Vector2 origin) {
        this.origin = origin;
    }
}
