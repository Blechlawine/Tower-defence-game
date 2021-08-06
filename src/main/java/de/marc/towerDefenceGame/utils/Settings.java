package de.marc.towerDefenceGame.utils;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.event.events.WindowResizeEvent;
import de.marc.towerDefenceGame.gui.GuiScreen;
import de.marc.towerDefenceGame.render.Camera;
import de.marc.towerDefenceGame.sound.SoundSource;

import java.util.ArrayList;
import java.util.HashMap;

public class Settings {

    //unsaved Settings
    public static Settings instance;
    public String settingsFile = "settings.json";
    public boolean isGamePaused = false;
    public boolean debugMode = false;

    //saved Settings
    public HashMap<KeyBindings, KeyEvent.KeyCode> keybindings = new HashMap<KeyBindings, KeyEvent.KeyCode>() {
        {
            put(KeyBindings.GUI_BACK, KeyEvent.KeyCode.ESC);
            put(KeyBindings.GUI_INTERACT, KeyEvent.KeyCode.MOUSE_1);
            put(KeyBindings.PLAYER_MOVEUP, KeyEvent.KeyCode.W);
            put(KeyBindings.PLAYER_MOVEDOWN, KeyEvent.KeyCode.S);
            put(KeyBindings.PLAYER_MOVELEFT, KeyEvent.KeyCode.A);
            put(KeyBindings.PLAYER_MOVERIGHT, KeyEvent.KeyCode.D);
            put(KeyBindings.PLAYER_MOVEFAST, KeyEvent.KeyCode.L_SHIFT);
            put(KeyBindings.PLAYER_MOVERESET, KeyEvent.KeyCode.SPACE);
            put(KeyBindings.PLAYER_MOVEMOUSE, KeyEvent.KeyCode.MOUSE_3);
            put(KeyBindings.TOOLS_DESELECT, KeyEvent.KeyCode.MOUSE_2);
            put(KeyBindings.TOOLS_BUILD, KeyEvent.KeyCode.MOUSE_1);
            put(KeyBindings.TOOLS_DESTROY, KeyEvent.KeyCode.MOUSE_2);
            put(KeyBindings.TOOLS_SLOT1, KeyEvent.KeyCode.ONE);
            put(KeyBindings.TOOLS_SLOT2, KeyEvent.KeyCode.TWO);
            put(KeyBindings.TOOLS_SLOT3, KeyEvent.KeyCode.THREE);
        }
    };
    public double currentGuiScale = 1;
    public String currentGuiScaleName = "Small";
    public HashMap<String, Double> guiScales = new HashMap<String, Double>() {
        {
            put("Small", 1d);
            put("Normal", 2d);
            put("Large", 3d);
        }
    };
    public Camera guiCamera = new Camera(new Vector2(0, 0), new Vector2(0, 0), this.currentGuiScale);

    // Audio
    public float musicVolume = 1F;
    public float sfxVolume = 1F;

    public Settings() {
        instance = this;
    }

    public void setGuiScale(String key) {
        if (this.guiScales.containsKey(key)) {
            this.currentGuiScaleName = key;
            this.currentGuiScale = this.guiScales.get(key);
            this.guiCamera.setScale(this.currentGuiScale);
            double[] windowSize = TowerDefenceGame.theGame.getWindowSize();
            GuiScreen.setWindowSize(windowSize[0], windowSize[1]);
            TowerDefenceGame.theGame.getEventManager().hook(new WindowResizeEvent(windowSize[0], windowSize[1]));
        }
    }

    public void setMusicVolume(float volume) {
        this.musicVolume = volume;
        ArrayList<SoundSource> sources = TowerDefenceGame.theGame.getSoundSourceManager().getSoundSourcesFromCategory(SoundSource.SoundSourceCategory.MUSIC);
        for(SoundSource source : sources) {
            source.setGain(this.musicVolume);
        }
    }

    public void setSfxVolume(float volume) {
        this.sfxVolume = volume;
        ArrayList<SoundSource> sources = TowerDefenceGame.theGame.getSoundSourceManager().getSoundSourcesFromCategory(SoundSource.SoundSourceCategory.SFX);
        for (SoundSource source : sources) {
            source.setGain(this.sfxVolume);
        }
    }

    public enum KeyBindings {
        GUI_BACK, GUI_INTERACT,
        PLAYER_MOVEUP, PLAYER_MOVEDOWN, PLAYER_MOVELEFT, PLAYER_MOVERIGHT, PLAYER_MOVEFAST, PLAYER_MOVEMOUSE, PLAYER_MOVERESET,
        TOOLS_DESELECT, TOOLS_BUILD, TOOLS_DESTROY, TOOLS_SLOT1, TOOLS_SLOT2, TOOLS_SLOT3
    }

}
