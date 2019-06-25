package characters;

import item.CoinBag;
import item.model.Bonus;
import item.model.Item;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Character {
    private OnDie onDie;
    protected HashMap<String, Item> inventory;
    private int curWeight;
    private int maxWeight;
    private String name;
    private int energy;
    private int enercyCap;
    private int attack;
    private int defense;

    public Character(String name, int energy, int attack, int defense, int coins, int maxWeight, OnDie onDie) {
        this.inventory = new HashMap<>();
        this.name = name;
        this.energy = energy;
        this.attack = attack;
        this.defense = defense;
        this.enercyCap = energy;
        this.maxWeight = maxWeight;
        this.onDie = onDie;
        inventory.put("Moedas", new CoinBag("Moedas do " + name, coins));
    }

    public int getEnergy() {
        return energy;
    }

    public String getName() {
        return name;
    }

    public void increaseEnergy(int amount) {
        energy += (energy + amount <= enercyCap) ? amount : enercyCap;
    }

    public void decreaseEnergy(int amount) {
        energy -= amount;
        if (energy <= 0) {
            onDie.onDie(inventory);
        }
    }

    public void printLife() {
        System.out.println("#Vida de " + name + ": " + (energy < 0 ? 0 : energy));
    }

    public int getAttack() {
        int bonus = 0;

        for (Item item : inventory.values()) {
            if (item instanceof Bonus) {
                if (((Bonus) item).bonusType() == Bonus.Type.ATTACK) {
                    bonus += ((Bonus) item).bonusAmount();
                }
            }
        }

        return attack + bonus;
    }

    public int getDefense() {
        int bonus = 0;

        for (Item item : inventory.values()) {
            if (item instanceof Bonus) {
                if (((Bonus) item).bonusType() == Bonus.Type.DEFENSE) {
                    bonus += ((Bonus) item).bonusAmount();
                }
            }
        }

        return defense + bonus;
    }

    public int getCurWeight() {
        return curWeight;
    }

    public int getMaxWeight() {
        int bonus = 0;

        for (Item item : inventory.values()) {
            if (item instanceof Bonus) {
                if (((Bonus) item).bonusType() == Bonus.Type.WEIGHT) {
                    bonus += ((Bonus) item).bonusAmount();
                }
            }
        }

        return maxWeight + bonus;
    }

    public Item removeItem(String name) {
        Item item = inventory.get(name);
        if (item != null) {
            if(item instanceof CoinBag){
                return dropCoins();
            } else {
                inventory.remove(name);
                curWeight -= item.getWeight();
                return item;
            }
        } else return null;
    }

    private boolean increaseWeightBy(int weight) {
        if (curWeight + weight <= getMaxWeight()) {
            curWeight += weight;
            return true;
        }
        return false;
    }

    public boolean grabCoins(CoinBag pickedBag){  ///retorna se eu peguei todas as moedas da coinbag;
        CoinBag herosBag = getCoinBag();
        curWeight -= herosBag.getWeight();

        int tmp = herosBag.getAmount() + pickedBag.getAmount();

        if(increaseWeightBy((int) Math.ceil(tmp/1000))){
            herosBag.setAmount(tmp);
            return true;
        } else {
            int maxAmount = (getMaxWeight() - curWeight) * 1000;
            herosBag.setAmount(maxAmount);
            increaseWeightBy(herosBag.getWeight());
            pickedBag.setAmount(maxAmount - tmp);
            return false;
        }
    }

    public boolean useCoins(int coins){
        CoinBag herosBag = getCoinBag();
        if(herosBag.getAmount() - coins < 0){
            return false;
        } else {
            this.curWeight -= herosBag.getWeight();
            herosBag.setAmount(herosBag.getAmount() - coins);
            increaseWeightBy(herosBag.getWeight());
            return true;
        }
    }

    public boolean putItem(Item item) {
        if(item instanceof CoinBag){
            return grabCoins((CoinBag) item);
        } else {
            if (increaseWeightBy(item.getWeight())) {
                inventory.put(item.getKey(), item);
                return true;
            } else {
                return false;
            }
        }
    }

    public CoinBag dropCoins(){
        System.out.println("Quantas moedas deseja dropar?");
        int amount = new Scanner(System.in).nextInt();
        if (amount > 0) {
            if (!useCoins(amount)) {
                amount = getCoinBag().getAmount();
                this.useCoins(amount);
            }
            return new CoinBag("Moedas de " + getName(), amount);
        } else {
            return null;
        }

    }

    private CoinBag getCoinBag() {
        return (CoinBag) inventory.get("Moedas");
    }

    public void printInventory() {
        inventory.forEach((s, item) -> System.out.println(item.getName()));
    }

    public String getKey() {
        if (name.contains(" ")) {
            return name.substring(0, name.indexOf(" "));
        } else {
            return name;
        }
    }

}
