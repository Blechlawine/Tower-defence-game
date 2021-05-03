package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.utils.Color;

public abstract class GuiToggleButton extends GuiButton {

    private boolean toggled;

    public GuiToggleButton(GuiComponent content, double xPos, double yPos, double width, double height, Color baseColor) {
        super(content, xPos, yPos, width, height, baseColor);
        this.toggled = false;
    }

    public abstract void onActivate();
    public abstract void onDeactivate();

    @Override
    public void onClick() {
        if (this.toggled) {
            this.onDeactivate();
            this.toggled = false;
            this.color = this.initialColor;
        } else {
            this.onActivate();
            this.toggled = true;
            this.color = new Color("#FF0000");
        }
    }
}
