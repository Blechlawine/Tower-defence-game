package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiLabel extends GuiComponent {

    private String text;
    private Color color;
    private double size;

    public GuiLabel(String text, Color color) {
        this(text, color, 2);
    }

    public GuiLabel(String text, Color color, double size) {
        this(text, new Vector2(0, 0), color, size);
    }

    public GuiLabel(String text, Vector2 pos, Color color) {
        this(text, pos, color, 2);
    }

    public GuiLabel(String text, Vector2 pos, Color color, double size) {
        super(pos);
        this.text = text;
        this.size = size;
        this.width = TowerDefenceGame.theGame.getFontRenderer().getRenderedStringWidth(this.text, this.size);
        this.height = TowerDefenceGame.theGame.getFontRenderer().getCharHeight(this.size);
        this.color = color;
    }

    public void setText(String text) {
        this.text = text;
        this.width = TowerDefenceGame.theGame.getFontRenderer().getRenderedStringWidth(this.text, this.size);
        this.height = TowerDefenceGame.theGame.getFontRenderer().getCharHeight(this.size);
    }

    @Override
    public void render() {
        TowerDefenceGame.theGame.getFontRenderer().drawString(this.text, this.pos, this.size, this.color);
    }
}
