package org.composer.constants;

import java.util.List;
import java.util.regex.Pattern;

public class KeyValidations {
    public final static Pattern VALID_KEY_NAME = Pattern.compile("^[A-G][#b]? ((minor)?|(major)?)$");
    public final static List<String> INVALID_KEYS = List.of(
            "Cb minor",
            "Db minor",
            "D# major",
            "E# major",
            "E# minor",
            "Fb major",
            "Fb minor",
            "Gb minor",
            "G# major",
            "A# major",
            "B# major",
            "B# minor"
    );
}
