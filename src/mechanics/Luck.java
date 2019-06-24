package mechanics;

import java.util.Random;

public class Luck {

    public static int d6() {
        return new Random().nextInt(6) + 1;
    }

    public static int d20() {
        return new Random().nextInt(20) + 1;
    }

}
