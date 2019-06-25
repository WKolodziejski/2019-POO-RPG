package utils;

import java.util.List;
import java.util.Random;

public class Generator {
    private List<String> names;
    private List<String> materials;
    private List<String> weapons;
    private static Generator generator;

    private Generator() {
        names = FileReader.readNames();
        materials = FileReader.readMaterials();
        weapons = FileReader.readWeapons();
    }

    public static synchronized Generator get() {
        if (generator == null) {
            generator = new Generator();
        }
        return generator;
    }

    public String name() {
        return names.get(new Random().nextInt(names.size()));
    }

    public String material() {
        return materials.get(new Random().nextInt(materials.size()));
    }

    public int getMaterialAmount() {
        return materials.size();
    }

    public String getMaterialByLevel(int level) {
        return materials.get(level);
    }

    public String weapon() {
        return weapons.get(new Random().nextInt(weapons.size()));
    }

}
