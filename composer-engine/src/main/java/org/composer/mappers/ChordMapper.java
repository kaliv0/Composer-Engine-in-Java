package org.composer.mappers;

import org.composer.common.Chord;
import org.composer.constants.ChromaticSigns;
import org.composer.constants.Modes;
import org.composer.constants.chords.ChordSuffixes;
import org.composer.constants.chords.ChordToneIndices;
import org.composer.constants.scales.ScaleCounter;
import org.composer.constants.scales.ScaleDegrees;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.composer.alterators.Alterators.raiseNote;
import static org.composer.mappers.DominantMapper.translateDominant;

/**
 * maps chord abbreviations to full representation of the chords
 */
public class ChordMapper {
    public static List<Chord> display(final List<String> progression, final List<String> scale, final String mode) {
        String root;
        String signChar;
        int notesCount;
        List<String> fullChord;
        List<Chord> chordTable = new ArrayList<>();
        for (String chord : progression) {
            signChar = chord.length() > 1 ? String.valueOf(chord.charAt(1)) : "";
            //finds chord root
            if (signChar.equals(ChromaticSigns.SHARP) || signChar.equals(ChromaticSigns.FLAT)) {
                root = chord.substring(0, 2);
            } else {
                root = String.valueOf(chord.charAt(0));
            }

            //calculates if is triad or seventh chord
            if (String.valueOf(chord.charAt(chord.length() - 1)).equals(ChordSuffixes.SEVENTH)) {
                notesCount = ChordToneIndices.DOMINANT_NOTE_COUNT;
            } else {
                notesCount = ChordToneIndices.TRIAD_NOTE_COUNT;
            }
            if (notesCount == ChordToneIndices.DOMINANT_NOTE_COUNT) {
                fullChord = translateDominant(scale, root, mode);
                chordTable.add(new Chord(chord, fullChord));
                continue;
            }
            final int rootIndex = scale.indexOf(root);
            fullChord = readOtherNotesAboveRoot(scale, rootIndex, notesCount, mode);

            //maps abbreviation to full chord
            chordTable.add(new Chord(chord, fullChord));
        }

        //adjusts suspended chord if any
        Optional<Chord> susChord = chordTable.stream()
                .filter(ch -> ch.getName().contains(ChordSuffixes.SUSPENDED))
                .findFirst();
        if (susChord.isPresent()) {
            //there could be no more than one suspended chord in the progression
            int susIndex = chordTable.indexOf(susChord.get());

            int middleIndex = scale.indexOf(
                    chordTable.get(susIndex)
                            .getContent().get(1)
            );
            //keeps index within octave boundaries
            if (middleIndex + ScaleCounter.SUSPENDED_CHORD_INCREMENTER >= ScaleCounter.DEGREE_COUNT) {
                middleIndex -= ScaleCounter.SUSPENDED_CHORD_DECREMENTER;
            } else {
                middleIndex += ScaleCounter.SUSPENDED_CHORD_INCREMENTER;
            }

            chordTable.get(susIndex)
                    .getContent().set(
                            1, scale.get(middleIndex)
                    );
        }

        //adjusts K46 chord if any
        Optional<Chord> slashChord = chordTable.stream()
                .filter(ch -> ch.getName().contains(ChordSuffixes.SLASH_CHORD))
                .findFirst();
        if (slashChord.isPresent()) {
            //there could be no more than one K64 chord in the progression
            int cadentialIndex = chordTable.indexOf(slashChord.get());
            int chordSize = chordTable.get(cadentialIndex).getContent().size();
            String bassNote = chordTable.get(cadentialIndex)
                    .getContent()
                    .remove(chordSize - 1);
            chordTable.get(cadentialIndex).getContent().add(0, bassNote);
        }
        return chordTable;
    }

    private static List<String> readOtherNotesAboveRoot(List<String> scale, int rootIndex, int notesCount, final String mode) {
        List<String> fullChord = new ArrayList<>();
        int scaleIndex = rootIndex;
        for (int i = ChordToneIndices.ROOT; i < notesCount; i++) {
            if (Objects.equals(mode, Modes.MINOR)
                    && rootIndex == ScaleDegrees.DOMINANT
                    && i == ChordToneIndices.THIRD) {
                //alters chord on fifth degree in minor mode
                fullChord.add(raiseNote(scale.get(scaleIndex)));
            } else {
                fullChord.add(scale.get(scaleIndex));
            }

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
