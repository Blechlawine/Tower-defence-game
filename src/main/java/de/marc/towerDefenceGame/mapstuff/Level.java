package de.marc.towerDefenceGame.mapstuff;

import de.marc.towerDefenceGame.utils.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private List<Chunk> chunks = new ArrayList<>();

    public Level() {

    }

    public void render() {
        for (Chunk chunk : this.chunks) {
            chunk.render();
        }
    }

    public static Level generateLevelFromJsonFile(String fileName) {
        JSONObject json = FileUtils.readJSONFile(fileName);
        JSONArray layers = json.getJSONArray("layers");
        JSONObject layer = layers.getJSONObject(0);
        JSONArray chunks = layer.getJSONArray("chunks");
        Level level = new Level();
        int tileSize = json.getInt("tilewidth");
        for(int i = 0; i < chunks.length(); i++) {
            JSONObject jsonChunk = chunks.getJSONObject(i);
            int chunkXPos = jsonChunk.getInt("x");
            int chunkYPos = jsonChunk.getInt("y");
            int chunkWidth = jsonChunk.getInt("width");
            int chunkHeight = jsonChunk.getInt("height");
            Chunk tempChunk = new Chunk(chunkXPos, chunkYPos, chunkWidth, chunkHeight);
            JSONArray chunkData = jsonChunk.getJSONArray("data");
            List<Tile> chunkTiles = new ArrayList<Tile>();
            for(int d = 0; d < chunkData.length(); d++) {
                int tileXPos = chunkXPos + (d % chunkWidth) * tileSize;
                int tileYPos = chunkYPos + (d / chunkWidth) * tileSize;
                Tile tile = new Tile(tileXPos, tileYPos, tileSize, Tile.TileType.PLATFORM);
                tile.setTextureIndex(chunkData.getInt(d));
                chunkTiles.add(tile);
            }
            tempChunk.setTiles(chunkTiles);
            level.addChunk(tempChunk);
        }
        return level;
    }

    private void addChunk(Chunk chunk) {
        this.chunks.add(chunk);
    }

}
