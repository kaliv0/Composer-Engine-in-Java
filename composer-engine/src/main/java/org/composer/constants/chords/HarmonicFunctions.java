package org.composer.constants.chords;

public class HarmonicFunctions {
    //main tonic written as 8 instead of 1 for computational reasons
    public static final int[] TONIC = {8, 6};
    public static final int[] SUBDOMINANT = {4, 2};
    public static final int[] DOMINANT = {5};
    public static final int[][] MATRIX = new int[][]{TONIC, SUBDOMINANT, DOMINANT};
    public static final int SUBSIDIARY_SUBDOMINANT = 2;

}
