package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiInteractableComponent;
import de.marc.towerDefenceGame.sound.SoundSource;
import de.marc.towerDefenceGame.utils.*;
import org.lwjgl.opengl.GL11;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;
import static de.marc.towerDefenceGame.utils.KeyAction.UP;
import static de.marc.towerDefenceGame.utils.Settings.KeyBindings.GUI_INTERACT;

public abstract class GuiComponentButton extends GuiInteractableComponent {

    private GuiComponent content;
    protected Color color, initialColor, hoverColor;
    private SoundSource soundSource;

    private Keybinding pressBinding;

    private byte state; // 0 == normal; 1 == hovered; 2 == pressed;

    public GuiComponentButton(Vector2 relativePos,
                              GuiComponent parent,
                              double width,
                              double height,
                              GuiComponent content,
                              boolean primary
    ) {
        super(relativePos, parent, width, height);

        this.initialColor = color;
        this.color = new Color(primary ? Colors.BUTTONPRIMARY : Colors.BUTTONPRIMARY);
        this.hoverColor = new Color(primary ? Colors.BUTTONPRIMARYHOVER : Colors.BUTTONPRIMARYHOVER);
        this.soundSource = TowerDefenceGame.theGame.getSoundSourceManager().createSoundSource("click",false, SoundSource.SoundSourceCategory.SFX);
        this.pressBinding = new Keybinding(GUI_INTERACT, new KeyAction[] {DOWN, UP}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (state == 1) { // this button is hovered
                    if (action == DOWN) {
                        state = 2;
                        event.cancel();
                    }
                } else if (state == 2) { // this button is pressed
                    if (action == UP) {
                        state = 1;
                        onClick();
                        soundSource.play();
                        event.cancel();
                    }
                }
            }
        };
    }

    public abstract void onClick();

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        this.pressBinding.onEvent(event);
    }

    protected void onMouseIn() {
        this.state = 1;
        this.color = this.hoverColor;
    }
    protected void onMouseOut() {
        this.state = 0;
        this.color = this.initialColor;
    }

    @Override
    public void render() {
        String textureHandle = (this.state == 2 ? "buttonpressed" : "button");
        // left side
        GLUtils.drawTexturedRect(this.getAbsolutePos().getX(), this.getAbsolutePos().getY(), this.height, this.height, 0, 0, 1, 1, textureHandle, new Color(1, 1, 1));
        // right side
        GLUtils.drawTexturedRect(this.getAbsolutePos().getX() + this.width - this.height, this.getAbsolutePos().getY(), this.height, this.height, 1, 0, -1, 1, textureHandle, new Color(1, 1, 1));
        // middle part
        GLUtils.drawTexturedRect(this.getAbsolutePos().getX() + this.height, this.getAbsolutePos().getY(), this.width - (this.height*2), this.height, 0.5, 0, 0.5, 1, textureHandle, new Color(1, 1, 1));
        double pressedTextOffset = this.height / 16 * 3;
        GL11.glPushMatrix();
        GL11.glTranslated((this.width / 2),
                (this.state == 2 ? pressedTextOffset / 2 : -pressedTextOffset / 2) + (this.height / 3),
                0);
        this.content.render();
        GL11.glPopMatrix();
    }

    public void setContent(GuiComponent content) {
        this.content = content;
        this.content.setParent(this);
    }
}
