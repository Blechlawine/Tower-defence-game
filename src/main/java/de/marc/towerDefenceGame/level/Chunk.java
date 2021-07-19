package de.marc.towerDefenceGame.level;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.utils.Renderable;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class Chunk implements Renderable, Listener {

    private double xPosPixel, yPosPixel, widthPixel, heightPixel;
    private int widthTiles, heightTiles;
    private double[] uvTileSize;
    private Level level;

    private List<Tile> tiles = new ArrayList<>();

    public Chunk(double xPosPixel, double yPosPixel, double widthPixel, double heightPixel, int widthTiles, int heightTiles, Level level) {
        this.uvTileSize = TowerDefenceGame.theGame.getTextureHandler().getTileUVSize();
        this.widthPixel = widthPixel;
        this.heightPixel = heightPixel;
        this.widthTiles = widthTiles;
        this.heightTiles = heightTiles;
        this.xPosPixel = xPosPixel;
        this.yPosPixel = yPosPixel;
        this.level = level;

//        TowerDefenceGame.theGame.getLogger().debug(this.xPosPixel, this.yPosPixel);
    }

    public void setTiles(List<Tile> tilesIn) {
        this.tiles = tilesIn;
    }

    public void render() {
        GL11.glPushMatrix();
//        GL11.glTranslated(this.xPosPixel, this.yPosPixel, 0);
        for (Tile tile : this.tiles) {
            tile.render();
        }
        GL11.glPopMatrix();
    }

    public void onEvent(Event event) {
        for(Tile tile : this.tiles) {
            tile.onEvent(event);
        }
    }

    public boolean contains(double x, double y) {
        return (x < this.xPosPixel + this.widthPixel && x >= this.xPosPixel) && (y < this.yPosPixel + this.heightPixel && y >= this.yPosPixel);
    }

    public Tile getTileFromCoords(double x, double y) {
        for (Tile tile : this.tiles) {
            if (tile.getPosVec().getX() <= x && tile.getPosVec().getX() + Tile.size > x &&
                tile.getPosVec().getY() <= y && tile.getPosVec().getY() + Tile.size > y &&
                tile.getTileType() != Tile.TileType.NONE) {
                return tile;
            }
        }
        return null;
    }

    public Vector2 getMiddlePos() {
        Vector2 average = new Vector2(this.xPosPixel, this.yPosPixel);
        int numTiles = 0;
        for (Tile tile : this.tiles) {
            if (tile.getTileType() != Tile.TileType.NONE) {
                average.add(tile.getMiddle());
                numTiles++;
            }
        }
        if (numTiles > 0) {
            average.divide(numTiles);
        }
        return average;
    }

    public Level getParentLevel() {
        return this.level;
    }
}
