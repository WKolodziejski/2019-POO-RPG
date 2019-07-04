package item.furniture;

import item.model.Item;
import utils.Console;
import utils.Generator;
import utils.Item_Creator;

import java.util.Scanner;

public class Chest extends Furniture {

    private String name;
    private int password;
    private int chances;
    private int base;
    private boolean opened;

    public Chest(String name, boolean opened) {
        this.name = name;
        this.opened = opened;
        this.base = Generator.get().number(6);
        this.password = base + Generator.get().number(4);
        this.chances = 2;

        //Console.print(Console.RED, String.valueOf(password));

        for (int i = 0; i < Generator.get().number(6); i++) {
            Item item = Item_Creator.get().getRandom();
            items.add(item);
        }
    }

    public String getName() {
        if (name.contains(" ")) {
            return name.substring(name.indexOf(" ") + 1);
        } else {
            return name;
        }
    }

    public void open() {
        if (chances > 0) {
            if (!opened) {
                Console.print(Console.PURPLE_BOLD, "A senha é um número entre " + base + " e " + (base + 3));
                Scanner reader = new Scanner(System.in);
                int tryal = reader.nextInt();

                if (tryal == password) {
                    opened = true;
                    Console.print(Console.CYAN_BOLD, "Você abriu " + name);
                    printInventory();
                } else {
                    chances--;
                    Console.print(Console.PURPLE_BOLD, "Você não conseguiu abrir " + name);
                    open();
                }
            } else {
                printInventory();
            }
        } else {
            Console.print(Console.PURPLE_BOLD, "Você quebrou a fechadura e agora não é mais possível abrir " + name);
        }
    }

    public void printInventory() {
        Console.print(Console.BLACK_UNDERLINED, "------" + name.toUpperCase() + "------");

        if (!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                Console.print(Console.BLUE_BRIGHT, i + ": "+ items.get(i).getDetails());
            }

        } else {
            Console.print(Console.BLACK_BOLD, "Não há itens");
        }
    }

    public Item take(int i) {
        if (opened) {
            Item item = findItem(i);

            if (item != null) {
                items.remove(item);
                printInventory();
                return item;
            } else {
                Console.print(Console.RED, "Esse item não existe");
                return null;
            }
        } else {
            Console.print(Console.PURPLE_BOLD, name + " está trancado");
            return null;
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

}
