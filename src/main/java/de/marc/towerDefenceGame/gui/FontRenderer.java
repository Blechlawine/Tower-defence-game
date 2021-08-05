package de.marc.towerDefenceGame.gui;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.texture.Texture;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

public class FontRenderer {

    private final int charWidth = 5;
    private final int charHeight = 7;
    private final int charGap = 1;
    private final int textureWidthInCharacters = 10, textureHeightInCharacters = 10;

    private Texture texture;

    private final double uvCharWidth, uvCharHeight;

    public FontRenderer() {
        this.uvCharWidth = (1D / ((this.textureWidthInCharacters * this.charWidth) + ((this.textureWidthInCharacters - 1) * this.charGap))) * this.charWidth;
        this.uvCharHeight = (1D / ((this.textureHeightInCharacters * this.charHeight) + ((this.textureHeightInCharacters - 1) * this.charGap))) * this.charHeight;
        this.texture = TowerDefenceGame.theGame.getTextureManager().getTextureFromName("font");
    }

    public void drawString(String text, Vector2 pos, double sizeMultiplier, Color color) {
        double x = pos.getX() / sizeMultiplier;
        double y = pos.getY() / sizeMultiplier;
        GL11.glPushMatrix();
        GL11.glScaled(sizeMultiplier, sizeMultiplier, 1);
        double charXPos = x;
        char[] characters = text.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int ascii = characters[i];
            int index = ascii - 32;
            if (index != 0) { // if index is 0, then its a space
                index -= 1;
                int uIndex = index % 10;
                int vIndex = index / 10;
                double u = (double)(uIndex * this.charWidth + uIndex) / this.texture.getWidth();
                double v = (double)(vIndex * this.charHeight + vIndex) / this.texture.getHeight();
                GLUtils.drawTexturedRect(charXPos, y, this.charWidth, this.charHeight, u, v, this.uvCharWidth, this.uvCharHeight, "font", color); // Irgendwas stimmt hier nicht
            }
            charXPos += this.charWidth + this.charGap;
        }
        GL11.glPopMatrix();
    }

    public double getRenderedStringWidth(String text, double sizeMultiplier) {
        int stringlength = text.toCharArray().length;
        if (stringlength == 0) {
            return 0;
        }
        return (stringlength * this.charWidth + (stringlength - 1) * this.charGap) * sizeMultiplier;
    }

    public int getCharAmountForWidth(double width, double sizeMultiplier) {
        return (int)Math.floor(width / ((this.charWidth + this.charGap) * sizeMultiplier));
    }

    public double getCharHeight(double sizeMultiplier) {
        return this.charHeight * sizeMultiplier;
    }

    public void drawCenteredString(String text, Vector2 pos, double sizeMultiplier, Color color) {
        double stringWidth = this.getRenderedStringWidth(text, sizeMultiplier);
        this.drawString(text, new Vector2(pos).subtract(new Vector2(stringWidth / 2, 0)), sizeMultiplier, color);
    }
}
