/*
const { scaleCounter, scaleDegrees } = require("../constants/scales");
const { ChordSuffixes, ChordToneIndices } = require("../constants/chords");
const { ChromaticSigns } = require("../constants/chromaticSigns");
const { modeTypes } = require("../constants/modes");
const { translateDominant } = require("./dominant-mapper");
const { raiseNote } = require('../alterators/note-alterator');

function readOtherNotesAboveRoot(scale, rootIndex, notesCount, mode) {
    let fullChord = [];
    let scaleIndex = rootIndex;
    for (let j = ChordToneIndices.ROOT; j < notesCount; j++) {
        if (mode === modeTypes.MINOR
            && rootIndex === scaleDegrees.DOMINANT
            && j === ChordToneIndices.THIRD) {

            //alters chord on fifth degree in minor mode
            fullChord.push(raiseNote(scale[scaleIndex]));
        } else {
            fullChord.push(scale[scaleIndex]);
        }

        //keeps index within octave boundaries
        if (scaleIndex + scaleCounter.INCREMENTER >= scaleCounter.DEGREE_COUNT) {
            scaleIndex -= scaleCounter.DECREMENTER;
        } else {
            scaleIndex += scaleCounter.INCREMENTER;
        }
    }
    return fullChord;
}
 */

package org.example.mappers;

import org.example.common.Chord;
import org.example.constants.ChromaticSigns;
import org.example.constants.chords.ChordSuffixes;
import org.example.constants.chords.ChordToneIndices;

import java.util.ArrayList;
import java.util.List;

//maps chord abbreviations to full representation of the chords
public class ChordMapper {
    public static List<Chord> display(List<String> progression, List<String> scale, String mode) {
        String root;
        String signChar;
        String[] fullChord;
        int notesCount;

//        let chordTable = progression.reduce((acc, chord) =>{
        List<Chord> chordTable = new ArrayList<>();
        for (String chord : progression) {
            signChar = String.valueOf(chord.charAt(1));
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
//                acc.push({
//                        name:chord,
//                        content:fullChord,
//            });
                chordTable.add(new Chord(chord, fullChord));
//                return acc;
                continue;
            }

        const rootIndex = scale.indexOf(root);
            fullChord = readOtherNotesAboveRoot(
                    scale, rootIndex, notesCount, mode);

            //maps abbrevation to full chord
            acc.push({
                    name:chord,
                    content:fullChord,
        });
            return acc;
        }

        //adjusts suspended chord if any
        if (chordTable.some(ch = > ch.name.includes(ChordSuffixes.SUSPENDED))){
            //there could be no more than one suspended chord in the progression
            let susIndex = chordTable.findIndex(ch = > ch.name.includes(ChordSuffixes.SUSPENDED));

            let middleIndex = scale.indexOf(chordTable[susIndex].content[1]);
            //keeps index within octave boundaries
            if (middleIndex + scaleCounter.SUSPENDED_CHORD_INCREMENTER >= scaleCounter.DEGREE_COUNT) {
                middleIndex -= scaleCounter.SUSPENDED_CHORD_DECREMENTER;
            } else {
                middleIndex += scaleCounter.SUSPENDED_CHORD_INCREMENTER;
            }

            chordTable[susIndex].content[1] = scale[middleIndex];
        }

        //аdjusts K46 chord if any
        if (chordTable.some(ch = > ch.name.includes(ChordSuffixes.SLASH_CHORD))){
            //there could be no more than one K64 chord in the progression
            let cadentialIndex = chordTable.findIndex(ch = > ch.name.includes(ChordSuffixes.SLASH_CHORD));
            let bassNote = chordTable[cadentialIndex].content.pop();
            chordTable[cadentialIndex].content.unshift(bassNote);
        }
        return chordTable;
    }
}
