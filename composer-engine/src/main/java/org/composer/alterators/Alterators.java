package org.composer.alterators;

import org.composer.constants.ChromaticSigns;

/**
 * adds chromatic signs to notes
 */
public class Alterators {
    public static String raiseNote(String note) {
        if (note.endsWith(ChromaticSigns.SHARP)) {
            return note.charAt(0) + ChromaticSigns.DOUBLE_SHARP;
        } else if (note.contains(ChromaticSigns.FLAT)) {
            return String.valueOf(note.charAt(0));
        }
        return note + ChromaticSigns.SHARP;
    }

    public static String lowerNote(String note) {
        if (note.endsWith(ChromaticSigns.SHARP)) {
            return String.valueOf(note.charAt(0));
        }
        return note + ChromaticSigns.FLAT;
    }
}
