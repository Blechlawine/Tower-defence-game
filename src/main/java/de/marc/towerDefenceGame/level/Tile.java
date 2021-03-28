package de.marc.towerDefenceGame.level;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Renderable;

public class Tile implements Renderable {

    private int xPos, yPos;
    private int size;
    private int textureIndex;
    private TileType type;

    private float[] color;

    public Tile(int xPos, int yPos, int size, TileType type) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
        this.type = type;

        switch(this.type) {
            case END:
                this.color = new float[] { 0.9f, 0.2f, 0f };
                break;
            case START:
                this.color = new float[] { 0.2f, 0.9f, 0f };
                break;
            case PATH:
                this.color = new float[] { 0.5f, 0.5f, 0.5f };
                break;
            case PLATFORM:
                this.color = new float[] { 0.3f, 0.3f, 0.3f };
                break;
            default:
                this.color = new float[] { 1f, 1f, 1f };
                break;
        }
    }

    public void setTextureIndex(int index) {
        this.textureIndex = index;
    }

    public double[] getUVforTextureIndex() {
        int rowLength = TowerDefenceGame.theGame.getTextureHandler().getTileSetRowLength();
        double[] uvTileSize = TowerDefenceGame.theGame.getTextureHandler().getTileUVSize();
        double u = (this.textureIndex % rowLength - 1) * uvTileSize[0];
        double v = (this.textureIndex / rowLength) * uvTileSize[1];
//        TowerDefenceGame.theGame.getLogger().info("TextureIndex: " + this.textureIndex + ", u: " + u + ", v: " + v + ", Rowlength: " + rowLength);
        return new double[] { u, v };
    }

    public void render() {
        if (this.textureIndex != 0) {
            double[] uv = this.getUVforTextureIndex();
            double[] uvTileSize = TowerDefenceGame.theGame.getTextureHandler().getTileUVSize();
//            TowerDefenceGame.theGame.getLogger().debug(this.xPos, this.yPos, this.size, uv[0], uv[1], uvTileSize[0], uvTileSize[1]);
            GLUtils.drawTexturedRect(this.xPos, this.yPos, this.size, this.size, uv[0], uv[1], uvTileSize[0], uvTileSize[1]);
        }
    }

    public enum TileType {
        PATH, START, END, PLATFORM;
    }

}
