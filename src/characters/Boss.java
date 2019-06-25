package characters;

import utils.Generator;

import java.util.Random;

public class Boss extends Character {

    public Boss(OnDie onDie) {
        super(Generator.get().name(),
                new Random().nextInt(10) + 1,
                new Random().nextInt(10) + 1,
                new Random().nextInt(10) + 1,
                new Random().nextInt(1000) + 1000,
                new Random().nextInt(10) + 1,
                onDie);
    }

}
