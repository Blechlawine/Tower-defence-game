package de.marc.towerDefenceGame.render;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.events.MouseMoveEvent;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Renderable;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class RenderLayer {

    private Camera camera;
    public final String name;

    private final List<Renderable> elements;

    public RenderLayer(String name, Camera camera) {
        this.camera = camera;
        this.name = name;
        this.elements = new ArrayList<Renderable>();
    }

    public void render() {
        double[] windowSize = TowerDefenceGame.theGame.getWindowSize();
        double scale = this.camera.getScale();
        double xPos = this.camera.getPos().getX();
        double yPos = this.camera.getPos().getY();
        double xOrigin = this.camera.getOrigin().getX();
        double yOrigin = this.camera.getOrigin().getY();

        GL11.glPushMatrix();
        GL11.glTranslated(xOrigin, yOrigin, 0);
        GL11.glScaled(scale, scale, 1);
        GL11.glTranslated(xPos, yPos, 0);

        for (Renderable element : this.elements) {
            element.render();
        }
        GL11.glPopMatrix();
    }

    public void addElement(Renderable go) {
        this.elements.add(go);
    }

    public Vector2 getCameraPos() {
        return this.camera.getPos();
    }
    public Vector2 getCameraOrigin() {
        return this.camera.getOrigin();
    }

    public double getCameraScale() {
        return this.camera.scale;
    }

    public Vector2 getCursorPosRelativeToLayer() {
        return new Vector2(
                (MouseMoveEvent.getAbsoluteX() - this.getCameraOrigin().getX()) / this.getCameraScale() - this.getCameraPos().getX(),
                (MouseMoveEvent.getAbsoluteY() - this.getCameraOrigin().getY()) / this.getCameraScale() - this.getCameraPos().getY());
    }
}
