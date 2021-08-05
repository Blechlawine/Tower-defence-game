package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiScreen;
import de.marc.towerDefenceGame.gui.components.GuiComponentButton;
import de.marc.towerDefenceGame.gui.components.GuiComponentFlexLayout;
import de.marc.towerDefenceGame.gui.components.GuiComponentText;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;

public class GuiScreenDefeat extends GuiScreen {

    private GuiComponentText defeatLabel;
    private GuiComponentButton mainMenuBtn;

    public GuiScreenDefeat() {
        super("defeat", true);
    }

    @Override
    public void createComponents() {
        double defeatLabelWidth = TowerDefenceGame.theGame.getFontRenderer().getRenderedStringWidth("Defeat", 2);
        this.defeatLabel = new GuiComponentText("Defeat", this.root);
        this.mainMenuBtn = new GuiComponentButton(
                new Vector2(0, 0),
                this.root,
                200, 40,
                null,
                true
        ) {
            @Override
            public void onClick() {
                TowerDefenceGame.theGame.getGuiManager().setActiveGui(TowerDefenceGame.theGame.getGuiManager().getGuiFromName("mainmenu"));
            }
        };
        this.mainMenuBtn.setContent(new GuiComponentText("Return to menu", this.mainMenuBtn));
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
                8
        );
    }

    @Override
    public void registerComponents() {
        ArrayList<GuiComponent> content = new ArrayList<GuiComponent>();
        content.add(this.defeatLabel);
        content.add(this.mainMenuBtn);
        this.root.setContent(content);
    }
}
