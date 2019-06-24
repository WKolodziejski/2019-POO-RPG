package characters;

import item.CoinBag;
import item.Item;

import java.util.HashMap;
import java.util.Random;

public abstract class Character {
    protected HashMap<String, Item> inventory;
    private String name;
    private int energy;
    private int enercyCap;
    private OnDie onDie;

    public Character(String name, int energy, int coins, OnDie onDie) {
        this.inventory = new HashMap<>();
        this.name = name;
        this.energy = energy;
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

    public void decreaseEnergy() {
        energy -= energy < 0 ? 0 : 1;
        if (energy <= 0) {
            onDie.onDie(inventory);
        }
    }

    public HashMap<String, Item> getInventory() {
        return inventory;
    }

    public void print() {
        System.out.println("#####################");
        System.out.println("# Nome: " + name);
        System.out.println("# Energia: " + energy);
        System.out.println("#####################");
    }

}
