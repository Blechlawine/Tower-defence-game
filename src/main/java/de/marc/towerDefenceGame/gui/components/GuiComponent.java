package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.utils.Renderable;
import de.marc.towerDefenceGame.utils.Vector2;

public abstract class GuiComponent implements Renderable, Listener {

    protected Vector2 pos;

    public GuiComponent(Vector2 pos) {
        this.pos = pos;
        TowerDefenceGame.theGame.getEventManager().addListener(this);
    }

    protected void setPos(Vector2 posIn) {
        this.pos.setX(posIn.getX());
        this.pos.setY(posIn.getY());
    }
}
