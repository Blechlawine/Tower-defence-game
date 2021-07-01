package de.marc.towerDefenceGame.utils;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.KeyEvent;

import java.util.Arrays;

public abstract class Keybinding implements Listener {

    private final Settings.KeyBindings settingsKeyBinding;
    private final KeyAction[] actions;

    public Keybinding(Settings.KeyBindings settingsKeyBinding, KeyAction[] actions) {
        this.settingsKeyBinding = settingsKeyBinding;
        this.actions = actions;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof KeyEvent) {
            KeyEvent e = (KeyEvent) event;
            if (e.getKey() == TowerDefenceGame.theGame.getSettings().keybindings.get(this.settingsKeyBinding)) {
                if (Arrays.stream(this.actions).anyMatch(action -> (action == e.getAction()))) {
                    this.onKeyAction(e.getAction(), e);
                }
            }
        }
    }

    public abstract void onKeyAction(KeyAction action, KeyEvent event);
}
