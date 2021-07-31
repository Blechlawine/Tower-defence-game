package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.*;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.FileUtils;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;

public class GuiCredits extends Gui {

    private GuiButton backBtn;

    private String[] credits;

    public GuiCredits() {
        super("credits");
        this.credits = FileUtils.readTxtResource("assets/credits.txt");
        this.hasBackground = true;
    }

    @Override
    public void initGui() {
        super.initGui();
        ArrayList<GuiComponent> credits = new ArrayList<>();

        credits.add(new GuiText(this.credits, new Vector2(getInPixels(50, "vw"), 10), getInPixels(100, "vw"), 2, 1.3, GuiText.TextAlignment.CENTER));

        this.backBtn = new GuiButton(
                new GuiLabel("Back", new Color(Colors.TEXT)),
                new Vector2(getInPixels(50, "vw") - 100, getInPixels(100, "vh") - 50),
                200, 40,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        ) {
            @Override
            public void onClick() {
                TowerDefenceGame.theGame.getGuiManager().back();
            }
        };
        this.components.add(this.backBtn);
        this.components.add(new GuiScrollContent(new Vector2(10, 10), getInPixels(100, "vw") - 20, getInPixels(100, "vh") - 100, credits.toArray(new GuiComponent[0])));
    }
}
