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
import de.marc.towerDefenceGame.utils.Vector2;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Level implements Renderable, Listener {

    private List<Chunk> chunks = new ArrayList<>();

    public Tile startPortalTile, endPortalTile;
    public EnemySpawner spawner;
    public double[] boundary = new double[4]; // left, top, right, bottom

    private ArrayList<Enemy> enemies;
    private Path path;
    private Vector2 middle;

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
        Enemy[] tempEnemies = this.enemies.toArray(new Enemy[0]);
        for (Enemy enemy : tempEnemies) {
            enemy.onEvent(event);
        }
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
        level.middle = level.createMiddlePos();
        level.createBoundary();
        TowerDefenceGame.theGame.getLogger().info("Finished Level: " + fileName);
        return level;
    }

    public void destroy() {
        this.spawner = null;
        Enemy[] tempEnemies = this.enemies.toArray(new Enemy[0]);
        for (Enemy enemy : tempEnemies) {
            enemy.remove();
        }
        this.chunks = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.path = null;
        this.middle = null;
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

    private Vector2 createMiddlePos() {
        Vector2 tempMiddle = new Vector2(0, 0);
        int numChunks = 0;
        for (Chunk chunk : this.chunks) {
            tempMiddle.add(chunk.getMiddlePos());
            numChunks++;
        }
        if (numChunks > 0) {
            tempMiddle.divide(numChunks);
        }
        return tempMiddle.invert();
    }

    private void createBoundary() {
        for (Chunk chunk : this.chunks) {
            for (Tile tile : chunk.getTiles()) {
                if (tile.getTileType() != Tile.TileType.NONE) {
                    Vector2 tilePos = tile.getPosVec();
                    if (tilePos.getX() < this.boundary[0]) {
                        this.boundary[0] = tilePos.getX();
                    }
                    if (tilePos.getY() < this.boundary[1]) {
                        this.boundary[1] = tilePos.getY();
                    }
                    if (tilePos.getX() + Tile.size > this.boundary[2]) {
                        this.boundary[2] = tilePos.getX() + Tile.size;
                    }
                    if (tilePos.getY() + Tile.size > this.boundary[3]) {
                        this.boundary[3] = tilePos.getY() + Tile.size;
                    }
                }
            }
        }
    }

    public Vector2 getMiddlePos() {
        return this.middle;
    }
}

