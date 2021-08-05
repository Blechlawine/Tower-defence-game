package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiInteractableComponent;
import de.marc.towerDefenceGame.utils.*;
import org.lwjgl.opengl.GL11;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;
import static de.marc.towerDefenceGame.utils.KeyAction.UP;
import static de.marc.towerDefenceGame.utils.Settings.KeyBindings.GUI_INTERACT;

public abstract class GuiComponentDropDown extends GuiInteractableComponent {

    private String[] values;
    private String value;
    private double initialHeight, openHeight;
    protected Color color, initialColor, hoverColor;

    private boolean open;
    private byte state; // 0 == normal; 1 == hovered; 2 == pressed;
    private Keybinding pressBinding;
    private int hoveredValueIndex;

    public GuiComponentDropDown(Vector2 relativePos,
                                GuiComponent parent,
                                double width,
                                double height,
                                String[] values,
                                String value
    ) {
        super(relativePos, parent, width, height);
        this.values = values;
        this.initialHeight = height;
        this.openHeight = this.initialHeight * values.length + this.initialHeight;
        this.value = value;

        this.state = 0;
        this.hoveredValueIndex = -1;

        this.open = false;
        this.initialColor = new Color(Colors.BUTTONPRIMARY);
        this.color = this.initialColor;
        this.hoverColor = new Color(Colors.BUTTONPRIMARYHOVER);
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
                    event.cancel();
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
    protected void onMouseOver(double mouseX, double mouseY) {
        this.hoveredValueIndex = (int)Math.round(mouseY - this.getAbsolutePos().getY()) / (int)this.initialHeight - 1;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void render() {
        if (this.visible) {
            double padding = (this.initialHeight - TowerDefenceGame.theGame.getFontRenderer().getCharHeight(2)) / 2;
            GLUtils.drawRect(this.getAbsolutePos().getX(), this.getAbsolutePos().getY(), this.width, this.initialHeight, this.color);
            TowerDefenceGame.theGame.getFontRenderer().drawString(this.value, new Vector2(this.getAbsolutePos()).add(new Vector2(padding, padding)), 2, new Color(Colors.TEXT));
            double iconSize = TowerDefenceGame.theGame.getFontRenderer().getCharHeight(2) * 3;
            GL11.glPushMatrix();
            GLUtils.rotateAroundLocation((this.open ? 180 : 0), new Vector2(this.getAbsolutePos().getX() + this.width - iconSize / 2, this.getAbsolutePos().getY() + iconSize / 2));
            GLUtils.drawTexturedRect(this.getAbsolutePos().getX() + this.width - iconSize, this.getAbsolutePos().getY(), iconSize, iconSize, 0, 0, 1, 1, "dropdownarrow", new Color(Colors.TEXT));
            GL11.glPopMatrix();
            // draw content
            GL11.glPushMatrix();
            GL11.glTranslated(this.getAbsolutePos().getX(), this.getAbsolutePos().getY() + this.initialHeight, 0);
            if (this.open) {
                for (int i = 0; i < this.values.length; i++) {
                    Vector2 tempPos = new Vector2(0, 0).add(new Vector2(0, 0 + this.initialHeight * i));
                    GLUtils.drawRect(tempPos.getX(), tempPos.getY(), this.width, this.initialHeight, (this.hoveredValueIndex == i ? this.hoverColor : this.initialColor));
                    TowerDefenceGame.theGame.getFontRenderer().drawString(this.values[i], new Vector2(tempPos).add(new Vector2(padding, padding)), 2, new Color(Colors.TEXT));
                }
            }
            GL11.glPopMatrix();
        }
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        this.pressBinding.onEvent(event);
    }
}
