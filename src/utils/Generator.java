package utils;

import java.util.Random;

public class Generator {
    private static String[] names = {"Telasko", "Avara"};

    public static String generateName() {
        return names[new Random().nextInt(names.length)];
    }

}
