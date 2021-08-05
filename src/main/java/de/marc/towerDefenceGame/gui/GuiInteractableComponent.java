package de.marc.towerDefenceGame.gui;

import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.utils.Vector2;

public abstract class GuiInteractableComponent extends GuiComponent {

    protected boolean hovered = false;

    public GuiInteractableComponent(Vector2 relativePos, GuiComponent parent, double width, double height) {
        super(relativePos, parent, width, height);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MouseMoveEvent && this.visible) {
            MouseMoveEvent mme = (MouseMoveEvent) event;
            double[] guiMousePos = mme.getCameraTransformedPos(this.game.getSettings().guiCamera);
            double clickXPos = guiMousePos[0];
            double clickYPos = guiMousePos[1];
            if (clickXPos >= this.getAbsolutePos().getX() && clickXPos < this.getAbsolutePos().getX() + this.width && clickYPos >= this.getAbsolutePos().getY() && clickYPos < this.getAbsolutePos().getY() + this.height) {
                // Mouse over
                if (!this.hovered) {
                    // Mouse in
                    this.onMouseIn();
                    this.hovered = true;
                }
                this.onMouseOver(clickXPos, clickYPos);
            } else {
                // Mouse out
                this.hovered = false;
                this.onMouseOut();
            }
        }
    }

    protected void onMouseIn() {}
    protected void onMouseOver(double relativeMouseX, double relativeMouseY) {}
    protected void onMouseOut() {}
}
