package de.marc.towerDefenceGame.utils;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Settings {

    public String settingsFile = "settings.json";

    public HashMap<String, KeyEvent.KeyCode> keybinds = new HashMap<String, KeyEvent.KeyCode>() {
        {
            put("gui.back", KeyEvent.KeyCode.ESC);
            put("player.moveUp", KeyEvent.KeyCode.W);
            put("player.moveDown", KeyEvent.KeyCode.S);
            put("player.moveLeft", KeyEvent.KeyCode.A);
            put("player.moveRight", KeyEvent.KeyCode.D);
            put("player.moveFast", KeyEvent.KeyCode.L_SHIFT);
            put("player.moveReset", KeyEvent.KeyCode.SPACE);
            put("tools.slot1", KeyEvent.KeyCode.ONE);
            put("tools.slot2", KeyEvent.KeyCode.TWO);
            put("tools.slot3", KeyEvent.KeyCode.THREE);
        }
    };

    public Settings() {
//        File testFile = new File(this.settingsFile);
//        if (testFile.exists()) {
//            this.loadSettings();
//        } else {
//            this.saveSettings();
//        }
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

}
