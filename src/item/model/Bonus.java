package item.model;

public interface Bonus {

    enum Type {
        ATTACK,
        DEFENSE,
        WEIGHT;
    }

    Type bonusType();
    int bonusAmount();
    void setBonusByLevel(int oldLevel, int newLevel);

}
