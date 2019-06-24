package jogorpg.world_of_zuul;

import characters.OnDie;
import item.Chest;
import item.Item;
import mechanics.Fight;
import characters.Hero;
import characters.Character;
import utils.FileReader;

import java.util.HashMap;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class Game implements OnDie {
    private Parser parser;
    private Room currentRoom;
    private Hero hero;
    private Chest lastChest;

    public Game() {
        currentRoom = FileReader.readRooms();
        parser = new Parser();
        hero = new Hero(this);
    }

    public void play() {
        printWelcome();
                
        boolean finished = false;

        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }

        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();
        lastChest = null;

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("Eoq?");
            return false;
        }
        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goRoom(command);
        }
        else if (commandWord == CommandWord.ATTACK) {
            attack(command);
        }
        else if (commandWord == CommandWord.OPEN) {
            openChest(command);
        }
        else if (commandWord == CommandWord.TAKE) {
           takeItem(command);
        }
        else if (commandWord == CommandWord.PICK) {
            pick(command);
        }
        else if (commandWord == CommandWord.DROP) {
            drop(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    private void openChest(Command command) {
        if (command.getSecondWord().isBlank()) {
            System.out.println("Abrir o que?");
        } else {
            int i = Integer.parseInt(command.getSecondWord());
            Chest chest = currentRoom.getChest(i);

            if (chest != null) {
                if (chest.open()) {
                    lastChest = chest;
                } else {
                    lastChest = null;
                }
            } else {
                System.out.println("Não tem esse baú aí não");
            }
        }
    }

    private void takeItem(Command command) {
        if (lastChest != null) {
            lastChest.take();
        } else {
            System.out.println("Pegar oq?");
        }
    }

    private void attack(Command command) {
        Character character = currentRoom.getAdversario(command.getSecondWord());
        if (character != null) {
            Fight.fight(hero, character);
            if (character.getEnergy() <= 0) {
                currentRoom.removeAdversario(character);
            }
        } else
            System.out.println("Atacar quem?");
    }

    private void pick(Command command){
        Item item = currentRoom.deleteItem(command.getSecondWord());

        if (item == null) {
            System.out.println("Pegar o que?");
        } else {
            if(!hero.putItem(item)){
                currentRoom.addItem(item);
            }
        }
    }

    private void drop(Command command){
        Item item = hero.removeItem(command.getSecondWord());

        if (item == null) {
            System.out.println("Dropar o que?");
        } else {
            currentRoom.addItem(item);
        }
    }

    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goRoom(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private boolean quit(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    @Override
    public void onDie(HashMap<String, Item> inventory) {
        System.out.println("Você morreu!");
    }

}
