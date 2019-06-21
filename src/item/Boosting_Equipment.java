package item;

public abstract class Boosting_Equipment extends Equipment {
    protected int bonus;

    public Boosting_Equipment(String name, int weight, int bonus) {
        super(name, weight);
        this.bonus = bonus;
    }
}
