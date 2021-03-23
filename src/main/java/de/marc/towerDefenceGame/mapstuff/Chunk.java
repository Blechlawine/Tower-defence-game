package de.marc.towerDefenceGame.mapstuff;

import java.util.ArrayList;
import java.util.List;

public class Chunk {

    private int x, y, width, height;

    private List<Tile> tiles = new ArrayList<>();

    public Chunk(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setTiles(List<Tile> tilesIn) {
        this.tiles = tilesIn;
    }

    public void render() {
        for (Tile tile : this.tiles) {
            tile.render();
        }
    }

}
