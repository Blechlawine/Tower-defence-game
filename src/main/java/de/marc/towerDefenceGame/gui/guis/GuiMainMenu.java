package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.GuiButton;
import de.marc.towerDefenceGame.gui.components.GuiLabel;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiMainMenu extends Gui {

    private GuiButton playButton;

    public GuiMainMenu() {
        super("mainmenu");
        this.hasBackground = true;
    }

    @Override
    public void initGui() {
        Vector2 playBtnPos = new Vector2(getInPixels(50, "vw") - 100, getInPixels(50, "vh") - 20);
        this.playButton = new GuiButton(
                new GuiLabel("Play", new Color(Colors.TEXT)),
                playBtnPos,
                200, 40,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        ) {
            @Override
            public void onClick() {
                TowerDefenceGame.theGame.getGuiManager().setActiveGui("selectLevel");
            }
        };

        this.components.add(this.playButton);
    }
}
