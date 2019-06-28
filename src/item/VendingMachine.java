package item;

import characters.Hero;
import item.model.Item;
import utils.Generator;
import utils.Item_Creator;
import java.util.ArrayList;

public class VendingMachine {
    private ArrayList<Item> items;

    public VendingMachine() {
        this.items = new ArrayList<>();

        for (int i = 0; i < Generator.get().number(10) + 10; i++) {
            Item item = Item_Creator.get().getRandom();

            while (item instanceof CoinBag) {
                item = Item_Creator.get().getRandom();
            }

            items.add(item);
        }
    }

    public void printInventory() {
        if (!items.isEmpty()) {
            System.out.println("Itens à venda:");

            for (int i = 0; i < items.size(); i++) {
                if (items.get(i) != null) {
                    System.out.println(i + ": "+ items.get(i).getName() + " - " + items.get(i).getPrice() + " moedas");
                }
            }

        } else {
            System.out.println("Não há itens à venda");
        }
    }

    private Item findItem(int i) {
        if (i < 0 || i >= items.size()) return null;
        else return items.get(i);
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
            System.out.println("Qual item?");
            return null;
        }
    }

    public CoinBag sell(Item item) {
        if (item instanceof CoinBag) {
            System.out.println("Tá maluco?");
            return null;
        } else {
            System.out.println("Venedeu " + item.getName() + " por " + item.getPrice() + " moedas");
            items.add(item);
            return new CoinBag(item.getPrice());
        }
    }

}
