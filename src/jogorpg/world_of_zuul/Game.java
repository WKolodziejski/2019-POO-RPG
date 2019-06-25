package jogorpg.world_of_zuul;

import item.Chest;
import item.Heal;
import item.model.Item;
import mechanics.Fight;
import characters.Hero;
import characters.Character;
import utils.FileReader;

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

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Hero hero;

    public Game() {
        currentRoom = FileReader.readRooms();
        parser = new Parser();
        hero = new Hero(inventory -> {
            System.out.println("Você morreu!");
        });
    }

    public void play() {
        System.out.println("Salve aí mermão!");

        boolean finished = false;

        while (!finished && hero.getEnergy() > 0) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }

        System.out.println("Valeu mermão!");
    }

    private boolean processCommand(Command command) {
        CommandWord commandWord = command.getCommandWord();

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
        else if (commandWord == CommandWord.USE) {
            use(command);
        }
        else if (commandWord == CommandWord.OPEN) {
            open(command);
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
        else if (commandWord == CommandWord.ME) {
            me(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            return quit(command);
        }

        return false;
    }

    private void me(Command command) {
        hero.printLife();
    }

    private void open(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Abrir o que?");
        } else {
            if (command.getSecondWord().equals("Chest")) {

                Chest chest = currentRoom.getChest();

                if (chest != null) {
                    chest.open();
                } else {
                    System.out.println("Não há baús na sala");
                }
            } else {
                System.out.println("Abrir o que?");
            }
        }
    }

    private void use(Command command) {
        if (command.hasSecondWord()) {
            Item item = hero.removeItem(command.getSecondWord());

            if (item != null) {
                if (item instanceof Heal) {
                    if (hero.increaseEnergy(((Heal) item).getHealAmount())) {
                        hero.printLife();
                    } else {
                        hero.putItem(item);
                    }
                } else {
                    System.out.println("Usar isso como?");
                    hero.putItem(item);
                }
            } else {
                System.out.println("Usar o que?");
            }

        } else {
            System.out.println("Usar o que?");
        }
    }

    private void takeItem(Command command) {
        Chest chest = currentRoom.getChest();

        if (chest != null) {
            if (command.hasSecondWord()) {
                Item item = chest.take(command.getSecondWord());

                if (item == null) {
                    System.out.println("Esse item não existe");
                } else {
                    hero.putItem(item);
                    System.out.println("Item adicionado ao inventário");
                }
            } else {
                System.out.println("Pegar o que?");
            }
        } else {
            System.out.println("Pegar onde?");
        }
    }

    private void inventory(Command command) {
        hero.printInventory();
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
            if(hero.putItem(item)) {
                System.out.println("Você pegou " + item.getName());
            } else {
                currentRoom.addItem(item);
                System.out.println("Sem espaço no inventário!");
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

            currentRoom.getCharacters().forEach((s, character) -> {
                hero.decreaseEnergy(character.getAttack() / (1 + hero.getDefense() / 10));
                hero.printLife();
            });

            currentRoom = nextRoom;
            currentRoom.describe();

        }
    }

    private void look(Command command) {
        currentRoom.describe();
    }

    private boolean quit(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("Sair de onde?");
            return false;
        }
        else {
            return true;
        }
    }

}
