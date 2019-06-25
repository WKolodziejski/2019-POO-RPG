package jogorpg.world_of_zuul;
import java.util.*;

import item.Chest;
import item.model.Item;
import characters.Character;
import characters.Enemy;
import utils.Generator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  For each existing exit, the room
 * stores a reference to the neighboring room.
 *
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class Room {
    private String description;
    private HashMap<String, Room> exits;
    private HashMap<String, Character> characters;
    private HashMap<String, Item> items;
    private Chest chest;

    public Room(String description, int enemiesAmount, boolean hasChest) {
        this.description = description;
        this.exits = new HashMap<>();
        this.characters = new HashMap<>();
        this.items = new HashMap<>();

        if (hasChest) {
            this.chest = new Chest();
        }

        for (int i = 0; i < enemiesAmount; i++) {
            String name = Generator.get().name();
            String nick = name;
            if (name.contains(" ")) {
                nick = name.substring(0, name.indexOf(" "));
            }
            characters.put(nick, new Enemy(name, inventory -> {
                System.out.println("-----Itens Droppados-----");
                this.items.putAll(inventory);

                inventory.forEach((s, item) -> {
                    System.out.println(item.getName());
                });
            }));
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

    public HashMap<String, Character> getCharacters() {
        return characters;
    }

    public Character removeEnemy(Character p) {
        return characters.remove(p.getName());
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public Item deleteItem(String item){
        return this.items.remove(item);
    }

    public void addItem(Item item){
        items.put(item.getName(), item);
    }

    public void describe() {
        System.out.println("Você está " + description + ".");
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

        if (!items.isEmpty()) {
            String s = "";
            for (Item i : items.values()) {
                s = s.concat(i.getName()).concat(", ");
            }
            System.out.println("Há itens no chão: " + s.substring(0, s.length() - 2));
        }
    }

}

