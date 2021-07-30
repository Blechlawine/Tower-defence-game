package de.marc.towerDefenceGame.utils;


import java.util.HashMap;

public abstract class MapManager<K, V> {

    protected final HashMap<K, V> content;

    public MapManager() {
        this.content = new HashMap<K, V>();
    }

    public final HashMap<K, V> getContent() {
        return this.content;
    }

    public abstract void setup();

}
