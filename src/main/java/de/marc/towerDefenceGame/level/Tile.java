package de.marc.towerDefenceGame.level;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.MouseButtonEvent;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.event.events.TileClickEvent;
import de.marc.towerDefenceGame.tower.Tower;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Renderable;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;
import java.util.List;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;

public class Tile implements Renderable, Listener {

    private double xPos, yPos;
    private Vector2 middle;
    private double[] uv, uvTileSize;
    public static int size;
    private int textureIndex;
    private TileType type;
    private Chunk chunk;

    private boolean occupied = false;
    private Tower myTower;
    public static Tile selectedTile;

    public Tile(double xPos, double yPos, int textureIndex, Chunk chunk) {
        TowerDefenceGame.theGame.getEventManager().addListener(this);

        this.xPos = xPos;
        this.yPos = yPos;
        this.middle = new Vector2(this.xPos + size / 2, this.yPos + size / 2);
        this.textureIndex = textureIndex;
        this.chunk = chunk;

        this.type = updateTileType();
        if (this.type == TileType.START) {
            chunk.getParentLevel().startPortalTile = this;
        } else if (this.type == TileType.END) {
            chunk.getParentLevel().endPortalTile = this;
        }

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
        return TileType.getTileTypeForId(this.textureIndex);
    }

    public void onEvent(Event event) {
        if (this.textureIndex != 0) {
            if (event instanceof MouseButtonEvent) {
                MouseButtonEvent e = (MouseButtonEvent) event;
                double clickXPos = MouseMoveEvent.getMapPosX();
                double clickYPos = MouseMoveEvent.getMapPosY();
                if (clickXPos >= this.xPos && clickXPos < this.xPos + size && clickYPos >= this.yPos && clickYPos < this.yPos + size) {
                    if (e.getAction() == DOWN) {
                        TowerDefenceGame.theGame.getEventManager().hook(new TileClickEvent(this, e.getButton()));
//                        if (e.getButton() == 0) {
//
//                        } else if (e.getButton() == 1) {
//
//                        }
                    }
                }
            }
        }
    }

    public void construct(Tower tower) {
        if (!this.occupied) {
            this.occupied = true;
            this.myTower = tower;
            TowerDefenceGame.theGame.getTowerManager().buildTower(this.myTower);
        }
    }

    public void deconstruct() {
        TowerDefenceGame.theGame.getTowerManager().destroyTower(this.myTower);
        this.occupied = false;
    }

    public boolean isOccupied() {
        return this.occupied;
    }

    public void render() {
        if (this.textureIndex != 0) {
            GLUtils.drawTexturedRect(this.xPos, this.yPos, size, size, 0, 0, 1, 1, this.type.getTextureId(), new Color(1, 1, 1 ));
            if (selectedTile == this) {
                GLUtils.drawTexturedRect(this.xPos, this.yPos, size, size, 0, 0, 1, 1, "cursor", new Color(1, 1, 1));
            }
        }
//        GLUtils.drawLine(0, 0, MouseMoveEvent.getMapPosX(), MouseMoveEvent.getMapPosY(), new float[] { 0.5F, 0.5F, 0.5F });

    }

    public List<Tile> getNeighbours() {
        List<Tile> neighbours = new ArrayList<Tile>();
        neighbours.add(this.chunk.getParentLevel().getTileFromCoords(this.xPos - size, this.yPos));
        neighbours.add(this.chunk.getParentLevel().getTileFromCoords(this.xPos, this.yPos - size));
        neighbours.add(this.chunk.getParentLevel().getTileFromCoords(this.xPos + size, this.yPos));
        neighbours.add(this.chunk.getParentLevel().getTileFromCoords(this.xPos, this.yPos + size));
        return neighbours;
    }

    public TileType getTileType() {
//        TowerDefenceGame.theGame.getLogger().debug(this.type);
        return this.type;
    }

    public Vector2 getMiddle() {
        return this.middle;
    }

    public Vector2 getPosVec() {
        return new Vector2(this.xPos, this.yPos);
    }

    public Chunk getParentChunk() {
        return this.chunk;
    }

    public void select() {
        selectedTile = this;
    }

    public enum TileType {
        PATHLR("pathleftright", 17),
        PATHRL("pathrightleft", 33),
        PATHTB("pathtopbottom", 18),
        PATHBT("pathbottomtop", 34),
        PATHLT("pathlefttop", 36),
        PATHTL("pathtopleft", 20),
        PATHTR("pathtopright", 37),
        PATHRT("pathrighttop", 21),
        PATHRB("pathrightbottom", 38),
        PATHBR("pathbottomright", 22),
        PATHBL("pathbottomleft", 35),
        PATHLB("pathleftbottom", 19),
        START("startportal", 1),
        END("endportal", 2),
        PLATFORM("platform", 3),
        NONE("", 0);

        private final String textureId;
        private final int id;

        TileType(String textureId, int id) {
            this.textureId = textureId;
            this.id = id;
        }

        public String getTextureId() {
            return this.textureId;
        }

        public static TileType getTileTypeForId(int id) {
            for (TileType type : values()) {
                if (type.id == id)
                    return type;
            }
            return NONE;
        }

    }

}
