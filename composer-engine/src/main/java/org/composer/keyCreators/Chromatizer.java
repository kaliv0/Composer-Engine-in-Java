package org.composer.keyCreators;

import org.composer.constants.ChromaticSigns;
import org.composer.constants.ErrorMessages;
import org.composer.constants.Modes;
import org.composer.constants.Pitches;

import java.util.List;

import static org.composer.constants.ChromaticSigns.SERIES;
import static org.composer.constants.ChromaticSigns.SIGN_INCREMENTAL_STEP;
import static org.composer.constants.Modes.F_KEY;

/**
 * appends required chromatic signs for a given key signature
 */
public class Chromatizer {
    public static List<String> addChromaticSigns(List<String> scale, final String tonic, final String mode) {
        int signCount = Pitches.MAJOR_CIRCLE_OF_FIFTHS.get(tonic);
        final boolean isWithFlats = (tonic.equals(F_KEY)
                || (tonic.length() > 1 && String.valueOf(tonic.charAt(1)).equals(ChromaticSigns.FLAT)));
        if (mode.equals(Modes.MINOR)) {
            if (isWithFlats) {
                signCount += SIGN_INCREMENTAL_STEP;
            } else {
                signCount -= SIGN_INCREMENTAL_STEP;
            }
        }
        if (signCount > ChromaticSigns.SIGN_COUNT_MAX) {
            throw new Error(ErrorMessages.INVALID_KEY_ERROR);
        }
        if (signCount < ChromaticSigns.SIGN_COUNT_MIN) {
            signCount = Math.abs(signCount);
            return addFlats(scale, signCount);
        }
        if (isWithFlats) {
            return addFlats(scale, signCount);
        }
        return addSharps(scale, signCount);
    }

    private static List<String> addFlats(List<String> scale, final int signCount) {
        String currNote;
        int degreeIndex;
        for (int i = signCount; i > ChromaticSigns.SIGN_COUNT_MIN; i--) {
            currNote = SERIES[SERIES.length - i];
            degreeIndex = scale.indexOf(currNote);
            scale.set(degreeIndex, scale.get(degreeIndex) + ChromaticSigns.FLAT);
        }
        return scale;
    }

    private static List<String> addSharps(List<String> scale, final int signCount) {
        int degreeIndex;
        for (int i = ChromaticSigns.SIGN_COUNT_MIN; i < signCount; i++) {
            degreeIndex = scale.indexOf(SERIES[i]);
            scale.set(degreeIndex, scale.get(degreeIndex) + ChromaticSigns.SHARP);
        }
        return scale;
    }
}
