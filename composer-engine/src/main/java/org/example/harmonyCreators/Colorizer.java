package org.example.harmonyCreators;

/*
//colorizes given progression by inserting applied dominants, ii-v 'movements'
//and appends final cadence
const { harmonicFunctions, chordIndeces, RootDegrees, APPLIED_DOMINANT_COEFFICIENT, COLORIZATION_RANGE } = require("../constants/chords");
const { randomBitStates, randomIntegerIndeces } = require("../constants/randoms");
const { modeTypes } = require("../constants/modes");
const { randomIntegerFromInterval, randomBit } = require("../random-generators/randomizer");
const { materialize } = require("../mappers/tonal-mapper");



function colorizeFourthDegree(
    chord, colorizedProgression, colorizationIndex, progression,
    index, appliedChord) {

    appliedChord = randomIntegerFromInterval(randomIntegerIndeces.MIN, randomIntegerIndeces.SECONDARY);
    //adds mediant between tonic and subdominant
    if (appliedChord === randomIntegerIndeces.PRIMARY
        && progression[index - 1] === RootDegrees.KEY_CENTER) {

        colorizedProgression.push(RootDegrees.MEDIANT);
        colorizationIndex++;
    }
    colorizedProgression.push(chord);
    return [colorizedProgression, colorizationIndex];
}

function colorizeFourthDegreeWithAppliedDominants(
    chord, colorizedProgression, colorizationIndex, progression,
    index, appliedChord) {

    appliedChord = randomIntegerFromInterval(randomIntegerIndeces.MIN, randomIntegerIndeces.MAX);
    //adds applied dominant 7th
    if (appliedChord === randomIntegerIndeces.PRIMARY) {
        colorizedProgression.push(chordIndeces.ALTERED_TONIC);
        colorizationIndex++;
    }
    //adds mediant between tonic and subdominant
    else if (appliedChord === randomIntegerIndeces.SECONDARY
        && progression[index - 1] === RootDegrees.KEY_CENTER) {

        colorizedProgression.push(RootDegrees.MEDIANT);
        colorizationIndex++;
    }
    colorizedProgression.push(chord);
    return [colorizedProgression, colorizationIndex];
}

function colorizeSixthDegree(
    chord, colorizedProgression, colorizationIndex,
    progression, index, appliedChord, mode) {

    appliedChord = randomIntegerFromInterval(randomIntegerIndeces.MIN, randomIntegerIndeces.MAX);
    //adds mediant chord
    if (appliedChord === randomIntegerIndeces.PRIMARY) {
        colorizedProgression.push(RootDegrees.MEDIANT);
        colorizationIndex++;
    }
    //adds ii-v transition after tonic
    else if (mode === modeTypes.MINOR
        && appliedChord === randomIntegerIndeces.SECONDARY
        && progression[index - 1] === RootDegrees.KEY_CENTER) {

        colorizedProgression.push(RootDegrees.SUBTONIC);
        colorizedProgression.push(RootDegrees.MEDIANT);
        colorizationIndex++;
    }
    colorizedProgression.push(chord);
    return [colorizedProgression, colorizationIndex];
}

function colorizeSixthDegreeWithAppliedDominants(
    chord, colorizedProgression, colorizationIndex,
    progression, index, appliedChord, mode) {

    appliedChord = randomIntegerFromInterval(
        randomIntegerIndeces.MIN, randomIntegerIndeces.RANGE_UPPER_BOUND);
    //adds applied dominant 7th
    if (appliedChord === randomIntegerIndeces.PRIMARY) {
        colorizedProgression.push(chordIndeces.ALTERED_MEDIANT);
        colorizationIndex++;
    }
    //adds mediant chord
    else if (appliedChord === randomIntegerIndeces.SECONDARY) {
        colorizedProgression.push(RootDegrees.MEDIANT);
        colorizationIndex++;
    }
    //adds ii-v transition after tonic
    else if (appliedChord === randomIntegerIndeces.MAX
        && progression[index - 1] === RootDegrees.KEY_CENTER) {

        colorizedProgression.push(RootDegrees.SUBTONIC);
        if (mode === modeTypes.MAJOR) {
            colorizedProgression.push(chordIndeces.ALTERED_MEDIANT)
        } else {
            colorizedProgression.push(RootDegrees.MEDIANT);
        }
        colorizationIndex++;
    }

    colorizedProgression.push(chord);
    return [colorizedProgression, colorizationIndex];
}

function createFinalAuthenticCadence(colorizedProgression) {
    if (randomBit() === randomBitStates.NEGATIVE
        && colorizedProgression[colorizedProgression.length - 1] !== RootDegrees.KEY_CENTER) {

        //adds double appoggiatura to dominant seventh chord
        colorizedProgression.push(chordIndeces.CADENTIAL_SIX_FOUR_CHORD);
        colorizedProgression.push(chordIndeces.DOMINANT_SEVENTH);
        return colorizedProgression;
    }
    //adds single leaning tone to dominant five chord
    colorizedProgression.push(chordIndeces.SUSPENDED_DOMINANT);
    colorizedProgression.push(harmonicFunctions.DOMINANT[0]);
    return colorizedProgression;
}

module.exports = { colorize };
 */
