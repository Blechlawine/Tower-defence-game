package de.marc.towerDefenceGame.level;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.Renderable;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class Chunk implements Renderable {

    private double xPos, yPos, width, height;
    private double[] uvTileSize;

    private List<Tile> tiles = new ArrayList<>();

    public Chunk(int xPos, int yPos, int width, int height) {
        this.uvTileSize = TowerDefenceGame.theGame.getTextureHandler().getTileUVSize();
        this.width = width - (width * this.uvTileSize[0]);
        this.height = height - (height * this.uvTileSize[1]);
        this.xPos = xPos * this.width;
        this.yPos = yPos * this.height;
//        TowerDefenceGame.theGame.getLogger().debug(this.xPos, this.yPos);
    }

    public void setTiles(List<Tile> tilesIn) {
        this.tiles = tilesIn;
    }

    public void render() {
        GL11.glPushMatrix();
        GL11.glTranslated(this.xPos, this.yPos, 0);
        for (Tile tile : this.tiles) {
            tile.render();
        }
        GL11.glPopMatrix();
    }

}
