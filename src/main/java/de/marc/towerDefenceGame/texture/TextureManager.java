package de.marc.towerDefenceGame.texture;

import de.marc.towerDefenceGame.utils.FileUtils;
import de.marc.towerDefenceGame.utils.ListManager;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class TextureManager extends ListManager<Texture> {

    @Override
    public void setup() {
        this.loadTexture("assets/TilesFuturistic.png", "tiles");
        this.loadTexture("assets/font.png", "font");
    }

    public void loadTexture(String path, String name) {
        this.content.add(FileUtils.readTexturePNG(path, name));
    }

    public void bindTexture(String name) {
        for (int i = 0; i < this.content.size(); i++) {
            Texture temp = this.content.get(i);
            if (temp.getName().equalsIgnoreCase(name)) {
                glActiveTexture(GL_TEXTURE0);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, temp.getID());
            }
        }
    }

    public void unbindTexture() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

}
