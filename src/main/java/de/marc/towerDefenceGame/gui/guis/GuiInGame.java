package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.GuiButton;
import de.marc.towerDefenceGame.gui.components.GuiImage;
import de.marc.towerDefenceGame.gui.components.GuiLabel;
import de.marc.towerDefenceGame.gui.components.GuiToolbar;
import de.marc.towerDefenceGame.player.Player;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.KeyAction;
import de.marc.towerDefenceGame.utils.Vector2;

import static de.marc.towerDefenceGame.utils.Settings.KeyBindings.GUI_BACK;

public class GuiInGame extends Gui {

    private GuiLabel walletLabel, healthLabel;
    private GuiImage walletIcon, healthIcon;
    private GuiToolbar towerToolbar;
    private boolean paused = false;
    private GuiButton resumeBtn, exitBtn, settingsBtn;

    private String walletLabelText = "- $", healthLabelText = "";

    public GuiInGame() {
        super("ingame");
    }

    public void initGui() {
        super.initGui();
        this.walletLabel = new GuiLabel(this.walletLabelText, new Vector2(28, 10), new Color(Colors.TEXT));
        this.walletIcon = new GuiImage("moneyicon", new Vector2(10, 10), 16, 16, null, true);
        this.healthLabel = new GuiLabel(this.healthLabelText, new Vector2(28, 30), new Color(Colors.TEXT));
        this.healthIcon = new GuiImage("hearticon", new Vector2(10, 30), 16, 16, null, true);
        this.towerToolbar = new GuiToolbar(new Vector2(getInPixels(50, "vw") - 75, getInPixels(100, "vh") - 50));

        this.components.add(this.walletLabel);
        this.components.add(this.walletIcon);
        this.components.add(this.healthLabel);
        this.components.add(this.healthIcon);
        this.components.add(this.towerToolbar);
        if (TowerDefenceGame.theGame.getSettings().isGamePaused) {
            this.makeEscMenu();
        }
    }

    private void makeEscMenu() {
        this.resumeBtn = new GuiButton(new GuiLabel("Resume", new Color(Colors.TEXT)),
                new Vector2(getInPixels(50, "vw") - 100, getInPixels(50, "vh") - 50),
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
                new Vector2(getInPixels(50, "vw") - 100, getInPixels(50, "vh")),
                200, 40,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        ) {
            @Override
            public void onClick() {
                TowerDefenceGame.theGame.getGuiManager().setActiveGui("mainmenu");
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
        this.makeEscMenu();
    }

    private void unpauseGame() {
        TowerDefenceGame.theGame.getGameManager().getCurrentGame().unpause();
        this.removeEscMenu();
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event instanceof UpdateEvent) {
            UpdateEvent e = (UpdateEvent) event;
            Player thePlayer = TowerDefenceGame.theGame.getPlayer();
            this.walletLabelText = thePlayer.getWallet() + " $";
            this.healthLabelText = thePlayer.getHealth() + "/" + thePlayer.getMaxHealth();
            this.walletLabel.setText(this.walletLabelText);
            this.healthLabel.setText(this.healthLabelText);
        } else if (event instanceof KeyEvent) {
            KeyEvent e = (KeyEvent) event;
            if (e.getKey() == TowerDefenceGame.theGame.getSettings().keybindings.get(GUI_BACK) && e.getAction() == KeyAction.UP) {
                // pause game and show menu
                if (!TowerDefenceGame.theGame.getSettings().isGamePaused) {
                    this.pauseGame();
                } else {
                    this.unpauseGame();
                }
            }
        }
    }
}
