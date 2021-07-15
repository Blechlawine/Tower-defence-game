package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Vector2;

public abstract class GuiShape extends GuiComponent {

    protected Color color;

    public GuiShape(Vector2 pos, double width, double height, Color color) {
        super(pos);
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public abstract void render();
}
