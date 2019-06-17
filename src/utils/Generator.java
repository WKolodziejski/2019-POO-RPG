package utils;

import java.util.List;
import java.util.Random;

public class Generator {

    public static String generateName() {
        List<String> names = FileReader.readNames();
        return names.get(new Random().nextInt(names.size()));
    }

}
