package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiTower extends GuiComponent {

    private final String turretTexture, baseTexture;
    private double size;

    public GuiTower(Vector2 pos, double size, String turretTexture, String baseTexture) {
        super(pos);
        this.turretTexture = turretTexture;
        this.baseTexture = baseTexture;
        this.size = size;
    }

    @Override
    public void render() {
        GLUtils.drawTexturedRect(this.pos.getX(), this.pos.getY(), this.size, this.size, 0, 0, 1, 1, this.baseTexture, new Color(0.5f, 0.5f, 0.5f));
        GLUtils.drawTexturedRect(this.pos.getX(), this.pos.getY(), this.size, this.size, 0, 0, 1, 1, this.turretTexture, new Color(1, 1, 1));
    }
}
