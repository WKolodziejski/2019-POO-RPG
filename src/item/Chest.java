package item;

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
        this.base = new Random().nextInt(6);
        this.password = base + new Random().nextInt(4);

        System.out.println(password);

        this.chances = 2;
        this.inventory = new HashMap<>();
    }

    public boolean open() {
        if (chances > 0) {
            if (!opened) {
                System.out.println("A senha é um número entre " + base + " e " + (base + 3));
                Scanner reader = new Scanner(System.in);
                int tryal = reader.nextInt();

                if (tryal == password) {
                    opened = true;
                    System.out.println("Você abriu o baú");
                    printInventory();
                    return true;
                } else {
                    chances--;
                    System.out.println("Você não conseguiu open o baú");
                    open();
                    return false;
                }
            } else {
                printInventory();
                return true;
            }
        } else {
            System.out.println("Você quebrou a fechadura e agora não é mais possível open o baú");
            return false;
        }
    }

    private void printInventory() {
        StringBuilder returnString = new StringBuilder("Itens: ");
        Collection<Item> keys = inventory.values();
        for (Item i : keys) {
            returnString.append(i.getName()).append(", ");
        }
        System.out.println(returnString.toString().substring(0, returnString.length() - 2));

    }

    public void take(/*Item*/) {
        if (opened) {

        } else {
            System.out.println("O baú está trancado");
        }
    }

}
