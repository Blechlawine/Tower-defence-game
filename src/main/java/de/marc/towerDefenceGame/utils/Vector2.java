package de.marc.towerDefenceGame.utils;

import de.marc.towerDefenceGame.TowerDefenceGame;

public class Vector2 {

    private double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2 duplicate(Vector2 vec) {
        return new Vector2(vec.getX(), vec.getY());
    }

    public Vector2 invert() {
        return new Vector2(-this.x, -this.y);
    }

    public Vector2 normalize() {
        double length = this.getLength();
        if (length != 0) {
            this.x /= length;
            this.y /= length;
        }
        return this;
    }

    public Vector2 add(Vector2 vec) {
        this.x += vec.getX();
        this.y += vec.getY();
        return this;
    }

    public Vector2 subtract(Vector2 vec) {
        return this.add(vec.invert());
    }

    public Vector2 multiply(double factor) {
        this.x = this.x * factor;
        this.y = this.y * factor;
        return this;
    }

    public Vector2 divide(double factor) {
        return this.multiply(1 / factor);
    }

    public double dot(Vector2 vec) {
        return this.x * vec.getX() + this.y * vec.getY();
    }

    public double getAngleRad() {
//        TODO: reicht das? Anscheinend, ja
        return Math.atan2(this.y, this.x);
    }

    public double getLength() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "Vector2, x: " + this.x + ", y: " + this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }
}
