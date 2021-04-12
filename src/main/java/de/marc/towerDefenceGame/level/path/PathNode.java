package de.marc.towerDefenceGame.level.path;

public class PathNode {

    private double xPos, yPos, middleX, middleY;
    public static int size;
    public PathNode next;

    public PathNode(double xPos, double yPos, PathNode next) {
        this.next = next;
        this.xPos = xPos;
        this.yPos = yPos;

        this.middleX = xPos + size / 2D;
        this.middleY = yPos + size / 2D;
    }

    public double getX() {
        return this.xPos;
    }

    public double getY() {
        return this.yPos;
    }

    public double getMiddleX() {
        return this.middleX;
    }

    public double getMiddleY() {
        return this.middleY;
    }
}
