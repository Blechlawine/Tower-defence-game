package de.marc.towerDefenceGame.games;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.level.Level;

public class Game {

    private Level level;

    public Game() {
        this.level = Level.generateLevelFromJsonFile("assets/TestBig.json");
        TowerDefenceGame.theGame.getRenderer().getLayerByName("level").addElement(this.level);
        TowerDefenceGame.theGame.getRenderer().getLayerByName("level").addElement(this.level.getPath());
        TowerDefenceGame.theGame.getGuiManager().setActiveGui("ingame");
    }

    public Level getLevel() {
        return this.level;
    }
}
