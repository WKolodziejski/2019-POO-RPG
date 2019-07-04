package jogorpg.world_of_zuul;

import java.util.*;

import characters.Boss;
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
    private HashMap<String, Enemy> characters;
    private HashMap<String, Furniture> furniture;
    private ArrayList<Item> items;
    private boolean firstAccess;

    public Room(String name, String description, int enemiesAmount, boolean hasBoss, HashMap<String, Furniture> furniture) {
        this.name = name;
        this.description = description;
        this.firstAccess = true;
        this.furniture = furniture;
        this.exits = new HashMap<>();
        this.characters = new HashMap<>();
        this.items = new ArrayList<>();

        if (hasBoss) {
            Boss boss = new Boss((inventory, character) -> {
                Console.print(Console.RED_BACKGROUND_BRIGHT, "Você matou o boss!");
            });
            characters.put(boss.getKey(), boss);
        }

        for (int i = 0; i < enemiesAmount; i++) {
            Enemy enemy = new Enemy((inventory, character) -> {
                if (!inventory.isEmpty()) {
                    Console.print(Console.BLACK_UNDERLINED, "------ITENS DROPADOS------");

                    for (Item item : inventory) {
                        if (item != null) {
                            items.add(item);
                            Console.print(Console.BLUE_BRIGHT, items.size() - 1 + ": " + item.getDetails());
                        }
                    }
                }
            });
            characters.put(enemy.getKey(), enemy);
        }
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Enemy getEnemy(String nome) {
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

    public HashMap<String, Enemy> getCharacters() {
        return characters;
    }

    public Enemy removeEnemy(Enemy p) {
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
        Console.print(Console.BLUE_BRIGHT, item.getName() + " caiu ao chão");
        Console.print(Console.BLUE_BRIGHT, items.size() - 1 + ": " + item.getDetails());
        items.add(item);
    }

    public Item findItem(int i) {
        if (i < 0 || i >= items.size()) return null;
        else return items.get(i);
    }

    public void describe() {
        if (firstAccess) {
            firstAccess = false;
            Console.print(Console.BLACK_BOLD, description);
        }

        Console.print(Console.BLACK_BRIGHT, "Você está " + name);
        Console.print(Console.BLACK_BRIGHT, "Você pode ir para " + exits.keySet());

        if (!characters.isEmpty()) {
            String e = "";
            for (Character c : characters.values()) {
                e = e.concat(c.getName()).concat(", ");
            }
            Console.print(Console.CYAN_BOLD, "Há inimigos na sala: " + e.substring(0, e.length() - 2));
        }

        Chest chest = (Chest) furniture.get("Chest");

        if (chest != null) {
            Console.print(Console.GREEN_BOLD, "Você vê um " + chest.getName());
        }

        VendingMachine machine = (VendingMachine) furniture.get("Vending");

        if (machine != null) {
            Console.print(Console.GREEN_BOLD, "Você vê uma máquina de vendas");
        }

        if (!items.isEmpty()) {
            Console.print(Console.BLACK_BOLD, "Há itens no chão:");
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i) != null) {
                    Console.print(Console.BLUE_BRIGHT, i + ": "+ items.get(i).getDetails());
                }
            }
        }
    }

}

