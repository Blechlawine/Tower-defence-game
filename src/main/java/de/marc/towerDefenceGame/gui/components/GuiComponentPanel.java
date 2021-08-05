package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiLayoutComponent;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiComponentPanel extends GuiComponent {

    private boolean hasBackground;
    private GuiLayoutComponent layout;

    public GuiComponentPanel(Vector2 relativePos,
                             GuiComponent parent,
                             double width,
                             double height,
                             GuiLayoutComponent layout,
                             boolean hasBackground) {
        super(relativePos, parent, width, height);
        this.layout = layout;
        this.layout.setParent(this);
        this.hasBackground = hasBackground;
    }

    @Override
    public void onEvent(Event event) {
        if (this.visible) {
            this.layout.onEvent(event);
        }
    }

    @Override
    public void render() {
        if (this.visible) {
            if (this.hasBackground) {
                GLUtils.drawRect(this.getAbsolutePos().getX(), this.getAbsolutePos().getY(), this.width, this.height, new Color(Colors.BACKGROUND));
            }
            this.layout.render();
        }
    }
}
