package item;

import item.model.Item;
import utils.Generator;
import utils.Item_Creator;
import java.util.ArrayList;
import java.util.Scanner;

public class Chest {
    private ArrayList<Item> items;
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

        System.out.println(password);

        this.chances = 2;
        this.items = new ArrayList<>();

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
                System.out.println("A senha é um número entre " + base + " e " + (base + 3));
                Scanner reader = new Scanner(System.in);
                int tryal = reader.nextInt();

                if (tryal == password) {
                    opened = true;
                    System.out.println("Você abriu " + name);
                    printInventory();
                } else {
                    chances--;
                    System.out.println("Você não conseguiu abrir " + name);
                    open();
                }
            } else {
                printInventory();
            }
        } else {
            System.out.println("Você quebrou a fechadura e agora não é mais possível abrir " + name);
        }
    }

    public void printInventory() {
        if (!items.isEmpty()) {
            System.out.println("Itens:");

            for (int i = 0; i < items.size(); i++) {
                System.out.println(i + ": "+ items.get(i).getDetails());
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
            System.out.println(name + " está trancado");
            return null;
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

}
