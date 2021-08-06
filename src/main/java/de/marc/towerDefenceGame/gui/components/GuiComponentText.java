package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;

public class GuiComponentText extends GuiComponent {

    private String[] lines;
    private double fontSize, lineHeight;
    private TextAlignment alignment;
    private Color color;

    public GuiComponentText(String text, GuiComponent parent) {
        this(
                new Vector2(0, 0),
                parent,
                TowerDefenceGame.theGame.getFontRenderer().getRenderedStringWidth(text, 2),
                TowerDefenceGame.theGame.getFontRenderer().getCharHeight(2),
                text,
                2,
                1.3,
                GuiComponentText.TextAlignment.CENTER
        );
    }

    public GuiComponentText(String text, Vector2 relativePos, GuiComponent parent) {
        this(
                relativePos,
                parent,
                TowerDefenceGame.theGame.getFontRenderer().getRenderedStringWidth(text, 2),
                TowerDefenceGame.theGame.getFontRenderer().getCharHeight(2),
                text,
                2,
                1.3,
                GuiComponentText.TextAlignment.CENTER
        );
    }

    public GuiComponentText(Vector2 relativePos,
                            GuiComponent parent,
                            double width,
                            double height,
                            String text,
                            double fontSize,
                            double lineHeight,
                            TextAlignment alignment
    ) {
        this(relativePos, parent, width, height, text.split("\n"), fontSize, lineHeight, alignment);
    }

    public GuiComponentText(Vector2 relativePos,
                            GuiComponent parent,
                            double width,
                            double height,
                            String[] lines,
                            double fontSize,
                            double lineHeight,
                            TextAlignment alignment
    ) {
        super(relativePos, parent, width, height);
        this.alignment = alignment;
        this.fontSize = fontSize;
        this.lineHeight = lineHeight;
        this.color = new Color(Colors.TEXT);
        this.setLines(lines);
    }

    private ArrayList<String> splitLineToWidth(String line) {
//        this.game.getLogger().debug(line);
        double lineWidth = this.game.getFontRenderer().getRenderedStringWidth(line, this.fontSize);
        int maxCharAmount = this.game.getFontRenderer().getCharAmountForWidth(this.width, this.fontSize);
        ArrayList<String> result = new ArrayList<>();
        if (lineWidth <= this.width || line.length() <= maxCharAmount || (line.length() > maxCharAmount && !line.contains(" "))) {
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

    public void setText(String text) {
        this.setLines(text.split("\n"));
    }

    public void setLines(String[] lines) {
        ArrayList<String> tempLines = new ArrayList<>();
        for(String line : lines) {
            tempLines.addAll(this.splitLineToWidth(line));
        }
        this.lines = tempLines.toArray(new String[0]);
        this.height = this.lines.length * this.lineHeight * this.game.getFontRenderer().getCharHeight(this.fontSize);
    }

    @Override
    public void render() {
        if (this.visible) {
            double yPos = this.getAbsolutePos().getY();
            for (String line : this.lines) {
                double xPos = this.getAbsolutePos().getX();
                double lineWidth = this.game.getFontRenderer().getRenderedStringWidth(line, this.fontSize);
                if (this.alignment == TextAlignment.CENTER) {
                    if (this.width != lineWidth) {
                        xPos += this.width / 2 - lineWidth / 2;
                    } else {
                        xPos -= lineWidth / 2;
                    }
                } else if (this.alignment == TextAlignment.RIGHT) {
                    xPos -= lineWidth;
                }
                this.game.getFontRenderer().drawString(line, new Vector2(xPos, yPos), this.fontSize, this.color);
                yPos += this.game.getFontRenderer().getCharHeight(this.fontSize) * this.lineHeight;
            }
        }
    }

    @Override
    public void onEvent(Event event) {}

    public void setAlignment(TextAlignment alignment) {
        this.alignment = alignment;
    }

    public enum TextAlignment {
        CENTER, LEFT, RIGHT
    }
}
