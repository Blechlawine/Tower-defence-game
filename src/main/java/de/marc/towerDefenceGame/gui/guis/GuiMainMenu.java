package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.GuiButton;
import de.marc.towerDefenceGame.gui.components.GuiLabel;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiMainMenu extends Gui {

    private GuiButton playButton;

    public GuiMainMenu() {
        super("mainmenu");
        this.hasBackground = true;
    }

    @Override
    public void initGui() {
        Vector2 playBtnPos = new Vector2(windowSize).divide(2);
        this.playButton = new GuiButton(new GuiLabel("Play", new Vector2(0, 0), new Color(1, 1, 1)), playBtnPos, 100, 20, new Color("#f44336")) {
            @Override
            public void onClick() {
                TowerDefenceGame.theGame.getGameManager().startNewGame();
            }
        };

        this.components.add(this.playButton);
    }
}
