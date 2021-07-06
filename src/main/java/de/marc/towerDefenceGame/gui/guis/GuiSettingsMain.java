package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.GuiButton;
import de.marc.towerDefenceGame.gui.components.GuiDropDown;
import de.marc.towerDefenceGame.gui.components.GuiLabel;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;
import java.util.Arrays;

public class GuiSettingsMain extends Gui {

    private GuiButton backBtn;

    private GuiDropDown uiScaleDropDown;

    public GuiSettingsMain() {
        super("settingsMain");
        this.hasBackground = true;
    }

    @Override
    public void initGui() {
        super.initGui();
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

        this.uiScaleDropDown = new GuiDropDown(
                new Vector2(getInPixels(50, "vw") - 100, getInPixels(50, "vh")),
                200, 40,
                TowerDefenceGame.theGame.getSettings().guiScales.keySet().toArray(new String[0]),
                TowerDefenceGame.theGame.getSettings().currentGuiScaleName,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        ) {
            @Override
            protected void valueChange(String oldValue, String newValue) {
                TowerDefenceGame.theGame.getSettings().setGuiScale(newValue);
                TowerDefenceGame.theGame.getLogger().debug(TowerDefenceGame.theGame.getSettings().currentGuiScale);
            }
        };

        this.components.add(this.backBtn);
        this.components.add(this.uiScaleDropDown);
    }
}
