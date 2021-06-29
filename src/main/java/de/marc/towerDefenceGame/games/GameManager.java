package de.marc.towerDefenceGame.games;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.ListManager;

public class GameManager extends ListManager<Game> {

    private Game currentGame = null;

    @Override
    public void setup() {

    }

    public void startNewGame(String levelName) {
        if (this.currentGame == null)
            this.currentGame = new Game(TowerDefenceGame.theGame.getLevelFileManager().getLevelFileByName(levelName));
    }

    public Game getCurrentGame() {
        return this.currentGame;
    }

    public void setCurrentGame(Game game) {
        this.currentGame = game;
    }
}
