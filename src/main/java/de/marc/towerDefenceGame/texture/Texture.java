package de.marc.towerDefenceGame.texture;

public class Texture {

    private int id;
    private String name;

    public Texture(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public int getID() {
        return this.id;
    }
}
