package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.GuiLabel;
import de.marc.towerDefenceGame.player.Player;

public class InGameGui extends Gui {

    private GuiLabel walletLabel, healthLabel;

    public InGameGui(String name) {
        super(name);
        this.walletLabel = new GuiLabel("- $", 10, 10, new float[] { 1, 0, 0 });
        this.healthLabel = new GuiLabel("", 10, 20, new float[] { 1, 0, 0 });

        this.components.add(this.walletLabel);
        this.components.add(this.healthLabel);
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
