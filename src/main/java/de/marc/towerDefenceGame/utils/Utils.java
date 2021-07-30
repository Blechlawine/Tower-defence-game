package de.marc.towerDefenceGame.utils;

public class Utils {

    public static double limitD(double value, double min, double max) {
        return Math.max(Math.min(value, max), min);
    }

}
