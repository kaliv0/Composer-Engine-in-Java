//creates simple chord progression in randomly chosen key
//applying traditional laws of tonal harmony

/*
features that could be added:
- add phrase boundaries
- add sequences
- add modal interchange (Neapolitan chord)
*/
package org.example.harmonyCreators;

import org.example.constants.chords.HarmonicFunctions;
import org.example.constants.random.RandomBitStates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.example.constants.chords.Common.*;
import static org.example.randomGenerators.Randomizer.randomBit;

public class ProgressionGenerator {
    public static List<String> generateProgression(Map<Integer, String> tonalChords, String mode, int shouldApplyDominants) {
        List<Integer> progression = new ArrayList<>();
        int progressionSize = 0;
        int funcIndex = 0;

        while (true) {
            for (int chord : HarmonicFunctions.MATRIX[funcIndex]) {
                //decides to include new chord in progression
                if (randomBit() == RandomBitStates.POSITIVE) {
                    //avoids duplicates
                    if (progressionSize > 0 &&
                            (progression.get(progressionSize - 1) == chord
                                    //avoids putting main chord after subsidiary one e.g. F after D min
                                    || progression.get(progressionSize - 1) == chord - CHORD_DECREMENTER)) {
                        continue;
                    }
                    //avoids duplicates at penultimate index
                    if (progressionSize >= PROGRESSION_LENGTH_DUPLICATE_THRESHOLD
                            && progression.get(progressionSize - 2) == chord) {
                        continue;
                    }
                    progression.add(chord);
                    progressionSize++;
                }
            }
            //checks total length of progression and decides to continue or not
            if ((progressionSize >= PROGRESSION_LENGTH_MIN && randomBit() == RandomBitStates.NEGATIVE)
                    || progressionSize == PROGRESSION_LENGTH_MAX) {
                break;
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
