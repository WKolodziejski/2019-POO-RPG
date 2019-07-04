package characters;

import item.model.Item;

import java.util.ArrayList;

public interface OnDie {

    void onDie(ArrayList<Item> inventory, Character character);

}
