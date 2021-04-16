package de.marc.towerDefenceGame.tower;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.tower.towers.*;
import de.marc.towerDefenceGame.utils.ListManager;

public class TowerManager extends ListManager<Tower> {

    public void setup() {

    }

    public void buildTower(Tower tower) {
        this.content.add(tower);
        TowerDefenceGame.theGame.getEventManager().addListener(tower);
        TowerDefenceGame.theGame.getRenderer().getLayerByName("towers").addElement(tower);
    }

    public void destroyTower(Tower tower) {
        if (this.content.contains(tower)) {
            this.content.remove(tower);
            TowerDefenceGame.theGame.getEventManager().removeListener(tower);
            TowerDefenceGame.theGame.getRenderer().getLayerByName("towers").removeElement(tower);
            tower.onDestroyed();
        }
    }
}
