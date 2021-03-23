package de.marc.towerDefenceGame.mapstuff;

import de.marc.towerDefenceGame.utils.GLUtils;

public class Tile {

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

    public void render() {
        if (this.textureIndex != 0)
            GLUtils.drawRect(this.xPos, this.yPos, this.size, this.size, this.color);
    }

    public enum TileType {
        PATH, START, END, PLATFORM;
    }

}
