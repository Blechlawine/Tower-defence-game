package de.marc.towerDefenceGame.gui;

import de.marc.towerDefenceGame.utils.GLUtils;
import org.lwjgl.opengl.GL11;

public class FontRenderer {

    private final int charWidth = 5;
    private final int charHeight = 7;
    private final int charGap = 1;
    private final int textureWidthInCharacters = 10, textureHeightInCharacters = 10;

    private final double uvCharWidth, uvCharHeight;

    public FontRenderer() {
        this.uvCharWidth = (1D / ((this.textureWidthInCharacters * this.charWidth) + ((this.textureWidthInCharacters - 1) * this.charGap))) * this.charWidth;
        this.uvCharHeight = (1D / ((this.textureHeightInCharacters * this.charHeight) + ((this.textureHeightInCharacters - 1) * this.charGap))) * this.charHeight;
    }

    public void drawString(String text, double x, double y, double sizeMultiplier, float[] color) {
        GL11.glPushMatrix();
        GL11.glScaled(sizeMultiplier, sizeMultiplier, 1);
        double charXPos = x;
        char[] characters = text.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int ascii = characters[i];
            int index = ascii - 32;
            if (index != 0) { // if index is 0, then its a space
                int uIndex = index % 10 - 1;
                int vIndex = index / 10;
                double u = uIndex * this.charWidth + uIndex;
                double v = vIndex * this.charWidth;
                GLUtils.drawTexturedRect(charXPos, y, this.charWidth, this.charHeight, u, v, this.uvCharWidth, this.uvCharHeight, "font", color);
            }
            charXPos += this.charWidth + this.charGap;
        }
        GL11.glPopMatrix();
    }

}
