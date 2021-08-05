package de.marc.towerDefenceGame.utils;

public class Utils {

    public static double limitD(double value, double min, double max) {
        return Math.max(Math.min(value, max), min);
    }

    public static double wrapAngleTo180(double angle) {
        angle %= 360;
        if (angle >= 180) {
            angle -= 360;
        }
        if (angle < -180) {
            angle += 360;
        }
        return -angle % 360;
    }
}
