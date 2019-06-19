package personagens;

import item.Item;
import java.util.HashMap;

public class Hero extends Character {
    private int maxWeight;
    private int actualWeight;
    private OnDie onDie;
    
    public Hero(String name, int energy) {
        super(name, energy, 0);
        this.maxWeight = 10;
    }
    
    public boolean putItem(Item item) {
        int newWeight = actualWeight + item.getWeight();
        if (newWeight <= maxWeight) {
            inventory.put(item.getName(), item);
            actualWeight = newWeight;
            return true;
        } else return false;
    }
    
    public Item removeItem(String name) {
        Item item = inventory.get(name);
        if (item != null) {
            inventory.remove(item);
            actualWeight -= item.getWeight();
            return item;
        } else return null;
    }

    public int getActualWeight() {
        return actualWeight;
    }
    
    public void feed() {
        increaseEnergy();
        increaseEnergy();
    }

    public void grabCoins(int amount) {
        coins += amount;
        //calcular peso
    }

    interface OnDie {
        void onDie();
    }

}
