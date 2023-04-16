package org.composer.midi;

import org.composer.common.Chord;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.composer.constants.ChromaticSigns.DOUBLE_SHARP;
import static org.composer.constants.MidiConstants.*;

/**
 * creates midi file - integrated with JFugue
 */
public class MidiCreator {
    public static void createMidi(List<Chord> result, final String chosenKeyName) {
        final String translated_progression;
        if (KEYS_WITH_DOUBLE_SHARPS.contains(chosenKeyName)) {
            translated_progression = translateWithSharps(result);
        } else {
            translated_progression = translate(result);
        }

        final Pattern pattern = new Pattern(translated_progression).getPattern().setTempo(TEMPO);
        try {
            final File filePath = new File(MIDI_FILE_PATH);
            MidiFileManager.savePatternToMidi(pattern, filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String translateWithSharps(List<Chord> progression) {
        return progression.stream()
                .map(chord -> chord.content().get(0) + BASE_NOTE_DELIMITER + String.join(
                                NOTE_DELIMITER, chord.content().stream()
                                        .map(n -> {
                                            if (n.endsWith(DOUBLE_SHARP)) {
                                                return n.replace(DOUBLE_SHARP, JFUGUE_DOUBLE_SHARP) + WHOLE_NOTE;
                                            }
                                            return n + WHOLE_NOTE;
                                        })
                                        .toList()
                        )
                )
                .collect(Collectors.joining(CHORD_DELIMITER));
    }

    private static String translate(List<Chord> progression) {
        return progression.stream()
                .map(chord -> chord.content().get(0) + BASE_NOTE_DELIMITER + String.join(
                                NOTE_DELIMITER, chord.content().stream()
                                        .map(n -> n + WHOLE_NOTE)
                                        .toList()
                        )
                )
                .collect(Collectors.joining(CHORD_DELIMITER));
    }
}
