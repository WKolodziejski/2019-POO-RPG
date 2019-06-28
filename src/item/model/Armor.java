package item.model;

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

    public void lowerLevel(){
        int level = getLevel();
        int newLevel = level - 1;
        if (newLevel >= 0){
            changeMaterial(newLevel);
            setBonusByLevel(level, newLevel);
            setDefenseByLevel(level, newLevel);
        }
    }

    public void setBonusByLevel(int oldLevel, int newLevel){
        bonusAmount = (bonusAmount/(oldLevel+1))*(newLevel+1);
    }

    private void setDefenseByLevel(int oldLevel, int newLevel){
        defense = (defense/(oldLevel+1))*(newLevel+1);
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

    @Override
    public int getPrice() {
        return getDefense() * bonusAmount();
    }

    public String getDetails(){
        return getName() + " - " + (bonusType != Type.DEFENSE ? defense + " de defesa" + " - " + "+" + bonusAmount + " de " + getBonusName(): bonusAmount + defense + " de defesa") + " - " + getWeight() + "kg";
    }

    public String getBonusName(){
        switch (bonusType){
            case WEIGHT: return "força";
            case LIFE: return "vida";
            case DEFENSE: return "defesa";
            case ATTACK: return "dano";
            case SPEED: return "velocidade";
            default: return "desconhecido";
        }
    }

}
