package de.marc.towerDefenceGame.utils;

public class Utils {

    public static double limitD(double value, double min, double max) {
        if (value > max) {
            return max;
        }
        if (value < min) {
            return min;
        }
        return value;
    }

}
