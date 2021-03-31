package de.marc.towerDefenceGame.level;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.MouseButtonEvent;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Renderable;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;
import java.util.List;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;

public class Tile implements Renderable, Listener {

    private double xPos, yPos;
    private double[] uv, uvTileSize;
    private int size;
    private int textureIndex;
    private TileType type;
    private Chunk chunk;

    private boolean selected;

    public Tile(double xPos, double yPos, int size, int textureIndex, Chunk chunk) {
        TowerDefenceGame.theGame.getEventManager().addListener(this);

        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
        this.textureIndex = textureIndex;
        this.chunk = chunk;

        this.type = updateTileType();

        if(this.textureIndex != 0)
            this.uv = this.getUVforTextureIndex();

        this.uvTileSize = TowerDefenceGame.theGame.getTextureHandler().getTileUVSize();
//        TowerDefenceGame.theGame.getLogger().debug(this.xPos, this.yPos);
    }

    public double[] getUVforTextureIndex() {
        int rowLength = TowerDefenceGame.theGame.getTextureHandler().getTileSetRowLength();
        double[] uvTileSize = TowerDefenceGame.theGame.getTextureHandler().getTileUVSize();
        double u = (this.textureIndex % rowLength - 1) * uvTileSize[0];
        double v = (this.textureIndex / rowLength) * uvTileSize[1];
//        TowerDefenceGame.theGame.getLogger().info("TextureIndex: " + this.textureIndex + ", u: " + u + ", v: " + v + ", Rowlength: " + rowLength);
        return new double[] { u, v };
    }

    public TileType updateTileType() {
        switch (this.textureIndex) {
            case 0:
                return TileType.NONE;
            case 1:
//                TowerDefenceGame.theGame.getLogger().debug("Start");
                this.chunk.getParentLevel().startPortalTile = this;
                return TileType.START;
            case 2:
                this.chunk.getParentLevel().endPortalTile = this;
                return TileType.END;
            case 3:
                return TileType.PLATFORM;
            default:
                return TileType.PATH;
        }
    }

    public void onEvent(Event event) {
        if (event instanceof MouseButtonEvent) {
            MouseButtonEvent e = (MouseButtonEvent) event;
            double clickXPos = MouseMoveEvent.getMapPosX();
            double clickYPos = MouseMoveEvent.getMapPosY();
            if (clickXPos >= this.xPos && clickXPos < this.xPos + this.size && clickYPos >= this.yPos && clickYPos < this.yPos + this.size) {
                if (e.getButton() == 0 && e.getAction() == DOWN) {
                    this.selected = !this.selected;
                }
            }
        }
    }

    public void render() {
        if (this.textureIndex != 0) {
//            double[] uv = this.getUVforTextureIndex();
//            TowerDefenceGame.theGame.getLogger().debug(this.xPos, this.yPos, this.size, uv[0], uv[1], uvTileSize[0], uvTileSize[1]);
            GLUtils.drawTexturedRect(this.xPos, this.yPos, this.size, this.size, this.uv[0], this.uv[1], this.uvTileSize[0], this.uvTileSize[1]);
            if (this.selected) {
                GLUtils.drawRect(this.xPos, this.yPos, this.size, this.size, new float[] { 1, 1, 1 });
            }
        }
//        GLUtils.drawLine(0, 0, MouseMoveEvent.getMapPosX(), MouseMoveEvent.getMapPosY(), new float[] { 0.5F, 0.5F, 0.5F });

    }

    public List<Tile> getNeighbours() {
        List<Tile> neighbours = new ArrayList<Tile>();
        neighbours.add(this.chunk.getParentLevel().getTileFromCoords(this.xPos - this.size, this.yPos));
        neighbours.add(this.chunk.getParentLevel().getTileFromCoords(this.xPos, this.yPos - this.size));
        neighbours.add(this.chunk.getParentLevel().getTileFromCoords(this.xPos + this.size, this.yPos));
        neighbours.add(this.chunk.getParentLevel().getTileFromCoords(this.xPos, this.yPos + this.size));
        return neighbours;
    }

    public TileType getTileType() {
        TowerDefenceGame.theGame.getLogger().debug(this.type);
        return this.type;
    }

    public Vector2 getPosVec() {
        return new Vector2(this.xPos, this.yPos);
    }

    public Chunk getParentChunk() {
        return this.chunk;
    }

    public enum TileType {
        PATH, START, END, PLATFORM, NONE;
    }

}
