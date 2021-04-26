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
        // Tiletextures
        this.loadTexture("assets/textures/tiles/startportal.png", "startPortal");
        this.loadTexture("assets/textures/tiles/endportal.png", "endPortal");

        this.loadTexture("assets/textures/tiles/platform.png", "platform");

        this.loadTexture("assets/textures/tiles/left-right.png", "pathLeftRight");
        this.loadTexture("assets/textures/tiles/right-left.png", "pathRightLeft");
        this.loadTexture("assets/textures/tiles/top-bottom.png", "pathTopBottom");
        this.loadTexture("assets/textures/tiles/bottom-top.png", "pathBottomTop");
        this.loadTexture("assets/textures/tiles/left-top.png", "pathLeftTop");
        this.loadTexture("assets/textures/tiles/top-left.png", "pathTopLeft");
        this.loadTexture("assets/textures/tiles/top-right.png", "pathTopRight");
        this.loadTexture("assets/textures/tiles/right-top.png", "pathRightTop");
        this.loadTexture("assets/textures/tiles/right-bottom.png", "pathRightBottom");
        this.loadTexture("assets/textures/tiles/bottom-right.png", "pathBottomRight");
        this.loadTexture("assets/textures/tiles/bottom-left.png", "pathBottomLeft");
        this.loadTexture("assets/textures/tiles/left-bottom.png", "pathLeftBottom");
        // Font
        this.loadTexture("assets/font.png", "font");
        // Towers
        this.loadTexture("assets/textures/basic_tower/base.png", "basicTowerBase");
        this.loadTexture("assets/textures/basic_tower/turret.png", "basicTowerTurret");
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

    public Texture getTextureFromName(String name) {
        for (Texture temp : this.content) {
            if (temp.getName().equalsIgnoreCase(name)) {
                return temp;
            }
        }
        return null;
    }

    public void unbindTexture() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

}