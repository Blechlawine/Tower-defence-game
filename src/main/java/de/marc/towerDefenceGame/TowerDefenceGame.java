package de.marc.towerDefenceGame;

import de.marc.towerDefenceGame.event.EventManager;
import de.marc.towerDefenceGame.event.events.*;
import de.marc.towerDefenceGame.games.GameManager;
import de.marc.towerDefenceGame.gui.FontRenderer;
import de.marc.towerDefenceGame.gui.GuiScreen;
import de.marc.towerDefenceGame.gui.GuiManager;
import de.marc.towerDefenceGame.level.LevelFileManager;
import de.marc.towerDefenceGame.player.Player;
import de.marc.towerDefenceGame.render.RenderLayer;
import de.marc.towerDefenceGame.render.Renderer;
import de.marc.towerDefenceGame.sound.MusicManager;
import de.marc.towerDefenceGame.sound.SoundBufferManager;
import de.marc.towerDefenceGame.sound.SoundSourceManager;
import de.marc.towerDefenceGame.texture.TextureManager;
import de.marc.towerDefenceGame.gameObjects.tower.TowerManager;
import de.marc.towerDefenceGame.utils.*;
import de.marc.towerDefenceGame.texture.TextureHandler;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;


public class TowerDefenceGame {

    public static TowerDefenceGame theGame;
    public final String name = "TowerDefenceGame";

    private long window;
    private int windowWidth = 800;
    private int windowHeight = 600;

    private Player thePlayer;

    private EventManager eventManager;
    private TextureHandler textureHandler;
    private TowerManager towerManager;
    private TextureManager textureManager;
    private SoundBufferManager soundBufferManager;
    private GuiManager guiManager;
    private Renderer renderer;
    private FontRenderer fontRenderer;
    private GameManager gameManager;
    private LevelFileManager levelFileManager;
    private SoundSourceManager soundSourceManager;
    private MusicManager musicManager;

    private Logger logger;
    private Settings settings;

    public TowerDefenceGame() {
        theGame = this;
        this.start();
    }

    private void start() {
        this.logger = new Logger();
        this.settings = new Settings();

        init();
        loop();
        this.getSoundSourceManager().cleanup();
        this.getSoundBufferManager().cleanup();
        glfwDestroyWindow(this.window);
        glfwTerminate();
        glfwSetErrorCallback(null);
    }

    private void init() {
        this.initializeWindow();

        this.levelFileManager = new LevelFileManager();
        this.textureHandler = new TextureHandler();
        this.renderer = new Renderer();
        this.eventManager = new EventManager();
        this.towerManager = new TowerManager();
        this.gameManager = new GameManager();
        this.textureManager = new TextureManager();
        this.soundBufferManager = new SoundBufferManager();
        this.guiManager = new GuiManager();
        this.thePlayer = new Player();
        this.soundSourceManager = new SoundSourceManager();
        this.musicManager = new MusicManager();

        GuiScreen.windowSize = new Vector2(this.windowWidth, this.windowHeight);

        this.levelFileManager.setup();
        this.eventManager.setup();
        this.gameManager.setup();

        this.renderer.addLayer(new RenderLayer("level", this.thePlayer));
        this.renderer.addLayer(new RenderLayer("enemies", this.thePlayer));
        this.renderer.addLayer(new RenderLayer("projectiles", this.thePlayer));
        this.renderer.addLayer(new RenderLayer("towers", this.thePlayer));
        this.renderer.addLayer(new RenderLayer("tools", this.thePlayer));
        this.renderer.addLayer(new RenderLayer("gui", this.settings.guiCamera));

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> this.eventManager.hook(new KeyEvent(KeyEvent.KeyCode.getKeyCodeFromGLFW(key), action)));

        glfwSetMouseButtonCallback(window, (window, button, action, mods) -> this.eventManager.hook(new KeyEvent(KeyEvent.KeyCode.getKeyCodeFromGLFW(button + 1000), action))); //new MouseButtonEvent(button, action)

        glfwSetScrollCallback(window, (window, xOffset, yOffset) -> this.eventManager.hook(new MouseScrollEvent(xOffset, yOffset)));

        glfwSetCursorPosCallback(window, (window, xPos, yPos) -> this.eventManager.hook(new MouseMoveEvent(xPos, yPos)));

        glfwSetWindowSizeCallback(window , (window, width, height) -> {
            this.windowWidth = width;
            this.windowHeight = height;
            glViewport(0, 0, this.windowWidth, this.windowHeight);
            this.thePlayer.updateCameraOrigin();
            GuiScreen.setWindowSize(this.windowWidth, this.windowHeight);
            this.eventManager.hook(new WindowResizeEvent(this.windowWidth, this.windowHeight));
            this.initGL();
        });

        glfwSetWindowPosCallback(window, (window, x, y) -> this.eventManager.hook(new WindowMoveEvent(x, y)));

        glfwMakeContextCurrent(this.window);
        glfwSwapInterval(1);
        GL.createCapabilities();

        this.initGL();

        this.soundBufferManager.initSoundSystem();
//        this.currentLevel = Level.generateLevelFromJsonFile("assets/TestBig.json");
        this.textureManager.setup();
        this.soundBufferManager.setup();
        this.soundSourceManager.setup();
        this.musicManager.setup();
        this.fontRenderer = new FontRenderer();
        this.guiManager.setup();
        this.guiManager.setActiveGui(this.guiManager.getGuiFromName("mainmenu"));
        this.musicManager.startMenuMusic();
    }

    private void loop() {
        Color clearColor = new Color(Colors.BACKGROUNDDARK);
        float[] clearColorArray = clearColor.getAsArray();
        glClearColor(clearColorArray[0], clearColorArray[1], clearColorArray[2], 0);
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT);

            long ms = System.nanoTime() / 1000000;

            if(this.gameManager.getCurrentGame() != null && !TowerDefenceGame.theGame.getSettings().isGamePaused) {
                this.eventManager.hook(new PreUpdateEvent(ms));
                this.eventManager.hook(new UpdateEvent(ms));
                this.eventManager.hook(new PostUpdateEvent(ms));
            }
            this.eventManager.hook(new UnPausedPostUpdateEvent(ms));

            PreUpdateEvent.lastMS = ms;
            UpdateEvent.lastMS = ms;
            PostUpdateEvent.lastMS = ms;
            UnPausedPostUpdateEvent.lastMS = ms;


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
        // window Icon
        this.setWindowIcon("assets/textures/ui/logo.png");
    }

    // Code from https://stackoverflow.com/questions/42322382/glfw-window-icon
    private void setWindowIcon(String path) {
        IntBuffer w = memAllocInt(1);
        IntBuffer h = memAllocInt(1);
        IntBuffer comp = memAllocInt(1);

        // Icons
        {
            ByteBuffer icon;
            try {
                icon = FileUtils.ioResourceToByteBuffer(path, 65536); // 4096 for 32 px, 65536 for 128px
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            try ( GLFWImage.Buffer icons = GLFWImage.malloc(1) ) {
                ByteBuffer pixels = STBImage.stbi_load_from_memory(icon, w, h, comp, 4);
                icons
                        .position(0)
                        .width(w.get(0))
                        .height(h.get(0))
                        .pixels(pixels);

                icons.position(0);
                glfwSetWindowIcon(window, icons);

                STBImage.stbi_image_free(pixels);
            }
        }

        memFree(comp);
        memFree(h);
        memFree(w);
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
    public TextureHandler getTextureHandler() {
        return this.textureHandler;
    }
    public EventManager getEventManager() {
        return this.eventManager;
    }
    public Renderer getRenderer() {
        return this.renderer;
    }
    public Player getPlayer() {
        return this.thePlayer;
    }
    public TowerManager getTowerManager() {
        return this.towerManager;
    }
    public TextureManager getTextureManager() {
        return this.textureManager;
    }
    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }
    public GameManager getGameManager() {
        return this.gameManager;
    }
    public GuiManager getGuiManager() {
        return this.guiManager;
    }
    public LevelFileManager getLevelFileManager() {
        return this.levelFileManager;
    }
    public Settings getSettings() {
        return this.settings;
    }
    public SoundBufferManager getSoundBufferManager() {
        return this.soundBufferManager;
    }
    public SoundSourceManager getSoundSourceManager() {
        return this.soundSourceManager;
    }
    public MusicManager getMusicManager() {
        return this.musicManager;
    }

    public double[] getWindowSize() {
        return new double[] { this.windowWidth, this.windowHeight };
    }

    public static void main(String[] args) {
        new TowerDefenceGame();
    }

}
