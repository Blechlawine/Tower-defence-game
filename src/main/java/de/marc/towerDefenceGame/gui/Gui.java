package de.marc.towerDefenceGame.gui;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.WindowResizeEvent;
import de.marc.towerDefenceGame.gui.components.GuiComponent;
import de.marc.towerDefenceGame.utils.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Gui implements Renderable, Listener {

    protected String name;
    protected List<GuiComponent> components;

    protected boolean hasBackground = false;

    public static Vector2 windowSize;

    public Gui(String name) {
        this.name = name;
        this.components = new ArrayList<GuiComponent>();
    }

    public void initGui() {
        this.components = new ArrayList<GuiComponent>();
    }

    public void enable() {
        TowerDefenceGame.theGame.getEventManager().addListener(this);
        this.initGui();
        TowerDefenceGame.theGame.getRenderer().getLayerByName("gui").addElement(this);
    }

    public void destroy() {
        TowerDefenceGame.theGame.getEventManager().removeListener(this);
        TowerDefenceGame.theGame.getRenderer().getLayerByName("gui").removeElement(this);
        for (GuiComponent c : this.components) {
            c.destroy();
        }
    }

    public static void setWindowSize(double width, double height) {
        windowSize.setX(width / TowerDefenceGame.theGame.getSettings().currentGuiScale);
        windowSize.setY(height / TowerDefenceGame.theGame.getSettings().currentGuiScale);
    }

    public void render() {
        if (this.hasBackground) this.drawBackground();
        for (GuiComponent c : this.components) {
            c.render();
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof WindowResizeEvent) {
            this.initGui();
        }
        List<GuiComponent> tempComponents = new ArrayList<GuiComponent>(this.components);
        for (GuiComponent component : tempComponents) {
            component.onEvent(event);
        }
    }

    public String getName() {
        return this.name;
    }

    protected void drawBackground() {
        GLUtils.drawRect(0, 0, windowSize.getX(), windowSize.getY(), new Color(Colors.BACKGROUND));
    }

    protected double getInPixels(double number, String unit) {
        double pixels = number;
        if (unit.equalsIgnoreCase("px")) {
            pixels = number;
        } else if (unit.equalsIgnoreCase("vw")) {
            pixels = windowSize.getX() * (number / 100);
        } else if (unit.equalsIgnoreCase("vh")) {
            pixels = windowSize.getY() * (number / 100);
        }
        return pixels;
    }

}
