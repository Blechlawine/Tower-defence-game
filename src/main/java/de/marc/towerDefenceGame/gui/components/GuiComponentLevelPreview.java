package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiInteractableComponent;
import de.marc.towerDefenceGame.texture.Texture;
import de.marc.towerDefenceGame.utils.*;

public abstract class GuiComponentLevelPreview extends GuiInteractableComponent {

    private boolean selected;
    private Keybinding interactionBinding;
    protected String levelName;
    private String previewTextureHandle;
    private double imageWidth, imageHeight, finalImageWidth, finalImageHeight;
    private double mouseX, mouseY;

    public GuiComponentLevelPreview(Vector2 relativePos,
                                    GuiComponent parent,
                                    double width,
                                    double height,
                                    String levelName,
                                    String previewTextureHandle,
                                    boolean selectedDefault
    ) {
        super(relativePos, parent, width, height);
        this.levelName = levelName;
        this.selected = selectedDefault;
        this.previewTextureHandle = previewTextureHandle;
        Texture temp = TowerDefenceGame.theGame.getTextureManager().getTextureFromName(this.previewTextureHandle);
        this.imageHeight = temp.getHeight();
        this.imageWidth = temp.getWidth();
        if (this.imageWidth == this.imageHeight) {
            this.finalImageWidth = this.height;
            this.finalImageHeight = this.height;
        } else if (this.imageWidth > this.imageHeight) {
            this.finalImageWidth = this.height;
            this.finalImageHeight = this.height / this.imageWidth * this.imageHeight;
        } else if (this.imageHeight > this.imageWidth) {
            this.finalImageWidth = this.height / this.imageHeight * this.imageWidth;
            this.finalImageHeight = this.height;
        }
        this.interactionBinding = new Keybinding(Settings.KeyBindings.GUI_INTERACT, new KeyAction[]{KeyAction.UP}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (hovered) {
                    if (!selected) {
                        select();
                    }
                    selected = !selected;
                }
            }
        };
    }

    public abstract void select();

    public void deselect() {
        this.selected = false;
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        this.interactionBinding.onEvent(event);
    }


    @Override
    public void render() {
        GLUtils.drawRect(this.getAbsolutePos().getX(), this.getAbsolutePos().getY(), this.width, this.height, new Color(Colors.BACKGROUND));
        GLUtils.drawTexturedRect(this.getAbsolutePos().getX(),
                this.getAbsolutePos().getY(),
                this.finalImageWidth,
                this.finalImageHeight,
                0,
                0,
                1,
                1,
                this.previewTextureHandle,
                new Color(1, 1, 1));

        this.game.getFontRenderer().drawString(this.levelName,
                new Vector2(this.getAbsolutePos()).add(this.height, 0),
                2,
                new Color(Colors.TEXT)
                );
        if (this.selected) {
            GLUtils.drawTexturedRect(this.getAbsolutePos().getX(),
                    this.getAbsolutePos().getY(),
                    this.height,
                    this.height,
                    0,
                    0,
                    1,
                    1,
                    "cursor",
                    new Color(1, 1, 1));
//            GLUtils.drawRect(this.getAbsolutePos().getX(), this.getAbsolutePos().getY(), this.width, this.height, new Color(Colors.BUTTONPRIMARYHOVER));
        }
    }
}
