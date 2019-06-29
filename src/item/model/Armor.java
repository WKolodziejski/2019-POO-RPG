package item.model;

public abstract class Armor extends Equipment {
    private int defense;

    public Armor(String name, int weight, int defense, int bonus, Type bonusType) {
        super(name, weight, bonus, bonusType);
        this.defense = defense;
    }

    public void lowerLevel(){
        int level = getLevel();
        int newLevel = level - 1;
        if (newLevel >= 1){
            changeMaterial(newLevel);
            setBonusByLevel(level, newLevel);
            setDefenseByLevel(level, newLevel);
        }
    }

    private void setDefenseByLevel(int oldLevel, int newLevel){
        defense = (int) (((double)defense/((double)oldLevel))*((double)newLevel));
    }

    public int getDefense() {
        return defense;
    }

    @Override
    public int getPrice() {
        return (int) (Math.ceil((((double) getDefense() * (double) bonusAmount()))/2.0) * ((double) getDurability())/((double)getLevel()));
    }

    public String getDetails(){
        return getName() + " (" + (bonusType() != Type.DEFENSE ? defense + " de defesa" + ", " + "+" + bonusAmount() + " de " + getBonusName(): bonusAmount() + defense + " de defesa") + ", " + getWeight() + " kg)";
    }

}
