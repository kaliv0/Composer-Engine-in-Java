package org.composer.common;

import java.util.List;

public class Chord {
    public String name;
    public List<String> content;

    public Chord(String name, List<String> content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
