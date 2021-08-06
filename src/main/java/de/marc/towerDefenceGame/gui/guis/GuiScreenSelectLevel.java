package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiScreen;
import de.marc.towerDefenceGame.gui.components.*;
import de.marc.towerDefenceGame.utils.*;

import java.util.ArrayList;
import java.util.Objects;

public class GuiScreenSelectLevel extends GuiScreen {

    private GuiComponentButton confirmBtn, cancelBtn;
    private ArrayList<GuiComponent> levelPreviews;
    private GuiComponentFlexLayout levelPreviewsLayout, buttonLayout;
    private GuiComponentShape highlight;

    private String currentlySelectedLevelName = "";
    private Vector2 highlightLocation;

    public GuiScreenSelectLevel() {
        super("selectLevel", true);
    }

    @Override
    public void createComponents() {
        this.levelPreviews = new ArrayList<>();

        this.buttonLayout = new GuiComponentFlexLayout(
                new Vector2(0, 0),
                this.root,
                420, 40,
                false,
                GuiComponentFlexLayout.FlexDirection.HORIZONTAL,
                GuiComponentFlexLayout.FlexAlignment.CENTER,
                GuiComponentFlexLayout.FlexDistribution.CENTER,
                20
        );

        ArrayList<GuiComponent> buttons = new ArrayList<>();

        this.confirmBtn = new GuiComponentButton(
//                new Vector2(getInPixels(50, "vw") + 10, getInPixels(100, "vh") - 20 - 40),
                new Vector2(0, 0),
                this.buttonLayout,
                200, 40,
                null,
                true,
                true,
                true
        ) {
            @Override
            public void onClick() {
                this.game.getGameManager().startNewGame(currentlySelectedLevelName);
            }
        };
        this.confirmBtn.setContent(new GuiComponentText("Play", this.confirmBtn));

        this.cancelBtn = new GuiComponentButton(
                new Vector2(0, 0),
                this.buttonLayout,
//                new Vector2(getInPixels(50, "vw") - 10 - 200, getInPixels(100, "vh") - 20 - 40),
                200, 40,
                null,
                true,
                true,
                true
        ) {
            @Override
            public void onClick() {
                // back to main menu
                this.game.getGuiManager().setActiveGui(this.game.getGuiManager().getGuiFromName("mainmenu"));
            }
        };
        this.cancelBtn.setContent(new GuiComponentText("Cancel", this.cancelBtn));
        buttons.add(this.cancelBtn);
        buttons.add(this.confirmBtn);
        this.buttonLayout.setContent(buttons);

        ArrayList<String[]> levelFiles = TowerDefenceGame.theGame.getLevelFileManager().getContent();
//        TowerDefenceGame.theGame.getLogger().debug(levelFiles);
        this.currentlySelectedLevelName = levelFiles.get(0)[1]; // first level selected by default
        this.highlightLocation = new Vector2(50, 50); // initial highlightlocation
        double levelPreviewHeight = Math.max(getInPixels(10, "vh"), 150);
        this.highlight = new GuiComponentShape(
                this.highlightLocation,
                this.root,
                getInPixels(60, "vw"),
                levelPreviewHeight,
                new Color(1, 1, 1)
        ) {
            @Override
            public void renderShape() {
                GLUtils.drawRectOutline(this.getAbsolutePos().getX(), this.getAbsolutePos().getY(), this.width, this.height, 10, this.color);
            }
        };

        Vector2 levelPreviewScrollPos = new Vector2(0, 0);

        for (int i = 0; i < levelFiles.size(); i++) {
            String[] temp = levelFiles.get(i);
//            Vector2 pos = new Vector2(50, 50 + ((levelPreviewHeight + 10) * i));
//            GuiComponentImage tempPreviewImage = new GuiComponentImage(
//                    new Vector2(0, 0),
//                    new Vector2(0, 0),
//                    levelPreviewHeight,
//                    levelPreviewHeight,
//                    temp[2],
//                    new Color(0, 0, 0),
//                    false
//            );
            this.levelPreviews.add(new GuiComponentLevelPreview(
                    new Vector2(0, 0),
                    this.levelPreviewsLayout,
                    getInPixels(60, "vw"),
                    levelPreviewHeight,
                    temp[1],
                    temp[2],
                    Objects.equals(temp[1], this.currentlySelectedLevelName)
            ) {
                @Override
                public void select() {
                    for(GuiComponent levelPreview : levelPreviews) {
                        if (levelPreview instanceof GuiComponentLevelPreview) {
                            ((GuiComponentLevelPreview) levelPreview).deselect();
                        }
                    }
                    currentlySelectedLevelName = this.levelName;
                }
            });
        }
//        this.levelPreviews.add(this.highlight);

        this.levelPreviewsLayout = new GuiComponentFlexLayout(
                levelPreviewScrollPos,
                this.root,
                getInPixels(100, "vw"),
                getInPixels(100, "vh") - 100,
                true,
                GuiComponentFlexLayout.FlexDirection.VERTICAL,
                GuiComponentFlexLayout.FlexAlignment.CENTER,
                GuiComponentFlexLayout.FlexDistribution.START,
                8
        );
        this.levelPreviewsLayout.setContent(this.levelPreviews);
    }

    @Override
    public void setRootComponent() {
        this.root = new GuiComponentFlexLayout(
                new Vector2(0, 0),
                null,
                getInPixels(100, "vw"),
                getInPixels(100, "vh"),
                false,
                GuiComponentFlexLayout.FlexDirection.VERTICAL,
                GuiComponentFlexLayout.FlexAlignment.CENTER,
                GuiComponentFlexLayout.FlexDistribution.START,
                20
        );
    }

    @Override
    public void registerComponents() {
        ArrayList<GuiComponent> content = new ArrayList<>();
        content.add(this.levelPreviewsLayout);
        content.add(this.buttonLayout);
        this.root.setContent(content);
    }
}
