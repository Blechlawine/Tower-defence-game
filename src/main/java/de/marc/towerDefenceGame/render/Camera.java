package de.marc.towerDefenceGame.render;

import de.marc.towerDefenceGame.utils.Vector2;

public class Camera {

    private Vector2 offset;
    private double scale = 1;

    public Camera() {
        this.offset = new Vector2(0, 0);
    }

    public Camera(Vector2 offset) {
        this.offset = offset;
    }

    protected void setScale(double scale) {
        this.scale = scale;
    }

    protected void setOffset(Vector2 offset) {
        this.offset = offset;
    }

    public Vector2 getOffset() {
        return this.offset;
    }

}
