# Composer-Engine-in-Java

A console application written in *Java* that generates a chord progression in a given musical key.
The progression could be used for improvisation and educational purposes.

Composer.App supports the following functionalities:
- *_RandomKeySelector_* => chooses a random key to be constructed
- *_KeyGenerator_* => creates the main scale of the given key
- *_Chromatizer_* => appends the required chromatic signs for the chosen key signature
- *_ChordGenerator_* => generates all of the main chords in the tonality with their applied dominants
- *_ProgressionGenerator_* => creates a simple chord progression in the chosen key applying the traditional laws of tonal harmony
- *_Colorizer_* => inserts applied dominants, ii-v 'movements' and appends final cadence
- *_TonalMapper_* => maps the progression to specific chords in the particular key
- *_ChordMapper_* => maps the chord abbreviations to full representation of the chords
- *_DominantMapper_* => reads dominant seventh chords and modifies them where necessary
- *_NoteAlterator_* => adds accidentals for raising or lowering chord tones
- *_Randomizer_* => helper function for random selection of integers used throughout the application
- *_Generator_* => main entry point of the application