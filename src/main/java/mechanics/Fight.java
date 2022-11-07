package mechanics;

import characters.Character;
import utils.Console;

public class Fight {

    public static void fight(Character c1, Character c2) {
        int p1 = c1.getLuck();
        int p2 = c2.getLuck();

        if (p1 > p2) {
            int damage = c1.getAttack() / (1 + c2.getDefense() / 10);
            Console.print(Console.PURPLE_BOLD, c2.getName() + " sofreu " + damage + " de dano");
            c1.damageEquippedWeapon();
            c2.takeDamage(damage);
        } else {
            int damage = c2.getAttack() / (1 + c1.getDefense() / 10);
            Console.print(Console.PURPLE_BOLD, c1.getName() + " sofreu " + damage + " de dano");
            c1.takeDamage(damage);
            c2.damageEquippedWeapon();
        }

        if (c1.getEnergy() > 0 && c2.getEnergy() > 0) {
            c1.printLife();
            c2.printLife();
        }
    }

}
