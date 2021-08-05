package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.texture.Texture;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiComponentImage extends GuiComponent {

    private double imageWidth, imageHeight, finalWidth, finalHeight;
    private String imageTexHandle;
    private Color backgroundColor;
    private boolean transparentBackground;

    public GuiComponentImage(Vector2 relativePos,
                             GuiComponent parent,
                             double width,
                             double height,
                             String imageTexHandle,
                             Color backgroundColor,
                             boolean transparentBackground
    ) {
        super(relativePos, parent, width, height);
        this.imageTexHandle = imageTexHandle;
        this.backgroundColor = backgroundColor;
        this.transparentBackground = transparentBackground;
        Texture temp = TowerDefenceGame.theGame.getTextureManager().getTextureFromName(this.imageTexHandle);
        this.imageHeight = temp.getHeight();
        this.imageWidth = temp.getWidth();
        if (this.imageWidth == this.imageHeight) {
            this.finalWidth = this.width;
            this.finalHeight = this.height;
        } else if (this.imageWidth > this.imageHeight) {
            this.finalWidth = this.width;
            this.finalHeight = this.height / this.imageWidth * this.imageHeight;
        } else if (this.imageHeight > this.imageWidth) {
            this.finalHeight = this.height;
            this.finalWidth = this.width / this.imageHeight * this.imageWidth;
        }
    }

    @Override
    public void render() {
        if (!transparentBackground) GLUtils.drawRect(this.getAbsolutePos().getX(), this.getAbsolutePos().getY(), this.width, this.height, this.backgroundColor);
        GLUtils.drawTexturedRect(this.getAbsolutePos().getX(),
                this.getAbsolutePos().getY(),
                this.finalWidth,
                this.finalHeight,
                0,
                0,
                1,
                1,
                this.imageTexHandle,
                new Color(1, 1, 1));
    }

    @Override
    public void onEvent(Event event) {}
}
