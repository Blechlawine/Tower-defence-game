package de.marc.towerDefenceGame.gui;

import de.marc.towerDefenceGame.gui.guis.InGameGui;
import de.marc.towerDefenceGame.utils.ListManager;

public class GuiManager extends ListManager<Gui> {

    private Gui currentGui;

    @Override
    public void setup() {
        this.addGui(new InGameGui("ingame"));
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
        this.currentGui = gui;
    }

    public Gui getCurrentGui() {
        return this.currentGui;
    }

}
