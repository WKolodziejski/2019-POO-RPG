package item.furniture;

import item.model.Item;

import java.util.ArrayList;

public abstract class Furniture {
    protected ArrayList<Item> items;

    public Furniture() {
        this.items = new ArrayList<>();
    }

    protected Item findItem(int i) {
        if (i < 0 || i >= items.size()) return null;
        else return items.get(i);
    }

}
