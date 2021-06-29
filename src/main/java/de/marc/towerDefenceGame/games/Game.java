package de.marc.towerDefenceGame.games;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.PostUpdateEvent;
import de.marc.towerDefenceGame.event.events.UnPausedPostUpdateEvent;
import de.marc.towerDefenceGame.event.events.WindowMoveEvent;
import de.marc.towerDefenceGame.level.Level;

public class Game implements Listener {

    private Level level;

    private boolean paused = false, shouldAutoUnpause = true;
    private long pausedTimeMS = 0L;

    public Game(String levelFileName) {
        this.level = new Level().generateFromJsonFile(levelFileName);
        TowerDefenceGame.theGame.getRenderer().getLayerByName("level").addElement(this.level);
        TowerDefenceGame.theGame.getRenderer().getLayerByName("level").addElement(this.level.getPath());
        TowerDefenceGame.theGame.getGuiManager().setActiveGui("ingame");
        TowerDefenceGame.theGame.getEventManager().addListener(this);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof UnPausedPostUpdateEvent) {
            if (this.isPaused()) {
                UnPausedPostUpdateEvent uppue = (UnPausedPostUpdateEvent) event;
                this.pausedTimeMS += uppue.partialMS;
            }
            if (this.shouldAutoUnpause) {
                this.paused = false;
            }
        } else if (event instanceof WindowMoveEvent) {
            this.paused = true;
            this.shouldAutoUnpause = true;
        } else if (event instanceof PostUpdateEvent) {
//            this.pausedTimeMS = 0L;
        }
    }

    public void end() {
        TowerDefenceGame.theGame.getGuiManager().setActiveGui("defeat");
        this.paused = true;
//        TowerDefenceGame.theGame.getRenderer().getLayerByName("level").removeElement(this.level);
//        TowerDefenceGame.theGame.getRenderer().getLayerByName("level").removeElement(this.level.getPath());
        TowerDefenceGame.theGame.getEventManager().removeListener(this);
    }

    public boolean isPaused() {
        return this.paused;
    }

    public long getPausedTimeMS() {
        return this.pausedTimeMS;
    }

    public Level getLevel() {
        return this.level;
    }

    public void unpause() {
        this.paused = false;
        this.shouldAutoUnpause = true;
    }

    public void pause() {
        this.paused = true;
        this.shouldAutoUnpause = false;
    }
}
