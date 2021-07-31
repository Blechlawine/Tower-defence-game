package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.*;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.Settings;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;

public class GuiSettingsMain extends Gui {

    private GuiButton backBtn;

    private GuiDropDown uiScaleDropDown;
    private GuiTabs settingsTabs;
    private GuiComponent[][] tabContentComponents;

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

        ArrayList<GuiComponent> controlTabComponents = new ArrayList<>();
        Settings.KeyBindings[] editableBindings = new Settings.KeyBindings[] {
                Settings.KeyBindings.PLAYER_MOVEUP,
                Settings.KeyBindings.PLAYER_MOVEDOWN,
                Settings.KeyBindings.PLAYER_MOVELEFT,
                Settings.KeyBindings.PLAYER_MOVERIGHT,
                Settings.KeyBindings.PLAYER_MOVEFAST,
                Settings.KeyBindings.PLAYER_MOVERESET,
                Settings.KeyBindings.PLAYER_MOVEMOUSE,
                Settings.KeyBindings.TOOLS_BUILD,
                Settings.KeyBindings.TOOLS_DESELECT,
                Settings.KeyBindings.TOOLS_DESTROY
        };
        int index = 0;
        for(Settings.KeyBindings binding : editableBindings) {
            Vector2 position = new Vector2(getInPixels(50, "vw"), getInPixels(0, "vh") + 60 * (1 + index++));
            controlTabComponents.add(new GuiLabel(binding.toString(), new Vector2(position).add(new Vector2(-250, 10)), new Color(Colors.TEXT)));
            controlTabComponents.add(new GuiKeyCodeInput(
                    position.add(new Vector2(100, 0)),
                    200, 40,
                    TowerDefenceGame.theGame.getSettings().keybindings.get(binding),
                    binding
            ));
        }

        ArrayList<GuiComponent> audioTabComponents = new ArrayList<>();
        // Audio volume sliders
        Vector2 startPosition = new Vector2(getInPixels(50, "vw"), getInPixels(0, "vh") + 60);
        // Music volume
        audioTabComponents.add(new GuiLabel("Music Volume", new Vector2(startPosition).add(-250, 10), new Color(Colors.TEXT)));
        audioTabComponents.add(new GuiSlider(
                new Vector2(startPosition).add(100, 0),
                200, 40,
                0, 1,
                TowerDefenceGame.theGame.getSettings().musicVolume,
                0.01
        ) {
            @Override
            public void onChange(double value) {
                TowerDefenceGame.theGame.getSettings().setMusicVolume((float)value);
            }
        });
        startPosition.add(0, 60);
        audioTabComponents.add(new GuiLabel("Sfx Volume", new Vector2(startPosition).add(-250, 10), new Color(Colors.TEXT)));
        audioTabComponents.add(new GuiSlider(
                new Vector2(startPosition).add(100, 0),
                200, 40,
                0, 1,
                TowerDefenceGame.theGame.getSettings().sfxVolume,
                0.01
        ) {
            @Override
            public void onChange(double value) {
                TowerDefenceGame.theGame.getSettings().setSfxVolume((float)value);
            }
        });

        GuiScrollContent controlTabContent = new GuiScrollContent(
                new Vector2(getInPixels(50, "vw"), getInPixels(0, "vh") + 60),
                getInPixels(100, "vw") - 200,
                getInPixels(80, "vh"),
                controlTabComponents.toArray(new GuiComponent[0])
        );

        GuiScrollContent audioTabContent = new GuiScrollContent(
                new Vector2(getInPixels(50, "vw"), getInPixels(0, "vh") + 60),
                getInPixels(100, "vw") - 200,
                getInPixels(80, "vh"),
                audioTabComponents.toArray(new GuiComponent[0])
        );

        this.tabContentComponents = new GuiComponent[][] {
                // Graphics
                new GuiComponent[] {
                    this.uiScaleDropDown
                },
                // Audio
                new GuiComponent[] {
                    audioTabContent
                },
                // Controls
                new GuiComponent[] {
                    controlTabContent
                }
        };

        this.settingsTabs = new GuiTabs(
                new Vector2(getInPixels(0, "vw"), getInPixels(0, "vh")),
                new String[] {"Graphics", "Audio", "Controls"},
                200, 40,
                0,
                this.tabContentComponents,
                getInPixels(100, "vh") - 100,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        );

        this.components.add(this.backBtn);
        this.components.add(this.settingsTabs);
    }
}
