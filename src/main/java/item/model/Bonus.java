package item.model;

public interface Bonus {

    enum Type {
        ATTACK(),
        DEFENSE,
        WEIGHT,
        LIFE,
        SPEED;
    }

    Type bonusType();
    int bonusAmount();
    void setBonusByLevel(int oldLevel, int newLevel);
    String getBonusName();
}
