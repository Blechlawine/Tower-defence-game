package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.utils.*;
import org.lwjgl.opengl.GL11;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;
import static de.marc.towerDefenceGame.utils.KeyAction.UP;
import static de.marc.towerDefenceGame.utils.Settings.KeyBindings.GUI_INTERACT;

public abstract class GuiDropDown extends GuiComponent {

    private String[] values;
    private String defaultValue;
    private String value;
    private double initialHeight, openHeight;
    protected Color color, initialColor, hoverColor;

    private boolean open;
    private byte state; // 0 == normal; 1 == hovered; 2 == pressed;
    private Keybinding pressBinding;
    private int hoveredValueIndex;

    public GuiDropDown(Vector2 pos, double width, double height, String[] values, String defaultValue, Color color, Color hoverColor) {
        super(pos);
        this.values = values;
        this.width = width;
        this.initialHeight = height;
        this.openHeight = this.initialHeight * values.length + this.initialHeight;
        this.defaultValue = defaultValue;
        this.initialColor = color;
        this.hoverColor = hoverColor;

        this.height = this.initialHeight;
        this.color = this.initialColor;
        this.value = this.defaultValue;
        this.state = 0;
        this.open = false;
        this.hoveredValueIndex = -1;

        this.pressBinding = new Keybinding(GUI_INTERACT, new KeyAction[] {DOWN, UP}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (state == 1) { // this button is hovered
                    if (action == DOWN) {
                        if (!open) {
                            state = 2;
                            open();
                        } else {
                            valueClick();
                        }
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

    private void open() {
        this.open = true;
        this.height = this.openHeight;
    }

    private void close() {
        this.open = false;
        this.height = this.initialHeight;
    }

    private void valueClick() {
        if (this.hoveredValueIndex != -1) {
            this.valueChange(this.value, this.values[this.hoveredValueIndex]);
            this.value = this.values[this.hoveredValueIndex];
        }
        close();
    }

    protected abstract void valueChange(String oldValue, String newValue);

    @Override
    protected void onMouseIn() {
        this.state = 1;
        this.color = this.hoverColor;
    }
    @Override
    protected void onMouseOut() {
        this.state = 0;
        this.color = this.initialColor;
        this.close();
    }
    @Override
    protected void onMouseOver(double relativeMouseX, double relativeMouseY) {
        this.hoveredValueIndex = (int)Math.round(relativeMouseY) / (int)this.initialHeight - 1;
    }

    @Override
    public void render() {
        double padding = (this.initialHeight - TowerDefenceGame.theGame.getFontRenderer().getCharHeight(2)) / 2;
        GLUtils.drawRect(this.pos.getX(), this.pos.getY(), this.width, this.initialHeight, this.color);
        TowerDefenceGame.theGame.getFontRenderer().drawString(this.value, new Vector2(this.pos).add(new Vector2(padding, padding)), 2, new Color(Colors.TEXT));
        double iconSize = TowerDefenceGame.theGame.getFontRenderer().getCharHeight(2) * 3;
        GL11.glPushMatrix();
        GLUtils.rotateAroundLocation((this.open ? 180 : 0), new Vector2(this.pos.getX() + this.width - iconSize / 2, this.pos.getY() + iconSize / 2));
        GLUtils.drawTexturedRect(this.pos.getX() + this.width - iconSize, this.pos.getY(), iconSize, iconSize, 0, 0, 1, 1, "dropdownarrow", new Color(Colors.TEXT));
        GL11.glPopMatrix();
        // draw content
        GL11.glPushMatrix();
        GL11.glTranslated(this.pos.getX(), this.pos.getY() + this.initialHeight, 0);
        if (this.open) {
            for (int i = 0; i < this.values.length; i++) {
                Vector2 tempPos = new Vector2(0, 0).add(new Vector2(0, 0 + this.initialHeight * i));
                GLUtils.drawRect(tempPos.getX(), tempPos.getY(), this.width, this.initialHeight, (this.hoveredValueIndex == i ? this.hoverColor : this.initialColor));
                TowerDefenceGame.theGame.getFontRenderer().drawString(this.values[i], new Vector2(tempPos).add(new Vector2(padding, padding)), 2, new Color(Colors.TEXT));
            }
        }
        GL11.glPopMatrix();
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        this.pressBinding.onEvent(event);
    }
}
