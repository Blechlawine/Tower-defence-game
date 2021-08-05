package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiInteractableComponent;
import de.marc.towerDefenceGame.utils.*;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;
import static de.marc.towerDefenceGame.utils.KeyAction.UP;

public abstract class GuiComponentSlider extends GuiInteractableComponent {

    private double min, max, value, stepSize, stepNumber;

    private double handlePos, visualStepSize;

    private Keybinding interactBinding;
    private boolean isMouseInteracting = false;

    public GuiComponentSlider(Vector2 relativePos,
                              GuiComponent parent,
                              double width,
                              double height,
                              double min,
                              double max,
                              double value,
                              double stepSize
    ) {
        super(relativePos, parent, width, height);
        this.value = value;
        this.min = min;
        this.max = max;
        this.stepSize = stepSize;
        this.stepNumber = (this.value - this.min) / this.stepSize;
        this.visualStepSize = this.width / ((this.max - this.min) / this.stepSize);
        this.handlePos = this.getAbsolutePos().getX() + this.stepNumber * this.visualStepSize;
        this.interactBinding = new Keybinding(Settings.KeyBindings.GUI_INTERACT, new KeyAction[]{UP, DOWN}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (action == DOWN) {
                    if (hovered) {
                        isMouseInteracting = true;
                    }
                } else if (action == UP) {
                    if (isMouseInteracting) {
                        onChangeEnd(GuiComponentSlider.this.value);
                        isMouseInteracting = false;
                    }
                }
            }
        };
    }

    @Override
    public void setRelativePos(Vector2 pos) {
        super.setRelativePos(pos);
        this.handlePos = this.getAbsolutePos().getX() + this.stepNumber * this.visualStepSize;
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        this.interactBinding.onEvent(event);
        if (event instanceof MouseMoveEvent && this.isMouseInteracting) {
            // calculate Value
            MouseMoveEvent mme = (MouseMoveEvent) event;
            double mouseXPos = mme.getCameraTransformedPos(TowerDefenceGame.theGame.getSettings().guiCamera)[0];
            double relMouseXPos = mouseXPos - this.getAbsolutePos().getX();
            double relMouseXPosLimited = Utils.limitD(relMouseXPos, 0, this.width);
            this.stepNumber = (int)Math.floor(relMouseXPosLimited / this.visualStepSize);
            this.handlePos = this.getAbsolutePos().getX() + this.stepNumber * this.visualStepSize;
            this.value = this.min + this.stepNumber * this.stepSize;
            this.onChange(this.value);
        }
    }

    public void onChange(double value) {}
    public void onChangeEnd(double value) {}

    @Override
    public void render() {
        // left side
        GLUtils.drawTexturedRect(this.getAbsolutePos().getX(), this.getAbsolutePos().getY(), this.height, this.height, 0, 0, 1, 1, "sliderbase", new Color(1, 1, 1));
        // right side
        GLUtils.drawTexturedRect(this.getAbsolutePos().getX() + this.width - this.height, this.getAbsolutePos().getY(), this.height, this.height, 1, 0, -1, 1, "sliderbase", new Color(1, 1, 1));
        // middle part
        GLUtils.drawTexturedRect(this.getAbsolutePos().getX() + this.height, this.getAbsolutePos().getY(), this.width - (this.height*2), this.height, 0.5, 0, 0.5, 1, "sliderbase", new Color(1, 1, 1));

        GLUtils.drawTexturedRect(this.handlePos - this.height / 2, this.getAbsolutePos().getY(), this.height, this.height, 0, 0, 1, 1, "sliderhandle", new Color(1, 1, 1));
    }
}
