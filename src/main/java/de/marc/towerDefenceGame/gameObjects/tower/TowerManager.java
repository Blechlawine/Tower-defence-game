package de.marc.towerDefenceGame.gameObjects.tower;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.ListManager;

public class TowerManager extends ListManager<Tower> {

    public void setup() {

    }

    public void buildTower(Tower tower) {
        this.content.add(tower);
        TowerDefenceGame.theGame.getEventManager().addGameListener(tower);
        TowerDefenceGame.theGame.getRenderer().getLayerByName("towers").addElement(tower);
    }

    public void destroyTower(Tower tower) {
        if (this.content.contains(tower)) {
            this.content.remove(tower);
            TowerDefenceGame.theGame.getEventManager().removeGameListener(tower);
            TowerDefenceGame.theGame.getRenderer().getLayerByName("towers").removeElement(tower);
            tower.onDestroyed();
        }
    }
}
