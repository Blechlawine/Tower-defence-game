package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.utils.*;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;
import static de.marc.towerDefenceGame.utils.Settings.KeyBindings.GUI_INTERACT;

public class GuiKeyCodeInput extends GuiComponent {

    private KeyEvent.KeyCode value;
    private Settings.KeyBindings settingToChange;

    private Keybinding clickBinding;
    private boolean active;

    public GuiKeyCodeInput(Vector2 pos, double width, double height, KeyEvent.KeyCode defaultValue, Settings.KeyBindings settingToChange) {
        super(pos);
        this.width = width;
        this.height = height;
        this.value = defaultValue;
        this.settingToChange = settingToChange;
        this.active = false;
        this.clickBinding = new Keybinding(GUI_INTERACT, new KeyAction[]{DOWN}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (hovered) {
                    active = true;
                    TowerDefenceGame.theGame.getLogger().debug(value);
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
            TowerDefenceGame.theGame.getSettings().keybindings.replace(this.settingToChange, this.value);
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
        GLUtils.drawTexturedRect(this.pos.getX(), this.pos.getY(), this.height, this.height, 0, 0, 1, 1, textureHandle, new Color(1, 1, 1));
        // right side
        GLUtils.drawTexturedRect(this.pos.getX() + this.width - this.height, this.pos.getY(), this.height, this.height, 1, 0, -1, 1, textureHandle, new Color(1, 1, 1));
        // middle part
        GLUtils.drawTexturedRect(this.pos.getX() + this.height, this.pos.getY(), this.width - (this.height*2), this.height, 0.5, 0, 0.5, 1, textureHandle, new Color(1, 1, 1));
        TowerDefenceGame.theGame.getFontRenderer().drawCenteredString((this.active ? "Press any Key..." : this.value.name()), new Vector2(this.pos).add(new Vector2(this.width / 2, padding)), 2, new Color(Colors.TEXT));
    }
}
