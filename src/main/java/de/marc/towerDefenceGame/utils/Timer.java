package de.marc.towerDefenceGame.utils;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.PreUpdateEvent;

public class Timer implements Listener {

    private long lastMS, ms;

    public Timer(boolean autostart) {
        if (autostart) {
            this.start();
        }
    }

    public void start() {
        TowerDefenceGame.theGame.getEventManager().addGeneralListener(this);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof PreUpdateEvent) {
            PreUpdateEvent pue = (PreUpdateEvent) event;
            this.ms += pue.partialMS;
        }
    }

    public long getCountdown(long ms) {
        return this.lastMS + ms - this.ms;
    }

    public boolean hasReached(long ms) {
        return this.ms >= this.lastMS + ms;
    }

    public void reset() {
        this.lastMS = this.ms;
    }

    public void destroy() {
        TowerDefenceGame.theGame.getEventManager().removeGeneralListener(this);
    }
}
