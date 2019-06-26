package item.model;

import item.Ring;
import utils.Generator;

public abstract class Equipment extends Item {
    private int durability;

    public Equipment(String name, int weight) {
        super(name, weight);
        this.durability = 100;
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

    protected int getLevel(){
        return Generator.get().getLevelByMaterial(this.getMaterial());
    }

    public String getMaterial(){
        return getName().substring(getName().lastIndexOf(" ") + 1);
    }

    protected void changeMaterial(int level){
        String material = getMaterial();
        int inicio = getName().indexOf(material);
        StringBuilder b = new StringBuilder();
        b.replace(inicio, inicio + material.length(), Generator.get().getMaterialByLevel(level));
        setName(b.toString());
    }

    @Override
    public boolean isConsumable() {
        return false;
    }

}
