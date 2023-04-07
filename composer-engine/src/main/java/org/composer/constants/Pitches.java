package org.composer.constants;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class Pitches {
    public final static List<String> SCALE_PITCHES = List.of("A", "B", "C", "D", "E", "F", "G");
    public final static Map<String, Integer> MAJOR_CIRCLE_OF_FIFTHS = Map.ofEntries(
            entry("C", 0),
            entry("G", 1),
            entry("D", 2),
            entry("A", 3),
            entry("E", 4),
            entry("B", 5),
            entry("F#", 6),
            entry("C#", 7),
            entry("Cb", 7),
            entry("Gb", 6),
            entry("Db", 5),
            entry("Ab", 4),
            entry("Eb", 3),
            entry("Bb", 2),
            entry("F", 1),
            //non-existing, added here for computational reasons
            entry("G#", 8),
            entry("D#", 9),
            entry("A#", 10)
    );
}
