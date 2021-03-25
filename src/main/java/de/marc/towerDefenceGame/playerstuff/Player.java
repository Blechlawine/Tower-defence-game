package de.marc.towerDefenceGame.playerstuff;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.KeyEvent;

public class Player implements Listener {

    public Player() {

    }

    public void onEvent(Event event) {
        if (event instanceof KeyEvent) {
            TowerDefenceGame.theGame.getLogger().info("player Key event");
        }
    }
}
