package item;

import item.model.Item;

public class Heal extends Item {
    private int healAmount;

    public Heal(String name, int weight, int healAmount) {
        super(name, weight);
        this.healAmount = healAmount;
    }

    @Override
    public boolean isConsumable() {
        return true;
    }

    public int getHealAmount() {
        return healAmount;
    }

}
