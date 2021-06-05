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

    private boolean paused = false;
    private long pausedTimeMS = 0L;

    public Game() {
        this.level = new Level().generateFromJsonFile("assets/levels/testbig.json");
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
            this.paused = false;
        } else if (event instanceof WindowMoveEvent) {
            this.paused = true;
        } else if (event instanceof PostUpdateEvent) {
//            this.pausedTimeMS = 0L;
        }
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
}
