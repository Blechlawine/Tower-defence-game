package de.marc.towerDefenceGame.texture;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.FileUtils;
import org.lwjgl.opengl.GL11;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class TileTextureHandler {

    private Texture texture;

    private double[] uvTileSize;
    private int tilesInTextureAtlasX;

    public TileTextureHandler() {
        this.updateTileSizes();
    }

    public double[] getTileUVSize() {
        return this.uvTileSize;
    }

    public void bindTexture() {
        glActiveTexture(GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture.getID());
    }

    public void unbindTexture() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    public void loadTexture(String path) {
        this.texture = FileUtils.readTexturePNG(path);
    }

    public int getTileSetRowLength() {
        return this.tilesInTextureAtlasX;
    }

    private void updateTileSizes() {
        Element rootElement = FileUtils.readXMLFile("assets/TilesFuturistic.tsx");
        if (rootElement == null) {
            TowerDefenceGame.theGame.getLogger().err("Couldn't read Tileset xml");
            return;
        }
        String columns = rootElement.getAttribute("columns");
        double tileWidth = Double.parseDouble(rootElement.getAttribute("tilewidth"));
        double tileHeight = Double.parseDouble(rootElement.getAttribute("tileheight"));
        Node imageElement = rootElement.getElementsByTagName("image").item(0);
//        double imageWidth = Double.parseDouble(imageElement.getAttributes().getNamedItem("width").getNodeValue());
//        double imageHeight = Double.parseDouble(imageElement.getAttributes().getNamedItem("height").getNodeValue());
        // UV-tilesize ist nicht in Pixel, sondern zwischen 0 und 1, 0 ist ganz links/oben, 1 ist ganz rechts/unten
        this.uvTileSize = new double[] { 1 / tileWidth, 1 / tileHeight };
        this.tilesInTextureAtlasX = Integer.parseInt(columns);
    }
}
