package item.model;

import item.model.Bonus;
import item.model.Equipment;

public abstract class Armor extends Equipment implements Bonus {
    private Type bonusType;
    private int bonusAmount;
    private int defense;

    public Armor(String name, int weight, int defense, int bonus, Type bonusType) {
        super(name, weight);
        this.defense = defense;
        this.bonusAmount = bonus;
        this.bonusType = bonusType;
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
