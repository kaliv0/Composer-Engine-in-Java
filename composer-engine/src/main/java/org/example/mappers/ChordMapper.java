/*
const { scaleCounter, scaleDegrees } = require("../constants/scales");
const { chordSuffixes, chordToneIndexes } = require("../constants/chords");
const { accidentals } = require("../constants/chromaticSigns");
const { modeTypes } = require("../constants/modes");
const { translateDominant } = require("./dominant-mapper");
const { raiseNote } = require('../alterators/note-alterator');

function readOtherNotesAboveRoot(scale, rootIndex, notesCount, mode) {
    let fullChord = [];
    let scaleIndex = rootIndex;
    for (let j = chordToneIndexes.ROOT; j < notesCount; j++) {
        if (mode === modeTypes.MINOR
            && rootIndex === scaleDegrees.DOMINANT
            && j === chordToneIndexes.THIRD) {

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

import java.util.List;

//maps chord abbreviations to full representation of the chords
public class ChordMapper {
    public static List<String> display(List<String> progression, List<String> scale, String mode) {
        let root;
        let signChar;
        let fullChord;
        let notesCount;

        let chordTable = progression.reduce((acc, chord) =>{
            signChar = chord.charAt(1);
            //finds chord root
            if (signChar == = accidentals.SHARP || signChar == = accidentals.FLAT) {
                root = chord.substring(0, 2);
            } else {
                root = chord.charAt(0);
            }

            //calculates if is triad or seventh chord
            if (chord.slice(-1) == = chordSuffixes.SEVENTH) {
                notesCount = chordToneIndexes.DOMINANT_NOTE_COUNT;
            } else {
                notesCount = chordToneIndexes.TRIAD_NOTE_COUNT;
            }

            if (notesCount == = chordToneIndexes.DOMINANT_NOTE_COUNT) {
                fullChord = translateDominant(scale, root, mode);
                acc.push({
                        name:chord,
                        content:fullChord,
            });
                return acc;
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
        }, []);

        //adjusts suspended chord if any
        if (chordTable.some(ch = > ch.name.includes(chordSuffixes.SUSPENDED))){
            //there could be no more than one suspended chord in the progression
            let susIndex = chordTable.findIndex(ch = > ch.name.includes(chordSuffixes.SUSPENDED));

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
        if (chordTable.some(ch = > ch.name.includes(chordSuffixes.SLASH_CHORD))){
            //there could be no more than one K64 chord in the progression
            let cadentialIndex = chordTable.findIndex(ch = > ch.name.includes(chordSuffixes.SLASH_CHORD));
            let bassNote = chordTable[cadentialIndex].content.pop();
            chordTable[cadentialIndex].content.unshift(bassNote);
        }
        return chordTable;
    }
}
