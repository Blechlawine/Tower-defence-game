package de.marc.towerDefenceGame.utils;

import java.util.ArrayList;
import java.util.List;

public abstract class ListManager<T> {

    protected ArrayList<T> content;

    public final ArrayList<T> getContent() {
        return this.content;
    }

    public abstract void setup();

}
