package de.marc.towerDefenceGame.player.tools;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.tower.towers.SniperTower;

public class SelectTool extends Tool {

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
    public void render() {

    }
}
