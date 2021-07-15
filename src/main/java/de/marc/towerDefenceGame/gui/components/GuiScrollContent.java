package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.event.events.MouseScrollEvent;
import de.marc.towerDefenceGame.utils.Utils;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

public class GuiScrollContent extends GuiComponent {

    private GuiComponent[] content;

    private double scrollOffset, contentHeight;

    public GuiScrollContent(Vector2 pos, double width, double height, GuiComponent[] content) {
        super(pos);
        this.width = width;
        this.height = height;
        this.content = content;
        this.scrollOffset = 0;
        this.contentHeight = 0;
        for (GuiComponent c : this.content) {
            if (c.pos.getY() + c.getHeight() > this.contentHeight) {
                this.contentHeight = c.pos.getY() + c.getHeight();
            }
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MouseScrollEvent) {
            MouseScrollEvent mse = (MouseScrollEvent) event;
            if (this.contentHeight > this.height) {
                this.scrollOffset += mse.getY() * 20;
                // down is negative scrollOffset, up is positive
                this.scrollOffset = Utils.limitD(this.scrollOffset, -(this.contentHeight - this.height), 0);
                mse.cancel();
            }
        } else if (event instanceof MouseMoveEvent) {
            MouseMoveEvent mme = (MouseMoveEvent) event;
            for (GuiComponent c : this.content) {
                c.onEvent(new MouseMoveEvent(mme.getAbsoluteX(), mme.getAbsoluteY() - this.scrollOffset * TowerDefenceGame.theGame.getSettings().currentGuiScale));
            }
        } else {
            for (GuiComponent c : this.content) {
                c.onEvent(event);
            }
        }
    }

    @Override
    public void render() {
        // TODO: render scrollbar
        GL11.glPushMatrix();
        GL11.glTranslated(0, this.scrollOffset, 0);
        for (GuiComponent c : this.content) {
            c.render();
        }
        GL11.glPopMatrix();
    }
}
