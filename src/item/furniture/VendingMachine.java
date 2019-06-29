package item.furniture;

import characters.Hero;
import item.CoinBag;
import item.Heal;
import item.model.Item;
import utils.Console;
import utils.Generator;
import utils.Item_Creator;

public class VendingMachine extends Furniture {

    public VendingMachine() {

        for (int i = 0; i < Generator.get().number(10) + 10; i++) {
            Item item = Item_Creator.get().getRandom();

            while (item instanceof CoinBag) {
                item = Item_Creator.get().getRandom();
            }

            items.add(item);
        }
    }

    public void printInventory() {
        Console.print(Console.BLACK_UNDERLINED, "------VENDING MACHINE------");
        if (!items.isEmpty()) {

            for (int i = 0; i < items.size(); i++) {
                if (items.get(i) != null) {
                    Console.print(Console.BLUE_BRIGHT, i + ": " + items.get(i).getPrice() + " moedas - " + items.get(i).getDetails());
                }
            }

        } else {
            Console.print(Console.BLACK_BOLD, "Não há itens à venda");
        }
    }

    public Item buy(int i, Hero hero) {
        Item item = findItem(i);

        if (item != null) {
            if (hero.useCoins(item.getPrice())) {
                items.remove(i);
                printInventory();
                return item;
            } else {
                return null;
            }
        } else {
            Console.print(Console.RED, "Qual item?");
            return null;
        }
    }

    public CoinBag sell(Item item) {
        if (item instanceof CoinBag) {
            Console.print(Console.PURPLE_BOLD, "Por que tu ta me vendendo tuas moedas? Não faz sentido- disse uma voz do alem");
            return null;
        } else {
            Console.print(Console.CYAN_BOLD, "Vendeu " + item.getName() + " por " + item.getPrice() + " moedas");
            items.add(item);
            return new CoinBag(item.getPrice());
        }
    }

}
