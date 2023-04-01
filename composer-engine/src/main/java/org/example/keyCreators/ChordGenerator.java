/*
const { MINOR_CHORD_DEGREE_INDECES, APPLIED_DOMINANT_COEFFICIENT,
    chordIndeces, rootDegrees, chordSuffixes } = require("../constants/chords");
const { modeTypes } = require("../constants/modes");
const { scaleDegrees } = require("../constants/scales");

function generateInMinor(scale) {
    let chords = scale.reduce((chordList, currScaleDegree, index) => {
        if (index === scaleDegrees.TONIC) {
            chordList[rootDegrees.KEY_CENTER] = currScaleDegree + chordSuffixes.MINOR;
            return chordList;
        }

        if (index === scaleDegrees.SUPERTONIC) {
            // could be changed to diminished seventh chord
            currScaleDegree += chordSuffixes.DIMINISHED
                    }
                    if (index === scaleDegrees.SUBDOMINANT) {
                    currScaleDegree += chordSuffixes.MINOR;
                    }
                    chordList[index + 1] = currScaleDegree;
                    return chordList;
                    }, {});

                    //creates applied dominants and cadential chords
                    let degreeIndex = scaleDegrees.SUBMEDIANT;
                    for (let j = chordIndeces.ALTERED_SUBMEDIANT; j <= chordIndeces.DOMINANT_SEVENTH; j += APPLIED_DOMINANT_COEFFICIENT) {
                    if (degreeIndex === scaleDegrees.SUBMEDIANT) {
                    // could be changed to French (flat five) chord
                    chords[j]=scale[degreeIndex]+chordSuffixes.MAJOR;
                            }
                            if(degreeIndex===scaleDegrees.UPPER_BOUND){
                            degreeIndex=scaleDegrees.TONIC;
                            }
                            chords[j]=scale[degreeIndex]+chordSuffixes.SEVENTH;
                            degreeIndex++;
                            }
                            return addSuspendedDominant(chords,scale);
                            }

                            function addSuspendedDominant(chords,scale){
                            chords[chordIndeces.SUSPENDED_DOMINANT]=scale[scaleDegrees.DOMINANT]+chordSuffixes.SUSPENDED;
                            chords[chordIndeces.CADENTIAL_SIX_FOUR_CHORD]=
                            `${scale[scaleDegrees.TONIC]}/${scale[scaleDegrees.DOMINANT]}`;
                            return chords;
                            }
        */
//generates all main chords in given key and all their applied dominants
package org.example.keyCreators;

import org.example.constants.Modes;

import java.util.List;

public class ChordGenerator {
    public static List<String> generateChords(List<String> scale, String mode) {
        if (mode.equals(Modes.MAJOR)) {
            return generateInMajor(scale);
        }
//        if (mode.equals(Modes.MINOR)) {
            return generateInMinor(scale);
//        }
    }

    private static List<String> generateInMajor(List<String> scale) {
        List<String> chords = scale.reduce((chordList, currScaleDegree, index) => {
            if (index === scaleDegrees.TONIC) {
                chordList[rootDegrees.KEY_CENTER] = currScaleDegree;
                return chordList;
            }

            if (MINOR_CHORD_DEGREE_INDECES.includes(index)) {
                currScaleDegree += chordSuffixes.MINOR;
            }
            if (index === scaleDegrees.SUBTONIC) {
                currScaleDegree += chordSuffixes.DIMINISHED
            }
            chordList[index + 1] = currScaleDegree;
            return chordList;
        }, {});

        //creates applied dominants and cadential chords
        let degreeIndex = scaleDegrees.SUBMEDIANT;
        for (let j = chordIndeces.ALTERED_SUBMEDIANT; j <= chordIndeces.DOMINANT_SEVENTH; j += APPLIED_DOMINANT_COEFFICIENT) {
            if (degreeIndex === scaleDegrees.UPPER_BOUND) {
                degreeIndex = scaleDegrees.TONIC;
            }
            if (degreeIndex === scaleDegrees.SUBDOMINANT) {
                chords[j] = scale[degreeIndex] + chordSuffixes.MAJOR + chordSuffixes.SEVENTH;
            }
            chords[j] = scale[degreeIndex] + chordSuffixes.SEVENTH;
            degreeIndex++;
        }
        return addSuspendedDominant(chords, scale);
    }
}
