package item;

import item.model.Equipment;

public class Weapon extends Equipment {
    private int damage;

    public Weapon(String name, int weight, int damage) {
        super(name, weight);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public int getPrice() {
        return damage * getDurability();
    }

}
