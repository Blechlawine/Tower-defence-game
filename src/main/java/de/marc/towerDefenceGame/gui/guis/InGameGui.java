package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.GuiLabel;

public class InGameGui extends Gui {

    public InGameGui(String name) {
        super(name);
        this.components.add(new GuiLabel("HALLO", 10, 10, new float[] { 1, 0, 0 }));
    }
}
