package org.example.keyCreators;

import org.example.constants.Modes;
import org.example.constants.scales.ScaleCounter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.constants.ErrorMessages.INVALID_KEY_ERROR;
import static org.example.constants.KeyValidations.VALID_KEY_NAME;
import static org.example.constants.Pitches.SCALE_PITCHES;
import static org.example.keyCreators.Chromatizer.addChromaticSigns;

//creates main scale of given key
public class KeyGenerator {
    public static List<String> generateKey(String tonality) {
        if (!VALID_KEY_NAME.matcher(tonality).matches()) {
            throw new IllegalArgumentException(INVALID_KEY_ERROR);
//            return; //TODO: validate and refactor
        }

        String[] tonicModeArr = tonality.split(Modes.KEY_SEPARATOR);
        String tonic = tonicModeArr[0];
        String mode = tonicModeArr[1];
        int pitchIndex = Arrays.asList(SCALE_PITCHES).indexOf(String.valueOf(tonic.charAt(0)));

        //populates scale degrees
        List<String> newKey = new ArrayList<>();
        for (int i = ScaleCounter.START_INDEX; i < ScaleCounter.UPPER_BOUND; i++) {
            newKey.add(SCALE_PITCHES[pitchIndex]);

            if (pitchIndex < SCALE_PITCHES.length - 1) {
                pitchIndex++;
            } else {
                pitchIndex = ScaleCounter.START_INDEX;
            }
        }
        //error handling needed if user selects key manually //TODO:implement
//        try {

        return addChromaticSigns(newKey, tonic, mode);

//        } catch (Exception) {
//            console.error(err.message);
//        }
    }
}
