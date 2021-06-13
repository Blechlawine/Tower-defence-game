package de.marc.towerDefenceGame.gui;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.guis.GuiDefeat;
import de.marc.towerDefenceGame.gui.guis.GuiInGame;
import de.marc.towerDefenceGame.gui.guis.GuiMainMenu;
import de.marc.towerDefenceGame.gui.guis.GuiSelectLevel;
import de.marc.towerDefenceGame.utils.ListManager;

public class GuiManager extends ListManager<Gui> {

    private Gui currentGui;

    @Override
    public void setup() {
        this.addGui(new GuiMainMenu());
        this.addGui(new GuiSelectLevel());
        this.addGui(new GuiDefeat());
        this.addGui(new GuiInGame());
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
        if (this.currentGui != null) {
            this.currentGui.destroy();
        }
        this.currentGui = gui;
        this.currentGui.enable();
    }

    public void setActiveGui(String guiName) {
        this.setActiveGui(this.getGuiFromName(guiName));
    }

    public Gui getCurrentGui() {
        return this.currentGui;
    }

}
