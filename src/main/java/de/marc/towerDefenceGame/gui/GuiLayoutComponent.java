package de.marc.towerDefenceGame.gui;

import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.event.events.MouseScrollEvent;
import de.marc.towerDefenceGame.utils.Utils;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public abstract class GuiLayoutComponent extends GuiComponent {

    protected ArrayList<GuiComponent> content;
    private boolean scrollable;
    private double scrollOffset, contentHeight;

    public GuiLayoutComponent(Vector2 relativePos,
                              GuiComponent parent,
                              double width,
                              double height,
                              boolean scrollable
                              ) {
        super(relativePos, parent, width, height);
        this.scrollable = scrollable;
    }

    public void setContent(ArrayList<GuiComponent> content) {
        this.updateContent(content);
        this.updateContentHeight();
    }

    public void updateContent(ArrayList<GuiComponent> content) {
        this.content = content;
        if (this.content != null) {
            for(GuiComponent c : this.content) {
                c.setParent(this);
            }
        }
    }

    public void updateContentHeight() {
        if (this.scrollable) {
            this.scrollOffset = 0;
            this.contentHeight = 0;
            for (GuiComponent c : this.content) {
                if (c.getRelativePos().getY() + c.getHeight() > this.contentHeight) {
                    this.contentHeight = c.getRelativePos().getY() + c.getHeight();
                }
            }
        }
    }

    @Override
    public void onEvent(Event event) {
        if (this.visible) {
            if (this.scrollable) {
                if (event instanceof MouseScrollEvent) {
                    MouseScrollEvent mse = (MouseScrollEvent) event;
                    this.game.getLogger().debug(this.contentHeight, this.height);
                    if (this.contentHeight > this.height) {
                        this.scrollOffset += mse.getY() * 20;
                        // down is negative scrollOffset, up is positive
                        this.scrollOffset = Utils.limitD(this.scrollOffset, -(this.contentHeight - this.height), 0);
                        mse.cancel();
                    }
                } else if (event instanceof MouseMoveEvent) {
                    MouseMoveEvent mme = (MouseMoveEvent) event;
                    for (GuiComponent c : this.content) {
                        c.onEvent(new MouseMoveEvent(mme.getAbsoluteX(), mme.getAbsoluteY() - this.scrollOffset * this.game.getSettings().currentGuiScale));
                    }
                } else {
                    for (GuiComponent c : this.content) {
                        c.onEvent(event);
                    }
                }
            } else {
                for (GuiComponent c : this.content) {
                    c.onEvent(event);
                }
            }
        }
    }

    @Override
    public void render() {
        // TODO: render scrollbar
        if (this.content != null && this.visible) {
            GL11.glPushMatrix();
            GL11.glTranslated(0, this.scrollOffset, 0);
            for (GuiComponent c : this.content) {
                c.render();
            }
            GL11.glPopMatrix();
        }
    }

    public ArrayList<GuiComponent> getContent() {
        return this.content;
    }

}
