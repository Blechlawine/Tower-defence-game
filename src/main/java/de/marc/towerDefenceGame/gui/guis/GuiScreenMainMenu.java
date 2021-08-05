package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiScreen;
import de.marc.towerDefenceGame.gui.components.GuiComponentButton;
import de.marc.towerDefenceGame.gui.components.GuiComponentFlexLayout;
import de.marc.towerDefenceGame.gui.components.GuiComponentImage;
import de.marc.towerDefenceGame.gui.components.GuiComponentText;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;

public class GuiScreenMainMenu extends GuiScreen {

    private GuiComponentButton playButton, settingsButton, creditsButton;
    private GuiComponentImage logo;

    public GuiScreenMainMenu() {
        super("mainmenu", true);
    }

    @Override
    public void setRootComponent() {
        this.root = new GuiComponentFlexLayout(new Vector2(0, 0),
                null,
                getInPixels(100, "vw"),
                getInPixels(80, "vh"),
                false,
                GuiComponentFlexLayout.FlexDirection.VERTICAL,
                GuiComponentFlexLayout.FlexAlignment.CENTER,
                GuiComponentFlexLayout.FlexDistribution.CENTER,
                8
                );
    }

    @Override
    public void registerComponents() {
        ArrayList<GuiComponent> content = new ArrayList<GuiComponent>();
        content.add(this.logo);
        content.add(this.playButton);
        content.add(this.settingsButton);
        content.add(this.creditsButton);
        this.root.setContent(content);
    }

    @Override
    public void createComponents() {
//        Vector2 playBtnPos = new Vector2(getInPixels(50, "vw") - 100, getInPixels(50, "vh") - 20);
        String playBtnText = (this.game.getGameManager().getCurrentGame() != null ? "Resume Game" : "Play");
        this.playButton = new GuiComponentButton(
                new Vector2(0, 0),
                this.root,
                200, 40,
                null,
                true
        ) {
            @Override
            public void onClick() {
                if (this.game.getGameManager().getCurrentGame() != null) {
                    // Resume current Game
                    this.game.getGameManager().getCurrentGame().resume();
                } else {
                    // Start new Game
                    this.game.getGuiManager().setActiveGui("selectLevel");
                }
            }
        };
        this.playButton.setContent(new GuiComponentText(playBtnText, this.playButton));

        this.settingsButton = new GuiComponentButton(
                new Vector2(0, 0),
                this.root,
                200, 40,
                null,
                true
        ) {
            @Override
            public void onClick() {
                this.game.getGuiManager().setActiveGui("settingsMain");
            }
        };
        this.settingsButton.setContent(new GuiComponentText("Settings", this.settingsButton));

//        new Vector2(playBtnPos).add(new Vector2(0, 100));
        this.creditsButton = new GuiComponentButton(
                new Vector2(0, 0),
                this.root,
                200, 40,
                null,
                true
        ) {
            @Override
            public void onClick() {
                this.game.getGuiManager().setActiveGui("credits");
            }
        };
        this.creditsButton.setContent(new GuiComponentText("Credits", this.creditsButton));
//        new Vector2(getInPixels(50, "vw") - 128, getInPixels(50, "vh") - 280);
        this.logo = new GuiComponentImage(
                new Vector2(0, 0),
                this.root,
                256, 256,
                "logo",
                new Color(1, 1, 1), true);
    }
}
