package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.utils.Keybinding;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

public class GuiFlexLayout extends GuiComponent {

    private GuiComponent[] content;
    private String flexDirection, alignItems, justifyContent;
    private double gap;
    private Keybinding interactBinding;

    public GuiFlexLayout(Vector2 pos, double width, double height, String flexDirection, String alignItems, String justifyContent, double gap, GuiComponent[] content) {
        this(pos, width, height, flexDirection, alignItems, justifyContent, gap, content, null);
    }

    public GuiFlexLayout(Vector2 pos, double width, double height, String flexDirection, String alignItems, String justifyContent, double gap, GuiComponent[] content, Keybinding interactBinding) {
        super(pos);
        this.content = content;
        this.width = width;
        this.height = height;
        this.flexDirection = flexDirection;
        this.alignItems = alignItems;
        this.justifyContent = justifyContent;
        this.interactBinding = interactBinding;
        int numContent = this.content.length;
        if (gap >= 0) {
            this.gap = gap;
        } else {
            if (justifyContent.equals("spaceBetween")) {
                if (flexDirection.equals("row")) {
                    double contentWidth = 0;
                    for (GuiComponent c : this.content) {
                        contentWidth += c.getWidth();
                    }
                    this.gap = (this.width - contentWidth) / (numContent - 1);
                } else if (flexDirection.equals("column")) {
                    double contentHeight = 0;
                    for (GuiComponent c : this.content) {
                        contentHeight += c.getHeight();
                    }
                    this.gap = (this.height - contentHeight) / (numContent - 1);
                }
            } else {
                this.gap = 0;
            }
        }

        // Position and size elements
        double prevElementPos = 0;
        double prevElementSize = 0;
        if (flexDirection.equals("row")) {
            for (int i = 0; i < numContent; i++) {
                GuiComponent c = this.content[i];
                Vector2 temp = new Vector2(c.pos.getX() + prevElementPos + prevElementSize + this.gap * i, c.pos.getY());
                c.setPos(temp);
                prevElementPos = temp.getX();
                prevElementSize = c.getWidth();
            }
        } else if (flexDirection.equals("column")) {
            for (int i = 0; i < numContent; i++) {
                GuiComponent c = this.content[i];
                Vector2 temp = new Vector2(c.pos.getX(), c.pos.getY() + prevElementPos + prevElementSize + this.gap * i);
                c.setPos(temp);
                prevElementPos = temp.getY();
                prevElementSize = c.getHeight();
            }
        }
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (this.interactBinding != null) {
            if (this.hovered) {
                this.interactBinding.onEvent(event);
            }
        }
        for (GuiComponent c : this.content) {
            c.onEvent(event);
        }
    }

    @Override
    public void render() {
        GL11.glPushMatrix();
        GL11.glTranslated(this.pos.getX(), this.pos.getY(), 0);
        for (GuiComponent c : this.content) {
            c.render();
        }
        GL11.glPopMatrix();
    }
}
