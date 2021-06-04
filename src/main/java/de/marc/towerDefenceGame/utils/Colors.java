package de.marc.towerDefenceGame.utils;

public enum Colors {
    BACKGROUND("#222738"),
    BUTTONPRIMARY("#FFC107"),
    BUTTONPRIMARYHOVER("#FFA000"),
    TEXT("#FFFFFF");

    private final String hexStr;
    Colors(String hexStr) {
        this.hexStr = hexStr;
    }

    public String getHexStr() {
        return this.hexStr;
    }
}
