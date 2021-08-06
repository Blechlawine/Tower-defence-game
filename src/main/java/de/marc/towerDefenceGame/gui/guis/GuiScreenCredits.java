package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiScreen;
import de.marc.towerDefenceGame.gui.components.GuiComponentButton;
import de.marc.towerDefenceGame.gui.components.GuiComponentFlexLayout;
import de.marc.towerDefenceGame.gui.components.GuiComponentText;
import de.marc.towerDefenceGame.utils.FileUtils;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;

public class GuiScreenCredits extends GuiScreen {

    private GuiComponentButton backBtn;
    private GuiComponentText credits;

    private String[] creditsText;

    public GuiScreenCredits() {
        super("credits", true);
    }

    @Override
    public void preInit() {
        this.creditsText = FileUtils.readTxtResource("assets/credits.txt");
    }

    @Override
    public void createComponents() {
        this.backBtn = new GuiComponentButton(
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
                this.game.getGuiManager().back();
            }
        };
        this.backBtn.setContent(new GuiComponentText("Back", this.backBtn));

        ArrayList<GuiComponent> credits = new ArrayList<>();

        this.credits = new GuiComponentText(
                new Vector2(0, 0),
                this.root,
                getInPixels(100, "vw"),
                getInPixels(80, "px"),
                this.creditsText,
                2, 1.3,
                GuiComponentText.TextAlignment.CENTER
                );
//        this.credits.setAlignment(GuiComponentText.TextAlignment.LEFT);

    }

    @Override
    public void setRootComponent() {
        this.root = new GuiComponentFlexLayout(new Vector2(0, 0),
                null,
                getInPixels(100, "vw"),
                getInPixels(100, "vh"),
                false,
                GuiComponentFlexLayout.FlexDirection.VERTICAL,
                GuiComponentFlexLayout.FlexAlignment.CENTER,
                GuiComponentFlexLayout.FlexDistribution.CENTER,
                80
        );
    }

    @Override
    public void registerComponents() {
        ArrayList<de.marc.towerDefenceGame.gui.GuiComponent> content = new ArrayList<de.marc.towerDefenceGame.gui.GuiComponent>();
        content.add(this.credits);
        content.add(this.backBtn);
        this.root.setContent(content);
    }
}
