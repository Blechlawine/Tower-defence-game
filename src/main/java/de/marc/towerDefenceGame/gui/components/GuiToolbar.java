package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.player.tools.Tool;
import de.marc.towerDefenceGame.utils.*;

import java.util.ArrayList;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;

public class GuiToolbar extends GuiComponent {

    private ArrayList<Tool> tools;

    private Keybinding toolSelectBinding;

    private final Double toolSize = 50d;
    private double relMousePosX;

    public GuiToolbar(Vector2 pos) {
        super(pos);
        this.height = this.toolSize;
        this.tools = TowerDefenceGame.theGame.getPlayer().getTools();
        this.width = this.toolSize * this.tools.size();
        this.toolSelectBinding = new Keybinding(Settings.KeyBindings.GUI_INTERACT, new KeyAction[] {DOWN}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (action == DOWN) {
                    if (hovered) {
                        int hoveredToolIndex = (int) (relMousePosX / toolSize);
                        TowerDefenceGame.theGame.getPlayer().setActiveTool(hoveredToolIndex);
                        event.cancel();
                        TowerDefenceGame.theGame.getLogger().debug("toolselectBinding on Event");
                    }
                }
            }
        };
    }

    private Vector2 generateToolPos(int index) {
        return new Vector2(this.pos).add(new Vector2(this.toolSize * index, 0));
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MouseMoveEvent) {
            MouseMoveEvent mme = (MouseMoveEvent) event;
            this.relMousePosX = mme.getCameraTransformedPos(TowerDefenceGame.theGame.getSettings().guiCamera)[0] - pos.getX();
        }
        super.onEvent(event);
        this.toolSelectBinding.onEvent(event);
    }

    @Override
    public void render() {
        for (int i = 0; i < this.tools.size(); i++) {
            Vector2 tempPos = this.generateToolPos(i);
            GLUtils.drawTexturedRect(tempPos.getX(), tempPos.getY(), this.toolSize, this.toolSize, 0, 0, 1, 1, "toolbar", new Color(1, 1, 1));
            this.tools.get(i).renderInUI(tempPos);
        }
        Vector2 highlightPos = this.generateToolPos(TowerDefenceGame.theGame.getPlayer().getActiveToolIndex());
        GLUtils.drawTexturedRect(highlightPos.getX(), highlightPos.getY(), this.toolSize, this.toolSize, 0, 0, 1, 1, "cursor",  new Color(1, 1, 1));
    }

    public void setPos(Vector2 posIn) {
        this.pos.setX(posIn.getX());
        this.pos.setY(posIn.getY());
    }
}
