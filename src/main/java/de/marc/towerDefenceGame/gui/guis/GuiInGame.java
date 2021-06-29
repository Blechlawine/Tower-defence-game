package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.event.events.KeyEvent.KeyCode;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.GuiButton;
import de.marc.towerDefenceGame.gui.components.GuiLabel;
import de.marc.towerDefenceGame.gui.components.GuiToolbar;
import de.marc.towerDefenceGame.player.Player;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.KeyAction;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiInGame extends Gui {

    private GuiLabel walletLabel, healthLabel;
    private GuiToolbar towerToolbar;
    private boolean paused = false;
    private GuiButton resumeBtn, exitBtn, settingsBtn;

    public GuiInGame() {
        super("ingame");
    }

    public void initGui() {
        this.walletLabel = new GuiLabel("- $", new Vector2(10, 10), new Color(1, 0, 0 ));
        this.healthLabel = new GuiLabel("", new Vector2(10, 30), new Color(1, 0, 0 ));
        this.towerToolbar = new GuiToolbar(new Vector2(getInPixels(50, "vw") - 50, getInPixels(100, "vh") - 50));

        this.components.add(this.walletLabel);
        this.components.add(this.healthLabel);
        this.components.add(this.towerToolbar);
    }

    private void makeEscMenu() {
        this.resumeBtn = new GuiButton(new GuiLabel("Resume", new Color(Colors.TEXT)),
                new Vector2(getInPixels(50, "vw") - 50, getInPixels(50, "vh") - 50),
                200, 40,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        ) {
            @Override
            public void onClick() {
                unpauseGame();
            }
        };
        this.exitBtn = new GuiButton(new GuiLabel("Return to menu", new Color(Colors.TEXT)),
                new Vector2(getInPixels(50, "vw") - 50, getInPixels(50, "vh")),
                200, 40,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        ) {
            @Override
            public void onClick() {
                TowerDefenceGame.theGame.getGameManager().getCurrentGame().end();
            }
        };
        this.components.add(this.resumeBtn);
        this.components.add(this.exitBtn);
    }

    private void removeEscMenu() {
        this.components.remove(this.resumeBtn);
        this.components.remove(this.exitBtn);
    }

    private void pauseGame() {
        TowerDefenceGame.theGame.getGameManager().getCurrentGame().pause();
        this.paused = true;
        this.makeEscMenu();
    }

    private void unpauseGame() {
        this.paused = false;
        removeEscMenu();
        TowerDefenceGame.theGame.getGameManager().getCurrentGame().unpause();
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event instanceof UpdateEvent) {
            UpdateEvent e = (UpdateEvent) event;
            Player thePlayer = TowerDefenceGame.theGame.getPlayer();
            this.walletLabel.setText(thePlayer.getWallet() + " $");
            this.healthLabel.setText(thePlayer.getHealth() + "/" + thePlayer.getMaxHealth());
        } else if (event instanceof KeyEvent) {
            KeyEvent e = (KeyEvent) event;
            if (e.getKey() == KeyCode.ESC && e.getAction() == KeyAction.UP) {
                // pause game and show menu
                if (!this.paused) {
                    this.pauseGame();
                } else {
                    this.unpauseGame();
                }
            }
        }
    }
}
