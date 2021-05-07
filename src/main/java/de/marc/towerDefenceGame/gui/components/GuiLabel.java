package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiLabel extends GuiComponent {

    private String text;
    private Color color;

    public GuiLabel(String text, Vector2 pos, Color color) {
        super(pos);
        this.text = text;
        this.color = color;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void render() {
        TowerDefenceGame.theGame.getFontRenderer().drawString(this.text, this.pos, 2, this.color);
    }

    @Override
    public void onEvent(Event event) {

    }
}
