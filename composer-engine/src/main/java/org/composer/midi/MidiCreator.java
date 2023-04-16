package org.composer.midi;

import org.composer.common.Chord;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

        System.out.println(translated_progression);
    }

    private static String translateWithSharps(List<Chord> progression) {
//        return progression.stream()
//                .map(chord -> chord.getContent().get(0) + BASE_NOTE_DELIMITER + String.join(
//                                NOTE_DELIMITER, chord.getContent().stream()
//                                        .map(n -> {
//                                            if (n.endsWith(DOUBLE_SHARP)) {
//                                                return n.replace(DOUBLE_SHARP, JFUGUE_DOUBLE_SHARP) + WHOLE_NOTE;
//                                            }
//                                            return n + WHOLE_NOTE;
//                                        })
//                                        .toList()
//                        )
//                )
//                .collect(Collectors.joining(CHORD_DELIMITER));
        return null;
    }

    private static String translate(List<Chord> progression) {
//        return progression.stream()
//                .map(chord -> chord.getContent().get(0) + BASE_NOTE_DELIMITER + String.join(
//                                NOTE_DELIMITER, chord.getContent().stream()
//                                        .map(n -> n + WHOLE_NOTE)
//                                        .toList()
//                        )
//                )
//                .collect(Collectors.joining(CHORD_DELIMITER));

        List<String> finalResult = new ArrayList<>();
        for (int i = 0; i < progression.size(); i++) {
            var currChord = progression.get(i).getContent();
            List<String> translatedChord = new ArrayList<>();
            //progression.get(i).getName() + BASE_NOTE_DELIMITER+

            //keeps all roots in central octave -> TODO: find different solution -> similar to Stream.of(...).anyMatch(...)
            translatedChord.add(currChord.get(0) + "5" + WHOLE_NOTE);
            for (int j = 1; j < currChord.size(); j++) {
                var previousNote = translatedChord.get(j - 1);
                if (previousNote.contains("6")
                        || (Stream.of("G", "A", "B").anyMatch(previousNote::startsWith)
                        && Stream.of("C", "D", "E").anyMatch(currChord.get(j)::startsWith))) {
                    translatedChord.add(currChord.get(j) + "6" + WHOLE_NOTE);
                } else {
                    translatedChord.add(currChord.get(j) + "5" + WHOLE_NOTE);
                }
            }
            finalResult.add(String.join(NOTE_DELIMITER, translatedChord));
        }

        /*
         //re-adjust first chord if second one shares common tone and starts in a different register due to range limitations
        List<String> firstChord = progression.get(0).getContent();
        List<String> secondChord = progression.get(1).getContent();
        String firstSoprano = firstChord.get(firstChord.size() - 1);
        String secondSoprano = secondChord.get(secondChord.size() - 1);
        //check if both chords share same top note but in different registers
        if ((firstSoprano.substring(0, firstSoprano.length() - 1).equals(secondSoprano.substring(0, secondSoprano.length() - 1)))
                && (firstSoprano.charAt(firstSoprano.length() - 1) != secondSoprano.length() - 1)) {
            //bring first chord an octave higher
            progression.get(0).setContent(
                    firstChord.stream()
                            .map(note -> note.substring(note.length() - 1) + "6")
                            .toList()
            );
        }
         */

        return String.join(CHORD_DELIMITER, finalResult);
    }
}
