package item;

import item.model.Equipment;

public class Weapon extends Equipment {
    private int damage;

    public Weapon(String name, int weight, int damage, int bonus, Type bonusType) {
        super(name, weight, bonus, bonusType);
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

    public String getDetails(){
        return getName() + " (" + (bonusType() != Type.ATTACK ? damage + " de dano, +" + bonusAmount() + " de " + getBonusName() + ", ": bonusAmount() + damage + " de dano, ") + getWeight() + " kg)";
    }

}
