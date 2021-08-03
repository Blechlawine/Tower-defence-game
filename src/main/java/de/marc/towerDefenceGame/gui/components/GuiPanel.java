package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

public class GuiPanel extends GuiComponent {
    private boolean hasBackground;
    private GuiComponent[] content;

    public GuiPanel(Vector2 pos, double width, double height, boolean hasBackground, GuiComponent[] content) {
        super(pos, width, height);
        this.hasBackground = hasBackground;
        this.content = content;
    }

    @Override
    public void render() {
        GL11.glPushMatrix();
        GL11.glTranslated(this.pos.getX(), this.pos.getY(), 0);
        if (this.hasBackground) {
            GLUtils.drawRect(0, 0, this.width, this.height, new Color(Colors.BACKGROUND));
        }
        for (GuiComponent c : this.content) {
            c.render();
        }
        GL11.glPopMatrix();
    }
}
