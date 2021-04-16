package de.marc.towerDefenceGame.utils;


public class RandomRange {

    private double low, high;

    public RandomRange(double low, double high) {
        this.low = low;
        this.high = high;
    }

    public double getValue() {
        return (Math.random() * (this.high - this.low)) + this.low;
    }

}
