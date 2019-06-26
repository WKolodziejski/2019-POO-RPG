package characters;

import item.*;
import item.model.Armor;
import item.model.Equipment;
import item.model.Item;

public class Hero extends Character {

    public Hero(OnDie onDie) {
        super("Cleytinho", 10, 20, 10, 0, 10, onDie);

        putItem(new Heal("Poção", 1, 2));
    }
}
