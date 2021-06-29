package de.marc.towerDefenceGame.player.tools;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.gui.components.GuiTower;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.gameObjects.tower.towers.SniperTower;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

public class SniperTowerTool extends Tool {

    private GuiTower guiTower;

    public SniperTowerTool() {
        super("sniperTower");
        this.guiTower = new GuiTower(new Vector2(0, 0), 50, "sniperTowerTurret", "sniperTowerBase");
    }

    @Override
    public void use(Tile target, int mouseButton) {
        if (mouseButton == 0) {
            if (target.getTileType() == Tile.TileType.PLATFORM && !target.isOccupied()) {
                if (TowerDefenceGame.theGame.getPlayer().pay(20)) {
                    target.construct(new SniperTower(target, target.getMiddle().getX(), target.getMiddle().getY()));
                }
            }
        }
    }

    @Override
    public void renderInUI(Vector2 pos) {
        GL11.glPushMatrix();
        GL11.glTranslated(pos.getX(), pos.getY(), 0);
        this.guiTower.render();
        GL11.glPopMatrix();
    }

    @Override
    public void render() {
        double size = 16;
        double xPos = MouseMoveEvent.getMapPosX() - size / 2;
        double yPos = MouseMoveEvent.getMapPosY() - size / 2;
        GLUtils.drawTexturedRect(xPos, yPos, size, size, 0, 0, 1, 1, "sniperTowerBase", new Color(0.5f, 0.5f, 0.5f));
        GLUtils.drawTexturedRect(xPos, yPos, size, size, 0, 0, 1, 1, "sniperTowerTurret", new Color(1, 1, 1));
        GLUtils.drawCircleCenteredOutline(MouseMoveEvent.getMapPosX(), MouseMoveEvent.getMapPosY(), 200, 32, 1, new Color(1, 1, 1));
    }
}
