package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiLayoutComponent;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiComponentFreeLayout extends GuiLayoutComponent {
    public GuiComponentFreeLayout(Vector2 relativePos,
                                  GuiComponent parent,
                                  double width,
                                  double height,
                                  boolean scrollable) {
        super(relativePos, parent, width, height, scrollable);
    }
}
