/*
//starts application
const keySelector = require("./random-generators/random-key-selector");
const colorizeSelector = require("./random-generators/randomizer");
const keyGenerator = require("./key-creator/key-generator");
const chordGenerator = require("./key-creator/chord-generator");
const progressionGenerator = require("./harmony-creator/progression-generator");
const chordMapper = require("./mappers/chord-mapper");
 */


package org.example;

import org.example.common.Chord;
import org.example.common.Tuple;
import org.example.harmonyCreators.ProgressionGenerator;
import org.example.keyCreators.ChordGenerator;
import org.example.keyCreators.KeyGenerator;
import org.example.mappers.ChordMapper;
import org.example.randomGenerators.RandomKeySelector;
import org.example.randomGenerators.Randomizer;

import java.util.List;
import java.util.Map;

public class Generator {
    public static void main(String[] args) {
        Tuple<String, String> keyModeTuple = RandomKeySelector.selectKey();
        final String key = keyModeTuple.getFirst();
        final String mode = keyModeTuple.getSecond();

        final int shouldApplyDominants = Randomizer.randomBit(); //TODO => change to boolean and re-adjust all if statements

        final List<String> scale = KeyGenerator.generateKey(String.format("%s %s", key, mode));
        final Map<Integer, String> chordsInKey = ChordGenerator.generateChords(scale, mode);
        final List<String> progression = ProgressionGenerator.generateProgression(chordsInKey, mode, shouldApplyDominants);
        final List<Chord> result = ChordMapper.display(progression, scale, mode);
        //
        //        console.log(progression);
        //        result.forEach(chord = > {
        //                console.log(JSON.stringify(chord));
        //});

        System.out.println(key + " " + mode);
        System.out.println(shouldApplyDominants);
        System.out.println(scale);
        System.out.println(chordsInKey);
        System.out.println(progression);
    }
}