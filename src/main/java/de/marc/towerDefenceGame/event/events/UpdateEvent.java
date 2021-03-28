package de.marc.towerDefenceGame.event.events;

import de.marc.towerDefenceGame.event.Event;

public class UpdateEvent extends Event {

    public static long lastMS;
    public long partialMS;

    public UpdateEvent(long ms) {
        this.partialMS = ms - lastMS;
    }

}
