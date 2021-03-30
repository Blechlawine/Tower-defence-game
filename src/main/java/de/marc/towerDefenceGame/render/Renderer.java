package de.marc.towerDefenceGame.render;

import de.marc.towerDefenceGame.TowerDefenceGame;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private final List<RenderLayer> layers;
    public int top;

    public Renderer() {
        this.layers = new ArrayList<RenderLayer>();
    }

    public void addLayer(RenderLayer layer, int index) {
        this.layers.add(index, layer);
        this.top = this.layers.size();
    }

    public void renderLayers() {
        for (int i = 0; i < this.top; i++) {
            this.layers.get(i).render();
        }
    }

    public RenderLayer getLayerByName(String name) {
        for (RenderLayer layer : this.layers) {
            if (layer.name.equalsIgnoreCase(name))
                return layer;
        }
        return null;
    }

    public List<RenderLayer> getLayers() {
        return this.layers;
    }
}
