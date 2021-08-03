package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;

public class GuiText extends GuiComponent {

    private String[] lines;
    private double lineHeight, size;
    private TextAlignment alignment;
    private Color color;

    public GuiText(String text, Vector2 pos, double width, double size, double lineHeight, TextAlignment alignment) {
        this(text.split("\n"), pos, width, size, lineHeight, alignment);
    }

    public GuiText(String[] lines, Vector2 pos, double width, double size, double lineHeight, TextAlignment alignment) {
        super(pos);
        this.width = width;
        this.size = size;
        this.lineHeight = lineHeight;
        ArrayList<String> tempLines = new ArrayList<>();
        for(String line : lines) {
            tempLines.addAll(this.splitLineToWidth(line));
        }
        this.lines = tempLines.toArray(new String[0]);
        this.alignment = alignment;
        this.color = new Color(Colors.TEXT);
        this.height = this.lines.length * this.size * this.lineHeight;
    }

    private ArrayList<String> splitLineToWidth(String line) {
        double lineWidth = TowerDefenceGame.theGame.getFontRenderer().getRenderedStringWidth(line, this.size);
        int maxCharAmount = TowerDefenceGame.theGame.getFontRenderer().getCharAmountForWidth(this.width, this.size);
        ArrayList<String> result = new ArrayList<>();
        if (lineWidth <= this.width) {
            result.add(line.trim());
        } else {
            int index = maxCharAmount;
            while (index > 0 && line.charAt(index) != ' ') {
                index--;
            }
            result.add(line.substring(0, index).trim());
            result.addAll(this.splitLineToWidth(line.substring(index)));
        }
        return result;
    }

    @Override
    public void render() {
        double yPos = this.pos.getY();
        for (String line : this.lines) {
            double xPos = this.pos.getX();
            double lineWidth = TowerDefenceGame.theGame.getFontRenderer().getRenderedStringWidth(line, this.size);
            if (this.alignment == TextAlignment.CENTER) {
                xPos -= lineWidth / 2;
            } else if (this.alignment == TextAlignment.RIGHT) {
                xPos -= lineWidth;
            }
            TowerDefenceGame.theGame.getFontRenderer().drawString(line, new Vector2(xPos, yPos), this.size, this.color);
            yPos += TowerDefenceGame.theGame.getFontRenderer().getCharHeight(this.size) * this.lineHeight;
        }
    }

    public enum TextAlignment {
        CENTER, LEFT, RIGHT
    }
}