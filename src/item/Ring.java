package item;

import item.model.Bonus;
import item.model.Equipment;

public class Ring extends Equipment implements Bonus {
    private Type bonusType;
    private int bonusAmount;

    public Ring(String name, Type bonusType, int bonusAmount) {
        super(name, 1);
        this.bonusType = bonusType;
        this.bonusAmount = bonusAmount;
    }

    @Override
    public Type bonusType() {
        return bonusType;
    }

    @Override
    public int bonusAmount() {
        return bonusAmount;
    }

    @Override
    public int getPrice() {
        return bonusAmount * 100;
    }

}
