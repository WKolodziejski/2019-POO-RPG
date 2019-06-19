package utils;

import java.util.List;
import java.util.Random;

public class Generator {
    private List<String> names;
    private static Generator generator;

    private Generator() {
        this.names = FileReader.readNames();
    }

    public static synchronized Generator get() {
        if (generator == null) {
            generator = new Generator();
        }
        return generator;
    }

    public String generateName() {
        return names.get(new Random().nextInt(names.size()));
    }

}
