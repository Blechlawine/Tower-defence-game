package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.player.tools.BasicTowerTool;
import de.marc.towerDefenceGame.player.tools.SniperTowerTool;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;

public class GuiToolbar extends GuiComponent {

    private ArrayList<GuiToggleButton> tools;

    private final Double toolSize = 50d;

    public GuiToolbar(Vector2 pos) {
        super(pos);
        this.tools = new ArrayList<>();
        Vector2 basicToolPos = this.generateTowerPos(0);
        this.tools.add(new GuiToggleButton(
                new GuiTower(basicToolPos, this.toolSize, "basicTowerTurret", "basicTowerBase"),
                basicToolPos,
                this.toolSize,
                this.toolSize,
                new Color(0.2f, 0.2f, 0.2f )) {
            @Override
            public void onActivate() {
                for (GuiToggleButton toggleButton : tools) {
                    toggleButton.deactivate();
                }
                TowerDefenceGame.theGame.getPlayer().setActiveTool(new BasicTowerTool());
            }

            @Override
            public void onDeactivate() {
                TowerDefenceGame.theGame.getPlayer().deactivateActiveTool();
            }
        });
        Vector2 sniperToolPos = this.generateTowerPos(1);
        this.tools.add(new GuiToggleButton(
                new GuiTower(sniperToolPos, this.toolSize, "sniperTowerTurret", "sniperTowerBase"),
                sniperToolPos,
                this.toolSize,
                this.toolSize,
                new Color(0.2f, 0.2f, 0.2f )) {
            @Override
            public void onActivate() {
                for (GuiToggleButton toggleButton : tools) {
                    toggleButton.deactivate();
                }
                TowerDefenceGame.theGame.getPlayer().setActiveTool(new SniperTowerTool());
            }

            @Override
            public void onDeactivate() {
                TowerDefenceGame.theGame.getPlayer().deactivateActiveTool();
            }
        });
    }

    private Vector2 generateTowerPos(int index) {
        return new Vector2(this.pos).add(new Vector2(this.toolSize * index, 0));
    }

    @Override
    public void onEvent(Event event) {

    }

    @Override
    public void render() {
        for (GuiToggleButton tool : this.tools) {
            tool.render();
        }
    }

    public void setPos(Vector2 posIn) {
        this.pos.setX(posIn.getX());
        this.pos.setY(posIn.getY());
        for (int i = 0; i < this.tools.size(); i++) {
            this.tools.get(i).setPos(this.generateTowerPos(i));
        }
    }
}
