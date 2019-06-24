package mechanics;

import characters.Character;

public class Fight {

    public static void fight(Character c1, Character c2) {
        int p1 = Luck.d6();
        int p2 = Luck.d6();

        if (p1 == p2) {
            c1.decreaseEnergy();
            c2.decreaseEnergy();
        } else {
            if (p1 > p2) {
                c1.increaseEnergy();
                c2.decreaseEnergy();
            } else {
                c1.decreaseEnergy();
                c2.increaseEnergy();
            }
        }
        c1.print();
        c2.print();
    }

}
