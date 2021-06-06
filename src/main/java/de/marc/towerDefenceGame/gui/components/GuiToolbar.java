package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.MouseButtonEvent;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.player.tools.Tool;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.KeyAction;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;

public class GuiToolbar extends GuiComponent {

    private ArrayList<Tool> tools;

    private final Double toolSize = 50d;

    public GuiToolbar(Vector2 pos) {
        super(pos);
        this.height = this.toolSize;
        this.tools = TowerDefenceGame.theGame.getPlayer().getTools();
        this.width = this.toolSize * this.tools.size();
    }

    private Vector2 generateToolPos(int index) {
        return new Vector2(this.pos).add(new Vector2(this.toolSize * index, 0));
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MouseButtonEvent) {
            MouseButtonEvent e = (MouseButtonEvent) event;
            if (e.getButton() == 0 && e.getAction() == KeyAction.DOWN) {
                if (this.hovered) {
                    double relMousePosX = MouseMoveEvent.getAbsoluteX() - this.pos.getX();
                    int hoveredToolIndex = (int) (relMousePosX / this.toolSize);
                    TowerDefenceGame.theGame.getPlayer().setActiveTool(hoveredToolIndex);
                    e.cancel();
                }
            }
        }
        super.onEvent(event);
    }

    @Override
    public void render() {
//        TODO: GuiToolbar background
        Vector2 highlightPos = this.generateToolPos(TowerDefenceGame.theGame.getPlayer().getActiveToolIndex());
        GLUtils.drawTexturedRect(highlightPos.getX(), highlightPos.getY(), this.toolSize, this.toolSize, 0, 0, 1, 1, "cursor",  new Color(1, 1, 1));
        for (int i = 0; i < this.tools.size(); i++) {
            this.tools.get(i).renderInUI(this.generateToolPos(i));
        }
    }

    public void setPos(Vector2 posIn) {
        this.pos.setX(posIn.getX());
        this.pos.setY(posIn.getY());
    }
}
