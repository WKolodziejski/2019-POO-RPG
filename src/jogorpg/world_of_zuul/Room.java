package jogorpg.world_of_zuul;

import java.util.*;
import item.Chest;
import item.VendingMachine;
import item.model.Item;
import characters.Character;
import characters.Enemy;

public class Room {
    private String description;
    private String name;
    private HashMap<String, Room> exits;
    private HashMap<String, Character> characters;
    //private HashMap<String, Item> items;
    private ArrayList<Item> items;
    private Chest chest;
    private VendingMachine machine;
    private boolean firstAccess;

    public Room(String name, String description, int enemiesAmount, boolean hasChest, boolean hasVending) {
        this.name = name;
        this.description = description;
        this.firstAccess = true;
        this.exits = new HashMap<>();
        this.characters = new HashMap<>();
        this.items = new ArrayList<>();

        if (hasChest) {
            this.chest = new Chest();
        }

        if (hasVending) {
            this.machine = new VendingMachine();
        }

        for (int i = 0; i < enemiesAmount; i++) {

            Enemy enemy = new Enemy(inventory -> {
                System.out.println("-----Itens Droppados-----");
                for(Item item: inventory){
                    if (item != null) {
                        addItem(item);
                        System.out.println(item.getName());
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
        return chest;
    }

    public VendingMachine getMachine() {
        return machine;
    }

    public HashMap<String, Character> getCharacters() {
        return characters;
    }

    public Character removeEnemy(Character p) {
        return characters.remove(p.getName());
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public void removeItem(Item item) {
        if (item != null)
            items.remove(item);
    }

    public void addItem(Item item){
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

        if (chest != null) {
            System.out.println("Você vê um baú");
        }

        if (machine != null) {
            System.out.println("Você vê uma máquina de vendas");
        }

        if (!items.isEmpty()) {
            System.out.println("Há itens no chão:");
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i) != null) {
                    System.out.println(i + ": "+ items.get(i).getName());
                }
            }
        }
    }

}

