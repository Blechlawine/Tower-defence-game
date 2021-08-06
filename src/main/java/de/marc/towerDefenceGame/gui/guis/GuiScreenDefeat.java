package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiScreen;
import de.marc.towerDefenceGame.gui.components.GuiComponentButton;
import de.marc.towerDefenceGame.gui.components.GuiComponentFlexLayout;
import de.marc.towerDefenceGame.gui.components.GuiComponentImage;
import de.marc.towerDefenceGame.gui.components.GuiComponentText;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;

public class GuiScreenDefeat extends GuiScreen {

    private GuiComponentText defeatLabel, killsLabel, moneyLabel;
    private GuiComponentImage killsIcon, moneyIcon;
    private GuiComponentFlexLayout killsLayout, moneyLayout;
    private GuiComponentButton mainMenuBtn;

    public GuiScreenDefeat() {
        super("defeat", true);
    }

    @Override
    public void createComponents() {
        double defeatLabelWidth = this.game.getFontRenderer().getRenderedStringWidth("Defeat", 3);
        this.defeatLabel = new GuiComponentText("Defeat", this.root);
        this.defeatLabel.setFontSize(3);
        this.killsLayout = new GuiComponentFlexLayout(
                new Vector2(0, 0),
                this.root,
                0,
                0,
                false,
                GuiComponentFlexLayout.FlexDirection.HORIZONTAL,
                GuiComponentFlexLayout.FlexAlignment.CENTER,
                GuiComponentFlexLayout.FlexDistribution.CENTER,
                4
        );
        this.killsLabel = new GuiComponentText(
                String.valueOf(this.game.getPlayer().getNumKills()),
                this.killsLayout
        );
        this.killsLabel.setAlignment(GuiComponentText.TextAlignment.LEFT);
        this.killsIcon = new GuiComponentImage(
                new Vector2(0, -2),
                this.killsLayout,
                16, 16,
                "killcounticon",
                null,
                true
        );
        ArrayList<GuiComponent> kills = new ArrayList<>();
        kills.add(this.killsIcon);
        kills.add(this.killsLabel);
        this.killsLayout.setContent(kills);
        this.moneyLayout = new GuiComponentFlexLayout(
                new Vector2(0, 0),
                this.root,
                0,
                0,
                false,
                GuiComponentFlexLayout.FlexDirection.HORIZONTAL,
                GuiComponentFlexLayout.FlexAlignment.CENTER,
                GuiComponentFlexLayout.FlexDistribution.CENTER,
                4
        );
        this.moneyLabel = new GuiComponentText(
                String.valueOf(this.game.getPlayer().getWallet()) + " $",
                this.moneyLayout
        );
        this.moneyLabel.setAlignment(GuiComponentText.TextAlignment.LEFT);
        this.moneyIcon = new GuiComponentImage(
                new Vector2(0, -2),
                this.moneyLayout,
                16, 16,
                "moneyicon",
                null, true
        );
        ArrayList<GuiComponent> money = new ArrayList<>();
        money.add(this.moneyIcon);
        money.add(this.moneyLabel);
        this.moneyLayout.setContent(money);
        this.mainMenuBtn = new GuiComponentButton(
                new Vector2(0, 0),
                this.root,
                200, 40,
                null,
                true,
                true,
                true
        ) {
            @Override
            public void onClick() {
                this.game.getGuiManager().setActiveGui(this.game.getGuiManager().getGuiFromName("mainmenu"));
            }
        };
        this.mainMenuBtn.setContent(new GuiComponentText("Return to menu", this.mainMenuBtn));
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
                30
        );
    }

    @Override
    public void registerComponents() {
        ArrayList<GuiComponent> content = new ArrayList<GuiComponent>();
        content.add(this.defeatLabel);
        content.add(this.killsLayout);
        content.add(this.moneyLayout);
        content.add(this.mainMenuBtn);
        this.root.setContent(content);
        this.game.getMusicManager().startEndMusic();
    }
}
