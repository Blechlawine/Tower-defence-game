package de.marc.towerDefenceGame.mapstuff;

import de.marc.towerDefenceGame.mapstuff.pathstuff.Path;

import java.util.ArrayList;
import java.util.HashSet;

public class Map {

//    private final Path path;

    private ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();

    public Map() {
//        this.path = path;
        this.tiles.add(new ArrayList<>());
        this.tiles.get(0).add(new Tile(10, 10, 20, Tile.TileType.PLATFORM));
    }

    public void render() {
        for(ArrayList<Tile> row : this.tiles) {
            for(Tile tile : row) {
                tile.render();
            }
        }
    }

}
