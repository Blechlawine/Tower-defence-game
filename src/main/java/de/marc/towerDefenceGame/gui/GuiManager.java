package de.marc.towerDefenceGame.gui;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.guis.GuiInGame;
import de.marc.towerDefenceGame.gui.guis.GuiMainMenu;
import de.marc.towerDefenceGame.gui.guis.GuiSelectLevel;
import de.marc.towerDefenceGame.utils.ListManager;

public class GuiManager extends ListManager<Gui> {

    private Gui currentGui;

    @Override
    public void setup() {
        this.addGui(new GuiInGame());
        this.addGui(new GuiMainMenu());
        this.addGui(new GuiSelectLevel());
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
        TowerDefenceGame.theGame.getRenderer().getLayerByName("gui").removeElement(this.currentGui);
        this.currentGui = gui;
        TowerDefenceGame.theGame.getRenderer().getLayerByName("gui").addElement(this.currentGui);
    }

    public void setActiveGui(String guiName) {
        this.setActiveGui(this.getGuiFromName(guiName));
    }

    public Gui getCurrentGui() {
        return this.currentGui;
    }

}
