package de.marc.towerDefenceGame.texture;

public class Texture {

    private int id;
    private String name;
    private int width, height;

    public Texture(int id, String name, int width, int height) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.id;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
