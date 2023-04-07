package org.composer.keyCreators;

import org.composer.constants.Modes;
import org.composer.constants.scales.ScaleCounter;

import java.util.ArrayList;
import java.util.List;

import static org.composer.constants.ErrorMessages.INVALID_KEY_ERROR;
import static org.composer.constants.KeyValidations.VALID_KEY_NAME;
import static org.composer.constants.Pitches.SCALE_PITCHES;
import static org.composer.keyCreators.Chromatizer.addChromaticSigns;

/**
 * creates main scale of given key
 */
public class KeyGenerator {
    public static List<String> generateKey(final String tonality) {
        if (!VALID_KEY_NAME.matcher(tonality).matches()) {
            throw new IllegalArgumentException(INVALID_KEY_ERROR);
        }

        final String[] tonicModeArr = tonality.split(Modes.KEY_SEPARATOR);
        final String tonic = tonicModeArr[0];
        final String mode = tonicModeArr[1];
        int pitchIndex = SCALE_PITCHES.indexOf(String.valueOf(tonic.charAt(0)));

        //populates scale degrees
        List<String> newKey = new ArrayList<>();
        for (int i = ScaleCounter.START_INDEX; i < ScaleCounter.UPPER_BOUND; i++) {
            newKey.add(SCALE_PITCHES.get(pitchIndex));

            if (pitchIndex < SCALE_PITCHES.size() - 1) {
                pitchIndex++;
            } else {
                pitchIndex = ScaleCounter.START_INDEX;
            }
        }
        return addChromaticSigns(newKey, tonic, mode);
    }
}
