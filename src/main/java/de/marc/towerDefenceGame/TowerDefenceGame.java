package de.marc.towerDefenceGame;

import de.marc.towerDefenceGame.event.EventManager;
import de.marc.towerDefenceGame.event.events.*;
import de.marc.towerDefenceGame.level.Level;
import de.marc.towerDefenceGame.player.Player;
import de.marc.towerDefenceGame.render.RenderLayer;
import de.marc.towerDefenceGame.render.Renderer;
import de.marc.towerDefenceGame.utils.Logger;
import de.marc.towerDefenceGame.texture.TileTextureHandler;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;


public class TowerDefenceGame {

    public static TowerDefenceGame theGame;
    public final String name = "TowerDefenceGame";

    private long window;
    private int windowWidth = 800;
    private int windowHeight = 600;

    public Level currentLevel;
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

        glfwSetWindowSizeCallback(window , (window, width, height) -> {
            this.windowWidth = width;
            this.windowHeight = height;
            glViewport(0, 0, this.windowWidth, this.windowHeight);
            for (RenderLayer layer : this.renderer.getLayers()) {
                layer.updateCameraOrigin();
            }
            this.initGL();
        });

        glfwMakeContextCurrent(this.window);
        glfwSwapInterval(1);
        GL.createCapabilities();

        this.initGL();

        this.currentLevel = Level.generateLevelFromJsonFile("assets/TestBig.json");
        this.getTextureHandler().loadTexture("assets/TilesFuturistic.png");
        this.renderer.getLayerByName("level").addElement(this.currentLevel);
        this.renderer.getLayerByName("level").addElement(this.currentLevel.getPath());
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
        // Anti-aliasing
        glfwWindowHint(GLFW_STENCIL_BITS, 4);
        glfwWindowHint(GLFW_SAMPLES, 4);

        this.window = glfwCreateWindow(this.windowWidth, this.windowHeight, this.name, 0, 0);
        if(this.window == 0) {
            throw new RuntimeException("Failed to create GLFW Window");
        }
    }

    private void initGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, this.windowWidth, this.windowHeight,0, 1, -1); // l is left offset, t is top offset
        glMatrixMode(GL_MODELVIEW);
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
