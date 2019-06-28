package item;

import item.model.Item;

public class Heal extends Item {
    private int healAmount;

    public Heal(String name, int weight, int healAmount) {
        super(name, weight);
        this.healAmount = healAmount;
    }

    @Override
    public int getPrice() {
        return healAmount * 100;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public String getDetails(){
        return getName() + " (" + healAmount + " de cura, " + getWeight() + " kg)";
    }

}
