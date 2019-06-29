package characters;

import utils.Generator;

public class Boss extends Enemy {

    public Boss(OnDie onDie) {
        super(Generator.get().name(),
                Generator.get().number(20) + 1,
                Generator.get().number(20) + 1,
                Generator.get().number(20) + 1,
                10,
                Generator.get().number(100) + 1000,
                Generator.get().number(20) + 1,
                onDie);
    }

}
