package characters;

import item.model.Item;

import java.util.HashMap;

public interface OnDie {

    void onDie(HashMap<String, Item> inventory);

}
