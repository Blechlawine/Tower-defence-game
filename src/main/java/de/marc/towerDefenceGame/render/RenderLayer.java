package de.marc.towerDefenceGame.render;

import de.marc.towerDefenceGame.utils.Renderable;
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
        GL11.glPushMatrix();
        GL11.glTranslated(this.camera.getOffset().getX(), this.camera.getOffset().getY(),0);
        for (Renderable element : this.elements) {
            element.render();
        }
        GL11.glPopMatrix();
    }

    public void addElement(Renderable go) {
        this.elements.add(go);
    }

}
