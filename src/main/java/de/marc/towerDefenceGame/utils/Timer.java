package de.marc.towerDefenceGame.utils;

public class Timer {

    private long milliseconds;
    private long lastMilliseconds;

    public final long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public final boolean hasReached(long ms) {
        return this.getTime() - this.lastMilliseconds >= ms;
    }

    public final void reset() {
        this.lastMilliseconds = this.getTime();
    }

}
