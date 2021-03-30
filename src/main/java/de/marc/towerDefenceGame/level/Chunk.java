package de.marc.towerDefenceGame.level;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.Renderable;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class Chunk implements Renderable {

    private double xPosPixel, yPosPixel, widthPixel, heightPixel;
    private int widthTiles, heightTiles;
    private double[] uvTileSize;

    private List<Tile> tiles = new ArrayList<>();

    public Chunk(double xPosPixel, double yPosPixel, double widthPixel, double heightPixel, int widthTiles, int heightTiles) {
        this.uvTileSize = TowerDefenceGame.theGame.getTextureHandler().getTileUVSize();
        this.widthPixel = widthPixel;
        this.heightPixel = heightPixel;
        this.widthTiles = widthTiles;
        this.heightTiles = heightTiles;
        this.xPosPixel = xPosPixel;
        this.yPosPixel = yPosPixel;

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

}
