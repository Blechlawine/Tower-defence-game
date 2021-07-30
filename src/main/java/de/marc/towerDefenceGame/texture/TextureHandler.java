package de.marc.towerDefenceGame.texture;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.FileUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class TextureHandler {

    private double[] uvTileSize;
    private int tilesInTextureAtlasX;

    public TextureHandler() {
        this.updateTileSizes();
    }

    public double[] getTileUVSize() {
        return this.uvTileSize;
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
        this.uvTileSize = new double[] { 1D / tileWidth, 1D / tileHeight };
        this.tilesInTextureAtlasX = Integer.parseInt(columns);
    }
}
