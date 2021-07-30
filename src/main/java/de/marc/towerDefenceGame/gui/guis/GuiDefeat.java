package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.GuiButton;
import de.marc.towerDefenceGame.gui.components.GuiLabel;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiDefeat extends Gui {

    private GuiLabel defeatLabel;
    private GuiButton mainMenuBtn;

    public GuiDefeat() {
        super("defeat");
        this.hasBackground = true;
    }

    @Override
    public void initGui() {
        super.initGui();
        double defeatLabelWidth = TowerDefenceGame.theGame.getFontRenderer().getRenderedStringWidth("Defeat", 2);
        this.defeatLabel = new GuiLabel("Defeat",
                new Vector2(getInPixels(50, "vw") - defeatLabelWidth / 2, getInPixels(50, "vh") - 80),
                new Color(1, 0, 0),
                2);
        this.mainMenuBtn = new GuiButton(
                new GuiLabel("Return to menu", new Color(1, 1, 1)),
                new Vector2(getInPixels(50, "vw") - 100, getInPixels(50, "vh")),
                200, 40,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        ) {
            @Override
            public void onClick() {
                TowerDefenceGame.theGame.getGuiManager().setActiveGui(TowerDefenceGame.theGame.getGuiManager().getGuiFromName("mainmenu"));
            }
        };

        this.components.add(this.defeatLabel);
        this.components.add(this.mainMenuBtn);
    }
}
