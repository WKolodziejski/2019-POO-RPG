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
    private HashMap<String, Character> personagens;
    private HashMap<String, Item> items;
    private List<Chest> chests;

    public Room(String description, int chestsAmount, int enemiesAmount) {
        this.description = description;
        this.exits = new HashMap<>();
        this.personagens = new HashMap<>();
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
            personagens.put(nick, new Enemy(name, this));
        }

    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription() {
        return "Você está " + description + ".\n" + getExitString() + ".\n" + getPersonagensString();
    }

    public Character getAdversario(String nome) {
        return personagens.get(nome);
    }

    public Chest getChest(int i) {
        if (i > chests.size() || i < 1) {
            System.out.println("Fala sério");
            return null;
        }
        else return chests.get(i - 1);
    }

    public Character removeAdversario(Character p) {
        return personagens.remove(p.getName());
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString() {
        String returnString = "Caminhos:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    private String getPersonagensString() {
        StringBuilder returnString = new StringBuilder("Personagens: ");
        Collection<Character> keys = personagens.values();
        for (Character p : keys) {
            returnString.append(p.getName()).append(", ");
        }
        return returnString.toString().substring(0, returnString.length() - 2);
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public Item deleteItem(String item){
        return this.items.remove(item);
    }

    public void addItem(Item item){
        items.put(item.getName(), item);
    }

    public void dropAllItems(Character character){
        items.putAll(character.getInventory());
    }

    @Override
    public void onDie(HashMap<String, Item> inventory) {
        System.out.println("-----Itens Droppados-----");
        this.items.putAll(inventory);
        inventory.forEach((s, item) -> {
            System.out.println(item.getName());
        });
    }

}

