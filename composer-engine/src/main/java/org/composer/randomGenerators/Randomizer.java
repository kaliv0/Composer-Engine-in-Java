package org.composer.randomGenerators;

/**
 * helper function for random selection of integers
 */
public class Randomizer {
    public static int randomIntegerFromInterval(int min, int max) { //max excluded
        return (int) (Math.random() * (max - min) + min);
    }

    public static int randomBit() {
        return (int) Math.round(Math.random());
    }

    public static boolean randomBoolean() {return randomBit() == 0;}
}
