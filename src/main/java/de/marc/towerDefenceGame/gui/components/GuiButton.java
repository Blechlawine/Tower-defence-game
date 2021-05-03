package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.MouseButtonEvent;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;
import static de.marc.towerDefenceGame.utils.KeyAction.UP;

public abstract class GuiButton extends GuiComponent {

    private GuiComponent content;
    protected double width, height;
    protected Color color, initialColor;

    private byte state; // 0 == normal; 1 == hovered; 2 == pressed;

    public GuiButton(GuiComponent content, double xPos, double yPos, double width, double height, Color color) {
        super(xPos, yPos);
        this.content = content;
        this.width = width;
        this.height = height;

        this.initialColor = color;
        this.color = color;
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
                    }
                }
            } else if (this.state == 2) { // this button is pressed
                if (e.getButton() == 0 && e.getAction() == UP) {
                    this.state = 1;
                }
            }
        } else if (event instanceof MouseMoveEvent) {
            double clickXPos = MouseMoveEvent.getAbsoluteX();
            double clickYPos = MouseMoveEvent.getAbsoluteY();
            if (clickXPos >= this.xPos && clickXPos < this.xPos + this.width && clickYPos >= this.yPos && clickYPos < this.yPos + this.height) {
                // Mouse over
                if (this.state == 0) {
                    // Mouse in
                    this.state = 1;
                }
            } else {
                // Mouse out
                this.state = 0;
            }
        }
    }

    @Override
    public void render() {
        GLUtils.drawRect(this.xPos, this.yPos, this.width, this.height, this.color);
        this.content.render();
    }
}
