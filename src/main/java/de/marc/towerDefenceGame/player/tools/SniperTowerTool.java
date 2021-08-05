package de.marc.towerDefenceGame.player.tools;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.gameObjects.tower.towers.SniperTower;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;

public class SniperTowerTool extends Tool {

    public SniperTowerTool() {
        super("sniperTower", "sniperTowerBase", "sniperTowerTurret");
    }

    @Override
    public void build(Tile target) {
        if (target.getTileType() == Tile.TileType.PLATFORM && !target.isOccupied()) {
            if (TowerDefenceGame.theGame.getPlayer().pay(20)) {
                target.construct(new SniperTower(target, target.getMiddle().getX(), target.getMiddle().getY()));
            }
        }
    }

    @Override
    public void destroy(Tile target) {
        TowerDefenceGame.theGame.getPlayer().deactivateActiveTool();
    }

    @Override
    public void render() {
        double size = 16;
        double xPos = this.mapPosX - size / 2;
        double yPos = this.mapPosY - size / 2;
        GLUtils.drawTexturedRect(xPos, yPos, size, size, 0, 0, 1, 1, "sniperTowerBase", new Color(0.5f, 0.5f, 0.5f));
        GLUtils.drawTexturedRect(xPos, yPos, size, size, 0, 0, 1, 1, "sniperTowerTurret", new Color(1, 1, 1));
        GLUtils.drawCircleCenteredOutline(this.mapPosX, this.mapPosY, 200, 32, 1, new Color(1, 1, 1));
    }
}
