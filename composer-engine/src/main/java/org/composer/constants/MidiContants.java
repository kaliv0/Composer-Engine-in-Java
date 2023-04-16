package org.composer.constants;

import java.util.List;

public class MidiContants {
    public static final String MIDI_FILE_PATH = "../engine.midi";
    public static final int TEMPO = 200;
    public static final String NOTE_DELIMITER = "+";
    public static final String CHORD_DELIMITER = " ";
    public static final String BASE_NOTE_DELIMITER = "4W+";
    public static final String WHOLE_NOTE = "W";
    public static final String JFUGUE_DOUBLE_SHARP = "##";
    public static final List<String> KEYS_WITH_DOUBLE_SHARPS = List.of(
            "B major",
            "F# major",
            "C# major",
            "C# minor",
            "G# minor",
            "D# minor",
            "A# minor"
    );
}
