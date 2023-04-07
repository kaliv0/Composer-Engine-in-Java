package org.composer.keyCreators;

import org.composer.constants.Modes;
import org.composer.constants.chords.ChordIndices;
import org.composer.constants.chords.ChordSuffixes;
import org.composer.constants.chords.RootDegrees;
import org.composer.constants.scales.ScaleDegrees;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.composer.constants.chords.Common.APPLIED_DOMINANT_COEFFICIENT;
import static org.composer.constants.chords.Common.MINOR_CHORD_DEGREE_INDICES;

/**
 * generates all main chords in given key and all their applied dominants
 */
public class ChordGenerator {
    public static Map<Integer, String> generateChords(final List<String> scale, final String mode) {
        if (mode.equals(Modes.MAJOR)) {
            return generateInMajor(scale);
        }
        return generateInMinor(scale);
    }

    private static Map<Integer, String> generateInMajor(final List<String> scale) {
        Map<Integer, String> chords = new HashMap<>();
        for (int i = 0; i < scale.size(); i++) {
            String currScaleDegree = scale.get(i);
            if (i == ScaleDegrees.TONIC) {
                chords.put(RootDegrees.KEY_CENTER, currScaleDegree);
                continue;
            }

            if (MINOR_CHORD_DEGREE_INDICES.contains(i)) {
                currScaleDegree += ChordSuffixes.MINOR;
            }
            if (i == ScaleDegrees.SUBTONIC) {
                currScaleDegree += ChordSuffixes.DIMINISHED;
            }
            chords.put(i + 1, currScaleDegree);
        }

        //creates applied dominants and cadential chords
        int degreeIndex = ScaleDegrees.SUBMEDIANT;
        for (int j = ChordIndices.ALTERED_SUBMEDIANT; j <= ChordIndices.DOMINANT_SEVENTH; j += APPLIED_DOMINANT_COEFFICIENT) {
            if (degreeIndex == ScaleDegrees.UPPER_BOUND) {
                degreeIndex = ScaleDegrees.TONIC;
            }
            if (degreeIndex == ScaleDegrees.SUBDOMINANT) {
                chords.put(j, scale.get(degreeIndex) + ChordSuffixes.MAJOR + ChordSuffixes.SEVENTH);
            }
            chords.put(j, scale.get(degreeIndex) + ChordSuffixes.SEVENTH);
            degreeIndex++;
        }
        return addSuspendedDominant(chords, scale, false);
    }

    private static Map<Integer, String> generateInMinor(final List<String> scale) {
        Map<Integer, String> chords = new HashMap<>();
        for (int i = 0; i < scale.size(); i++) {
            String currScaleDegree = scale.get(i);
            if (i == ScaleDegrees.TONIC) {
                chords.put(RootDegrees.KEY_CENTER, currScaleDegree + ChordSuffixes.MINOR);
                continue;
            }

            if (i == ScaleDegrees.SUPERTONIC) {
                // could be changed to diminished seventh chord
                currScaleDegree += ChordSuffixes.DIMINISHED;
            }
            if (i == ScaleDegrees.SUBDOMINANT) {
                currScaleDegree += ChordSuffixes.MINOR;
            }
            chords.put(i + 1, currScaleDegree);
        }

        //creates applied dominants and cadential chords
        int degreeIndex = ScaleDegrees.SUBMEDIANT;
        for (int j = ChordIndices.ALTERED_SUBMEDIANT; j <= ChordIndices.DOMINANT_SEVENTH; j += APPLIED_DOMINANT_COEFFICIENT) {
            if (degreeIndex == ScaleDegrees.SUBMEDIANT) {
                // could be changed to French (flat five) chord
                chords.put(j, scale.get(degreeIndex) + ChordSuffixes.MAJOR);
            }
            if (degreeIndex == ScaleDegrees.UPPER_BOUND) {
                degreeIndex = ScaleDegrees.TONIC;
            }
            chords.put(j, scale.get(degreeIndex) + ChordSuffixes.SEVENTH);
            degreeIndex++;
        }
        return addSuspendedDominant(chords, scale, true);
    }

    private static Map<Integer, String> addSuspendedDominant(Map<Integer, String> chords, final List<String> scale, final boolean isMinor) {
        chords.put(ChordIndices.SUSPENDED_DOMINANT, scale.get(ScaleDegrees.DOMINANT) + ChordSuffixes.SUSPENDED);
        final String template = isMinor ? "%sm/%s" : "%s/%s";
        chords.put(ChordIndices.CADENTIAL_SIX_FOUR_CHORD,
                String.format(template, scale.get(ScaleDegrees.TONIC), scale.get(ScaleDegrees.DOMINANT)));
        return chords;
    }
}
