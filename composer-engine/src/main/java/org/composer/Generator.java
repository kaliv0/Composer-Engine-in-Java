package org.composer;

import org.composer.common.Chord;
import org.composer.harmonyCreators.ProgressionGenerator;
import org.composer.inverters.ChordInverter;
import org.composer.keyCreators.ChordGenerator;
import org.composer.keyCreators.KeyGenerator;
import org.composer.mappers.ChordMapper;
import org.composer.midi.MidiCreator;

import java.util.List;
import java.util.Map;

/**
 * starts application
 */
public class Generator {
    public static void main(String[] args) {
//        Tuple<String, String> keyModeTuple = RandomKeySelector.selectKey();
//        final String key = keyModeTuple.getFirst();
//        final String mode = keyModeTuple.getSecond();
        String key = "C";
        String mode = "major";
        final String chosenKeyName = String.format("%s %s", key, mode);
//        final boolean shouldApplyDominants = BooleanTranslator.intToBoolean(Randomizer.randomBit());
        final boolean shouldApplyDominants = false;

        final List<String> scale = KeyGenerator.generateKey(chosenKeyName);
        final Map<Integer, String> chordsInKey = ChordGenerator.generateChords(scale, mode);
        final List<String> progression = ProgressionGenerator.generateProgression(chordsInKey, mode, shouldApplyDominants);
        final List<Chord> mapped_progression = ChordMapper.display(progression, scale, mode);


        for (var ch : mapped_progression) {
            System.out.print(ch.getContent() + " ");
        }

        final List<Chord> inverted_progression = ChordInverter.applyVoiceLeading(mapped_progression);
        System.out.println();

        for (var ch : inverted_progression) {
            System.out.print(ch.getContent() + " ");
        }
        System.out.println();

        MidiCreator.createMidi(inverted_progression, chosenKeyName);

//        var commonNote = "G";
//        var targetIndex = 0;
//        var chord = new ArrayList<>(List.of("C", "E", "G", "Bb"));
//        System.out.println("Old cord: " + chord);
//        var x = ChordInvertor.invertChord(commonNote, targetIndex, chord);
//        System.out.println("Move " + commonNote + " to index " + targetIndex + " and rotate");
//        System.out.println("Final result " + x);
    }
}
