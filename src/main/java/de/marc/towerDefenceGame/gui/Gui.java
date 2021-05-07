package de.marc.towerDefenceGame.gui;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.gui.components.GuiComponent;
import de.marc.towerDefenceGame.utils.Renderable;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;
import java.util.List;

public abstract class Gui implements Renderable, Listener {

    protected String name;
    protected List<GuiComponent> components;

    public static Vector2 windowSize;

    public Gui(String name) {
        this.name = name;
        this.components = new ArrayList<GuiComponent>();
        TowerDefenceGame.theGame.getEventManager().addListener(this);
    }

    public void render() {
        for (GuiComponent c : this.components) {
            c.render();
        }
    }

    public String getName() {
        return this.name;
    }
}
