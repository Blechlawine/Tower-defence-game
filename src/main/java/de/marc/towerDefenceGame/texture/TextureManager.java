package de.marc.towerDefenceGame.texture;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.FileUtils;
import de.marc.towerDefenceGame.utils.MapManager;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class TextureManager extends MapManager<String, Texture> {

    @Override
    public void setup() {
        // Miscellanous
        this.loadTexture("assets/textures/misc/cursor.png", "cursor");
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
        this.loadTexture("assets/textures/sniper_tower/base.png", "sniperTowerBase");
        this.loadTexture("assets/textures/sniper_tower/turret.png", "sniperTowerTurret");
        // Level previews
        this.loadTexture("assets/levels/testbig.png", "testBigPreview");
        this.loadTexture("assets/levels/test2.png", "test2Preview");
        // Effects
        this.loadTexture("assets/textures/effects/dustexplosion2.png", "dustexplosion");
        // UI
        this.loadTexture("assets/textures/ui/selecttool.png", "selecttool");
        this.loadTexture("assets/textures/ui/toolbar.png", "toolbar");
        this.loadTexture("assets/textures/ui/logo.png", "logo");
        this.loadTexture("assets/textures/ui/dropdownarrow.png", "dropdownarrow");
        this.loadTexture("assets/textures/ui/button.png", "button");
        this.loadTexture("assets/textures/ui/buttonpressed.png", "buttonpressed");
        this.loadTexture("assets/textures/ui/textinput.png", "textinput");
        this.loadTexture("assets/textures/ui/sliderbase.png", "sliderbase");
        this.loadTexture("assets/textures/ui/sliderhandle.png", "sliderhandle");
        // ICONS
        this.loadTexture("assets/textures/icons/moneyicon.png", "moneyicon");
        this.loadTexture("assets/textures/icons/hearticon.png", "hearticon");
    }

    public void loadTexture(String path, String name) {
        TowerDefenceGame.theGame.getLogger().info("Loading Texture: \"" + path + "\" as name: \"" + name + "\"");
        this.content.put(name.toLowerCase(), FileUtils.readTexturePNG(path, name));
    }

    public void bindTexture(String name) {
        Texture temp = this.content.get(name.toLowerCase());
        glActiveTexture(GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, temp.getID());
    }

    public Texture getTextureFromName(String name) {
        return this.content.get(name.toLowerCase());
    }

    public void unbindTexture() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

}
