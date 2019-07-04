package characters;

import item.CoinBag;
import item.Weapon;
import item.model.Bonus;
import item.model.Equipment;
import item.model.Item;
import mechanics.Luck;
import utils.Console;
import utils.Generator;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Character {
    private OnDie onDie;
    protected ArrayList<Item> inventory;
    protected HashMap<String, Equipment> equipped;
    private int maxWeight;
    private String name;
    protected int energy;
    protected int energyCap;
    private int attack;
    private int defense;
    private int speed;
    private boolean hasDied;

    public Character(String name, int energy, int attack, int defense, int speed, int coins, int maxWeight, OnDie onDie) {
        this.inventory = new ArrayList<>();
        this.name = name;
        this.energy = energy;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.energyCap = energy;
        this.maxWeight = maxWeight;
        this.onDie = onDie;
        this.equipped = new HashMap<>();
        this.hasDied = false;

        inventory.add(new CoinBag(coins));
    }

    abstract public boolean putItem(Item item);

    abstract protected void damageItem(Equipment equip);

    abstract protected void unEquip(Equipment equip);

    abstract protected void equip(Equipment equip);

    public int getEnergy() {
        return energy;
    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack + getEquippedWeaponDamage();
    }

    public int getLuck(){
        return Luck.d20() + getSpeed();
    }

    public int getSpeed(){
        return speed;
    }

    protected int getEnergyCap(){
        return energyCap;
    }

    protected int getEquippedWeaponDamage(){
        Weapon weapon = (Weapon)equipped.get(Weapon.class.getSimpleName());
        return weapon != null ? weapon.getDamage() : 0;
    }

    public int getDefense() {
        return defense;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public String getKey() {
        if (name.contains(" ")) {
            return name.substring(0, name.indexOf(" "));
        } else {
            return name;
        }
    }

    public void printLife() {
        if (getEnergy() > 0) {
            Console.print(Console.GREEN_BOLD, "#Vida de " + getName() + ": " + (getEnergy() < 0 ? 0 : getEnergy()) + " de " + getEnergyCap());
        }
    }

    private void decreaseEnergy(int amount) {
        energy -= amount;
        if (energy <= 0 && !hasDied) {
            hasDied = true;
            onDie.onDie(inventory, this);
        }
    }

    public Item findItem(int i) {
        if (i < 0 || i >= inventory.size()) return null;
        else return inventory.get(i);
    }

    protected CoinBag getCoinBag() {
        for (Item item : inventory) {
            if (item instanceof CoinBag) {
                return (CoinBag) item;
            }
        }
        return null;
    }

    public void printEquipped() {
        Console.print(Console.BLACK_UNDERLINED, "------ITENS EQUIPADOS------");
        int i = 0;
        if (equipped.size()!=0) {
            for (Equipment equipment : equipped.values()) {
                Console.print(Console.BLUE_BRIGHT, i + ": " + equipment.getDetails());
                i++;
            }
        } else {
            Console.print(Console.BLACK_BOLD, "Nada equipado");
        }
    }

    public void takeDamage(int energyDecrease) {
        this.decreaseEnergy(energyDecrease);
        this.damageEquipped();
    }

    private void damageEquipped() {
        for (Equipment equip : equipped.values()) {
            if(equip!=null) {
                damageItem(equip);
            }
        }
    }

    public void damageEquippedWeapon() {
        Equipment equipment = equipped.get(Weapon.class.getSimpleName());
        if(equipment!=null) {
            damageItem(equipment);
        }
    }

     protected void processBonus(Bonus.Type bonus, int amount){
        switch (bonus){
            case LIFE: energyCap += amount;
                break;
            case ATTACK: attack += amount;
                break;
            case WEIGHT: maxWeight += amount;
                break;
            case DEFENSE: defense += amount;
                break;
            case SPEED: speed += amount;
                break;
                default: break;
        }
     }

    protected boolean isEquipped(Equipment equip) {
        return equipped.get(equip.getClass().getSimpleName()) != null;
    }

}
