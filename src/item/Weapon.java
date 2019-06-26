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

    public void lowerLevel(){
        int level = getLevel();
        int newLevel = level-1;
        if(newLevel>=0){
            changeMaterial(newLevel);
            setDamageByLevel(level, newLevel);
        }
    }

    private void setDamageByLevel(int oldLevel, int newLevel){
        damage = (damage/(oldLevel+1))*(newLevel+1);
    }

}
