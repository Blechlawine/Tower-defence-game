package de.marc.towerDefenceGame.utils;

public class Vector2 {

    private double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vec) {
        this.x = vec.getX();
        this.y = vec.getY();
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

    public Vector2 add(double x, double y) {
        this.x += x;
        this.y += y;
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

    public Vector2 limitX(double min, double max) {
        this.x = Utils.limitD(this.x, min, max);
        return this;
    }

    public Vector2 limitY(double min, double max) {
        this.y = Utils.limitD(this.y, min, max);
        return this;
    }

    public Vector2 limitXY(double minX, double maxX, double minY, double maxY) {
        this.limitX(minX, maxX);
        this.limitY(minY, maxY);
        return this;
    }

    public double getAngleRad() {
        return Math.atan2(this.y, this.x);
    }

    public double getAngleDeg() {
        return Math.toDegrees(this.getAngleRad());
    }

    public double getAngleDeg360() {
        double angle = Math.toDegrees(Math.acos(new Vector2(1, 0).dot(this) / (1 * this.getLength())));
        if (this.y < 0) {
            return angle;
        } else {
            return 180 + (180 - angle);
        }
    }

    public void setAngleDeg(double deg) {
        this.setAngleRad(Math.toRadians(deg%360));
    }

    public void setAngleRad(double rad) {
        double len = this.getLength();
        this.y = Math.sin(rad%(2*Math.PI)) * len;
        this.x = Math.cos(rad%(2*Math.PI)) * len;
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
