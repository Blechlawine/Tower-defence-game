package de.marc.towerDefenceGame.player.tools;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.components.GuiTower;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.gameObjects.tower.towers.BasicTower;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

public class BasicTowerTool extends Tool {

    private final GuiTower guiTower;

    public BasicTowerTool() {
        super("basicTower");
        this.guiTower = new GuiTower(new Vector2(0, 0), 50, "basicTowerTurret", "basicTowerBase");
    }

    @Override
    public void render() {
        double size = 16;
        double xPos = this.mapPosX - size / 2;
        double yPos = this.mapPosY - size / 2;
        GLUtils.drawTexturedRect(xPos, yPos, size, size, 0, 0, 1, 1, "basicTowerBase", new Color(0.5f, 0.5f, 0.5f));
        GLUtils.drawTexturedRect(xPos, yPos, size, size, 0, 0, 1, 1, "basicTowerTurret", new Color(1, 1, 1));
        GLUtils.drawCircleCenteredOutline(this.mapPosX, this.mapPosY, 40, 32, 1, new Color(1, 1, 1));
    }

    @Override
    public void renderInUI(Vector2 pos) {
        GL11.glPushMatrix();
        GL11.glTranslated(pos.getX(), pos.getY(), 0);
        this.guiTower.render();
        GL11.glPopMatrix();
    }

    @Override
    public void build(Tile target) {
        if (target.getTileType() == Tile.TileType.PLATFORM && !target.isOccupied()) {
            if (TowerDefenceGame.theGame.getPlayer().pay(10)) {
                target.construct(new BasicTower(target, target.getMiddle().getX(), target.getMiddle().getY()));
            }
        }
    }

    @Override
    public void destroy(Tile target) {
        TowerDefenceGame.theGame.getPlayer().deactivateActiveTool();
    }
}
