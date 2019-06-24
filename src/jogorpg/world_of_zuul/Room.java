package jogorpg.world_of_zuul;
import java.util.*;
import characters.OnDie;
import item.Chest;
import item.Item;
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

public class Room implements OnDie {
    private String description;
    private HashMap<String, Room> exits;
    private HashMap<String, Character> characters;
    private HashMap<String, Item> items;
    private List<Chest> chests;

    public Room(String description, int chestsAmount, int enemiesAmount) {
        this.description = description;
        this.exits = new HashMap<>();
        this.characters = new HashMap<>();
        this.items = new HashMap<>();
        this.chests = new ArrayList<>();

        for (int i = 0; i < chestsAmount; i++) {
            chests.add(new Chest());
        }

        for (int i = 0; i < enemiesAmount; i++) {
            String name = Generator.get().generateName();
            String nick = name;
            if (name.contains(" ")) {
                nick = name.substring(0, name.indexOf(" "));
            }
            characters.put(nick, new Enemy(name, this));
        }

    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Character getEnemy(String nome) {
        return characters.get(nome);
    }

    public Chest getChest(int i) {
        if (i > chests.size() || i < 1) {
            System.out.println("Fala sério");
            return null;
        }
        else return chests.get(i - 1);
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

    @Override
    public void onDie(HashMap<String, Item> inventory) {
        System.out.println("-----Itens Droppados-----");
        this.items.putAll(inventory);
        inventory.forEach((s, item) -> {
            System.out.println(item.getName());
        });
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

        if (!chests.isEmpty()) {
            System.out.println("Você vê " + chests.size() + " baú(s)");
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

