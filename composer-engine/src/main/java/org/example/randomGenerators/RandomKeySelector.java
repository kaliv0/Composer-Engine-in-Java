package org.example.randomGenerators;

import org.example.common.Tuple;
import org.example.constants.ChromaticSigns;
import org.example.constants.KeyValidations;
import org.example.constants.Modes;
import org.example.constants.Pitches;
import org.example.constants.random.RandomIndexIntervals;
import org.example.constants.random.RandomIntegerIndices;

import java.util.Arrays;

import static org.example.randomGenerators.Randomizer.randomIntegerFromInterval;

/*
randomly selects key to be constructed
 */
public class RandomKeySelector {
    public static Tuple<String, String> selectKey() {
        //chooses random key
        final int randomIndex = randomIntegerFromInterval(RandomIndexIntervals.MIN, RandomIndexIntervals.MAX);
        String key = Pitches.SCALE_PITCHES[randomIndex];

        final int randomInteger = randomIntegerFromInterval(RandomIntegerIndices.MIN, RandomIntegerIndices.MAX);
        if (randomInteger == RandomIntegerIndices.PRIMARY) {
            key += ChromaticSigns.SHARP; //TODO: refactor string concat with sb
        }
        if (randomInteger == RandomIntegerIndices.SECONDARY) {
            key += ChromaticSigns.FLAT;
        }

        String mode;
        if (Randomizer.randomBit() == RandomIntegerIndices.MIN) {
            mode = Modes.MAJOR;
        } else {
            mode = Modes.MINOR;
        }

        //checks for invalid key and chooses new one via recursion if necessary
        if (Arrays.asList(KeyValidations.INVALID_KEYS).contains(String.format("%s %s", key, mode))) {
            return selectKey();
        }
        return new Tuple<>(key, mode);
    }
}
