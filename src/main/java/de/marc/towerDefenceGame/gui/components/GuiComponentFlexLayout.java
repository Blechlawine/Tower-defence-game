package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiLayoutComponent;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;

public class GuiComponentFlexLayout extends GuiLayoutComponent {

    private FlexDirection flexDirection;
    private FlexDistribution flexDistribution;
    private FlexAlignment flexAlignment;
    private double gap;

    public GuiComponentFlexLayout(Vector2 relativePos,
                                  GuiComponent parent,
                                  double width,
                                  double height,
                                  boolean scrollable,
                                  FlexDirection flexDirection,
                                  FlexAlignment flexAlignment,
                                  FlexDistribution flexDistribution,
                                  double gap
                                  ) {
        super(relativePos, parent, width, height, scrollable);
        this.flexDirection = flexDirection;
        this.flexAlignment = flexAlignment;
        this.flexDistribution = flexDistribution;
        this.gap = gap;
    }

    @Override
    public void updateContent(ArrayList<GuiComponent> content) {
        super.updateContent(content);
        int numContent = this.content.size();
        double prevElementPos = 0;
        double prevElementSize = 0;
        if (flexDirection == FlexDirection.HORIZONTAL) {
            if (this.flexDistribution == FlexDistribution.END) {
                prevElementPos = this.getAbsolutePos().getX() + this.width;
            } else if (this.flexDistribution == FlexDistribution.CENTER) {
                double contentWidth = 0;
                for(GuiComponent c : this.content) {
                    contentWidth += c.getWidth();
                }
                prevElementPos = this.getAbsolutePos().getX() + (this.width - contentWidth + this.gap * numContent - 1) / 2;
            }
            for (int i = 0; i < numContent; i++) {
                GuiComponent c;
                Vector2 temp;
                if (this.flexDistribution == FlexDistribution.END) {
                    c = this.content.get(numContent - i - 1);
                    temp = new Vector2(prevElementPos - c.getWidth() - this.gap + c.getRelativePos().getX(), c.getRelativePos().getY());
                } else {
                    c = this.content.get(i);
                    temp = new Vector2(c.getRelativePos().getX() + prevElementPos + prevElementSize + this.gap * Math.min(1, i), c.getRelativePos().getY());
                }
                if (this.flexAlignment == FlexAlignment.CENTER) {
                    temp.add(0, this.height / 2 - c.getHeight() / 2);
                } else if (this.flexAlignment == FlexAlignment.END) {
                    temp.add(0, this.height - c.getHeight());
                }
                c.setRelativePos(temp);
                prevElementPos = temp.getX();
                prevElementSize = c.getWidth();
            }
        } else if (flexDirection == FlexDirection.VERTICAL) {
            if (this.flexDistribution == FlexDistribution.END) {
                prevElementPos = this.getAbsolutePos().getY() + this.height;
            } else if (this.flexDistribution == FlexDistribution.CENTER) {
                double contentHeight = 0;
                for(GuiComponent c : this.content) {
                    contentHeight += c.getHeight();
                }
                prevElementPos = this.getAbsolutePos().getY() + (this.height - contentHeight + this.gap * numContent - 1) / 2;
            }
            for (int i = 0; i < numContent; i++) {
                GuiComponent c;
                Vector2 temp;
                if (this.flexDistribution == FlexDistribution.END) {
                    c = this.content.get(numContent - i - 1);
                    temp = new Vector2(c.getRelativePos().getX(), prevElementPos - c.getHeight() - this.gap + c.getRelativePos().getY());
                } else {
                    c = this.content.get(i);
                    temp = new Vector2(c.getRelativePos().getX(), c.getRelativePos().getY() + prevElementPos + prevElementSize + this.gap * Math.min(1, i));
                }
                if (this.flexAlignment == FlexAlignment.CENTER) {
                    temp.add(this.width / 2 - c.getWidth() / 2, 0);
                } else if (this.flexAlignment == FlexAlignment.END) {
                    temp.add(this.width - c.getWidth(), 0);
                }
                c.setRelativePos(temp);
                prevElementPos = temp.getY();
                prevElementSize = c.getHeight();
            }
        }
    }

    public enum FlexDirection {
        HORIZONTAL, VERTICAL
    }

    public enum FlexDistribution {
        CENTER, START, END
    }

    public enum FlexAlignment {
        CENTER, START, END
    }
}
