package de.marc.towerDefenceGame.gui.guis;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.gui.components.*;
import de.marc.towerDefenceGame.utils.*;

import java.util.ArrayList;
import java.util.List;

public class GuiSelectLevel extends Gui {

    private GuiButton confirmBtn, cancelBtn;
    private List<GuiComponent> levelPreviews;
    private GuiScrollContent levelPreviewScroll;
    private GuiShape highlight;

    private String currentlySelectedLevelName = "";
    private Vector2 highlightLocation;

    public GuiSelectLevel() {
        super("selectLevel");
        this.hasBackground = true;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.levelPreviews = new ArrayList<>();
        this.confirmBtn = new GuiButton(
                new GuiLabel("Confirm", new Color(1, 1, 1)),
                new Vector2(getInPixels(50, "vw") + 10, getInPixels(100, "vh") - 20 - 40),
                200, 40,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        ) {
            @Override
            public void onClick() {
                TowerDefenceGame.theGame.getGameManager().startNewGame(currentlySelectedLevelName);
            }
        };
        this.cancelBtn = new GuiButton(
                new GuiLabel("Cancel", new Color(1, 1, 1)),
                new Vector2(getInPixels(50, "vw") - 10 - 200, getInPixels(100, "vh") - 20 - 40),
                200, 40,
                new Color(Colors.BUTTONPRIMARY),
                new Color(Colors.BUTTONPRIMARYHOVER)
        ) {
            @Override
            public void onClick() {
                // back to main menu
                TowerDefenceGame.theGame.getGuiManager().setActiveGui(TowerDefenceGame.theGame.getGuiManager().getGuiFromName("mainmenu"));
            }
        };

        ArrayList<String[]> levelFiles = TowerDefenceGame.theGame.getLevelFileManager().getContent();
//        TowerDefenceGame.theGame.getLogger().debug(levelFiles);
        this.currentlySelectedLevelName = levelFiles.get(0)[1]; // first level selected by default
        this.highlightLocation = new Vector2(50, 50); // initial highlightlocation
        double levelPreviewHeight = Math.max(getInPixels(10, "vh"), 150);
        this.highlight = new GuiShape(this.highlightLocation, getInPixels(60, "vw"), levelPreviewHeight, new Color(1, 1, 1)) {
            @Override
            public void render() {
                GLUtils.drawRectOutline(this.pos.getX(), this.pos.getY(), this.width, this.height, 10, this.color);
            }
        };
        for (int i = 0; i < levelFiles.size(); i++) {
            String[] temp = levelFiles.get(i);
            Vector2 pos = new Vector2(50, 50 + ((levelPreviewHeight + 10) * i));
            GuiImage tempPreviewImage = new GuiImage(
                    temp[2],
                    new Vector2(0, 0),
                    levelPreviewHeight, levelPreviewHeight,
                    new Color(0, 0, 0),
                    false
            );
            ArrayList<GuiComponent> tempComponents = new ArrayList<>();
            tempComponents.add(tempPreviewImage);
            tempComponents.add(new GuiLabel(temp[1], new Vector2(10, 10), new Color(Colors.TEXT)));
            this.levelPreviews.add(new GuiFlexLayout(
                    pos,
                    getInPixels(60, "vw"),
                    levelPreviewHeight,
                    "row", "center", "start",
                    0,
                    tempComponents.toArray(new GuiComponent[0]),
                    new Keybinding(Settings.KeyBindings.GUI_INTERACT, new KeyAction[]{KeyAction.UP}) {
                @Override
                public void onKeyAction(KeyAction action, KeyEvent event) {
                    currentlySelectedLevelName = temp[1];
                    highlightLocation = pos;
                    highlight.updatePos(highlightLocation);
                }
            }));
        }
        this.levelPreviews.add(this.highlight);

        this.levelPreviewScroll = new GuiScrollContent(new Vector2(0, 0), getInPixels(100, "vw"), getInPixels(100, "vh") - 100, this.levelPreviews.toArray(new GuiComponent[0]));

        this.components.add(this.levelPreviewScroll);
        this.components.add(this.confirmBtn);
        this.components.add(this.cancelBtn);
    }
}
