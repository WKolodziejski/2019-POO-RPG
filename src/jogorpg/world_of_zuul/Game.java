package jogorpg.world_of_zuul;

import characters.Enemy;
import item.RepairPiece;
import item.furniture.Chest;
import item.CoinBag;
import item.Heal;
import item.furniture.RepairTable;
import item.furniture.VendingMachine;
import item.model.Equipment;
import item.model.Item;
import mechanics.Fight;
import characters.Hero;
import characters.Character;
import utils.Console;
import utils.FileReader;

import java.util.Scanner;

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Hero hero;
    private Room lastRoom;

    public Game() {
        currentRoom = FileReader.readRooms();
        parser = new Parser();
        hero = new Hero(inventory -> {
            Console.print(Console.RED_BACKGROUND_BRIGHT, "Você morreu!");
        });
    }

    public void play() {
        Console.print(Console.BLACK, "Salve aí mermão!");
        currentRoom.describe();

        boolean finished = false;

        while (!finished && hero.getEnergy() > 0) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }

        Console.print(Console.BLACK, "Valeu mermão!");
    }

    private boolean processCommand(Command command) {

        while (hero.isOverweight()) {
            forceDrop();
        }

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            Console.print(Console.RED, "Eoq?");
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
        else if (commandWord == CommandWord.SELL) {
            sell(command);
        }
        else if (commandWord == CommandWord.REPAIR) {
            repair(command);
        }
        else if (commandWord == CommandWord.DESTROY) {
            destroy(command);
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

    private void repair(Command command) {
        RepairTable repairTable = currentRoom.getRepair();
        if (repairTable != null) {
            if (command.hasSecondWord()) {
                int index;

                try{
                    index = Integer.parseInt(command.getSecondWord());
                } catch(NumberFormatException e){
                    Console.print(Console.RED, "Reparar o que?");
                    return;
                }

                Item item = hero.findItem(index);

                if (item != null) {
                    if (item instanceof Equipment) {
                        repairTable.repairItem((Equipment) item, hero.getPieces());

                        takeEnemyDamage();

                    } else {
                        Console.print(Console.RED, "Esse item não é reparável");
                    }
                } else {
                    Console.print(Console.RED, "Reparar o que?");
                }

            } else {
                Console.print(Console.RED, "Reparar o que?");
            }
        } else {
            Console.print(Console.RED, "Não há mesas de reparo");
        }
    }

    private void destroy(Command command) {
        RepairTable repairTable = currentRoom.getRepair();

        if (repairTable != null) {
            if (command.hasSecondWord()) {
                int index;

                try{
                    index = Integer.parseInt(command.getSecondWord());
                } catch(NumberFormatException e){
                    Console.print(Console.RED, "Destruir o que?");
                    return;
                }

                Item item = hero.findItem(index);

                if (item != null) {
                    if (item instanceof Equipment) {
                        RepairPiece r = repairTable.disassemblyItem((Equipment) item);
                        hero.removeItem(item);

                        if (!hero.putItem(r)) {
                            currentRoom.addItem(r);
                        }

                        takeEnemyDamage();

                    } else {
                        Console.print(Console.RED, "Esse item não é destrutível");
                    }
                } else {
                    Console.print(Console.RED, "Destruir o que?");
                }
            } else {
                Console.print(Console.RED, "Destruir o que?");
            }
        } else {
            Console.print(Console.RED, "Não há mesas para destruir");
        }
    }

    private void forceDrop() {
        Console.print(Console.PURPLE_BOLD, hero.getName() + " está carregando mais itens do que pode. Escolha algo para dropar.");
        hero.printInventory();
        hero.removeItem(hero.findItem(new Scanner(System.in).nextInt()));
    }

    private void me(Command command) {
        hero.printLife();
        Console.print(Console.BLACK, "Sua defesa é de " + hero.getDefense());
        Console.print(Console.BLACK, "Seu ataque é de " + hero.getAttack());
        Console.print(Console.BLACK, "Sua velocidade é de " + hero.getSpeed());
        Console.print(Console.BLACK, "Você está carregando " + hero.getCurWeight() + " de "  + hero.getMaxWeight() + " kg.");
    }

    private void open(Command command) {
        if (!command.hasSecondWord()) {
            Console.print(Console.RED, "Abrir o que?");
        } else {

            if (command.getSecondWord().equals("Vending")) {
                VendingMachine machine = currentRoom.getMachine();

                if (machine != null) {
                    takeEnemyDamage();
                    machine.printInventory();
                } else {
                    Console.print(Console.RED, "Não há máquinas na sala");
                }
            } else {
                Chest chest = currentRoom.getChest();

                if (chest != null) {
                    if (command.getSecondWord().equals(chest.getName())) {
                        takeEnemyDamage();
                        chest.open();
                    } else {
                        Console.print(Console.RED, "Abrir o que?");
                    }
                } else {
                    Console.print(Console.RED, "Abrir o que?");
                }
            }
        }
    }

    private void equip(Command command){
        int index;

        try{
            index = Integer.parseInt(command.getSecondWord());
        } catch(NumberFormatException e){
            Console.print(Console.RED, "Equipar o que?");
            return;
        }
        hero.equipItem(index);
    }

    private void unequip(Command command){
        int index;

        try{
            index = Integer.parseInt(command.getSecondWord());
        } catch(NumberFormatException e){
            Console.print(Console.RED, "Desequipar o que?");
            return;
        }
        hero.unequipItem(index);
    }

    private void equipped(Command command){
        hero.printEquipped();
    }

    private void buy(Command command) {
        VendingMachine machine = currentRoom.getMachine();

        if (machine != null) {
            Item item;

            try{
                item = machine.buy(Integer.parseInt(command.getSecondWord()), hero);
            } catch(NumberFormatException e){
                Console.print(Console.RED, "Comprar o que?");
                return;
            }

            if (item != null) {
                if (!hero.putItem(item)) {
                    currentRoom.addItem(item);
                    Console.print(Console.PURPLE_BOLD, "Item está no chão");
                }
                takeEnemyDamage();
            }
        } else {
            Console.print(Console.RED, "Comprar onde?");
        }
    }

    private void sell(Command command) {
        VendingMachine machine = currentRoom.getMachine();

        if (machine != null) {
            Item item;

            try{
                item = hero.findItem(Integer.parseInt(command.getSecondWord()));
            } catch(NumberFormatException e){
                Console.print(Console.RED, "Vender o que?");
                return;
            }

            if (item != null) {

                Item sell = machine.sell(item);

                if (sell != null) {
                    hero.removeItem(item);

                    if (!hero.putItem(sell)) {
                        currentRoom.addItem(sell);
                    }
                    takeEnemyDamage();
                }
            }
        } else {
            Console.print(Console.RED, "Vender onde?");
        }
    }

    private void use(Command command) {
        Item item;

        try{
            item = hero.findItem(Integer.parseInt(command.getSecondWord()));
        } catch(NumberFormatException e){
            Console.print(Console.RED, "Usar o que?");
            return;
        }

        if (item != null) {
            if (item instanceof Heal) {
                hero.increaseEnergy((Heal) item);
            } else {
                Console.print(Console.RED, "Usar isso como?");
            }
        } else {
            Console.print(Console.RED, "Usar o que?");
        }
    }

    private void takeItem(Command command) {
        Chest chest = currentRoom.getChest();

        if (chest != null) {
            Item item;
            try {
                item = chest.take(Integer.parseInt(command.getSecondWord()));
            } catch (NumberFormatException e){
                Console.print(Console.RED, "Pegar o que?");
                return;
            }

            if (item != null) {
                if(!hero.putItem(item)) {
                    currentRoom.addItem(item);
                    Console.print(Console.PURPLE_BOLD, "Item está no chão");
                }
            }

            takeEnemyDamage();

        } else {
            Console.print(Console.RED, "Pegar onde?");
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
            Console.print(Console.RED, "Atacar quem?");
    }

    private void pick(Command command){
        Item item;

        try {
            item = currentRoom.findItem(Integer.parseInt(command.getSecondWord()));
        } catch (NumberFormatException e){
            Console.print(Console.RED, "Pegar o que?");
            return;
        }

        if (item == null) {
            Console.print(Console.RED, "Pegar o que?");
        } else {
            if (hero.putItem(item)) {
                currentRoom.removeItem(item);
            }
        }
    }

    private void drop(Command command) {
        Item item;

        try {
            item = hero.findItem(Integer.parseInt(command.getSecondWord()));
        } catch (NumberFormatException e){
            Console.print(Console.RED, "Dropar o que?");
            return;
        }

        if (item == null) {
            Console.print(Console.RED, "Dropar o que?");
        } else {
            if (item instanceof CoinBag) {
                currentRoom.addItem(new CoinBag(hero.dropCoins()));
            } else {
                currentRoom.addItem(item);
                hero.removeItem(item);
                Console.print(Console.CYAN_BOLD, "Dropou " + item.getName());
            }
        }
    }

    private void printHelp() {
        parser.showCommands();
    }

    private void goRoom(Command command) {
        if(!command.hasSecondWord()) {
            Console.print(Console.RED, "Ir onde?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            Console.print(Console.RED, "Ir onde?");
        } else {
            if (nextRoom != lastRoom) {
                takeEnemyDamage();
            }
            lastRoom = currentRoom;
            currentRoom = nextRoom;
            currentRoom.describe();

        }
    }

    private void look(Command command) {
        if (command.hasSecondWord()) {
            if (command.getSecondWord().equals("Around")) {
                currentRoom.describe();
            } else {

                Character enemy = currentRoom.getEnemy(command.getSecondWord());

                if (enemy != null) {
                    Console.print(Console.PURPLE_BOLD, enemy.getName());
                } else {
                    Console.print(Console.RED, "Olhar para onde?");
                }
            }
        } else {
            Console.print(Console.RED, "Olhar para onde?");
        }
    }

    private boolean quit(Command command) {
        if(command.hasSecondWord()) {
            Console.print(Console.RED, "Sair de onde?");
            return false;
        }
        else {
            return true;
        }
    }

    private void takeEnemyDamage() {
        currentRoom.getCharacters().forEach((s, character) -> {
            hero.takeDamage(character.getAttack() / (1 + hero.getDefense() / 10));
            hero.printLife();
        });
    }

}
