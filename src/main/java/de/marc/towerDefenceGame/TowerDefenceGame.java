package de.marc.towerDefenceGame;

import de.marc.towerDefenceGame.event.EventManager;
import de.marc.towerDefenceGame.event.events.*;
import de.marc.towerDefenceGame.level.Level;
import de.marc.towerDefenceGame.player.Player;
import de.marc.towerDefenceGame.render.RenderLayer;
import de.marc.towerDefenceGame.render.Renderer;
import de.marc.towerDefenceGame.utils.Logger;
import de.marc.towerDefenceGame.texture.TileTextureHandler;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;


public class TowerDefenceGame {

    public static TowerDefenceGame theGame;
    public final String name = "TowerDefenceGame";

    private long window;
    private int windowWidth = 800;
    private int windowHeight = 600;

    private Level testLevel;
    private Player thePlayer;

    private EventManager eventManager;
    private TileTextureHandler tileTextureHandler;
    private Renderer renderer;

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
        this.renderer = new Renderer();
        this.eventManager = new EventManager();
        this.eventManager.setup();


        this.thePlayer = new Player();
        this.renderer.addLayer(new RenderLayer("level", this.thePlayer), this.renderer.top);

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            this.eventManager.hook(new KeyEvent(KeyEvent.KeyCode.getKeyCodeFromGLFW(key), action));
        });

        glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            this.eventManager.hook(new MouseButtonEvent(button, action));
        });

        glfwSetScrollCallback(window, (window, xOffset, yOffset) -> {
            this.eventManager.hook(new MouseScrollEvent(xOffset, yOffset));
        });

        glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
            this.eventManager.hook(new MouseMoveEvent(xpos, ypos));
        });

        glfwMakeContextCurrent(this.window);
        glfwSwapInterval(1);
        GL.createCapabilities();

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, this.windowWidth, this.windowHeight,0, 1, -1); // l is left offset, t is top offset
        glMatrixMode(GL_MODELVIEW);

        this.testLevel = Level.generateLevelFromJsonFile("assets/TestBig.json");
        this.getTextureHandler().loadTexture("assets/TilesFuturistic.png");
        this.renderer.getLayerByName("level").addElement(this.testLevel);
    }

    private void loop() {
        glClearColor(0, 0, 0, 0);
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT);

            // Update hier
            long ms = System.nanoTime() / 1000;
            this.eventManager.hook(new UpdateEvent(ms));
            UpdateEvent.lastMS = ms;

            // Rendern hier
            this.renderer.renderLayers();

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
    public EventManager getEventManager() {
        return this.eventManager;
    }
    public Renderer getRenderer() {
        return this.renderer;
    }

    public double[] getWindowSize() {
        return new double[] { this.windowWidth, this.windowHeight };
    }

    public static void main(String[] args) {
        new TowerDefenceGame();
    }

}
