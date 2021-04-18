package de.marc.towerDefenceGame.gui;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.components.GuiComponent;
import de.marc.towerDefenceGame.utils.Renderable;

import java.util.ArrayList;
import java.util.List;

public abstract class Gui implements Renderable {

    protected String name;
    protected List<GuiComponent> components;

    public Gui(String name) {
        this.name = name;
        this.components = new ArrayList<GuiComponent>();
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
