package de.marc.towerDefenceGame.games;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.PostUpdateEvent;
import de.marc.towerDefenceGame.event.events.UnPausedPostUpdateEvent;
import de.marc.towerDefenceGame.event.events.WindowMoveEvent;
import de.marc.towerDefenceGame.level.Level;

public class Game implements Listener {

    private final Level level;

    private boolean shouldAutoUnpause = true;

    public Game(String levelFileName) {
        this.level = new Level().generateFromJsonFile(levelFileName);
        TowerDefenceGame.theGame.getPlayer().setActiveTool(0);
        this.resume();
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof UnPausedPostUpdateEvent) {
            if (this.shouldAutoUnpause) {
                TowerDefenceGame.theGame.getSettings().isGamePaused = false;
            }
        } else if (event instanceof WindowMoveEvent) {
            TowerDefenceGame.theGame.getSettings().isGamePaused = true;
//            this.paused = true;
            this.shouldAutoUnpause = true;
        }  //            this.pausedTimeMS = 0L;

        if (!TowerDefenceGame.theGame.getSettings().isGamePaused) {
            TowerDefenceGame.theGame.getPlayer().onEvent(event);
            this.level.onEvent(event);
        }
    }

    public void end() {
        TowerDefenceGame.theGame.getGuiManager().setActiveGui("defeat");
        TowerDefenceGame.theGame.getSettings().isGamePaused = true;
//        TowerDefenceGame.theGame.getRenderer().getLayerByName("level").removeElement(this.level);
//        TowerDefenceGame.theGame.getRenderer().getLayerByName("level").removeElement(this.level.getPath());
        TowerDefenceGame.theGame.getEventManager().removeGameListener(this);
    }

    public Level getLevel() {
        return this.level;
    }

    public void unpause() {
        TowerDefenceGame.theGame.getSettings().isGamePaused = false;
        this.shouldAutoUnpause = true;
    }

    public void pause() {
        TowerDefenceGame.theGame.getSettings().isGamePaused = true;
        this.shouldAutoUnpause = false;
    }

    public void resume() {
        TowerDefenceGame.theGame.getRenderer().getLayerByName("level").addElement(this.level);
        TowerDefenceGame.theGame.getRenderer().getLayerByName("level").addElement(this.level.getPath());
        TowerDefenceGame.theGame.getPlayer().setPos(this.level.getMiddlePos());
        TowerDefenceGame.theGame.getGuiManager().setActiveGui("ingame");
        TowerDefenceGame.theGame.getEventManager().addGameListener(this);
    }
}
