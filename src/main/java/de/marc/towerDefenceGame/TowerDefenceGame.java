package de.marc.towerDefenceGame;

import de.marc.towerDefenceGame.mapstuff.Level;
import de.marc.towerDefenceGame.utils.FileUtils;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;


public class TowerDefenceGame {

    private long window;
    private int windowWidth = 800;
    private int windowHeight = 600;

    private Level testLevel;

    private void start() {
        init();
        loop();
        glfwDestroyWindow(this.window);
        glfwTerminate();
        glfwSetErrorCallback(null);
    }

    private void init() {
        if(!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        this.window = glfwCreateWindow(this.windowWidth, this.windowHeight, "test", 0, 0);
        if(this.window == 0) {
            throw new RuntimeException("Failed to create GLFW Window");
        }

        glfwMakeContextCurrent(this.window);
        GL.createCapabilities();

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, this.windowWidth, this.windowHeight,0, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        this.testLevel = Level.generateLevelFromJsonFile("assets/Testmap.json");
    }

    private void loop() {
        glClearColor(0, 0, 0, 0);
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT);

            // Rendern hier
            this.testLevel.render();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new TowerDefenceGame().start();
    }
}
