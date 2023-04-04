package org.example.harmonyCreators;

import org.example.common.Tuple;
import org.example.constants.Modes;
import org.example.constants.chords.ChordIndices;
import org.example.constants.chords.HarmonicFunctions;
import org.example.constants.chords.RootDegrees;
import org.example.constants.random.RandomBitStates;
import org.example.constants.random.RandomIntegerIndices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.example.constants.chords.Common.APPLIED_DOMINANT_COEFFICIENT;
import static org.example.constants.chords.Common.COLORIZATION_RANGE_MAX;
import static org.example.mappers.TonalMapper.materialize;
import static org.example.randomGenerators.Randomizer.randomBit;
import static org.example.randomGenerators.Randomizer.randomIntegerFromInterval;


//colorizes given progression by inserting applied dominants, ii-v 'movements'
//and appends final cadence
public class Colorizer {
    public static List<String> colorize(List<Integer> progression, Map<Integer, String> tonalChords,
                                        String mode, int shouldApplyDominants) {
        int colorizationIndex = COLORIZATION_RANGE_MAX;
        List<Integer> colorizedProgression = new ArrayList<>();
        colorizedProgression.add(progression.get(0));

        //adds up to three applied dominants or mediants skipping first chord in given progression
        int chord;
        int appliedChord = 0; //TODO: check usage and how it's transformed
        Tuple<List<Integer>, Integer> colorizedTuple;
        for (int index = 1; index < progression.size(); index++) {
            chord = progression.get(index);

            if (colorizationIndex == COLORIZATION_RANGE_MAX) {
                colorizedProgression.add(chord);
                continue;
            }

            //colorizes 4th degree
            if (chord == RootDegrees.SUBDOMINANT) {
                if (shouldApplyDominants == 1) {
                    colorizedTuple = colorizeFourthDegreeWithAppliedDominants(
                            chord, colorizedProgression, colorizationIndex,
                            progression, index, appliedChord
                    );
                    colorizedProgression = colorizedTuple.getFirst();
                    colorizationIndex = colorizedTuple.getSecond();
                    continue;
                }

                colorizedTuple = colorizeFourthDegree(
                        chord, colorizedProgression, colorizationIndex,
                        progression, index, appliedChord
                );
                colorizedProgression = colorizedTuple.getFirst();
                colorizationIndex = colorizedTuple.getSecond();
                continue;
            }

            //avoids situations of type 'Gdim, G7, C7, Fm'
            if (Objects.equals(mode, Modes.MINOR)
                    && chord == RootDegrees.DOMINANT
                    && progression.get(index - 1) == RootDegrees.SUPERTONIC) {

                colorizedProgression.add(chord);
                continue;
            }

            //colorizes 6th degree
            if (chord == RootDegrees.SUBMEDIANT) {
                if (shouldApplyDominants == 1) {
                    colorizedTuple = colorizeSixthDegreeWithAppliedDominants(
                            chord, colorizedProgression, colorizationIndex,
                            progression, index, appliedChord, mode
                    );
                    colorizedProgression = colorizedTuple.getFirst();
                    colorizationIndex = colorizedTuple.getSecond();
                    continue;
                }

                colorizedTuple = colorizeSixthDegree(
                        chord, colorizedProgression, colorizationIndex, progression,
                        index, shouldApplyDominants, appliedChord, mode
                );
                colorizedProgression = colorizedTuple.getFirst();
                colorizationIndex = colorizedTuple.getSecond();
                continue;
            }

            //colorizes chords on other degrees
            if (shouldApplyDominants == 1
                    && progression.get(index) != RootDegrees.KEY_CENTER
                    && randomBit() == RandomBitStates.POSITIVE) {

                colorizedProgression.add(chord * APPLIED_DOMINANT_COEFFICIENT);
                colorizationIndex++;
            }
            colorizedProgression.add(chord);
        }

        if (colorizedProgression.get(colorizedProgression.size() - 1) != RootDegrees.DOMINANT) {
            colorizedProgression = createFinalAuthenticCadence(colorizedProgression);
            //adds final tonic
            colorizedProgression.add(HarmonicFunctions.TONIC[0]);
            return materialize(colorizedProgression, tonalChords);
        }
        //turns last dominant from five to seventh chord
        colorizedProgression.set(colorizedProgression.size() - 1, ChordIndices.DOMINANT_SEVENTH);
        colorizedProgression.add(HarmonicFunctions.TONIC[0]);
        return materialize(colorizedProgression, tonalChords);
    }

    private static Tuple<List<Integer>, Integer> colorizeFourthDegree(
            int chord, List<Integer> colorizedProgression, int colorizationIndex,
            List<Integer> progression, int index, int appliedChord) {

        appliedChord = randomIntegerFromInterval(RandomIntegerIndices.MIN, RandomIntegerIndices.SECONDARY);
        //adds mediant between tonic and subdominant
        if (appliedChord == RandomIntegerIndices.PRIMARY
                && progression.get(index - 1) == RootDegrees.KEY_CENTER) {

            colorizedProgression.add(RootDegrees.MEDIANT);
            colorizationIndex++;
        }
        colorizedProgression.add(chord);
        return new Tuple<>(colorizedProgression, colorizationIndex);
    }

    private static Tuple<List<Integer>, Integer> colorizeFourthDegreeWithAppliedDominants(
            int chord, List<Integer> colorizedProgression, int colorizationIndex,
            List<Integer> progression, int index, int appliedChord) {

        appliedChord = randomIntegerFromInterval(RandomIntegerIndices.MIN, RandomIntegerIndices.MAX);
        //adds applied dominant 7th
        if (appliedChord == RandomIntegerIndices.PRIMARY) {
            colorizedProgression.add(ChordIndices.ALTERED_TONIC);
            colorizationIndex++;
        }
        //adds mediant between tonic and subdominant
        else if (appliedChord == RandomIntegerIndices.SECONDARY
                && progression.get(index - 1) == RootDegrees.KEY_CENTER) {

            colorizedProgression.add(RootDegrees.MEDIANT);
            colorizationIndex++;
        }
        colorizedProgression.add(chord);
        return new Tuple<>(colorizedProgression, colorizationIndex);
    }

    private static Tuple<List<Integer>, Integer> colorizeSixthDegree(
            int chord, List<Integer> colorizedProgression, int colorizationIndex,
            List<Integer> progression, int index, int shouldApplyDominants, int appliedChord, String mode) {

        appliedChord = randomIntegerFromInterval(RandomIntegerIndices.MIN, RandomIntegerIndices.MAX);
        //adds mediant chord
        if (appliedChord == RandomIntegerIndices.PRIMARY) {
            colorizedProgression.add(RootDegrees.MEDIANT);
            colorizationIndex++;
        }
        //adds ii-v transition after tonic
        else if (Objects.equals(mode, Modes.MINOR)
                && appliedChord == RandomIntegerIndices.SECONDARY
                && progression.get(index - 1) == RootDegrees.KEY_CENTER) {

            colorizedProgression.add(RootDegrees.SUBTONIC);
            colorizedProgression.add(RootDegrees.MEDIANT);
            colorizationIndex++;
        }
        colorizedProgression.add(chord);
        return new Tuple<>(colorizedProgression, colorizationIndex);
    }


    private static Tuple<List<Integer>, Integer> colorizeSixthDegreeWithAppliedDominants(
            int chord, List<Integer> colorizedProgression, int colorizationIndex,
            List<Integer> progression, int index, int appliedChord, String mode) {

        appliedChord = randomIntegerFromInterval(
                RandomIntegerIndices.MIN, RandomIntegerIndices.RANGE_UPPER_BOUND);
        //adds applied dominant 7th
        if (appliedChord == RandomIntegerIndices.PRIMARY) {
            colorizedProgression.add(ChordIndices.ALTERED_MEDIANT);
            colorizationIndex++;
        }
        //adds mediant chord
        else if (appliedChord == RandomIntegerIndices.SECONDARY) {
            colorizedProgression.add(RootDegrees.MEDIANT);
            colorizationIndex++;
        }
        //adds ii-v transition after tonic
        else if (appliedChord == RandomIntegerIndices.MAX
                && progression.get(index - 1) == RootDegrees.KEY_CENTER) {

            colorizedProgression.add(RootDegrees.SUBTONIC);
            if (Objects.equals(mode, Modes.MAJOR)) {
                colorizedProgression.add(ChordIndices.ALTERED_MEDIANT);
            } else {
                colorizedProgression.add(RootDegrees.MEDIANT);
            }
            colorizationIndex++;
        }

        colorizedProgression.add(chord);
        return new Tuple<>(colorizedProgression, colorizationIndex);
    }

    private static List<Integer> createFinalAuthenticCadence(List<Integer> colorizedProgression) {
        if (randomBit() == RandomBitStates.NEGATIVE
                && colorizedProgression.get(colorizedProgression.size() - 1) != RootDegrees.KEY_CENTER) {

            //adds double appoggiatura to dominant seventh chord
            colorizedProgression.add(ChordIndices.CADENTIAL_SIX_FOUR_CHORD);
            colorizedProgression.add(ChordIndices.DOMINANT_SEVENTH);
            return colorizedProgression;
        }
        //adds single leaning tone to dominant five chord
        colorizedProgression.add(ChordIndices.SUSPENDED_DOMINANT);
        colorizedProgression.add(HarmonicFunctions.DOMINANT[0]);
        return colorizedProgression;
    }
}
