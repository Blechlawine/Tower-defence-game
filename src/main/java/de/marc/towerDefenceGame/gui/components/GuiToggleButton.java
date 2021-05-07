package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Vector2;

public abstract class GuiToggleButton extends GuiButton {

    private boolean toggled;

    public GuiToggleButton(GuiComponent content, Vector2 pos, double width, double height, Color baseColor) {
        super(content, pos, width, height, baseColor);
        this.toggled = false;
    }

    public abstract void onActivate();
    public abstract void onDeactivate();

    @Override
    public void onClick() {
        if (this.toggled) {
            this.deactivate();
        } else {
            this.activate();
        }
    }

    public void deactivate() {
        this.onDeactivate();
        this.toggled = false;
        this.color = this.initialColor;
    }

    public void activate() {
        this.onActivate();
        this.toggled = true;
        this.color = new Color("#FF0000");
    }
}
