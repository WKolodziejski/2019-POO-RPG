package jogorpg.world_of_zuul;

import java.util.*;
import item.furniture.Chest;
import item.furniture.Furniture;
import item.furniture.RepairTable;
import item.furniture.VendingMachine;
import item.model.Item;
import characters.Character;
import characters.Enemy;
import utils.Console;

public class Room {
    private String description;
    private String name;
    private HashMap<String, Room> exits;
    private HashMap<String, Character> characters;
    private HashMap<String, Furniture> furniture;
    private ArrayList<Item> items;
    private boolean firstAccess;

    public Room(String name, String description, int enemiesAmount, HashMap<String, Furniture> furniture) {
        this.name = name;
        this.description = description;
        this.firstAccess = true;
        this.furniture = furniture;
        this.exits = new HashMap<>();
        this.characters = new HashMap<>();
        this.items = new ArrayList<>();

        for (int i = 0; i < enemiesAmount; i++) {

            Enemy enemy = new Enemy(inventory -> {
                //System.out.println("------ITENS DROPADOS------");
                Console.print(Console.BLACK_BACKGROUND, "ITENS DROPADOS");
                for (Item item: inventory){
                    if (item != null) {
                        addItem(item);
                        System.out.println(items.size() - 1 + ": " + item.getName());
                    }
                }
            });

            characters.put(enemy.getKey(), enemy);
        }
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Character getEnemy(String nome) {
        return characters.get(nome);
    }

    public Chest getChest() {
        return (Chest) furniture.get("Chest");
    }

    public VendingMachine getMachine() {
        return (VendingMachine) furniture.get("Vending");
    }

    public RepairTable getRepair() {
        return (RepairTable) furniture.get("Repair");
    }

    public HashMap<String, Character> getCharacters() {
        return characters;
    }

    public Character removeEnemy(Character p) {
        return characters.remove(p.getKey());
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public void removeItem(Item item) {
        if (item != null)
            items.remove(item);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item findItem(int i) {
        if (i < 0 || i >= items.size()) return null;
        else return items.get(i);
    }

    public void describe() {
        if (firstAccess) {
            firstAccess = false;
            System.out.println(description);
        }

        System.out.println("Você está " + name);
        System.out.println("Você pode ir para " + exits.keySet());

        if (!characters.isEmpty()) {
            String e = "";
            for (Character c : characters.values()) {
                e = e.concat(c.getName()).concat(", ");
            }
            System.out.println("Há inimigos na sala: " + e.substring(0, e.length() - 2));
        }

        Chest chest = (Chest) furniture.get("Chest");

        if (chest != null) {
            System.out.println("Você vê um " + chest.getName());
        }

        VendingMachine machine = (VendingMachine) furniture.get("Vending");

        if (machine != null) {
            System.out.println("Você vê uma máquina de vendas");
        }

        if (!items.isEmpty()) {
            Console.print(Console.RED, "Há itens no chão:");
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i) != null) {
                    Console.print(Console.BLUE_BRIGHT, i + ": "+ items.get(i).getDetails());
                }
            }
        }
    }

}

