package org.composer.mappers;

import org.composer.constants.Modes;
import org.composer.constants.chords.ChordToneIndices;
import org.composer.constants.dominantIndices.MajorDominant;
import org.composer.constants.dominantIndices.MinorDominant;
import org.composer.constants.scales.ScaleCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.composer.alterators.Alterators.lowerNote;
import static org.composer.alterators.Alterators.raiseNote;

/**
 * reads notes above root in dominant cords and adds accidentals where necessary
 */
public class DominantMapper {
    public static List<String> translateDominant(List<String> scale, String root, String mode) {
        List<Integer> raiseThirdIndices;
        List<Integer> raiseFifthIndex;
        List<Integer> lowerSeventhIndices;
        List<String> fullChord = new ArrayList<>();
        if (Objects.equals(mode, Modes.MAJOR)) {
            raiseThirdIndices = MajorDominant.RAISE_THIRD_INDICES;
            raiseFifthIndex = MajorDominant.RAISE_FIFTH_INDICES;
            lowerSeventhIndices = MajorDominant.LOWER_SEVENTH_INDICES;
        } else {
            raiseThirdIndices = MinorDominant.RAISE_THIRD_INDICES;
            raiseFifthIndex = MinorDominant.RAISE_FIFTH_INDICES;
            lowerSeventhIndices = MinorDominant.LOWER_SEVENTH_INDICES;
        }

        int rootIndex = scale.indexOf(root);
        int scaleIndex = rootIndex;
        String currNote;
        for (int j = ChordToneIndices.ROOT; j < ChordToneIndices.DOMINANT_NOTE_COUNT; j++) {
            currNote = scale.get(scaleIndex);
            if (j == ChordToneIndices.THIRD && raiseThirdIndices.contains(rootIndex)) {
                currNote = raiseNote(currNote);
            }
            if (j == ChordToneIndices.FIFTH && raiseFifthIndex.contains(rootIndex)) {
                currNote = raiseNote(currNote);
            }
            if (j == ChordToneIndices.SEVENTH && lowerSeventhIndices.contains(rootIndex)) {
                currNote = lowerNote(currNote);
            }
            fullChord.add(currNote);
            //keeps index within octave boundaries
            if (scaleIndex + ScaleCounter.INCREMENTER >= ScaleCounter.DEGREE_COUNT) {
                scaleIndex -= ScaleCounter.DECREMENTER;
            } else {
                scaleIndex += ScaleCounter.INCREMENTER;
            }
        }
        return fullChord;
    }
}
