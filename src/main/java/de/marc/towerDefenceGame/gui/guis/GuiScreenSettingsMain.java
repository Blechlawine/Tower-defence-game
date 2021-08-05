package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiLayoutComponent;
import de.marc.towerDefenceGame.gui.GuiScreen;
import de.marc.towerDefenceGame.gui.components.*;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.Settings;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;

public class GuiScreenSettingsMain extends GuiScreen {

    private GuiComponentButton backBtn;

    private GuiComponentDropDown uiScaleDropDown;
    private GuiComponentTabs settingsTabs;
    private ArrayList<GuiComponentTabContent> tabs;

    public GuiScreenSettingsMain() {
        super("settingsMain", true);
    }

    @Override
    public void createComponents() {
        ArrayList<GuiComponent> graphicsTabComponents = new ArrayList<>();
        ArrayList<GuiComponent> controlTabComponents = new ArrayList<>();
        ArrayList<GuiComponent> audioTabComponents = new ArrayList<>();

        GuiComponentFlexLayout graphicsTabLayout = new GuiComponentFlexLayout(
                new Vector2(0, 40),
                null,
//                new Vector2(200, getInPixels(0, "vh") + 60),
                getInPixels(100, "vw") - 200,
                getInPixels(80, "vh") - 40,
                true,
                GuiComponentFlexLayout.FlexDirection.VERTICAL,
                GuiComponentFlexLayout.FlexAlignment.CENTER,
                GuiComponentFlexLayout.FlexDistribution.CENTER,
                8
        );

        GuiComponentFlexLayout audioTabLayout = new GuiComponentFlexLayout(
                new Vector2(0, 40),
                null,
//                new Vector2(200, getInPixels(0, "vh") + 60),
                getInPixels(100, "vw") - 200,
                getInPixels(80, "vh") - 40,
                true,
                GuiComponentFlexLayout.FlexDirection.VERTICAL,
                GuiComponentFlexLayout.FlexAlignment.CENTER,
                GuiComponentFlexLayout.FlexDistribution.CENTER,
                8
        );

        GuiComponentFlexLayout controlTabLayout = new GuiComponentFlexLayout(
                new Vector2(0, 40),
                null,
//                new Vector2(200, getInPixels(0, "vh") + 60),
                getInPixels(100, "vw") - 200,
                getInPixels(80, "vh") - 40,
                true,
                GuiComponentFlexLayout.FlexDirection.VERTICAL,
                GuiComponentFlexLayout.FlexAlignment.CENTER,
                GuiComponentFlexLayout.FlexDistribution.START,
                0
        );

        //        new Vector2(getInPixels(50, "vw") - 100, getInPixels(100, "vh") - 50),
        this.backBtn = new GuiComponentButton(
                new Vector2(getInPixels(50, "vw") - 100, getInPixels(100, "vh") - 60),
                this.root,
                200, 40,
                null,
                true
        ) {
            @Override
            public void onClick() {
                this.game.getGuiManager().back();
            }
        };
        this.backBtn.setContent(new GuiComponentText("Back", this.backBtn));

//                new Vector2(getInPixels(50, "vw") - 100, getInPixels(50, "vh")),
        graphicsTabComponents.add(new GuiComponentDropDown(
                new Vector2(0, 0),
                graphicsTabLayout,
                200, 40,
                this.game.getSettings().guiScales.keySet().toArray(new String[0]),
                this.game.getSettings().currentGuiScaleName
        ) {
            @Override
            protected void valueChange(String oldValue, String newValue) {
                this.game.getSettings().setGuiScale(newValue);
                this.game.getLogger().debug(this.game.getSettings().currentGuiScale);
            }
        });

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
//            Vector2 position = new Vector2(getInPixels(50, "vw"), getInPixels(0, "vh") + 60 * (1 + index++));
            GuiComponentFlexLayout keyBindingLayout = new GuiComponentFlexLayout(
                    new Vector2(0, 0),
                    controlTabLayout,
                    getInPixels(100,"vw") - 200,
                    50,
                    false,
                    GuiComponentFlexLayout.FlexDirection.HORIZONTAL,
                    GuiComponentFlexLayout.FlexAlignment.CENTER,
                    GuiComponentFlexLayout.FlexDistribution.CENTER,
                    8
            );
            ArrayList<GuiComponent> temp = new ArrayList<>();

            temp.add(new GuiComponentText(binding.toString(), keyBindingLayout));
//                    position.add(new Vector2(100, 0)),
            temp.add(new GuiComponentKeyCodeInput(
                    new Vector2(0, 0),
                    keyBindingLayout,
                    200, 40,
                    this.game.getSettings().keybindings.get(binding),
                    binding
            ));
            keyBindingLayout.setContent(temp);
            controlTabComponents.add(keyBindingLayout);
        }

        // Audio volume sliders
        // Music volume
        audioTabComponents.add(new GuiComponentText("Music Volume", null));
        audioTabComponents.add(new GuiComponentSlider(
                new Vector2(0, 0),
                audioTabLayout,
                200, 40,
                0, 1,
                this.game.getSettings().musicVolume,
                0.01
        ) {
            @Override
            public void onChange(double value) {
                this.game.getSettings().setMusicVolume((float)value);
            }
        });
        // Sfx volume
        audioTabComponents.add(new GuiComponentText("Sfx Volume", null));
        audioTabComponents.add(new GuiComponentSlider(
                new Vector2(0, 0),
                audioTabLayout,
                200, 40,
                0, 1,
                this.game.getSettings().sfxVolume,
                0.01
        ) {
            @Override
            public void onChange(double value) {
                this.game.getSettings().setSfxVolume((float)value);
            }
        });

        graphicsTabLayout.setContent(graphicsTabComponents);
        audioTabLayout.setContent(audioTabComponents);
        controlTabLayout.setContent(controlTabComponents);

        this.tabs = new ArrayList<>();
        this.tabs.add(new GuiComponentTabContent(
                new Vector2(200, 0),
                this.root,
                getInPixels(100, "vw") - 200,
                getInPixels(80, "vh"),
                "Graphics",
                graphicsTabLayout
        ));
        this.tabs.add(new GuiComponentTabContent(
                new Vector2(200, 0),
                this.root,
                getInPixels(100, "vw") - 200,
                getInPixels(80, "vh"),
                "Audio",
                audioTabLayout
        ));
        this.tabs.add(new GuiComponentTabContent(
                new Vector2(200, 0),
                this.root,
                getInPixels(100, "vw") - 200,
                getInPixels(80, "vh"),
                "Controls",
                controlTabLayout
        ));

        this.settingsTabs = new GuiComponentTabs(
                new Vector2(0, 0),
                this.root,
                200, 40,
                new String[] {"Graphics", "Audio", "Controls"},
                40,
                0,
                this.tabs,
                getInPixels(100, "vh") - 100
        );
    }

    @Override
    public void setRootComponent() {
        this.root = new GuiComponentFreeLayout(new Vector2(0, 0),
                null,
                getInPixels(100, "vw"),
                getInPixels(100, "vh"),
                false
        );
    }

    @Override
    public void registerComponents() {
        ArrayList<GuiComponent> content = new ArrayList<GuiComponent>();
        content.add(this.settingsTabs);
        content.add(this.backBtn);
        this.root.setContent(content);
    }
}
