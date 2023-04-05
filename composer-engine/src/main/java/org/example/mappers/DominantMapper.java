/*
const { modeTypes } = require("../constants/modes");
const { chordToneIndexes } = require("../constants/chords");
const { scaleCounter } = require("../constants/scales");
const { majorDominant, minorDominant } = require("../constants/dominantIndeces");
const { raiseNote, lowerNote } = require('../alterators/note-alterator');

function translateDominant(scale, root, mode) {
    let raiseThirdIndeces;
    let raiseFifthIndex;
    let lowerSeventhIndeces;

    if (mode === modeTypes.MAJOR) {
        raiseThirdIndeces = majorDominant.RAISE_THIRD_INDECES;
        raiseFifthIndex = majorDominant.RAISE_FIFTH_INDECES;
        lowerSeventhIndeces = majorDominant.LOWER_SEVENTH_INDECES;
    } else {
        raiseThirdIndeces = minorDominant.RAISE_THIRD_INDECES;
        raiseFifthIndex = minorDominant.RAISE_FIFTH_INDECES;
        lowerSeventhIndeces = minorDominant.LOWER_SEVENTH_INDECES;
    }

    let rootIndex = scale.indexOf(root);
    let scaleIndex = rootIndex;
    let fullChord = [];
    let currNote;
    for (let j = chordToneIndexes.ROOT; j < chordToneIndexes.DOMINANT_NOTE_COUNT; j++) {
        currNote = scale[scaleIndex];

        if (j === chordToneIndexes.THIRD && raiseThirdIndeces.includes(rootIndex)) {
            currNote = raiseNote(currNote);
        }

        if (j === chordToneIndexes.FIFTH && raiseFifthIndex.includes(rootIndex)) {
            currNote = raiseNote(currNote);
        }

        if (j === chordToneIndexes.SEVENTH && lowerSeventhIndeces.includes(rootIndex)) {
            currNote = lowerNote(currNote);
        }

        fullChord.push(currNote);
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

//reads notes above root in dominant cords and adds accidentals where necessary
public class DominantMapper {
}
