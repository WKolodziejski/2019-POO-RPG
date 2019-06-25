package utils;

public enum Level {
    AWFUL("terrivel"),
    BAD("pessimo"),
    GOOD("mediano"),
    GREAT("grande"),
    LEGENDARY("look");

    private String level;

    Level(String level) {
        this.level = level;
    }

    public String toString() {
        return level;
    }
}
