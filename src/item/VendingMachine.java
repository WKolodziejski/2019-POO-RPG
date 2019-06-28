package item;

import characters.Hero;
import item.model.Item;
import utils.Generator;
import utils.Item_Creator;
import java.util.HashMap;

public class VendingMachine {
    private HashMap<String, Item> inventory;

    public VendingMachine() {
        this.inventory = new HashMap<>();

        for (int i = 0; i < Generator.get().number(10) + 10; i++) {
            Item item = Item_Creator.get().getRandom();

            while (item instanceof CoinBag) {
                item = Item_Creator.get().getRandom();
            }

            inventory.put(item.getKey(), item);
        }
    }

    public void printInventory() {
        if (!inventory.isEmpty()) {
            System.out.println("Itens à venda:");

            inventory.forEach((s, item) -> {
                System.out.println(item.getName() + ": " + item.getPrice() + " moedas");
            });

        } else {
            System.out.println("Não há itens à venda");
        }
    }

    public Item buy(String name, Hero hero) {
        Item item = inventory.get(name);

        if (item != null) {
            if (hero.useCoins(item.getPrice())) {
                System.out.println(item.getPrice() + " moedas gastas");
                inventory.remove(name);
                return item;
            } else {
                System.out.println("Você não tem moedas suficientes");
                return null;
            }
        } else {
            System.out.println("Qual item?");
            return null;
        }
    }

}
