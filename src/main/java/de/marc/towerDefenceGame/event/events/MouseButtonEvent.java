package de.marc.towerDefenceGame.event.events;

import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.utils.KeyAction;
import org.lwjgl.glfw.GLFW;

public class MouseButtonEvent extends Event {
    private final int button;
    private KeyAction action;

    public MouseButtonEvent(int glfwButton, int action) {
        this.button = glfwButton;
        switch (action) {
            case GLFW.GLFW_PRESS:
                this.action = KeyAction.DOWN;
                break;
            case GLFW.GLFW_RELEASE:
                this.action = KeyAction.UP;
                break;
            case GLFW.GLFW_REPEAT:
                this.action = KeyAction.HOLD;
                break;
        }
    }

    public KeyAction getAction() {
        return this.action;
    }
    public int getButton() {
        return this.button;
    }
}
