package item;

import item.model.Bonus;
import item.model.Equipment;

public class Armor extends Equipment implements Bonus {
    private Type bonusType;
    private int bonusAmount;
    private int defense;

    public Armor(String name, int weight, int defense, int bonus) {
        super(name, weight);
        this.defense = defense;
        this.bonusAmount = bonus;
        this.bonusType = Type.WEIGHT;
    }

    public int getDefense() {
        return defense;
    }

    @Override
    public Type bonusType() {
        return bonusType;
    }

    @Override
    public int bonusAmount() {
        return bonusAmount;
    }

}
