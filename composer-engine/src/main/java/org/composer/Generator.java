package org.composer;

import org.composer.common.Chord;
import org.composer.common.Tuple;
import org.composer.harmonyCreators.ProgressionGenerator;
import org.composer.keyCreators.ChordGenerator;
import org.composer.keyCreators.KeyGenerator;
import org.composer.mappers.ChordMapper;
import org.composer.midi.MidiCreator;
import org.composer.randomGenerators.RandomKeySelector;
import org.composer.randomGenerators.Randomizer;
import org.composer.utils.BooleanTranslator;

import java.util.List;
import java.util.Map;

/**
 * starts application
 */
public class Generator {
    public static void main(String[] args) {
        Tuple<String, String> keyModeTuple = RandomKeySelector.selectKey();
        final String key = keyModeTuple.getFirst();
        final String mode = keyModeTuple.getSecond();
        final String chosenKeyName = String.format("%s %s", key, mode);
        final boolean shouldApplyDominants = BooleanTranslator.intToBoolean(Randomizer.randomBit());

        final List<String> scale = KeyGenerator.generateKey(chosenKeyName);
        final Map<Integer, String> chordsInKey = ChordGenerator.generateChords(scale, mode);
        final List<String> progression = ProgressionGenerator.generateProgression(chordsInKey, mode, shouldApplyDominants);
        final List<Chord> result = ChordMapper.display(progression, scale, mode);

        MidiCreator.createMidi(result, chosenKeyName);
    }
}