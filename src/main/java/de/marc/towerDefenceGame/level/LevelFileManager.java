package de.marc.towerDefenceGame.level;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.ListManager;

public class LevelFileManager extends ListManager<String[]> {
    @Override
    public void setup() {
        this.addLevelFile("assets/levels/test2.json", "test2", "test2Preview");
        this.addLevelFile("assets/levels/testBig.json", "testbig", "testbigPreview");
    }

    private void addLevelFile(String path, String name, String previewTexture) {
        this.content.add(new String[] { path, name, previewTexture });
        TowerDefenceGame.theGame.getLogger().info("Levelfile: \"" + path + "\" as \"" + name + "\" with \"" + previewTexture + "\" added.");
    }

    public String getLevelFileByName(String name) {
        for (int i = 0; i < this.content.size(); i++) {
            String[] temp = this.content.get(i);
            if (temp[1].equalsIgnoreCase(name)) {
                return temp[0];
            }
        }
        return null;
    }
}
