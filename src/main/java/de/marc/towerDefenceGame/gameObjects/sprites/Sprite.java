package de.marc.towerDefenceGame.gameObjects.sprites;

import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Renderable;
import de.marc.towerDefenceGame.utils.Vector2;

public class Sprite implements Renderable {

    private Vector2 pos;
    private String textureName;
    private double width, height;

    public Sprite(Vector2 pos, double width, double height, String textureName) {
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.textureName = textureName;
    }

    @Override
    public void render() {
        GLUtils.drawTexturedRect(this.pos.getX(), this.pos.getY(), this.width, this.height, 0, 0, 1, 1, this.textureName, new Color(1, 1, 1));
    }
}
