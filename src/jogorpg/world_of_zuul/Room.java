package jogorpg.world_of_zuul;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashMap;

import item.Chest;
import personagens.Character;
import personagens.Enemy;
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
    private HashMap<String, Character> personagens;
    private List<Chest> chests;

    public Room(String description, int chestsAmount, int enemiesAmount) {
        this.description = description;
        this.exits = new HashMap<>();
        this.personagens = new HashMap<>();
        this.chests = new ArrayList<>();

        for (int i = 0; i < chestsAmount; i++) {
            chests.add(new Chest());
        }

        for (int i = 0; i < enemiesAmount; i++) {
            String name = Generator.generateName();
            personagens.put(name, new Enemy(name));
        }
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
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
    public String getLongDescription()
    {
        return "Você está " + description + ".\n" + getExitString() + ".\n" + getPersonagensString();
    }
    
    public Character getAdversario(String nome) {
        return personagens.get(nome);
    }
    
    public void removeAdversario(Character p) {
        personagens.remove(p);
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Caminhos:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    private String getPersonagensString()
    {
        String returnString = "Personagens:";
        Set<String> keys = personagens.keySet();
        for(String p : keys) {
            returnString += " " + p;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

}

