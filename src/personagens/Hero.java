package personagens;

import item.Item;
import java.util.HashMap;

public class Hero extends Character {
    private HashMap<String, Item> inventory;
    private int maxWeight;
    private int actualWeight;
    
    public Hero(String name, int energy, int maxWeight) {
        super(name, energy);
        this.maxWeight = maxWeight;
        this.inventory = new HashMap<String, Item>();
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
    
    public void fight(Character opponent) {
        int h = luck();
        int a = opponent.luck();
        
        if (h == a) {
            decreaseEnergy();
            opponent.decreaseEnergy();
        } else {
            if (h > a) {
                increaseEnergy();
                opponent.decreaseEnergy();
            } else {
                decreaseEnergy();
                opponent.increaseEnergy();
            }
        }

        print();
        opponent.print();
    }
    
}
