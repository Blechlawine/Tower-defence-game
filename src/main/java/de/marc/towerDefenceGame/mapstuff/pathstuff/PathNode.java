package de.marc.towerDefenceGame.mapstuff.pathstuff;

public class PathNode {

    private int xPos, yPos;
    public PathNode next;

    public PathNode(int xPos, int yPos, PathNode next) {
        this.next = next;
        this.xPos = xPos;
        this.yPos = yPos;
    }

}
