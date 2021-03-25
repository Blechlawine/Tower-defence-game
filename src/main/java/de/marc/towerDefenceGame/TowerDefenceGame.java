package de.marc.towerDefenceGame;

import de.marc.towerDefenceGame.event.EventManager;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.mapstuff.Level;
import de.marc.towerDefenceGame.utils.FileUtils;
import de.marc.towerDefenceGame.utils.Logger;
import de.marc.towerDefenceGame.texturestuff.TileTextureHandler;
import org.lwjgl.opengl.GL;
import org.w3c.dom.Element;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;


public class TowerDefenceGame {

    public static TowerDefenceGame theGame;
    public final String name = "TowerDefenceGame";

    private long window;
    private int windowWidth = 800;
    private int windowHeight = 600;

    private Level testLevel;

    private EventManager eventManager;
    private TileTextureHandler tileTextureHandler;

    private Logger logger;

    public TowerDefenceGame() {
        theGame = this;
        this.start();
    }

    private void start() {
        this.logger = new Logger();

        init();
        loop();
        glfwDestroyWindow(this.window);
        glfwTerminate();
        glfwSetErrorCallback(null);
    }

    private void init() {
        this.initializeWindow();

        this.tileTextureHandler = new TileTextureHandler();
        this.eventManager = new EventManager();
        this.eventManager.setup();

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            this.eventManager.hook(new KeyEvent(key, action));
        });

        glfwMakeContextCurrent(this.window);
        GL.createCapabilities();

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, this.windowWidth, this.windowHeight,0, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        this.testLevel = Level.generateLevelFromJsonFile("assets/Test2.json");
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

    private void initializeWindow() {
        if(!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        this.window = glfwCreateWindow(this.windowWidth, this.windowHeight, this.name, 0, 0);
        if(this.window == 0) {
            throw new RuntimeException("Failed to create GLFW Window");
        }
    }

    public Logger getLogger() {
        return this.logger;
    }
    public TileTextureHandler getTextureHandler() {
        return this.tileTextureHandler;
    }

    public static void main(String[] args) {
        new TowerDefenceGame();
    }
}
