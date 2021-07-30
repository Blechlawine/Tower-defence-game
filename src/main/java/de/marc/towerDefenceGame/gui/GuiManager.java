package de.marc.towerDefenceGame.gui;

import de.marc.towerDefenceGame.gui.guis.*;
import de.marc.towerDefenceGame.utils.ListManager;

public class GuiManager extends ListManager<Gui> {

    private Gui currentGui, previousGui;

    @Override
    public void setup() {
        this.addGui(new GuiMainMenu());
        this.addGui(new GuiSelectLevel());
        this.addGui(new GuiDefeat());
        this.addGui(new GuiInGame());
        this.addGui(new GuiSettingsMain());
    }

    public void addGui(Gui gui) {
        if (!this.content.contains(gui)) {
            this.content.add(gui);
        }
    }

    public Gui getGuiFromName(String name) {
        for (Gui gui : this.content) {
            if (gui.getName().equalsIgnoreCase(name)) {
                return gui;
            }
        }
        return null;
    }

    public void setActiveGui(Gui gui) {
        this.previousGui = this.currentGui;
        if (this.currentGui != null) {
            this.currentGui.destroy();
        }
        this.currentGui = gui;
        this.currentGui.enable();
    }

    public void back() {
        this.setActiveGui(this.previousGui);
    }

    public void setActiveGui(String guiName) {
        this.setActiveGui(this.getGuiFromName(guiName));
    }

    public Gui getCurrentGui() {
        return this.currentGui;
    }

}
