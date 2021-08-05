package de.marc.towerDefenceGame.gui;

import de.marc.towerDefenceGame.gui.guis.*;
import de.marc.towerDefenceGame.utils.ListManager;

public class GuiManager extends ListManager<GuiScreen> {

    private GuiScreen currentGuiScreen, previousGuiScreen;

    @Override
    public void setup() {
        this.addGui(new GuiScreenMainMenu());
        this.addGui(new GuiScreenSelectLevel());
        this.addGui(new GuiScreenDefeat());
        this.addGui(new GuiScreenInGame());
        this.addGui(new GuiScreenSettingsMain());
        this.addGui(new GuiScreenCredits());
    }

    public void addGui(GuiScreen guiScreen) {
        if (!this.content.contains(guiScreen)) {
            this.content.add(guiScreen);
        }
    }

    public GuiScreen getGuiFromName(String name) {
        for (GuiScreen guiScreen : this.content) {
            if (guiScreen.getName().equalsIgnoreCase(name)) {
                return guiScreen;
            }
        }
        return null;
    }

    public void setActiveGui(GuiScreen guiScreen) {
        this.previousGuiScreen = this.currentGuiScreen;
        if (this.currentGuiScreen != null) {
            this.currentGuiScreen.destroy();
        }
        this.currentGuiScreen = guiScreen;
        this.currentGuiScreen.enable();
    }

    public void back() {
        this.setActiveGui(this.previousGuiScreen);
    }

    public void setActiveGui(String guiName) {
        this.setActiveGui(this.getGuiFromName(guiName));
    }

    public GuiScreen getCurrentGui() {
        return this.currentGuiScreen;
    }

}
