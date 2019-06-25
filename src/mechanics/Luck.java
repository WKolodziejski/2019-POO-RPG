package mechanics;

import utils.Generator;

public class Luck {

    public static int d6() {
        return Generator.get().number(6);
    }

    public static int d20() {
        return Generator.get().number(20);
    }

}
