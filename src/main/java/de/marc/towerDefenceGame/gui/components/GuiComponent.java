package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.utils.Renderable;
import de.marc.towerDefenceGame.utils.Vector2;

public abstract class GuiComponent implements Renderable, Listener {

    protected Vector2 pos;
    protected double width, height;

    protected boolean hovered = false;

    public GuiComponent(Vector2 pos) {
        this.pos = pos;
    }

    protected void setPos(Vector2 posIn) {
        this.pos.setX(posIn.getX());
        this.pos.setY(posIn.getY());
    }

    protected void onMouseIn() {}
    protected void onMouseOver(double relativeMouseX, double relativeMouseY) {}
    protected void onMouseOut() {}

    @Override
    public void onEvent(Event event) {
        if (event instanceof MouseMoveEvent) {
            MouseMoveEvent mme = (MouseMoveEvent) event;
            double[] guiMousePos = mme.getCameraTransformedPos(TowerDefenceGame.theGame.getSettings().guiCamera);
            double clickXPos = guiMousePos[0];
            double clickYPos = guiMousePos[1];
            if (clickXPos >= this.pos.getX() && clickXPos < this.pos.getX() + this.width && clickYPos >= this.pos.getY() && clickYPos < this.pos.getY() + this.height) {
                // Mouse over
                if (!this.hovered) {
                    // Mouse in
                    this.onMouseIn();
                    this.hovered = true;
                }
                this.onMouseOver(clickXPos - this.pos.getX(), clickYPos - this.pos.getY());
            } else {
                // Mouse out
                this.hovered = false;
                this.onMouseOut();
            }
        }
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public void updatePos(Vector2 pos) {
        this.pos = pos;
    }
}
