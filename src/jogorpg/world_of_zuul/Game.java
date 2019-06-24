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
        boolean finished = false;

        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }

        System.out.println("Thank you for playing.  Good bye.");
    }

    private boolean processCommand(Command command) {
        CommandWord commandWord = command.getCommandWord();
        lastChest = null;

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("Eoq?");
        }
        else if (commandWord == CommandWord.HELP) {
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
        else if (commandWord == CommandWord.LOOK) {
            look(command);
        }
        else if (commandWord == CommandWord.INVENTORY) {
            inventory(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            return quit(command);
        }

        return false;
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

    private void inventory(Command command) {
        hero.getInventory().forEach((s, item) -> System.out.println(item.getName()));
    }

    private void attack(Command command) {
        Character character = currentRoom.getEnemy(command.getSecondWord());
        if (character != null) {
            Fight.fight(hero, character);
            if (character.getEnergy() <= 0) {
                currentRoom.removeEnemy(character);
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
        parser.showCommands();
    }

    private void goRoom(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Ir onde?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Ir onde?");
        } else {
            currentRoom = nextRoom;
            currentRoom.describe();
        }
    }

    private void look(Command command) {
        currentRoom.describe();
    }

    private boolean quit(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onDie(HashMap<String, Item> inventory) {
        System.out.println("Você morreu!");
    }

}
