package de.marc.towerDefenceGame.event.events;

import de.marc.towerDefenceGame.event.Event;

public class PreUpdateEvent extends Event {

    public static long lastMS;
    public long partialMS;

    public PreUpdateEvent(long ms) {
        this.partialMS = ms - lastMS;
    }

}
