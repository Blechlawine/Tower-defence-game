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
    private List<GuiImage> levelPreviews;

    public GuiSelectLevel() {
        super("selectLevel");
        this.hasBackground = true;
    }

    @Override
    public void initGui() {
        this.levelPreviews = new ArrayList<GuiImage>();
        this.confirmBtn = new GuiButton(
                new GuiLabel("Confirm", new Color(1, 1, 1)),
                new Vector2(this.windowSize.getX() / 2 + 20, this.windowSize.getY() - 50),
                100, 20,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        ) {
            @Override
            public void onClick() {
                TowerDefenceGame.theGame.getGameManager().startNewGame();
            }
        };

        this.levelPreviews.add(new GuiImage(
                "test2Preview",
                new Vector2(50, 50),
                200, 200,
                new Color(1, 0, 0)
        ));

        this.levelPreviews.add(new GuiImage(
                "testbigPreview",
                new Vector2(300, 50),
                200, 200,
                new Color(1, 0, 0)
        ));

        for(GuiImage guiImage : this.levelPreviews) {
            this.components.add(guiImage);
        }
        this.components.add(this.confirmBtn);
    }
}
