package item;

import item.model.Item;
import utils.Generator;
import utils.Item_Creator;
import java.util.ArrayList;
import java.util.Scanner;

public class Chest {
    private ArrayList<Item> items;
    private int password;
    private int chances;
    private int base;
    private boolean opened;

    public Chest() {
        this.base = Generator.get().number(6);
        this.password = base + Generator.get().number(4);

        System.out.println(password);

        this.chances = 2;
        this.items = new ArrayList<>();

        for (int i = 0; i < Generator.get().number(6); i++) {
            Item item = Item_Creator.get().getRandom();
            items.add(item);
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

    public void printInventory() {
        if (!items.isEmpty()) {
            System.out.println("Itens:");

            for (int i = 0; i < items.size(); i++) {
                if (items.get(i) != null) {
                    System.out.println(i + ": "+ items.get(i).getName());
                }
            }

        } else {
            System.out.println("Não há itens");
        }
    }

    private Item findItem(int i) {
        if (i < 0 || i >= items.size()) return null;
        else return items.get(i);
    }

    public Item take(int i) {
        if (opened) {
            Item item = findItem(i);

            if (item != null) {
                items.remove(item);
                printInventory();
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
