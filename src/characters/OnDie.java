package characters;

import item.Item;

import java.util.HashMap;

public interface OnDie {

    void onDie(HashMap<String, Item> inventory);

}
