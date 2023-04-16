package org.composer.invertors;

import org.composer.common.Chord;
import org.composer.randomGenerators.Randomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.composer.constants.chords.ChordSuffixes.SLASH_CHORD;

public class ChordInverter {
    public static List<Chord> applyVoiceLeading(List<Chord> progression) {
        int shouldInvertChord = Randomizer.randomIntegerFromInterval(0, 3);
        if (shouldInvertChord != 2) {
            List<String> currentChordTones = progression.get(0).getContent(); //TODO: declare above if clause
            int chordTonesCount = currentChordTones.size();
            String pivotNote = currentChordTones.get(Randomizer.randomIntegerFromInterval(0, chordTonesCount));
            int targetIndex = Randomizer.randomIntegerFromInterval(0, chordTonesCount);
            progression.get(0).setContent(invertChord(pivotNote, targetIndex, currentChordTones));
        }

        List<String> currentChordTones;
        List<String> previousChordTones;
        int shouldKeepCommonNote;
        //find indexOf final cadence -> loop til there instead of progression.size()
        for (int i = 1; i < progression.size(); i++) {
            if (progression.get(i).getName().contains(SLASH_CHORD)) {
                continue;
            }

            currentChordTones = progression.get(i).getContent();
            previousChordTones = progression.get(i - 1).getContent();

//            shouldInvertChord = Randomizer.randomIntegerFromInterval(0, 3);
//            if (shouldInvertChord != 2) {
            //check if current chord and previous one have any notes in common
            List<String> commonNotesList = new ArrayList<>(previousChordTones);

            commonNotesList.retainAll(currentChordTones);
            if (commonNotesList.size() != 0) {
                //if yes decide to keep it in the same voice -> at same index in arr
//                    shouldKeepCommonNote = Randomizer.randomIntegerFromInterval(0, 3);
//                    if (shouldKeepCommonNote != 2) {
                //find index of common note and swap
                String commonNote = commonNotesList.get(0); //TODO: move logic inside private method
                int indexInPreviousChord = previousChordTones.indexOf(commonNote);
                progression.get(i).setContent(invertChord(commonNote, indexInPreviousChord, currentChordTones));
//                    }
            }
            //else apply other inversion or go to root position (skip inversion)
//            }
        }
        return progression;
    }

    private static List<String> invertChord(final String pivotNote, final int targetIndex, List<String> chordTones) {
        final int currIndex = chordTones.indexOf(pivotNote);
        if (targetIndex == currIndex) {
            //no need to invert
            return chordTones;
        }

    /*
        //if one of the chords is a dominant 7th -> decide between two contiguous index positions
        int shiftIndexPosition = 0;
        if (chordTones.size() == 4) {
            shiftIndexPosition += Randomizer.randomBit();
        }
        Collections.rotate(chordTones, chordTones.size() - currIndex + targetIndex + shiftIndexPosition);
    */

        Collections.rotate(chordTones, chordTones.size() - currIndex + targetIndex);
        return chordTones;
    }
}
