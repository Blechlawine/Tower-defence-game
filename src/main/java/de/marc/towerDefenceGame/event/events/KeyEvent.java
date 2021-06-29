package de.marc.towerDefenceGame.event.events;

import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.utils.KeyAction;
import org.lwjgl.glfw.GLFW;

public class KeyEvent extends Event {

    private KeyCode key;
    private KeyAction action;

    public KeyEvent(KeyCode key, int action) {
        this.key = key;
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

    /***
     * Getter for the Key of the KeyEvent
     * @return KeyCode
     */
    public KeyCode getKey() {
        return this.key;
    }

    /***
     * Getter for the Action of the KeyEvent, if the event was an UP, DOWN, or HOLD
     * @return KeyAction
     */
    public KeyAction getAction() {
        return this.action;
    }

    public enum KeyCode {
        A(GLFW.GLFW_KEY_A),
        B(GLFW.GLFW_KEY_B),
        C(GLFW.GLFW_KEY_C),
        D(GLFW.GLFW_KEY_D),
        E(GLFW.GLFW_KEY_E),
        F(GLFW.GLFW_KEY_F),
        G(GLFW.GLFW_KEY_G),
        H(GLFW.GLFW_KEY_H),
        I(GLFW.GLFW_KEY_I),
        J(GLFW.GLFW_KEY_J),
        K(GLFW.GLFW_KEY_K),
        L(GLFW.GLFW_KEY_L),
        M(GLFW.GLFW_KEY_M),
        N(GLFW.GLFW_KEY_N),
        O(GLFW.GLFW_KEY_O),
        P(GLFW.GLFW_KEY_P),
        Q(GLFW.GLFW_KEY_Q),
        R(GLFW.GLFW_KEY_R),
        S(GLFW.GLFW_KEY_S),
        T(GLFW.GLFW_KEY_T),
        U(GLFW.GLFW_KEY_U),
        V(GLFW.GLFW_KEY_V),
        W(GLFW.GLFW_KEY_W),
        X(GLFW.GLFW_KEY_X),
        Y(GLFW.GLFW_KEY_Y),
        Z(GLFW.GLFW_KEY_Z),
        L_SHIFT(GLFW.GLFW_KEY_LEFT_SHIFT),
        L_CTRL(GLFW.GLFW_KEY_LEFT_CONTROL),
        SPACE(GLFW.GLFW_KEY_SPACE),
        ESC(GLFW.GLFW_KEY_ESCAPE),
        NONE(9999);


        private final int glfwKey;

        KeyCode(int glfwKey) {
            this.glfwKey = glfwKey;
        }

        /***
         * looks for the corresponding KeyCode to the glfw key
         * @param glfwKey int key from glfw keypress event
         * @return KeyCode
         */
        public static KeyCode getKeyCodeFromGLFW(int glfwKey) {
            for (KeyCode key : values()) {
                if (key.glfwKey == glfwKey)
                    return key;
            }
            return NONE;
        }
    }

}
