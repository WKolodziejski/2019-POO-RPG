package mechanics;

import characters.Character;

public class Fight {

    public static void fight(Character c1, Character c2) {
        int p1 = c1.luck();
        int p2 = c2.luck();

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
