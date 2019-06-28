package item;

import item.model.Equipment;

public class Ring extends Equipment {

    public Ring(String name, Type bonusType, int bonusAmount) {
        super(name, 1, bonusAmount, bonusType);
    }

    public void lowerLevel(){
        int level = getLevel();
        int newLevel = level-1;
        if(newLevel>=0){
            changeMaterial(newLevel);
            setBonusByLevel(level, newLevel);
        }
    }

    @Override
    public int getPrice() {
        return bonusAmount() * 100;
    }

    public String getDetails(){
        return getName() + " - " + "+" + bonusAmount() + " de " + getBonusName() + " - " + getWeight() + "kg";
    }

}
