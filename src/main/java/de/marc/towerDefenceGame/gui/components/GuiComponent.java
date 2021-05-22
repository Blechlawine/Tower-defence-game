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
        TowerDefenceGame.theGame.getEventManager().addListener(this);
    }

    protected void setPos(Vector2 posIn) {
        this.pos.setX(posIn.getX());
        this.pos.setY(posIn.getY());
    }

    protected void onMouseIn() {}
    protected void onMouseOver() {}
    protected void onMouseOut() {}

    @Override
    public void onEvent(Event event) {
        if (event instanceof MouseMoveEvent) {
            double clickXPos = MouseMoveEvent.getAbsoluteX();
            double clickYPos = MouseMoveEvent.getAbsoluteY();
            if (clickXPos >= this.pos.getX() && clickXPos < this.pos.getX() + this.width && clickYPos >= this.pos.getY() && clickYPos < this.pos.getY() + this.height) {
                // Mouse over
                if (!this.hovered) {
                    // Mouse in
                    this.onMouseIn();
                    this.hovered = true;
                }
                this.onMouseOver();
            } else {
                // Mouse out
                this.hovered = false;
                this.onMouseOut();
            }
        }
    }
}
