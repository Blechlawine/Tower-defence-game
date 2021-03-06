package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.event.events.UnPausedPostUpdateEvent;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.gameObjects.tower.Tower;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiScreen;
import de.marc.towerDefenceGame.gui.components.*;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.player.Player;
import de.marc.towerDefenceGame.utils.KeyAction;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

import static de.marc.towerDefenceGame.utils.Settings.KeyBindings.GUI_BACK;

public class GuiScreenInGame extends GuiScreen {

    private GuiComponentText walletLabel, healthLabel, killCountLabel, firstWaveCountdownLabel, nextWaveBonusLabel;
    private GuiComponentImage walletIcon, healthIcon, killCountIcon, nextWaveBonusIcon;
    private GuiComponentToolbar towerToolbar;
    private GuiComponentButton nextWaveButton;
    private boolean showDetailsPanel = false, showEscMenu = false;

    private GuiComponentFlexLayout escMenu;
    private GuiComponentButton resumeBtn, exitBtn, settingsBtn;

    private final double detailsPanelWidth = 200, detailsPanelHeight = 400;
    private GuiComponentPanel detailsPanel;
    private GuiComponentText titleLabel, descriptionText;
    private GuiComponentDropDown targetModeDropDown;

    private String walletLabelText = "- $", healthLabelText = "", killCountLabelText = "";
    private GuiComponentFlexLayout detailsPanelLayout;
    private ArrayList<GuiComponent> detailsPanelComponents;

    public GuiScreenInGame() {
        super("ingame", false);
    }

    @Override
    public void createComponents() {
        this.walletLabel = new GuiComponentText("- $", new Vector2(28, 10), this.root);
        this.walletLabel.setAlignment(GuiComponentText.TextAlignment.LEFT);
        this.walletIcon = new GuiComponentImage(
                new Vector2(10, 10),
                this.root,
                16,
                16,
                "moneyicon",
                null,
                true);

        this.healthLabel = new GuiComponentText("", new Vector2(28, 30), this.root);
        this.healthLabel.setAlignment(GuiComponentText.TextAlignment.LEFT);
        this.killCountLabel = new GuiComponentText("", new Vector2(28, 50), this.root);
        this.killCountLabel.setAlignment(GuiComponentText.TextAlignment.LEFT);
        this.healthIcon = new GuiComponentImage(
                new Vector2(10, 30),
                this.root,
                16,
                16,
                "hearticon",
                null,
                true
        );
        this.killCountIcon = new GuiComponentImage(
                new Vector2(10, 50),
                this.root,
                16,
                16,
                "killcounticon",
                null,
                true
        );

        this.firstWaveCountdownLabel = new GuiComponentText(
                new Vector2(getInPixels(50, "vw"), getInPixels(10, "vh")),
                this.root,
                TowerDefenceGame.theGame.getFontRenderer().getRenderedStringWidth("14", 4),
                TowerDefenceGame.theGame.getFontRenderer().getCharHeight(4),
                "14",
                4,
                1.3,
                GuiComponentText.TextAlignment.CENTER);

        this.towerToolbar = new GuiComponentToolbar(
                new Vector2(getInPixels(50, "vw") - 75, getInPixels(100, "vh") - 50),
                this.root
        );

        GuiComponentImage nextWaveIcon = new GuiComponentImage(
                new Vector2(0, 0),
                null,
                60, 60,
                "nextwaveicon",
                null, true
        );
        Vector2 nextWaveButtonPos = new Vector2(getInPixels(100, "vw") - 80, getInPixels(100, "vh") - 80);
        this.nextWaveButton = new GuiComponentButton(
                nextWaveButtonPos,
                this.root,
                60, 60,
                nextWaveIcon,
                false,
                false,
                false
        ) {
            @Override
            public void onClick() {
                if (this.game.getGameManager().getCurrentGame().getLevel().spawner.allowNextWave) {
                    int bonus = this.game.getGameManager().getCurrentGame().getLevel().spawner.calculateNextWaveBonus();
                    this.game.getPlayer().addMoney(bonus);
                    this.game.getGameManager().getCurrentGame().getLevel().spawner.nextWave();
                }
            }
        };

        this.nextWaveBonusIcon = new GuiComponentImage(
                new Vector2(nextWaveButtonPos).add(0, -10),
                this.root,
                16, 16,
                "moneyicon",
                null,
                true
        );
        this.nextWaveBonusLabel = new GuiComponentText(
                new Vector2(nextWaveButtonPos).add(16, -10),
                this.root,
                10,
                this.game.getFontRenderer().getCharHeight(2),
                "",
                2,
                1.3,
                GuiComponentText.TextAlignment.LEFT
        );

        // DetailsPanel
        this.detailsPanelComponents = new ArrayList<>();
//        HashMap<String, String> details = Tile.selectedTile.getDetails();
        // The details panel is positioned on the right side vertically centered
        Vector2 panelPos = new Vector2(
                getInPixels(100, "vw") - this.detailsPanelWidth,
                getInPixels(50, "vh") - this.detailsPanelHeight/2);
        this.titleLabel = new GuiComponentText(
                new Vector2(0, 0),
                null,
                this.detailsPanelWidth,
                0,
                "name",
                3,
                1.3,
                GuiComponentText.TextAlignment.LEFT);
        this.descriptionText = new GuiComponentText(
                new Vector2(0, 0),
                null,
                this.detailsPanelWidth,
                0,
                "description",
                2, 1.3,
                GuiComponentText.TextAlignment.LEFT);
        ArrayList<String> targetModes = new ArrayList<>();
        for (Tower.TargetMode mode : Tower.TargetMode.values()) {
            targetModes.add(mode.toString());
        }
        this.targetModeDropDown = new GuiComponentDropDown(
                new Vector2(0, 0),
                null,
                this.detailsPanelWidth,
                40,
                targetModes.toArray(new String[0]),
                "First") {
            @Override
            protected void valueChange(String oldValue, String newValue) {
                if (Tile.selectedTile.getTower() != null) {
                    Tower selectedTower = Tile.selectedTile.getTower();
                    switch (newValue.toLowerCase()) {
                        case "first":
                            selectedTower.setTargetMode(Tower.TargetMode.FIRST);
                            break;
                        case "random":
                            selectedTower.setTargetMode(Tower.TargetMode.RANDOM);
                            break;
                        case "weakest":
                            selectedTower.setTargetMode(Tower.TargetMode.WEAKEST);
                            break;
                        case "strongest":
                            selectedTower.setTargetMode(Tower.TargetMode.STRONGEST);
                            break;
                    }
                }
            }
        };
        this.detailsPanelComponents.add(this.titleLabel);
        this.detailsPanelComponents.add(this.descriptionText);
        this.detailsPanelComponents.add(this.targetModeDropDown);

        this.detailsPanelLayout = new GuiComponentFlexLayout(
                new Vector2(0, 0),
                this.detailsPanel,
                this.detailsPanelWidth,
                this.detailsPanelHeight,
                false,
                GuiComponentFlexLayout.FlexDirection.VERTICAL,
                GuiComponentFlexLayout.FlexAlignment.START,
                GuiComponentFlexLayout.FlexDistribution.START,
                16);

        this.detailsPanelLayout.setContent(this.detailsPanelComponents);

        this.detailsPanel = new GuiComponentPanel(
                panelPos,
                this.root,
                this.detailsPanelWidth,
                this.detailsPanelHeight,
                this.detailsPanelLayout,
                true
        );
        this.detailsPanel.setVisible(false);

        // Esc menu
        this.escMenu = new GuiComponentFlexLayout(
                new Vector2(0, 0),
                this.root,
                getInPixels(100, "vw"),
                getInPixels(100, "vh"),
                false,
                GuiComponentFlexLayout.FlexDirection.VERTICAL,
                GuiComponentFlexLayout.FlexAlignment.CENTER,
                GuiComponentFlexLayout.FlexDistribution.CENTER,
                8
        );
        ArrayList<GuiComponent> escMenuComponents = new ArrayList<>();
        this.resumeBtn = new GuiComponentButton(
                new Vector2(0, 0),
                this.escMenu,
                200, 40,
                null,
                true,
                true,
                true
        ) {
            @Override
            public void onClick() {
                unpauseGame();
            }
        };
        this.resumeBtn.setContent(new GuiComponentText("Resume", this.resumeBtn));
        escMenuComponents.add(this.resumeBtn);
        this.exitBtn = new GuiComponentButton(
                new Vector2(0, 0),
                this.escMenu,
                200, 40,
                null,
                true,
                true,
                true
        ) {
            @Override
            public void onClick() {
                TowerDefenceGame.theGame.getGuiManager().setActiveGui("mainmenu");
            }
        };
        this.exitBtn.setContent(new GuiComponentText("Return to menu", this.exitBtn));
        escMenuComponents.add(this.exitBtn);
        this.escMenu.setContent(escMenuComponents);
        this.escMenu.setVisible(false);
    }

    @Override
    public void setRootComponent() {
        this.root = new GuiComponentFreeLayout(
                new Vector2(0, 0),
                null,
                getInPixels(100, "vw"),
                getInPixels(100, "vh"),
                false
        );
    }

    @Override
    public void registerComponents() {
        ArrayList<GuiComponent> content = new ArrayList<>();
        content.add(this.walletLabel);
        content.add(this.walletIcon);
        content.add(this.healthLabel);
        content.add(this.healthIcon);
        content.add(this.killCountIcon);
        content.add(this.killCountLabel);
        content.add(this.towerToolbar);
        content.add(this.firstWaveCountdownLabel);
        content.add(this.nextWaveButton);
        content.add(this.nextWaveBonusIcon);
        content.add(this.nextWaveBonusLabel);
        content.add(this.detailsPanel);
        content.add(this.escMenu);
        this.root.setContent(content);
    }

    private void showEscMenu() {
        this.showEscMenu = true;
        this.escMenu.setVisible(true);
    }

    public void showDetailsPanel() {
        this.showDetailsPanel = true;
        this.detailsPanel.setVisible(true);
    }

    public void hideDetailsPanel() {
        this.showDetailsPanel = false;
        this.detailsPanel.setVisible(false);
    }

    private void hideEscMenu() {
        this.showEscMenu = false;
        this.escMenu.setVisible(false);
    }

    public void updateDetailsPanel() {
        if (Tile.selectedTile.getTower() != null) {
            this.targetModeDropDown.setVisible(true);
            this.targetModeDropDown.setValue(Tile.selectedTile.getTower().getTargetMode().toString());
        } else {
            this.targetModeDropDown.setVisible(false);
        }
        HashMap<String, String> details = Tile.selectedTile.getDetails();
        String desc = details.get("description");
        String title = details.get("name");
        this.game.getLogger().debug(title, desc);
        this.descriptionText.setText(desc);
        this.titleLabel.setText(title);
        for(GuiComponent c : this.detailsPanelComponents) {
            c.setRelativePos(new Vector2(0, 0));
        }
        this.detailsPanelLayout.setContent(this.detailsPanelComponents);
    }

    private void pauseGame() {
        TowerDefenceGame.theGame.getGameManager().getCurrentGame().pause();
        this.showEscMenu();
    }

    private void unpauseGame() {
        TowerDefenceGame.theGame.getGameManager().getCurrentGame().unpause();
        this.hideEscMenu();
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event instanceof UnPausedPostUpdateEvent) {
            Player thePlayer = TowerDefenceGame.theGame.getPlayer();
            this.walletLabelText = thePlayer.getWallet() + " $";
            this.healthLabelText = thePlayer.getHealth() + "/" + thePlayer.getMaxHealth();
            this.killCountLabelText = String.valueOf(thePlayer.getNumKills());
            this.walletLabel.setWidth(this.game.getFontRenderer().getRenderedStringWidth(this.walletLabelText, 2));
            this.healthLabel.setWidth(this.game.getFontRenderer().getRenderedStringWidth(this.healthLabelText, 2));
            this.killCountLabel.setWidth(this.game.getFontRenderer().getRenderedStringWidth(this.killCountLabelText, 2));
            this.walletLabel.setText(this.walletLabelText);
            this.healthLabel.setText(this.healthLabelText);
            this.killCountLabel.setText(this.killCountLabelText);
            String firstWaveCountDownText = this.game.getGameManager().getCurrentGame().getLevel().spawner.getFirstWaveCountdown();
            this.firstWaveCountdownLabel.setWidth(this.game.getFontRenderer().getRenderedStringWidth(firstWaveCountDownText, 4));
            this.firstWaveCountdownLabel.setText(firstWaveCountDownText);
            String nextWaveBonusText = "+"+this.game.getGameManager().getCurrentGame().getLevel().spawner.calculateNextWaveBonus();
            this.nextWaveBonusLabel.setWidth(this.game.getFontRenderer().getRenderedStringWidth(nextWaveBonusText, 2));
            this.nextWaveBonusLabel.setText(nextWaveBonusText);
            if (!this.showDetailsPanel && Tile.selectedTile != null) {
                this.updateDetailsPanel();
                this.showDetailsPanel();
            } else if (this.showDetailsPanel && Tile.selectedTile == null) {
                this.hideDetailsPanel();
            }
            if (this.game.getGameManager().getCurrentGame().getLevel().spawner.allowNextWave && !this.game.getSettings().isGamePaused) {
                this.nextWaveButton.setVisible(true);
                this.nextWaveBonusLabel.setVisible(true);
                this.nextWaveBonusIcon.setVisible(true);
            } else {
                this.nextWaveButton.setVisible(false);
                this.nextWaveBonusLabel.setVisible(false);
                this.nextWaveBonusIcon.setVisible(false);
            }
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
