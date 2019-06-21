package item;

public abstract class Consumable extends Item{
    private int uses;

    public Consumable(String name, int weight, int uses) {
        super(name, weight);
        this.uses = uses;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }
}
