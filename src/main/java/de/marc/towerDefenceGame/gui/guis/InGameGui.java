package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.GuiLabel;
import de.marc.towerDefenceGame.gui.components.GuiToolbar;
import de.marc.towerDefenceGame.player.Player;
import de.marc.towerDefenceGame.utils.Color;

public class InGameGui extends Gui {

    private GuiLabel walletLabel, healthLabel;
    private GuiToolbar towerToolbar;

    public InGameGui() {
        super("ingame");
        this.walletLabel = new GuiLabel("- $", 10, 10, new Color(1, 0, 0 ));
        this.healthLabel = new GuiLabel("", 10, 20, new Color(1, 0, 0 ));
        this.towerToolbar = new GuiToolbar(10, 100);

        this.components.add(this.walletLabel);
        this.components.add(this.healthLabel);
        this.components.add(this.towerToolbar);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof UpdateEvent) {
            UpdateEvent e = (UpdateEvent) event;
            Player thePlayer = TowerDefenceGame.theGame.getPlayer();
            this.walletLabel.setText(thePlayer.getWallet() + " $");
            this.healthLabel.setText(thePlayer.getHealth() + "/" + thePlayer.getMaxHealth());
        }
    }
}
