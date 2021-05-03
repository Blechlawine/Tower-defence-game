package de.marc.towerDefenceGame.utils;

public class Color {

    private float r, g, b;

    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(String hexStr) {
        if (hexStr.startsWith("#")) hexStr = hexStr.replace("#", "");
        if (hexStr.length() == 6) {
            int rInt = Integer.parseInt(hexStr.substring(0, 2), 16);
            this.r = rInt / 255f;
            int gInt = Integer.parseInt(hexStr.substring(2, 4), 16);
            this.g = gInt / 255f;
            int bInt = Integer.parseInt(hexStr.substring(4, 6), 16);
            this.b = bInt / 255f;
        }

    }

    public float[] getAsArray() {
        return new float[] {this.r, this.g, this.b};
    }

}
