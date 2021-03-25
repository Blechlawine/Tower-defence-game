package de.marc.towerDefenceGame.event.events;

import de.marc.towerDefenceGame.event.Event;
import org.lwjgl.glfw.GLFW;

public class KeyEvent extends Event {

    private int key;
    private KeyAction action;

    public KeyEvent(int key, int action) {
        this.key = key;
        switch (action) {
            case GLFW.GLFW_PRESS:
                this.action = KeyAction.DOWN;
                break;
            case GLFW.GLFW_RELEASE:
                this.action = KeyAction.UP;
                break;
        }
    }

    public enum KeyAction {
        DOWN, UP;
    }

}
