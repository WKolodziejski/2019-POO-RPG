package item;

import item.model.Item;
import utils.Generator;
import utils.Item_Creator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Chest {
    private HashMap<String, Item> inventory;
    private int password;
    private int chances;
    private int base;
    private boolean opened;

    public Chest() {
        this.base = Generator.get().number(6);
        this.password = base + Generator.get().number(4);

        System.out.println(password);

        this.chances = 2;
        this.inventory = new HashMap<>();

        for (int i = 0; i < Generator.get().number(6); i++) {
            Item item = Item_Creator.get().getRandom();
            inventory.put(item.getKey(), item);
        }
    }

    public void open() {
        if (chances > 0) {
            if (!opened) {
                System.out.println("A senha é um número entre " + base + " e " + (base + 4));
                Scanner reader = new Scanner(System.in);
                int tryal = reader.nextInt();

                if (tryal == password) {
                    opened = true;
                    System.out.println("Você abriu o baú");
                    printInventory();
                } else {
                    chances--;
                    System.out.println("Você não conseguiu open o baú");
                    open();
                }
            } else {
                printInventory();
            }
        } else {
            System.out.println("Você quebrou a fechadura e agora não é mais possível open o baú");
        }
    }

    private void printInventory() {
        if (!inventory.isEmpty()) {
            System.out.println("Itens:");

            inventory.forEach((s, item) -> {
                System.out.println(item.getName());
            });

        } else {
            System.out.println("Não há itens");
        }
    }

    public Item take(String name) {
        if (opened) {
            Item item = inventory.get(name);

            if (item != null) {
                inventory.remove(name);
                return item;
            } else {
                System.out.println("Esse item não existe");
                return null;
            }
        } else {
            System.out.println("O baú está trancado");
            return null;
        }
    }

}
