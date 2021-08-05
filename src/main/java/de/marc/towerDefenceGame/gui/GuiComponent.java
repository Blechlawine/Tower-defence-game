package de.marc.towerDefenceGame.gui;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.utils.Renderable;
import de.marc.towerDefenceGame.utils.Vector2;

public abstract class GuiComponent implements Renderable, Listener {
    protected TowerDefenceGame game;

    protected Vector2 relativePos;
    protected GuiComponent parent;
    protected double width, height;
    protected boolean visible;

    public GuiComponent(Vector2 relativePos, GuiComponent parent, double width, double height) {
        this.relativePos = relativePos;
        this.parent = parent;
        this.width = width;
        this.height = height;
        this.visible = true;
        this.game = TowerDefenceGame.theGame;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setRelativePos(Vector2 pos) {
        this.relativePos.setX(pos.getX());
        this.relativePos.setY(pos.getY());
    }

    public Vector2 getRelativePos() {
        return this.relativePos;
    }

    public Vector2 getAbsolutePos() {
        if (this.parent != null) {
            return new Vector2(this.parent.getAbsolutePos()).add(this.relativePos);
        } else {
            return new Vector2(this.relativePos);
        }
    }

    public void setParent(GuiComponent parent) {
        this.parent = parent;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
