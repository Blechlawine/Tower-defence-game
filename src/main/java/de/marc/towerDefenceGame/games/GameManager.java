package de.marc.towerDefenceGame.games;

import de.marc.towerDefenceGame.utils.ListManager;

public class GameManager extends ListManager<Game> {

    private Game currentGame = null;

    @Override
    public void setup() {

    }

    public void startNewGame() {
        this.currentGame = new Game();
    }

    public Game getCurrentGame() {
        return this.currentGame;
    }

}
