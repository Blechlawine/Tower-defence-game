package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.player.tools.BasicTowerTool;
import de.marc.towerDefenceGame.utils.Color;

import java.util.ArrayList;

public class GuiToolbar extends GuiComponent {

    private ArrayList<GuiToggleButton> tools;

    public GuiToolbar(double xPos, double yPos) {
        super(xPos, yPos);
        this.tools = new ArrayList<>();
        this.tools.add(new GuiToggleButton(
                new GuiTower(this.xPos, this.yPos, 50, "basicTowerTurret", "basicTowerBase"),
                this.xPos,
                this.yPos,
                50,
                50,
                new Color(0.2f, 0.2f, 0.2f )) {
            @Override
            public void onActivate() {
                TowerDefenceGame.theGame.getPlayer().setActiveTool(new BasicTowerTool());
//                TowerDefenceGame.theGame.getLogger().info("Button pressed");
            }

            @Override
            public void onDeactivate() {
                TowerDefenceGame.theGame.getPlayer().getActiveTool().deactivate();
                TowerDefenceGame.theGame.getPlayer().setActiveTool(null);
            }
        });
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
}
