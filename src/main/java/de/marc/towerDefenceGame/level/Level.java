package de.marc.towerDefenceGame.level;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.gameObjects.enemy.Enemy;
import de.marc.towerDefenceGame.gameObjects.enemy.EnemySpawner;
import de.marc.towerDefenceGame.level.path.Path;
import de.marc.towerDefenceGame.level.path.PathNode;
import de.marc.towerDefenceGame.utils.FileUtils;
import de.marc.towerDefenceGame.utils.Renderable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Level implements Renderable, Listener {

    private List<Chunk> chunks = new ArrayList<>();

    public Tile startPortalTile, endPortalTile;
    public EnemySpawner spawner;
    private final ArrayList<Enemy> enemies;

    private Path path;

    public Level() {
        this.enemies = new ArrayList<>();
    }

    public void render() {
        for (Chunk chunk : this.chunks) {
            chunk.render();
        }
    }

    public void onEvent(Event event) {
        for(Chunk chunk : this.chunks) {
            chunk.onEvent(event);
        }
        this.spawner.onEvent(event);
    }

    public Level generateFromJsonFile(String fileName) {
        TowerDefenceGame.theGame.getLogger().info("Generating Level: " + fileName);
        JSONObject json = FileUtils.readJSONResource(fileName);
        JSONArray layers = json.getJSONArray("layers");
        JSONObject layer = layers.getJSONObject(0);
        JSONArray chunks = layer.getJSONArray("chunks");
        Level level = this;
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
            Chunk tempChunk = new Chunk(chunkXPosInPixels, chunkYPosInPixels, chunkWidthInPixels, chunkHeightInPixels, chunkWidthInTiles, chunkHeightInTiles, level);
            JSONArray chunkData = jsonChunk.getJSONArray("data");
            List<Tile> chunkTiles = new ArrayList<Tile>();
            Tile.size = tileSizeInPixel;
            PathNode.size = tileSizeInPixel;
            for(int d = 0; d < chunkData.length(); d++) {
                double tileXPos = chunkXPosInTiles * tileSizeInPixel + (d % chunkWidthInTiles) * tileSizeInPixel;
                double tileYPos = chunkYPosInTiles * tileSizeInPixel + (d / chunkWidthInTiles) * tileSizeInPixel;
                Tile tile = new Tile(tileXPos, tileYPos, chunkData.getInt(d), tempChunk);
//                tile.setTextureIndex(chunkData.getInt(d));
                chunkTiles.add(tile);
            }
            tempChunk.setTiles(chunkTiles);
            level.addChunk(tempChunk);
        }
        level.setPath(Path.buildPath(level));
        TowerDefenceGame.theGame.getLogger().info("Finished Level: " + fileName);
        return level;
    }

    private void setPath(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return this.path;
    }

    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

    private void addChunk(Chunk chunk) {
        this.chunks.add(chunk);
    }

    public Tile getTileFromCoords(double x, double y) {
        for (Chunk chunk : this.chunks) {
            if (chunk.contains(x, y)) {
                return chunk.getTileFromCoords(x, y);
            }
        }
        return null;
    }

}

