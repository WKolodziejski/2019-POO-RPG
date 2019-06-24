package characters;

import item.CoinBag;
import item.Item;
import java.util.HashMap;

public abstract class Character {
    private OnDie onDie;
    protected HashMap<String, Item> inventory;
    private String name;
    private int energy;
    private int enercyCap;
    private int attack;
    private int defense;

    public Character(String name, int energy, int attack, int defense, int coins, OnDie onDie) {
        this.inventory = new HashMap<>();
        this.name = name;
        this.energy = energy;
        this.attack = attack;
        this.defense = defense;
        this.enercyCap = energy;
        this.onDie = onDie;
        inventory.put("Moedas", new CoinBag("Moedas do " + name, coins));
    }

    public int getEnergy() {
        return energy;
    }

    public String getName() {
        return name;
    }

    public void increaseEnergy() {
        energy += energy < this.enercyCap ? 1 : 0;
    }

    public void decreaseEnergy(int amount) {
        energy -= amount;
        if (energy <= 0) {
            onDie.onDie(inventory);
        }
    }

    public void print() {
        System.out.println("#Vida de " + name + ": " + (energy < 0 ? 0 : energy));
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

}
