package org.example.randomGenerators;

public class Randomizer {
    //helper function for random selection of integers
    public static int randomIntegerFromInterval(int min, int max) { //max excluded
        return (int) (Math.random() * (max - min) + min);
    }

    public static int randomBit() {
        return (int) Math.round(Math.random());
    }
}
