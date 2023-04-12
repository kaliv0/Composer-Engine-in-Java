package org.composer.midi;

import org.composer.common.Chord;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.composer.constants.MidiContants.*;

/**
 * creates midi file - integrated with JFugue
 */
public class MidiCreator {
    public static void createMidi(List<Chord> result) {
        final String translated_progression = translate(result);

        final Player player = new Player();
        final Pattern pattern = new Pattern(translated_progression).getPattern().setTempo(TEMPO);
        player.play(pattern);

        try {
            final File filePath = new File(MIDI_FILE_PATH);
            MidiFileManager.savePatternToMidi(pattern, filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String translate(List<Chord> progression) {
        return progression.stream()
                .map(ch -> String.join(NOTE_DELIMITER, ch.content()))
                .collect(Collectors.joining(CHORD_DELIMITER));
    }
}
