package characters;

import item.model.Item;
import utils.Generator;
import utils.Item_Creator;

public class Enemy extends Character {
    
    public Enemy(OnDie onDie) {
        super(Generator.get().name(),
                Generator.get().number(10),
                Generator.get().number(10),
                Generator.get().number(10),
                Generator.get().number(100),
                Generator.get().number(10),
                onDie);

        for (int i = 0; i < Generator.get().number(3); i++) {
            Item item = Item_Creator.get().getRandom();
            inventory.add(item);
        }
    }

}
