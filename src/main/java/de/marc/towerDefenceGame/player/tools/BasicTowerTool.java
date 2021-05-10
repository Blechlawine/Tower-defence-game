package de.marc.towerDefenceGame.player.tools;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.level.path.Path;
import de.marc.towerDefenceGame.tower.towers.BasicTower;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Renderable;

public class BasicTowerTool extends Tool {

    public BasicTowerTool() {
        super();
    }

    @Override
    public void render() {
        double size = 16;
        double xPos = MouseMoveEvent.getMapPosX() - size / 2;
        double yPos = MouseMoveEvent.getMapPosY() - size / 2;
        GLUtils.drawTexturedRect(xPos, yPos, size, size, 0, 0, 1, 1, "basicTowerBase", new Color(0.5f, 0.5f, 0.5f));
        GLUtils.drawTexturedRect(xPos, yPos, size, size, 0, 0, 1, 1, "basicTowerTurret", new Color(1, 1, 1));
        GLUtils.drawCircleCenteredOutline(xPos, yPos, 40, 32, 1, new Color(1, 1, 1));
    }

    @Override
    public void use(Tile target, int mouseButton) {
        if (mouseButton == 0) {
            if (target.getTileType() == Tile.TileType.PLATFORM && !target.isOccupied()) {
                if (TowerDefenceGame.theGame.getPlayer().pay(10)) {
                    target.construct(new BasicTower(target, target.getMiddle().getX(), target.getMiddle().getY()));
                }
            }
        } else if (mouseButton == 1) {
            TowerDefenceGame.theGame.getPlayer().deactivateActiveTool();
//            TowerDefenceGame.theGame.getPlayer().addMoney(BasicTower.cost);
//            target.deconstruct();
        }
    }
}
