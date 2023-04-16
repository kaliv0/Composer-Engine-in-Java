package org.composer.harmonyCreators;

import org.composer.common.Tuple;
import org.composer.constants.Modes;
import org.composer.constants.chords.ChordIndices;
import org.composer.constants.chords.HarmonicFunctions;
import org.composer.constants.chords.RootDegrees;
import org.composer.constants.random.RandomBitStates;
import org.composer.constants.random.RandomIntegerIndices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.composer.constants.chords.Common.*;
import static org.composer.mappers.TonalMapper.materialize;
import static org.composer.randomGenerators.Randomizer.randomBit;
import static org.composer.randomGenerators.Randomizer.randomIntegerFromInterval;

/**
 * colorizes given progression by inserting applied dominants, ii-v 'movements'
 * and appends final cadence
 */
public class Colorizer {
    public static List<String> colorize(List<Integer> progression, final Map<Integer, String> tonalChords,
                                        final String mode, final boolean shouldApplyDominants) {
        int colorizationIndex = COLORIZATION_RANGE_MIN;
        List<Integer> colorizedProgression = new ArrayList<>();
        colorizedProgression.add(progression.get(0));

        //adds up to three applied dominants or mediants skipping first chord in given progression
        int chord;
        Tuple<List<Integer>, Integer> colorizedTuple;
        for (int index = 1; index < progression.size(); index++) {
            chord = progression.get(index);

            if (colorizationIndex == COLORIZATION_RANGE_MAX) {
                colorizedProgression.add(chord);
                continue;
            }

            //colorizes 4th degree
            if (chord == RootDegrees.SUBDOMINANT) {
                if (shouldApplyDominants) {
                    colorizedTuple = colorizeFourthDegreeWithAppliedDominants(
                            chord, colorizedProgression, colorizationIndex,
                            progression, index
                    );
                    colorizedProgression = colorizedTuple.getFirst();
                    colorizationIndex = colorizedTuple.getSecond();
                    continue;
                }

                colorizedTuple = colorizeFourthDegree(
                        chord, colorizedProgression, colorizationIndex,
                        progression, index
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
                if (shouldApplyDominants) {
                    colorizedTuple = colorizeSixthDegreeWithAppliedDominants(
                            chord, colorizedProgression, colorizationIndex,
                            progression, index, mode
                    );
                    colorizedProgression = colorizedTuple.getFirst();
                    colorizationIndex = colorizedTuple.getSecond();
                    continue;
                }

                colorizedTuple = colorizeSixthDegree(
                        chord, colorizedProgression, colorizationIndex, progression,
                        index, mode
                );
                colorizedProgression = colorizedTuple.getFirst();
                colorizationIndex = colorizedTuple.getSecond();
                continue;
            }

            //colorizes chords on other degrees
            if (shouldApplyDominants
                    && progression.get(index) != RootDegrees.KEY_CENTER
                    && randomBit() == RandomBitStates.POSITIVE) {

                colorizedProgression.add(chord * APPLIED_DOMINANT_COEFFICIENT);
                colorizationIndex++;
            }
            colorizedProgression.add(chord);
        }

        if (colorizedProgression.get(colorizedProgression.size() - 1) != RootDegrees.DOMINANT) {
            createFinalAuthenticCadence(colorizedProgression);
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
            final int chord, List<Integer> colorizedProgression, int colorizationIndex,
            final List<Integer> progression, final int index) {
        int appliedChord = randomIntegerFromInterval(RandomIntegerIndices.MIN, RandomIntegerIndices.SECONDARY);
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
            final int chord, List<Integer> colorizedProgression, int colorizationIndex,
            final List<Integer> progression, final int index) {
        int appliedChord = randomIntegerFromInterval(RandomIntegerIndices.MIN, RandomIntegerIndices.MAX);
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
            final int chord, List<Integer> colorizedProgression, int colorizationIndex,
            final List<Integer> progression, final int index, final String mode) {
        int appliedChord = randomIntegerFromInterval(RandomIntegerIndices.MIN, RandomIntegerIndices.MAX);
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
            final int chord, List<Integer> colorizedProgression, int colorizationIndex,
            final List<Integer> progression, final int index, final String mode) {
        int appliedChord = randomIntegerFromInterval(
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

    private static void createFinalAuthenticCadence(List<Integer> colorizedProgression) {
        if (randomBit() == RandomBitStates.NEGATIVE
                && colorizedProgression.get(colorizedProgression.size() - 1) != RootDegrees.KEY_CENTER) {
            //adds double appoggiatura to dominant seventh chord
            colorizedProgression.add(ChordIndices.CADENTIAL_SIX_FOUR_CHORD);
            colorizedProgression.add(ChordIndices.DOMINANT_SEVENTH);
            return;
        }
        //adds single leaning tone to dominant five chord
        colorizedProgression.add(ChordIndices.SUSPENDED_DOMINANT);
        colorizedProgression.add(HarmonicFunctions.DOMINANT[0]);
    }
}
