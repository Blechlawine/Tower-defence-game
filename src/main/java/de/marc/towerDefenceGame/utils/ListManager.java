package de.marc.towerDefenceGame.utils;

import java.util.ArrayList;

public abstract class ListManager<T> {

    protected ArrayList<T> content;

    public ListManager() {
        this.content = new ArrayList<T>();
    }

    public final ArrayList<T> getContent() {
        return this.content;
    }

    public abstract void setup();

}
