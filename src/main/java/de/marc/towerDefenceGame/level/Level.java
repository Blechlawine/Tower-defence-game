package de.marc.towerDefenceGame.level;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.FileUtils;
import de.marc.towerDefenceGame.utils.Renderable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Level implements Renderable {

    private List<Chunk> chunks = new ArrayList<>();

    public Level() {

    }

    public void render() {
        TowerDefenceGame.theGame.getTextureHandler().bindTexture();
        for (Chunk chunk : this.chunks) {
            chunk.render();
        }
        TowerDefenceGame.theGame.getTextureHandler().unbindTexture();
    }

    public static Level generateLevelFromJsonFile(String fileName) {
        JSONObject json = FileUtils.readJSONFile(fileName);
        JSONArray layers = json.getJSONArray("layers");
        JSONObject layer = layers.getJSONObject(0);
        JSONArray chunks = layer.getJSONArray("chunks");
        Level level = new Level();
        int tileSizeInPixel = json.getInt("tilewidth");
        for(int i = 0; i < chunks.length(); i++) {
            JSONObject jsonChunk = chunks.getJSONObject(i);
            int chunkXPosInTiles = jsonChunk.getInt("x");
            int chunkYPosInTiles = jsonChunk.getInt("y");
            int chunkWidthInTiles = jsonChunk.getInt("width");
            int chunkHeightInTiles = jsonChunk.getInt("height");
            double chunkXPosInPixels = chunkXPosInTiles * tileSizeInPixel;
            double chunkYPosInPixels = chunkYPosInTiles * tileSizeInPixel;
            double chunkWidthInPixels = chunkWidthInTiles * tileSizeInPixel;
            double chunkHeightInPixels = chunkHeightInTiles * tileSizeInPixel;
            Chunk tempChunk = new Chunk(chunkXPosInPixels, chunkYPosInPixels, chunkWidthInPixels, chunkHeightInPixels, chunkWidthInTiles, chunkHeightInTiles);
            JSONArray chunkData = jsonChunk.getJSONArray("data");
            List<Tile> chunkTiles = new ArrayList<Tile>();
            for(int d = 0; d < chunkData.length(); d++) {
                double tileXPos = chunkXPosInTiles * tileSizeInPixel + (d % chunkWidthInTiles) * tileSizeInPixel;
                double tileYPos = chunkYPosInTiles * tileSizeInPixel + (d / chunkWidthInTiles) * tileSizeInPixel;
                Tile tile = new Tile(tileXPos, tileYPos, tileSizeInPixel, chunkData.getInt(d));
//                tile.setTextureIndex(chunkData.getInt(d));
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

