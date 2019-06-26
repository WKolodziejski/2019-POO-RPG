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

    public void lowerLevel(){
        int level = getLevel();
        int newLevel = level-1;
        if(newLevel>=0){
            changeMaterial(newLevel);
            setBonusByLevel(level, newLevel);
        }
    }

    public void setBonusByLevel(int oldLevel, int newLevel){
        bonusAmount = (bonusAmount/(oldLevel+1))*(newLevel+1);
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
