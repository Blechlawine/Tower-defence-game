package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiInteractableComponent;
import de.marc.towerDefenceGame.utils.*;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;
import static de.marc.towerDefenceGame.utils.Settings.KeyBindings.GUI_INTERACT;

public class GuiComponentKeyCodeInput extends GuiInteractableComponent {

    private KeyEvent.KeyCode value;
    private Settings.KeyBindings settingToChange;

    private Keybinding clickBinding;
    private boolean active;

    public GuiComponentKeyCodeInput(Vector2 relativePos,
                                    GuiComponent parent,
                                    double width,
                                    double height,
                                    KeyEvent.KeyCode defaultValue,
                                    Settings.KeyBindings settingToChange
    ) {
        super(relativePos, parent, width, height);
        this.value = defaultValue;
        this.settingToChange = settingToChange;
        this.active = false;
        this.clickBinding = new Keybinding(GUI_INTERACT, new KeyAction[]{DOWN}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (hovered) {
                    active = true;
//                    game.getLogger().debug(value);
                }
            }
        };
    }


    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (this.active && event instanceof KeyEvent && ((KeyEvent) event).getAction() == DOWN) {
            KeyEvent ke = (KeyEvent) event;
            this.value = ke.getKey();
            this.game.getSettings().keybindings.replace(this.settingToChange, this.value);
            this.active = false;
            event.cancel();
        } else {
            this.clickBinding.onEvent(event);
        }
    }

    @Override
    public void render() {
        double padding = (this.height - TowerDefenceGame.theGame.getFontRenderer().getCharHeight(2)) / 2;
        String textureHandle = "textinput";
        // left side
        GLUtils.drawTexturedRect(this.getAbsolutePos().getX(), this.getAbsolutePos().getY(), this.height, this.height, 0, 0, 1, 1, textureHandle, new Color(1, 1, 1));
        // right side
        GLUtils.drawTexturedRect(this.getAbsolutePos().getX() + this.width - this.height, this.getAbsolutePos().getY(), this.height, this.height, 1, 0, -1, 1, textureHandle, new Color(1, 1, 1));
        // middle part
        GLUtils.drawTexturedRect(this.getAbsolutePos().getX() + this.height, this.getAbsolutePos().getY(), this.width - (this.height*2), this.height, 0.5, 0, 0.5, 1, textureHandle, new Color(1, 1, 1));
        TowerDefenceGame.theGame.getFontRenderer().drawCenteredString((this.active ? "Press any Key..." : this.value.name()), new Vector2(this.getAbsolutePos()).add(new Vector2(this.width / 2, padding)), 2, new Color(Colors.TEXT));
    }
}
