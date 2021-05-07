package de.marc.towerDefenceGame.player.tools;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.tower.towers.SniperTower;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;

public class SniperTowerTool extends Tool {

    @Override
    public void use(Tile target, int mouseButton) {
        if (mouseButton == 0) {
            if (target.getTileType() == Tile.TileType.PLATFORM && !target.isOccupied()) {
                if (TowerDefenceGame.theGame.getPlayer().pay(SniperTower.cost)) {
                    target.construct(new SniperTower(target.getMiddle().getX(), target.getMiddle().getY()));
                }
            }
        } else if (mouseButton == 1) {
            TowerDefenceGame.theGame.getPlayer().addMoney(SniperTower.cost);
            target.deconstruct();
        }
    }

    @Override
    public void render() {
        double size = 16;
        double xPos = MouseMoveEvent.getMapPosX() - size / 2;
        double yPos = MouseMoveEvent.getMapPosY() - size / 2;
        GLUtils.drawTexturedRect(xPos, yPos, size, size, 0, 0, 1, 1, "sniperTowerBase", new Color(0.5f, 0.5f, 0.5f));
        GLUtils.drawTexturedRect(xPos, yPos, size, size, 0, 0, 1, 1, "sniperTowerTurret", new Color(1, 1, 1));
    }
}
