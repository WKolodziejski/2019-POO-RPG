package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    public static void main(String[] args) {
        //writeFile();
        readNames();
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

}
