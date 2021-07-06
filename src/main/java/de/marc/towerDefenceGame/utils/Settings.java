package de.marc.towerDefenceGame.utils;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.event.events.WindowResizeEvent;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.render.Camera;

import java.util.HashMap;

public class Settings {

    //unsaved Settings
    public static Settings instance;
    public String settingsFile = "settings.json";
    public boolean isGamePaused = false;

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

    public Settings() {
        instance = this;
//        File testFile = new File(this.settingsFile);
//        if (testFile.exists()) {
//            this.loadSettings();
//        } else {
//            this.saveSettings();
//        }
    }

    public void setGuiScale(String key) {
        if (this.guiScales.containsKey(key)) {
            this.currentGuiScaleName = key;
            this.currentGuiScale = this.guiScales.get(key);
            this.guiCamera.setScale(this.currentGuiScale);
            double[] windowSize = TowerDefenceGame.theGame.getWindowSize();
            Gui.setWindowSize(windowSize[0], windowSize[1]);
            TowerDefenceGame.theGame.getEventManager().hook(new WindowResizeEvent(windowSize[0], windowSize[1]));
        }
    }

    private void saveSettings() {
        //TODO: save settings file
//        JSONObject json = new JSONObject();
//        JSONArray keybindsJson = new JSONArray();
//        for (Map.Entry<String, KeyEvent.KeyCode> keybind : this.keybinds.entrySet()) {
//            JSONObject keybindJson = new JSONObject();
//            keybindJson.put(keybind.getKey(), keybind.getValue().getGlfwKey());
//            keybindsJson.put(keybindJson);
//        }
//        json.put("keybinds", keybindsJson);
//        FileUtils.writeJSONFile(this.settingsFile, json);
    }

    private void loadSettings() {
        //TODO: load settings file
//        JSONObject settingsJson = FileUtils.readJSONResource(this.settingsFile);
//        JSONArray keybindsJsonArray = settingsJson.getJSONArray("keybinds");
//        for (int i = 0; i < keybindsJsonArray.length(); i++) {
//            JSONObject keybind = keybindsJsonArray.getJSONObject(i);
//            this.keybinds.put(keybind.getString("binding"), KeyEvent.KeyCode.getKeyCodeFromGLFW(keybind.getInt("value")));
//        }
//        TowerDefenceGame.theGame.getLogger().debug(this.keybinds.entrySet());
    }

    public enum KeyBindings {
        GUI_BACK, GUI_INTERACT,
        PLAYER_MOVEUP, PLAYER_MOVEDOWN, PLAYER_MOVELEFT, PLAYER_MOVERIGHT, PLAYER_MOVEFAST, PLAYER_MOVEMOUSE, PLAYER_MOVERESET,
        TOOLS_DESELECT, TOOLS_BUILD, TOOLS_DESTROY, TOOLS_SLOT1, TOOLS_SLOT2, TOOLS_SLOT3;
    }

}
