package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.utils.*;
import org.lwjgl.opengl.GL11;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;
import static de.marc.towerDefenceGame.utils.KeyAction.UP;
import static de.marc.towerDefenceGame.utils.Settings.KeyBindings.GUI_INTERACT;

public abstract class GuiButton extends GuiComponent {

    private GuiComponent content;
    protected Color color, initialColor, hoverColor;

    private Keybinding pressBinding;

    private byte state; // 0 == normal; 1 == hovered; 2 == pressed;

    public GuiButton(GuiComponent content, Vector2 pos, double width, double height, Color color, Color hoverColor) {
        super(pos);
        this.content = content;
        this.width = width;
        this.height = height;

        this.initialColor = color;
        this.color = color;
        this.hoverColor = hoverColor;
        this.pressBinding = new Keybinding(GUI_INTERACT, new KeyAction[] {DOWN, UP}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (state == 1) { // this button is hovered
                    if (action == DOWN) {
                        state = 2;
                        onClick();
                        event.cancel();
                    }
                } else if (state == 2) { // this button is pressed
                    if (action == UP) {
                        state = 1;
                    }
                }
            }
        };
    }

    public abstract void onClick();

    @Override
    public void onEvent(Event event) {
        this.pressBinding.onEvent(event);
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
