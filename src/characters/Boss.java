package characters;

import item.Weapon;
import item.model.Bonus;
import utils.Generator;

public class Boss extends Enemy {

    public Boss(OnDie onDie) {
        super("Zabuza Momochi, o demônio do gás oculto",
                Generator.get().number(20) + 20,
                Generator.get().number(20) + 1,
                Generator.get().number(20) + 1,
                10,
                Generator.get().number(100) + 1000,
                Generator.get().number(20) + 1,
                onDie);

        putItem(new Weapon("Kubikiribōchō", 5, 20, 5, Bonus.Type.DEFENSE));
    }

}
