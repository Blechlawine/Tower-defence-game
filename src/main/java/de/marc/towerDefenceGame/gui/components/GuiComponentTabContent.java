package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiLayoutComponent;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiComponentTabContent extends GuiComponent {

    private GuiLayoutComponent layout;
    private boolean visible = false;
    private String title;

    public GuiComponentTabContent(Vector2 relativePos,
                                  GuiComponent parent,
                                  double width,
                                  double height,
                                  String title,
                                  GuiLayoutComponent layout
    ) {
        super(relativePos, parent, width, height);
        this.title = title;
        this.layout = layout;
        this.layout.setParent(this);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void onEvent(Event event) {
        if(this.visible) {
            layout.onEvent(event);
        }
    }

    @Override
    public void render() {
        if (this.visible) {
            GLUtils.drawRect(this.getAbsolutePos().getX(), this.getAbsolutePos().getY(), this.width, this.height, new Color(Colors.BACKGROUND));
            this.layout.render();
            GLUtils.drawRect(this.getAbsolutePos().getX(), this.getAbsolutePos().getY(), this.width, 40, new Color(Colors.BACKGROUND));
            this.game.getFontRenderer().drawString(this.title, new Vector2(this.getAbsolutePos()).add(new Vector2(10, 10)), 3, new Color(Colors.TEXT));
        }
    }
}
