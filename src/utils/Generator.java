package utils;

import java.util.List;
import java.util.Random;

public class Generator {
    private List<String> names;
    private List<String> materials;
    private List<String> weapons;
    private Random random;
    private static Generator generator;

    private Generator() {
        random = new Random();
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
        return names.get(number(names.size()));
    }

    public String material() {
        return materials.get(number(materials.size()));
    }

    public int getMaterialAmount() {
        return materials.size()-1;
    }

    public String getMaterialByLevel(int level) {
        return materials.get(level);
    }

    public int getLevelByMaterial(String material) {
        for (int i = 0; i < materials.size(); i++) {
            if (materials.get(i).equals(material)) {
                return i;
            }
        }
        Console.print(Console.BLACK, "Material não encontrado");
        return -1;
    }

    public String weapon() {
        return weapons.get(number(weapons.size()));
    }

    public int number(int bound) {
        return random.nextInt(bound);
    }

}
