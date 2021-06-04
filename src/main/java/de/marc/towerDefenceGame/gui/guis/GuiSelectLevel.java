package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.GuiButton;
import de.marc.towerDefenceGame.gui.components.GuiLabel;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiSelectLevel extends Gui {

    private GuiButton confirmBtn;

    public GuiSelectLevel() {
        super("selectLevel");
        this.hasBackground = true;
    }

    @Override
    public void initGui() {
        this.confirmBtn = new GuiButton(
                new GuiLabel("Confirm", new Color(1, 1, 1)),
                new Vector2(this.windowSize.getX() / 2 + 20, this.windowSize.getY() - 50),
                100, 20,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        ) {
            @Override
            public void onClick() {
                TowerDefenceGame.theGame.getGameManager().startNewGame();
            }
        };

        this.components.add(this.confirmBtn);
    }
}
