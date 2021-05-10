package de.marc.towerDefenceGame.event;

public abstract class Event {
    protected boolean canceled;
    public boolean isCancelled() {
        return this.canceled;
    }

    public void cancel() {
        this.canceled = true;
    }
}
