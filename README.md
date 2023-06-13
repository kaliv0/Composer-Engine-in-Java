# Composer-Engine-in-Java

A console application written in *Java* that generates a chord progression in a given musical key.
The progression could be used for improvisation and educational purposes.

========================================================================

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
- *_MidiCreator_* => creates MIDI file with the generated progression
- *_Generator_* => main entry point of the application


========================================================================

Example progression generated by the application:
<br><img src="https://github.com/Mithras11/Composer-Engine-in-Java/blob/main/example-progression.png"/>

which could be realized on the piano in the following manner:<br>
<img src="https://github.com/Mithras11/Composer-Engine-in-Java/blob/main/piano-version.png"/>

A short piano piece based on the progression can be found here:
<br>https://soundcloud.com/user-962833289/embers-m4a?si=fb17f612ce27482a8535aff6ff6629dd&utm_source=clipboard&utm_medium=text&utm_campaign=social_sharing
