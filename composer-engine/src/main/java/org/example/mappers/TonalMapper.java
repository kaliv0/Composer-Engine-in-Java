package org.example.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//maps progression to chords in particular key
//could be modified to any major or minor tonality
public class TonalMapper {
    public static List<String> materialize(List<Integer> progression, Map<Integer, String> tonalChords) {
        ArrayList<String> materializedProgression = new ArrayList<>();
        for (int chordIndex : progression) {
            materializedProgression.add(tonalChords.get(chordIndex));
        }
        return materializedProgression;
    }
}
