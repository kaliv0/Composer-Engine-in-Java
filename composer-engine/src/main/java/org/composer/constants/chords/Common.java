package org.composer.constants.chords;

import java.util.List;

public class Common {
    public static final int PROGRESSION_LENGTH_DUPLICATE_THRESHOLD = 2;
    public static final int PROGRESSION_LENGTH_MIN = 8;
    public static final int PROGRESSION_LENGTH_MAX = 16;
    public static final int COLORIZATION_RANGE_MAX = 4;
    public static final int COLORIZATION_RANGE_MIN = 0;
    public static final List<Integer> MINOR_CHORD_DEGREE_INDICES = List.of(1, 2, 5);
    public static final int APPLIED_DOMINANT_COEFFICIENT = 10;
    public static final int CHORD_DECREMENTER = 2;
}
