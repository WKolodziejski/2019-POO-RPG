package mechanics;

import characters.Character;

public class Fight {

    public static void fight(Character c1, Character c2) {
        int p1 = c1.getLuck();
        int p2 = c2.getLuck();

        if (p1 > p2) {
            int damage = c1.getAttack() / (1 + c2.getDefense() / 10);
            System.out.println(c2.getName() + " sofreu " + damage + " de dano");
            c1.damageEquippedWeapon();
            c2.takeDamage(damage);
        } else {
            int damage = c2.getAttack() / (1 + c1.getDefense() / 10);
            System.out.println(c1.getName() + " sofreu " + damage + " de dano");
            c1.takeDamage(damage);
            c2.damageEquippedWeapon();
        }

        c1.printLife();
        c2.printLife();
    }

}
