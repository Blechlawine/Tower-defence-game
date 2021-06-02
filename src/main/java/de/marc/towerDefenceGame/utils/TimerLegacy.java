package de.marc.towerDefenceGame.utils;

import de.marc.towerDefenceGame.TowerDefenceGame;

public class TimerLegacy {

    private long milliseconds, lastMilliseconds;

    public TimerLegacy() {
        this.milliseconds = 0L;
        this.lastMilliseconds = 0L;
    }

    public TimerLegacy(long ms) {
        this.milliseconds = ms;
        this.lastMilliseconds = ms;
    }

    public final long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public void updateTime(long ms) {
        this.milliseconds = ms;
    }

    public final boolean hasReached(long ms) {
        return this.milliseconds - this.lastMilliseconds >= ms;
    }

    public final void reset() {
        this.lastMilliseconds = this.milliseconds;
    }

}
