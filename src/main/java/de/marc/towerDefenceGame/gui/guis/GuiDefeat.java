package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gameObjects.tower.Tower;
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
        this.defeatLabel = new GuiLabel("Defeat", new Vector2(getInPixels(50, "vw"), getInPixels(50, "vh")), new Color(1, 0, 0), 2);
//        this.mainMenuBtn = new GuiButton(
//                new GuiLabel("Back to main menu", new Color(1, 1, 1)),
//                new Vector2(getInPixels(50, "vw"), getInPixels(50, "vh") + 50),
//                200, 20,
//                new Color(Colors.BUTTONPRIMARY),
//                new Color(Colors.BUTTONPRIMARYHOVER)
//        ) {
//            @Override
//            public void onClick() {
//                TowerDefenceGame.theGame.getGuiManager().setActiveGui("mainmenu");
//            }
//        };

        this.components.add(this.defeatLabel);
    }
}
