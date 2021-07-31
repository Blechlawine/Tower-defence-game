package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.GuiButton;
import de.marc.towerDefenceGame.gui.components.GuiImage;
import de.marc.towerDefenceGame.gui.components.GuiLabel;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiMainMenu extends Gui {

    private GuiButton playButton, settingsButton, creditsButton;
    private GuiImage logo;

    public GuiMainMenu() {
        super("mainmenu");
        this.hasBackground = true;
    }

    @Override
    public void initGui() {
        super.initGui();
        Vector2 playBtnPos = new Vector2(getInPixels(50, "vw") - 100, getInPixels(50, "vh") - 20);
        String playBtnText = (TowerDefenceGame.theGame.getGameManager().getCurrentGame() != null ? "Resume Game" : "Play");
        this.playButton = new GuiButton(
                new GuiLabel(playBtnText, new Color(Colors.TEXT)),
                playBtnPos,
                200, 40,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        ) {
            @Override
            public void onClick() {
                if (TowerDefenceGame.theGame.getGameManager().getCurrentGame() != null) {
                    // Resume current Game
                    TowerDefenceGame.theGame.getGameManager().getCurrentGame().resume();
                } else {
                    // Start new Game
                    TowerDefenceGame.theGame.getGuiManager().setActiveGui("selectLevel");
                }
            }
        };

        this.settingsButton = new GuiButton(
                new GuiLabel("Settings", new Color(Colors.TEXT)),
                new Vector2(playBtnPos).add(new Vector2(0, 50)),
                200, 40,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        ) {
            @Override
            public void onClick() {
                TowerDefenceGame.theGame.getGuiManager().setActiveGui("settingsMain");
            }
        };

        this.creditsButton = new GuiButton(
                new GuiLabel("Credits", new Color(Colors.TEXT)),
                new Vector2(playBtnPos).add(new Vector2(0, 100)),
                200, 40,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        ) {
            @Override
            public void onClick() {
                TowerDefenceGame.theGame.getGuiManager().setActiveGui("credits");
            }
        };

        this.logo = new GuiImage("logo",
                new Vector2(getInPixels(50, "vw") - 128, getInPixels(50, "vh") - 280),
                256, 256,
                new Color(1, 1, 1), true);

        this.components.add(this.playButton);
        this.components.add(this.settingsButton);
        this.components.add(this.creditsButton);
        this.components.add(this.logo);
    }
}
