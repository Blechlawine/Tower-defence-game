package de.marc.towerDefenceGame;

import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;


public class TowerDefenceGame {

    private long window;

    private void start() {
        init();
        loop();
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null);
    }

    private void init() {
        if(!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(300, 300, "test", 0, 0);
        if(window == 0) {
            throw new RuntimeException("Failed to create GLFW Window");
        }

        glfwMakeContextCurrent(window);
    }

    private void loop() {
        GL.createCapabilities();
        glClearColor(0, 0, 0, 0);
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new TowerDefenceGame().start();
    }
}
