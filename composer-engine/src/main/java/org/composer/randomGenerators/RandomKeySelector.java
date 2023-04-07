package org.composer.randomGenerators;

import org.composer.common.Tuple;
import org.composer.constants.ChromaticSigns;
import org.composer.constants.KeyValidations;
import org.composer.constants.Modes;
import org.composer.constants.Pitches;
import org.composer.constants.random.RandomIndexIntervals;
import org.composer.constants.random.RandomIntegerIndices;

import static org.composer.randomGenerators.Randomizer.randomIntegerFromInterval;

/**
 * randomly selects key to be constructed
 */
public class RandomKeySelector {
    public static Tuple<String, String> selectKey() {
        //chooses random key
        final int randomIndex = randomIntegerFromInterval(RandomIndexIntervals.MIN, RandomIndexIntervals.MAX);
        StringBuilder keySb = new StringBuilder(Pitches.SCALE_PITCHES.get(randomIndex));

        final int randomInteger = randomIntegerFromInterval(RandomIntegerIndices.MIN, RandomIntegerIndices.MAX);
        if (randomInteger == RandomIntegerIndices.PRIMARY) {
            keySb.append(ChromaticSigns.SHARP);
        }
        if (randomInteger == RandomIntegerIndices.SECONDARY) {
            keySb.append(ChromaticSigns.FLAT);
        }

        String mode;
        if (Randomizer.randomBit() == RandomIntegerIndices.MIN) {
            mode = Modes.MAJOR;
        } else {
            mode = Modes.MINOR;
        }

        String key = keySb.toString();
        //checks for invalid key and chooses new one via recursion if necessary
        if (KeyValidations.INVALID_KEYS.contains(String.format("%s %s", key, mode))) {
            return selectKey();
        }
        return new Tuple<>(key, mode);
    }
}
