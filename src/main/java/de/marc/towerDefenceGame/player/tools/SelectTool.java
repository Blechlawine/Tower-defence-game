package de.marc.towerDefenceGame.player.tools;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.tower.towers.SniperTower;
import de.marc.towerDefenceGame.utils.Vector2;

public class SelectTool extends Tool {

    public SelectTool() {
        super("select");
    }

    @Override
    public void use(Tile target, int mouseButton) {
        if (mouseButton == 0) {
            if (Tile.selectedTile == null || Tile.selectedTile != target) {
                target.select();
            } else {
                Tile.selectedTile = null;
            }
        } else if(mouseButton == 1) {
            if (target.isOccupied()) {
                TowerDefenceGame.theGame.getPlayer().addMoney(target.getTower().cost);
                target.deconstruct();
            }
        }
    }

    @Override
    public void renderInUI(Vector2 pos) {
//        TODO: Selecttool Icon in UI
    }

    @Override
    public void render() {

    }
}
