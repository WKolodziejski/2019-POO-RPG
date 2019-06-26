package item.model;

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
        return durability <= 0 ? true : false;
    }

    public int getcurDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    @Override
    public boolean isConsumable() {
        return false;
    }

}
