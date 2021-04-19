package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.utils.Renderable;

public abstract class GuiComponent implements Renderable, Listener {

    protected double xPos, yPos;

    public GuiComponent(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        TowerDefenceGame.theGame.getEventManager().addListener(this);
    }
}
