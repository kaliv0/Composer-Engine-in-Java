package org.composer.inverters;

import org.composer.common.Chord;
import org.composer.randomGenerators.Randomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.composer.constants.Pitches.LEAP_INDICES;
import static org.composer.constants.chords.ChordSuffixes.SLASH_CHORD;

public class ChordInverter {
    public static List<Chord> applyVoiceLeading(List<Chord> progression) {
        int shouldInvertChord = Randomizer.randomIntegerFromInterval(0, 5);
        if (shouldInvertChord != 4) {
            List<String> currentChordTones = progression.get(0).getContent(); //TODO: declare above if clause
            int chordTonesCount = currentChordTones.size();
            String pivotNote = currentChordTones.get(Randomizer.randomIntegerFromInterval(0, chordTonesCount));
            int targetIndex = Randomizer.randomIntegerFromInterval(0, chordTonesCount);
            progression.get(0).setContent(invertChord(pivotNote, targetIndex, currentChordTones, ""));
        }

        List<String> currentChordTones;
        List<String> previousChordTones;
        int shouldKeepCommonNote;

        for (int i = 1; i < progression.size() - 1; i++) {
            //find final cadence with K64 if present
            if (progression.get(i).getName().contains(SLASH_CHORD)) {
                shouldInvertChord = Randomizer.randomIntegerFromInterval(0, 3);
                if (shouldInvertChord != 2) {
                    //invert final dominant chord
                    List<String> finalDominantChord = progression.get(i + 1).getContent();
                    String root = finalDominantChord.get(0);
                    progression.get(i + 1).setContent(invertChord(root, 1, finalDominantChord, ""));
                }
                break;
            }

            currentChordTones = progression.get(i).getContent();
            previousChordTones = progression.get(i - 1).getContent();
            String previousTopNote = previousChordTones.get(previousChordTones.size() - 1);
            shouldInvertChord = Randomizer.randomIntegerFromInterval(0, 5);
            if (shouldInvertChord != 4) {
                //check if current chord and previous one have any notes in common
                List<String> commonNotesList = new ArrayList<>(previousChordTones);
                commonNotesList.retainAll(currentChordTones);
                if (commonNotesList.size() != 0) {
                    //if yes decide to keep it in the same voice -> at same index in arr - or not
                    shouldKeepCommonNote = Randomizer.randomIntegerFromInterval(0, 5);
                    if (shouldKeepCommonNote != 4) {
                        //find index of common note and swap
                        String commonNote = commonNotesList.get(0); //TODO: move logic inside private method
                        int indexInPreviousChord = previousChordTones.indexOf(commonNote);
                        currentChordTones = invertChord(commonNote, indexInPreviousChord, currentChordTones, previousTopNote);
                    }
                } else {
                    //TODO: else apply other inversion or go to root position (skip inversion) -> go to the nearest tone in the top voice
                    //TODO: combine with previous if clause
                    int chordTonesCount = currentChordTones.size();
                    String pivotNote = currentChordTones.get(Randomizer.randomIntegerFromInterval(0, chordTonesCount));
                    int targetIndex = Randomizer.randomIntegerFromInterval(0, chordTonesCount);
                    currentChordTones = invertChord(pivotNote, targetIndex, currentChordTones, previousTopNote);
                }
                progression.get(i).setContent(currentChordTones);
            }
        }

        //re-adjust final tonic resolution
        //TODO: refactor -> same variables used previously
        currentChordTones = progression.get(progression.size() - 1).getContent();
        previousChordTones = progression.get(progression.size() - 2).getContent();
        String previousTopNote = previousChordTones.get(previousChordTones.size() - 1);
        List<String> commonNotesList = new ArrayList<>(previousChordTones);
        commonNotesList.retainAll(currentChordTones);
        if (commonNotesList.size() != 0) {
            if (previousChordTones.size() == 3 && Randomizer.randomIntegerFromInterval(0, 3) != 2) {
                //skip inverting the final tonic after simple dominant triad
                return progression;
            }

            String commonNote = commonNotesList.get(0); //TODO: move logic inside private method
            int indexInPreviousChord = previousChordTones.indexOf(commonNote);
            progression.get(progression.size() - 1)
                    .setContent(invertChord(commonNote, indexInPreviousChord, currentChordTones, previousTopNote));
        }

        return progression;
    }

    private static List<String> invertChord(final String pivotNote, final int targetIndex,
                                            List<String> chordTones, final String previousTopNote) {
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

        int chordSize = chordTones.size();
        Collections.rotate(chordTones, chordSize - currIndex + targetIndex);

        //TODO: add check here if newly rotated chord created a leap from the previous one (also pass previous chord as a parameter)
        //if true -> rotate chord once again.
        //decide implementation for first chord in progression as there is no previous one
        //TODO: find optimal way to calculate distance

        String newTopNote = chordTones.get(chordSize - 1);
        if (!previousTopNote.equals("") && Math.abs(LEAP_INDICES.get(previousTopNote) - LEAP_INDICES.get(newTopNote)) > 2) {
            invertChord(pivotNote, targetIndex, chordTones, previousTopNote);
        }
        return chordTones; //TODO: unnecessary return
    }
}
