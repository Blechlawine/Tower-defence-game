package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;

public class GuiLabel extends GuiComponent {

    private String text;
    private float[] color;

    public GuiLabel(String text, double x, double y, float[] color) {
        this.xPos = x;
        this.yPos = y;
        this.text = text;
        this.color = color;
    }

    @Override
    public void render() {
        TowerDefenceGame.theGame.getFontRenderer().drawString(this.text, this.xPos, this.yPos, 2, this.color);
    }
}
