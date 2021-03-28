package de.marc.towerDefenceGame.level.path;

public class Path {

    private PathNode start;
    private int length = 1;

    public Path(PathNode start) {
        this.start = start;
        PathNode node = this.start;
        while (node.next != null) {
            this.length++;
            node = node.next;
        }
    }
    
    public PathNode getNode(int index) {
        PathNode node = this.start;
        int currentIndex = 0;
        while ((node.next != null) && (currentIndex < index)) {
            node = node.next;
        }
        return node;
    }

}
