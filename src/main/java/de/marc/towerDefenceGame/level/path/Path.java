package de.marc.towerDefenceGame.level.path;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.enemy.EnemySpawner;
import de.marc.towerDefenceGame.level.Level;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.level.Tile.TileType;
import static de.marc.towerDefenceGame.level.Tile.TileType.*;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Renderable;

import java.util.Arrays;

public class Path implements Renderable {

    private PathNode start;
    private int length = 1;

    private Level level;

    private static TileType[] validPathTileTypes = { PATHLT, PATHLR, PATHLB, PATHTL, PATHTR, PATHTB, PATHRL, PATHRT, PATHRB, PATHBL, PATHBT, PATHBR, START };

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
                        if (Arrays.asList(validPathTileTypes).contains(neighbor.getTileType())) {
                            prevTile = currentTile;
                            prevNode = currentNode;
                            currentTile = neighbor;
                            break;
                        }
                    }
                }
            }
        }
        level.spawner = new EnemySpawner(currentNode.getMiddleX(), currentNode.getMiddleY(), PathNode.size, level);
        return new Path(currentNode, level);
    }

    public void render() {
        if (TowerDefenceGame.theGame.getRenderDebugStuff()) {
            PathNode node = this.start;
            while (node.next != null) {
                GLUtils.drawCircleCentered(node.getMiddleX(), node.getMiddleY(), 2, 20, new float[] { 1, 1, 1 });
    //            GLUtils.drawRectCentered(node.getMiddleX(), node.getMiddleY(), 5, 5, new float[] { 1, 1, 1 });
                GLUtils.drawLine(node.getMiddleX(), node.getMiddleY(), node.next.getMiddleX(), node.next.getMiddleY(), 2f, new float[] { 1, 1, 1 });
                node = node.next;
            }
        }
    }

}
