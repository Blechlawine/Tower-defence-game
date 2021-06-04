package de.marc.towerDefenceGame.utils;

import de.marc.towerDefenceGame.TowerDefenceGame;

public class Color {

    private float r, g, b;

    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(float[] rgb) {
        this(rgb[0], rgb[1], rgb[2]);
    }

    public Color(String hexStr) {
        this(convertToRGB(hexStr));
    }

    public Color(Colors colorPreset) {
        this(colorPreset.getHexStr());
    }

    public float[] getAsArray() {
        return new float[] {this.r, this.g, this.b};
    }

    public String getAsHex(boolean withPrefix) {
        StringBuilder builder = new StringBuilder(withPrefix ? "#" : "");
        String rStr = String.format("%02X", Math.round(this.r * 255));
        String gStr = String.format("%02X", Math.round(this.g * 255));
        String bStr = String.format("%02X", Math.round(this.b * 255));
        builder.append(rStr);
        builder.append(gStr);
        builder.append(bStr);
        return builder.toString();
    }

    public static float[] convertToRGB(String hexStr) {
        float[] rgb = new float[3];
        if (hexStr.startsWith("#")) hexStr = hexStr.replace("#", "");
        if (hexStr.length() == 6) {
            int rInt = Integer.parseInt(hexStr.substring(0, 2), 16);
            rgb[0] = rInt / 255f;
            int gInt = Integer.parseInt(hexStr.substring(2, 4), 16);
            rgb[1] = gInt / 255f;
            int bInt = Integer.parseInt(hexStr.substring(4, 6), 16);
            rgb[2] = bInt / 255f;
        }
        return rgb;
    }

    public static Color lighten(String hexStr, float amount) {
        float[] rgb = convertToRGB(hexStr);
        float rStep = (1 - rgb[0]) * amount;
        float gStep = (1 - rgb[1]) * amount;
        float bStep = (1 - rgb[2]) * amount;
        return new Color(rgb[0] + rStep, rgb[1] + gStep, rgb[2] + bStep);
    }

    public static Color darken(String hexStr, float amount) {
        float[] rgb = convertToRGB(hexStr);
        float rStep = rgb[0] * amount;
        float gStep = rgb[1] * amount;
        float bStep = rgb[2] * amount;
        return new Color(rgb[0] - rStep, rgb[1] - gStep, rgb[2] - bStep);
    }

}
