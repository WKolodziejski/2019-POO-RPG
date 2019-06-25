package item.model;

public abstract class Equipment extends Item {
    private int durability;

    public Equipment(String name, int weight) {
        super(name, weight);
        this.durability = 100;
    }

    public int getDurability() {
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
