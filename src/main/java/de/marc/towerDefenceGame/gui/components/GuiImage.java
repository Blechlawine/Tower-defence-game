package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.texture.Texture;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiImage extends GuiComponent {

    private double width, height, imageWidth, imageHeight, finalWidth, finalHeight;
    private String imageTexHandle;
    private Color backgroundColor;
    private boolean transparentBackground = false;

    public GuiImage(String imageTexHandle, Vector2 pos, double width, double height, Color backgroundColor, boolean transparentBackground) {
        super(pos);
        this.width = width;
        this.height = height;
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
        if (!transparentBackground) GLUtils.drawRect(this.pos.getX(), this.pos.getY(), this.width, this.height, this.backgroundColor);
        GLUtils.drawTexturedRect(this.pos.getX(), this.pos.getY(), this.finalWidth, this.finalHeight, 0, 0, 1, 1, this.imageTexHandle, new Color(1, 1, 1));
    }
}
