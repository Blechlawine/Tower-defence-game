package de.marc.towerDefenceGame.level;

import de.marc.towerDefenceGame.utils.Renderable;

import java.util.ArrayList;
import java.util.List;

public class Chunk implements Renderable {

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
