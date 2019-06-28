package jogorpg.world_of_zuul;

import item.Chest;
import item.CoinBag;
import item.Heal;
import item.VendingMachine;
import item.model.Item;
import mechanics.Fight;
import characters.Hero;
import characters.Character;
import utils.FileReader;

import java.util.Scanner;

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

        while (hero.isOverweight()) {
            forceDrop();
        }

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
        else if (commandWord == CommandWord.BUY) {
            buy(command);
        }
        else if (commandWord == CommandWord.EQUIP) {
            equip(command);
        }
        else if (commandWord == CommandWord.UNEQUIP) {
            unequip(command);
        }
        else if (commandWord == CommandWord.EQUIPPED) {
            equipped(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            return quit(command);
        }

        return false;
    }

    private void forceDrop() {
        System.out.println(hero.getName() + " está carregando mais itens do que pode. Escolha algo para dropar.");
        hero.printInventory();
        hero.removeItem(hero.findItem(new Scanner(System.in).nextInt()));
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
            } else if (command.getSecondWord().equals("Vending")) {
                VendingMachine machine = currentRoom.getMachine();

                if (machine != null) {
                    machine.printInventory();
                } else {
                    System.out.println("Não há máquinas na sala");
                }

            } else {
                System.out.println("Abrir o que?");
            }
        }
    }

    private void equip(Command command){
        int index;

        try{
            index = Integer.parseInt(command.getSecondWord());
        } catch(NumberFormatException e){
            System.out.println("Equipar o que?");
            return;
        }
        hero.equipItem(index);
    }

    private void unequip(Command command){
        int index;

        try{
            index = Integer.parseInt(command.getSecondWord());
        } catch(NumberFormatException e){
            System.out.println("Equipar o que?");
            return;
        }
        hero.unEquip(index);
    }

    private void equipped(Command command){
        hero.printEquipped();
    }

    private void buy(Command command) {
        VendingMachine machine = currentRoom.getMachine();

        if (machine != null) {
            if (command.hasSecondWord()) {
                Item item = machine.buy(Integer.parseInt(command.getSecondWord()), hero);

                if (item != null) {
                    if (!hero.putItem(item)) {
                        currentRoom.addItem(item);
                        System.out.println("Item está no chão");
                    }
                }
            } else {
                System.out.println("Comprar o que?");
            }
        } else {
            System.out.println("Comprar onde?");
        }
    }

    private void use(Command command) {
        if (command.hasSecondWord()) {
            Item item = hero.findItem(Integer.parseInt(command.getSecondWord()));

            if (item != null) {
                if (item instanceof Heal) {
                    hero.increaseEnergy((Heal) item);
                } else {
                    System.out.println("Usar isso como?");
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
                Item item = chest.take(Integer.parseInt(command.getSecondWord()));

                if (item != null) {
                    if(!hero.putItem(item)) {
                        currentRoom.addItem(item);
                        System.out.println("Item está no chão");
                    }
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
        Item item = currentRoom.findItem(Integer.parseInt(command.getSecondWord()));

        if (item == null) {
            System.out.println("Pegar o que?");
        } else {
            if (hero.putItem(item)) {
                currentRoom.removeItem(item);
            }
        }
    }

    private void drop(Command command) {
        Item item = hero.findItem(Integer.parseInt(command.getSecondWord()));

        if (item == null) {
            System.out.println("Dropar o que?");
        } else {
            if (item instanceof CoinBag) {
                currentRoom.addItem(new CoinBag(hero.dropCoins()));
            } else {
                currentRoom.addItem(item);
                hero.removeItem(item);
                System.out.println("Dropou " + item.getName());
            }
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
        if (command.hasSecondWord()) {
            if (command.getSecondWord().equals("Around")) {
                currentRoom.describe();
            } else {
                System.out.println("Olhar para onde?");
            }
        } else {
            System.out.println("Olhar para onde?");
        }
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
