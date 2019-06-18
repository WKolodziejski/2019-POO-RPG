package utils;

import jogorpg.world_of_zuul.Room;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    public static void main(String[] args) {

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
                    //System.out.println(data);
                }

                /*if (data.equals("<names>")) {
                    data = scanner.nextLine();

                    while (!data.equals("</names>")) {
                        if (!data.isBlank()) {
                            names.add(data);
                            System.out.println(data);
                        }
                        data = scanner.nextLine();
                    }
                }*/
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return names;
    }

    public static List<Room> readRooms() {
        List<Room> rooms = new ArrayList<>();

        try {
            File file = new File("src/data/rooms");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                int enemies = 0;
                int chests = 0;

                if (data.equals("<room>")) {
                    data = scanner.nextLine();
                    String description = "";

                    while (!data.equals("</room>")) {

                        switch (data) {
                            case "<description>":
                                data = scanner.nextLine();

                                while (!data.equals("</description>")) {
                                    description = description.concat(scanner.nextLine());
                                    data = scanner.nextLine();
                                }

                                break;
                            case "<enemies>":
                                data = scanner.nextLine();

                                while (!data.equals("</enemies>")) {
                                    enemies = scanner.nextInt();
                                    data = scanner.nextLine();
                                }

                                break;
                            case "<chests>":
                                data = scanner.nextLine();

                                while (!data.equals("</chests>")) {
                                    chests = scanner.nextInt();
                                    data = scanner.nextLine();
                                }

                                break;
                        }

                        data = scanner.nextLine();
                    }
                    rooms.add(new Room(description, chests, enemies));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return rooms;
    }

}
