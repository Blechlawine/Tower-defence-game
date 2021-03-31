package de.marc.towerDefenceGame.level.path;

public class PathNode {

    private double xPos, yPos;
    public PathNode next;

    public PathNode(double xPos, double yPos, PathNode next) {
        this.next = next;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public double getX() {
        return this.xPos;
    }

    public double getY() {
        return this.yPos;
    }

}
