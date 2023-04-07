package org.composer.utils;

public class BooleanTranslator {
    public static boolean intToBoolean(int num) {
        if (num < 0 || num > 1) {
            throw new IllegalArgumentException("Invalid integer value!");
        }
        return num == 0;
    }
}
