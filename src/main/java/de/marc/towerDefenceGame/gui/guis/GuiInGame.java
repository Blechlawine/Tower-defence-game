package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.*;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.player.Player;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.KeyAction;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.HashMap;

import static de.marc.towerDefenceGame.utils.Settings.KeyBindings.GUI_BACK;

public class GuiInGame extends Gui {

    private GuiLabel walletLabel, healthLabel;
    private GuiImage walletIcon, healthIcon;
    private GuiToolbar towerToolbar;
    private boolean paused = false, showDetailsPanel = false;
    private GuiButton resumeBtn, exitBtn, settingsBtn;

    private final double detailsPanelWidth = 200, detailsPanelHeight = 400;
    private GuiPanel detailsPanel;

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
        if (this.showDetailsPanel) {
            this.makeDetailsPanel();
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

    public void makeDetailsPanel() {
        this.showDetailsPanel = true;
        HashMap<String, String> details = Tile.selectedTile.getDetails();
        // The details panel is positioned on the right side vertically centered
        Vector2 panelPos = new Vector2(
                getInPixels(100, "vw") - this.detailsPanelWidth,
                getInPixels(50, "vh") - this.detailsPanelHeight/2);
        GuiLabel titleLabel = new GuiLabel(details.get("name"), new Color(Colors.TEXT), 2.5);
        GuiText descriptionText = new GuiText(
                details.get("description"),
                new Vector2(0, 0),
                this.detailsPanelWidth,
                2, 1.3,
                GuiText.TextAlignment.LEFT);
        GuiComponent[] panelComponents = new GuiComponent[] {
                titleLabel,
                descriptionText
        };
        GuiFlexLayout detailsPanelLayout = new GuiFlexLayout(
                new Vector2(0, 0),
                this.detailsPanelWidth,
                this.detailsPanelHeight,
                "column",
                "flex-start",
                "start",
                20, panelComponents);
        this.detailsPanel = new GuiPanel(panelPos, this.detailsPanelWidth, this.detailsPanelHeight, true, new GuiComponent[] {detailsPanelLayout});
        this.components.add(this.detailsPanel);
    }

    public void removeDetailsPanel() {
        this.showDetailsPanel = false;
        this.components.remove(this.detailsPanel);
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
