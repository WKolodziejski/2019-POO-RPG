package utils;

import jogorpg.world_of_zuul.Room;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    public static void main(String[] args) {
        Room room = readRooms();
        System.out.println(room.getLongDescription());
    }

    public static List<String> readNames() {
        List<String> names = new ArrayList<>();

        try {
            File file = new File("src/data/names");
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

    public static Room readRooms() {
        List<Room> rooms = new ArrayList<>();

        try {
            File file = new File("src/data/rooms");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                int enemies = 0;
                int chests = 0;
                HashMap<Integer, String> exits = new HashMap<>();

                if (data.equals("<room>")) {
                    data = scanner.nextLine();
                    String description = "";

                    while (!data.equals("</room>")) {

                        switch (data) {
                            case "<description>":
                                data = scanner.nextLine();

                                while (!data.equals("</description>")) {
                                    description = data;
                                    data = scanner.nextLine();
                                }

                                break;
                            case "<enemies>":
                                data = scanner.nextLine();

                                while (!data.equals("</enemies>")) {
                                    enemies = Integer.parseInt(data);
                                    data = scanner.nextLine();
                                }

                                break;
                            case "<chests>":
                                data = scanner.nextLine();

                                while (!data.equals("</chests>")) {
                                    chests = Integer.parseInt(data);
                                    data = scanner.nextLine();
                                }

                                break;

                            case "<exits>":
                                data = scanner.nextLine();

                                while (!data.equals("</exits>")) {
                                    exits.put(Integer.parseInt(data), scanner.nextLine()) ;
                                    data = scanner.nextLine();
                                }

                                break;
                        }

                        data = scanner.nextLine();
                    }
                    rooms.add(new Room(description, chests, enemies, exits));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Room room : rooms) {
            for (Integer i : room.getExitsID().keySet()) {
                room.setExit(room.getExitsID().get(i), rooms.get(i));
            }
        }

        return rooms.get(0);
    }

}
