package personagens;

import utils.Generator;

import java.util.Random;

public class Boss extends Character {

    public Boss() {
        super(Generator.get().generateName(), new Random().nextInt(10) + 1, new Random().nextInt(1000) + 1000);
    }

}
