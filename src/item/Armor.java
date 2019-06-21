package item;

public class Armor extends Equipment {
    private int damageReduction;

    public Armor(String name, int weight, int damageReduction) {
        super(name, weight);
        this.damageReduction = damageReduction;
    }

    public int getDamageReduction() {
        return damageReduction;
    }
}
