package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;

public class GuiLabel extends GuiComponent {

    private String text;
    private float[] color;

    public GuiLabel(String text, double x, double y, float[] color) {
        super(x, y);
        this.text = text;
        this.color = color;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void render() {
        TowerDefenceGame.theGame.getFontRenderer().drawString(this.text, this.xPos, this.yPos, 2, this.color);
    }

    @Override
    public void onEvent(Event event) {

    }
}