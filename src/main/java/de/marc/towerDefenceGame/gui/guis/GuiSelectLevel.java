package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.GuiButton;
import de.marc.towerDefenceGame.gui.components.GuiImage;
import de.marc.towerDefenceGame.gui.components.GuiLabel;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.ArrayList;
import java.util.List;

public class GuiSelectLevel extends Gui {

    private GuiButton confirmBtn;
    private List<GuiButton> levelPreviews;

    private String currentlySelectedLevelName = "";

    public GuiSelectLevel() {
        super("selectLevel");
        this.hasBackground = true;
    }

    @Override
    public void initGui() {
        this.levelPreviews = new ArrayList<GuiButton>();
        this.confirmBtn = new GuiButton(
                new GuiLabel("Confirm", new Color(1, 1, 1)),
                new Vector2(getInPixels(50, "vw") + 20, getInPixels(100, "vh") - 50),
                100, 20,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        ) {
            @Override
            public void onClick() {
                TowerDefenceGame.theGame.getGameManager().startNewGame(currentlySelectedLevelName);
            }
        };

        ArrayList<String[]> levelFiles = TowerDefenceGame.theGame.getLevelFileManager().getContent();
        TowerDefenceGame.theGame.getLogger().debug(levelFiles);
        for (int i = 0; i < levelFiles.size(); i++) {
            String[] temp = levelFiles.get(i);
            TowerDefenceGame.theGame.getLogger().debug(temp[2]);
            Vector2 pos = new Vector2(50 + (220 * i), 50);
            GuiImage tempPreviewImage = new GuiImage(
                    temp[2],
                    new Vector2(-100, -100),
                    200, 200,
                    new Color(0, 0, 0)
            );
            this.levelPreviews.add(new GuiButton(tempPreviewImage,
                    pos,
                    200, 200,
                    new Color(Colors.BACKGROUND),
                    new Color(1, 0, 0)) {
                @Override
                public void onClick() {
                    currentlySelectedLevelName = temp[1];
                }
            });
        }

        this.components.addAll(this.levelPreviews);
        this.components.add(this.confirmBtn);
    }
}
