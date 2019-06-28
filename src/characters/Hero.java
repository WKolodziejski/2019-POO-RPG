package characters;

import item.*;

public class Hero extends Character {

    public Hero(OnDie onDie) {
        super("Cleytinho", 10, 20, 10, 10, 10, onDie);

        inventory.add(new Heal("Poção", 1, 2));
    }

}
