package item.model;

public abstract class Equipment extends Item {

    public Equipment(String name, int weight) {
        super(name, weight);
    }

    @Override
    public boolean isConsumable() {
        return false;
    }

}
