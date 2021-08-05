package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Vector2;

public abstract class GuiComponentShape extends GuiComponent {
    public Color color;
    public GuiComponentShape(Vector2 relativePos,
                             GuiComponent parent,
                             double width,
                             double height,
                             Color color) {
        super(relativePos, parent, width, height);
        this.color = color;
    }

    @Override
    public void onEvent(Event event) {

    }

    public abstract void renderShape();

    @Override
    public void render() {
        this.renderShape();
    }
}
