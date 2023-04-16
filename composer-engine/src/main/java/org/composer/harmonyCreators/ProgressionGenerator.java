package org.composer.harmonyCreators;

import org.composer.constants.Modes;
import org.composer.constants.chords.HarmonicFunctions;
import org.composer.constants.random.RandomBitStates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.composer.constants.chords.Common.*;
import static org.composer.constants.chords.HarmonicFunctions.SUBSIDIARY_SUBDOMINANT;
import static org.composer.randomGenerators.Randomizer.randomBit;

/**
 * creates simple chord progression in randomly chosen key
 * applying traditional laws of tonal harmony
 * features that could be added:
 * - add phrase boundaries
 * - add sequences
 * - add modal interchange (Neapolitan chord)
 */
public class ProgressionGenerator {
    public static List<String> generateProgression(final Map<Integer, String> tonalChords,
                                                   final String mode, final boolean shouldApplyDominants) {
        int progressionSize = 0;
        int funcIndex = 0;
        List<Integer> progression = new ArrayList<>();

        //checks total length of progression and decides to continue or not
        while (!((progressionSize >= PROGRESSION_LENGTH_MIN && randomBit() == RandomBitStates.NEGATIVE)
                || progressionSize == PROGRESSION_LENGTH_MAX)) {
            for (int chord : HarmonicFunctions.MATRIX[funcIndex]) {
                //decides to include new chord in progression
                if (randomBit() == RandomBitStates.POSITIVE) {
                    //avoids duplicates
                    if (progressionSize > 0 && (progression.get(progressionSize - 1) == chord
                            //avoids putting main chord after subsidiary one e.g. F after D min
                            || progression.get(progressionSize - 1) == chord - CHORD_DECREMENTER)) {
                        continue;
                    }
                    //avoids duplicates at penultimate index
                    if (progressionSize >= PROGRESSION_LENGTH_DUPLICATE_THRESHOLD && progression.get(progressionSize - 2) == chord) {
                        continue;
                    }
                    //avoids usage of Sii in minor outside ii-v sequence
                    if (mode.equals(Modes.MINOR) && chord == SUBSIDIARY_SUBDOMINANT) {
                        continue;
                    }
                    progression.add(chord);
                    progressionSize++;
                }
            }
            //decides to go to next chord function or skip one
            funcIndex += (randomBit() + RandomBitStates.RANDOM_BIT_INCREMENTER);
            if (funcIndex >= HarmonicFunctions.MATRIX.length) {
                funcIndex = 0;
            }
        }
        return Colorizer.colorize(progression, tonalChords, mode, shouldApplyDominants);
    }
}
