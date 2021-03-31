package de.marc.towerDefenceGame.level.path;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.level.Level;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Renderable;

public class Path implements Renderable {

    private PathNode start;
    private int length = 1;

    private Level level;

    public Path(PathNode start, Level level) {
        this.start = start;
        this.level = level;
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
            currentIndex++;
        }
        return node;
    }

    public static Path buildPath(Level level) {
        TowerDefenceGame.theGame.getLogger().info("Building Path");
        Tile currentTile = level.endPortalTile;
        Tile prevTile = null;
        PathNode prevNode = null;
        PathNode currentNode = new PathNode(currentTile.getPosVec().getX(), currentTile.getPosVec().getY(), prevNode);
        while (currentTile != null) {
            currentNode = new PathNode(currentTile.getPosVec().getX(), currentTile.getPosVec().getY(), prevNode);
            for (int i = 0; i < 5; i++) {
                if (i == 4) {
                    currentTile = null;
                } else {
                    assert currentTile != null;
                    Tile neighbor = currentTile.getNeighbours().get(i);
                    if (neighbor != prevTile && neighbor != null) {
                        if (neighbor.getTileType() == Tile.TileType.PATH || neighbor.getTileType() == Tile.TileType.START) {
                            prevTile = currentTile;
                            prevNode = currentNode;
                            currentTile = neighbor;
                            break;
                        }
                    }
                }
            }
        }
        return new Path(currentNode ,level);
    }

    public void render() {
        PathNode node = this.start;
        while (node.next != null) {
            GLUtils.drawRect(node.getX(), node.getY(), 5, 5, new float[] { 1, 1, 1 });
            GLUtils.drawLine(node.getX(), node.getY(), node.next.getX(), node.next.getY(), new float[] { 1, 1, 1 });
            node = node.next;
        }
    }

}
