package utils;

import item.furniture.Chest;
import item.furniture.Furniture;
import item.furniture.RepairTable;
import item.furniture.VendingMachine;
import item.model.Item;
import jogorpg.world_of_zuul.Room;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    public static List<String> readNames() {
        List<String> names = new ArrayList<>();

        try {
            File file = new File("src/data/enemies/common");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                if (!data.isBlank()) {
                    names.add(data);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return names;
    }

    public static List<String> readMaterials() {
        List<String> materials = new ArrayList<>();

        try {
            File file = new File("src/data/items/material");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                if (!data.isBlank()) {
                    materials.add(data);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return materials;
    }

    public static List<String> readWeapons() {
        List<String> weapons = new ArrayList<>();

        try {
            File file = new File("src/data/items/weapons");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                if (!data.isBlank()) {
                    weapons.add(data);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return weapons;
    }


    public static Room readRooms() {
        List<Room> rooms = new ArrayList<>();
        List<HashMap<Integer, String>> exits = new ArrayList<>();

        try {
            File file = new File("src/data/rooms");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String data = scanner.nextLine().trim();

                if (data.equals("<room>")) {
                    data = scanner.nextLine().trim();

                    StringBuilder name = new StringBuilder();
                    HashMap<Integer, String> exit = new HashMap<>();
                    HashMap<String, Furniture> furniture = new HashMap<>();
                    String description = "";
                    int enemies = 0;

                    while (!data.equals("</room>")) {

                        switch (data) {
                            case "<name>":
                                data = scanner.nextLine().trim();

                                while (!data.equals("</name>")) {
                                    name.append(data);
                                    data = scanner.nextLine().trim();
                                }

                                break;

                            case "<description>":
                                data = scanner.nextLine().trim();

                                while (!data.equals("</description>")) {
                                    description = data;
                                    data = scanner.nextLine().trim();
                                }

                                break;

                            case "<enemies>":
                                data = scanner.nextLine().trim();

                                while (!data.equals("</enemies>")) {
                                    enemies = Integer.parseInt(data);
                                    data = scanner.nextLine().trim();
                                }

                                break;

                            case "<chest>":
                                data = scanner.nextLine().trim();
                                Item item = null;

                                while (!data.equals("</chest>")) {
                                    Chest chest = new Chest(data, Boolean.parseBoolean(scanner.nextLine().trim()));
                                    data = scanner.nextLine().trim();

                                    if (data.equals("<item>")) {
                                        data = scanner.nextLine().trim();

                                        while (!data.equals("</item>")) {
                                            item = Item_Creator.get().getSpec(Item_Category.get(Integer.parseInt(data)), Integer.parseInt(scanner.nextLine().trim()));
                                            data = scanner.nextLine().trim();
                                        }

                                        chest.addItem(item);
                                        data = scanner.nextLine().trim();
                                    }

                                    furniture.put("Chest", chest);

                                }

                                break;

                            case "<vending>":
                                furniture.put("Vending", new VendingMachine());

                                break;

                            case "<repair>":
                                furniture.put("Repair", new RepairTable());

                                break;

                            case "<exits>":
                                data = scanner.nextLine().trim();

                                while (!data.equals("</exits>")) {
                                    exit.put(Integer.parseInt(data), scanner.nextLine().trim()) ;
                                    data = scanner.nextLine().trim();
                                }

                                break;
                        }

                        data = scanner.nextLine().trim();
                    }
                    rooms.add(new Room(name.toString(), description, enemies, furniture));
                    exits.add(exit);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < rooms.size(); i++) {
            for (Integer j : exits.get(i).keySet()) {
                rooms.get(i).setExit(exits.get(i).get(j), rooms.get(j));
            }
        }

        return rooms.get(0);
    }

}
