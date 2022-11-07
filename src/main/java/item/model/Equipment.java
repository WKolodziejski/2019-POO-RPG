package item.model;

import utils.Console;
import utils.Generator;

public abstract class Equipment extends Item implements Bonus {
    private int durability;
    private Type bonusType;
    private int bonusAmount;

    public Equipment(String name, int weight, int bonus, Type bonusType) {
        super(name, weight);
        this.durability = getLevel() * 10;
        this.bonusAmount = bonus;
        this.bonusType = bonusType;
    }

    public void takeAHit(){
        durability = durability - (durability > 0 ? 1 : 0);
    }

    public boolean isBroken(){
        return durability <= 0;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public abstract void lowerLevel();

    public int getLevel() {
        return Generator.get().getLevelByMaterial(getMaterial());
    }

    public String getMaterial() {
        return getName().substring(getName().lastIndexOf(" ") + 1);
    }

    protected void changeMaterial(int level){
        String material = getMaterial();
        int inicio = getName().indexOf(material);
        StringBuilder b = new StringBuilder(getName());
        b.replace(inicio, inicio + material.length(), Generator.get().getMaterialByLevel(level));
        setName(b.toString());
    }

    public void setBonusByLevel(int oldLevel, int newLevel){
        bonusAmount = (int)(((double)bonusAmount/((double)oldLevel))*((double)newLevel));
    }

    public Type bonusType() {
        return bonusType;
    }

    public int bonusAmount() {
        return bonusAmount;
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
