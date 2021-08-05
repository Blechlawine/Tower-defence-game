package de.marc.towerDefenceGame.gui;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.WindowResizeEvent;
import de.marc.towerDefenceGame.utils.*;

import java.util.ArrayList;

public abstract class GuiScreen implements Renderable, Listener {

    protected final String name;
    protected GuiLayoutComponent root;

    protected boolean hasBackground = false;
    protected TowerDefenceGame game;

    public static Vector2 windowSize;

    public GuiScreen(String name, boolean hasBackground) {
        this.name = name;
        this.hasBackground = hasBackground;
        this.game = TowerDefenceGame.theGame;
        this.preInit();
        this.initGui();
    }

    public void initGui() {
        this.setRootComponent();
        this.createComponents();
        this.registerComponents();
    }

    public void preInit() {}

    public abstract void createComponents();
    public abstract void setRootComponent();
    public abstract void registerComponents();

    public void enable() {
        TowerDefenceGame.theGame.getEventManager().addUiListener(this);
        this.initGui();
        TowerDefenceGame.theGame.getRenderer().getLayerByName("gui").addElement(this);
    }

    public void destroy() {
        TowerDefenceGame.theGame.getEventManager().removeUiListener(this);
        TowerDefenceGame.theGame.getRenderer().getLayerByName("gui").removeElement(this);
    }

    public static void setWindowSize(double width, double height) {
        windowSize.setX(width / TowerDefenceGame.theGame.getSettings().currentGuiScale);
        windowSize.setY(height / TowerDefenceGame.theGame.getSettings().currentGuiScale);
    }

    public void render() {
        if (this.hasBackground) this.drawBackground();
        this.root.render();
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof WindowResizeEvent) {
            this.initGui();
        }
        this.root.onEvent(event);
    }

    public String getName() {
        return this.name;
    }

    protected void drawBackground() {
        GLUtils.drawRect(0, 0, windowSize.getX(), windowSize.getY(), new Color(Colors.BACKGROUNDDARK));
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
