package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.MouseButtonEvent;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;
import static de.marc.towerDefenceGame.utils.KeyAction.UP;

public abstract class GuiButton extends GuiComponent {

    private GuiComponent content;
    protected Color color, initialColor, hoverColor;

    private byte state; // 0 == normal; 1 == hovered; 2 == pressed;

    public GuiButton(GuiComponent content, Vector2 pos, double width, double height, Color color, Color hoverColor) {
        super(pos);
        this.content = content;
        this.width = width;
        this.height = height;

        this.initialColor = color;
        this.color = color;
        this.hoverColor = hoverColor;
    }

    public abstract void onClick();

    @Override
    public void onEvent(Event event) {
        if (event instanceof MouseButtonEvent) {
            MouseButtonEvent e = (MouseButtonEvent) event;
            if (this.state == 1) { // this button is hovered
                if (e.getButton() == 0) {
                    if (e.getAction() == DOWN) {
                        this.state = 2;
                        this.onClick();
                        event.cancel();
                    }
                }
            } else if (this.state == 2) { // this button is pressed
                if (e.getButton() == 0 && e.getAction() == UP) {
                    this.state = 1;
                }
            }
        }
        super.onEvent(event);
    }

    protected void onMouseIn() {
        this.state = 1;
        this.color = this.hoverColor;
    }
    protected void onMouseOut() {
        this.state = 0;
        this.color = this.initialColor;
    }

    @Override
    public void render() {
        GLUtils.drawRect(this.pos.getX(), this.pos.getY(), this.width, this.height, this.color);
        GL11.glPushMatrix();
        GL11.glTranslated(this.pos.getX() + (this.width / 2) - (this.content.getWidth() / 2), this.pos.getY() + (this.height / 2) - (this.content.getHeight() / 2), 0);
        this.content.render();
        GL11.glPopMatrix();
    }
}
