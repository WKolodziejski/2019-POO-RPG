package characters;

import utils.Generator;

import java.util.Random;

public class Boss extends Character {

    public Boss(OnDie onDie) {
        super(Generator.get().name(),
                Generator.get().number(10),
                Generator.get().number(10),
                Generator.get().number(10),
                Generator.get().number(100) + 1000,
                Generator.get().number(10),
                onDie);
    }

}
