package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.event.events.WindowResizeEvent;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.GuiLabel;
import de.marc.towerDefenceGame.gui.components.GuiToolbar;
import de.marc.towerDefenceGame.player.Player;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiInGame extends Gui {

    private GuiLabel walletLabel, healthLabel;
    private GuiToolbar towerToolbar;

    public GuiInGame() {
        super("ingame");
    }

    public void initGui() {
        this.walletLabel = new GuiLabel("- $", new Vector2(10, 10), new Color(1, 0, 0 ));
        this.healthLabel = new GuiLabel("", new Vector2(10, 20), new Color(1, 0, 0 ));
        this.towerToolbar = new GuiToolbar(new Vector2(windowSize.getX() / 2 - 50, windowSize.getY() - 50));

        this.components.add(this.walletLabel);
        this.components.add(this.healthLabel);
        this.components.add(this.towerToolbar);

//        this.towerToolbar.setPos(new Vector2(windowSize.getX() / 2 - 50, windowSize.getY() - 50));
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event instanceof UpdateEvent) {
            UpdateEvent e = (UpdateEvent) event;
            Player thePlayer = TowerDefenceGame.theGame.getPlayer();
            this.walletLabel.setText(thePlayer.getWallet() + " $");
            this.healthLabel.setText(thePlayer.getHealth() + "/" + thePlayer.getMaxHealth());
        }
    }
}
