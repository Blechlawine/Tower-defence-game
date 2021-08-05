package de.marc.towerDefenceGame.player.tools;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.guis.GuiScreenInGame;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Vector2;

public class SelectTool extends Tool {

    private double size = 50;

    public SelectTool() {
        super("select", "", "");
    }

    @Override
    public void build(Tile target) {
        GuiScreenInGame ingameGui = (GuiScreenInGame)TowerDefenceGame.theGame.getGuiManager().getGuiFromName("ingame");
        if (target != null && (Tile.selectedTile == null || Tile.selectedTile != target)) {
            target.select();
            ingameGui.updateDetailsPanel();
        } else {
            Tile.selectedTile = null;
//            ingameGui.hideDetailsPanel();
        }
    }

    @Override
    public void destroy(Tile target) {
        if (target != null && target.isOccupied()) {
            target.deconstruct();
        }
    }

    @Override
    public void renderInUI(Vector2 pos) {
        GLUtils.drawTexturedRect(pos.getX(), pos.getY(), this.size, this.size, 0, 0, 1, 1, "selecttool", new Color(1, 1, 1));
    }

    @Override
    public void render() {

    }
}
