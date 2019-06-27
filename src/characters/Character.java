package characters;

import item.Armor_Parts.Arm_Piece;
import item.Armor_Parts.Chest_Piece;
import item.Armor_Parts.Helmet;
import item.Armor_Parts.Leg_Piece;
import item.CoinBag;
import item.Ring;
import item.Weapon;
import item.model.Armor;
import item.model.Bonus;
import item.model.Equipment;
import item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Character {
    private OnDie onDie;
    protected ArrayList<Item> inventory;
    private int curWeight;
    private int maxWeight;
    private String name;
    private int energy;
    private int energyCap;
    private int attack;
    private int defense;
    private HashMap<String, Equipment> equipped;

    public Character(String name, int energy, int attack, int defense, int coins, int maxWeight, OnDie onDie) {
        this.inventory = new ArrayList<>();
        this.name = name;
        this.energy = energy;
        this.attack = attack;
        this.defense = defense;
        this.energyCap = energy;
        this.maxWeight = maxWeight;
        this.onDie = onDie;
        this.equipped = new HashMap<>();
        putItem(new CoinBag("Moedas do " + name, coins));
    }

    public int getEnergy() {
        return energy;
    }

    public String getName() {
        return name;
    }

    public boolean increaseEnergy(int amount) {
        if (energy + amount <= energyCap) {
            energy += amount;
            System.out.println("Recuperou " + amount + " pontos de vida");
        } else {
            if (energy == energyCap) {
                System.out.println("Vida já está no máximo");
                return false;
            } else {
                System.out.println("Recuperou " + (energyCap - energy) + " pontos de vida");
                energy = energyCap;
            }
        }
        return true;
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
        int bonus = getEquippedWeaponDamage();

        for (Item item : equipped.values()) {
            if (item instanceof Bonus) {
                if (((Bonus) item).bonusType() == Bonus.Type.ATTACK) {
                    bonus += ((Bonus) item).bonusAmount();
                }
            }
        }

        return attack + bonus;
    }

    private int getEquippedWeaponDamage(){
        Weapon weapon = (Weapon)equipped.get(Weapon.class.getSimpleName());
        return weapon!= null ? weapon.getDamage() : 0;
    }

    public int getDefense() {
        int bonus = 0;

        for (Item item : equipped.values()) {
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

        for (Item item : inventory) {
            if (item instanceof Bonus) {
                if (((Bonus) item).bonusType() == Bonus.Type.WEIGHT) {
                    bonus += ((Bonus) item).bonusAmount();
                }
            }
        }

        return maxWeight + bonus;
    }

    public Item removeItem(String name){
        int index = getIndexByName(name);
        if(index==-1){
            return null;
        } else {
            return removeItem(index);
        }
    }

    private int getIndexByName(String name){
        Item item;
        for(int i = 0; i < inventory.size(); i++){
            item = inventory.get(i);
            if(item!=null && item.getName().equals(name)){
                return i;
            }
        }
        return -1;
    }

    public Item removeItem(int index) {
        Item item = inventory.get(index);
        if (item != null) {
            if(item instanceof CoinBag){
                return dropCoins();
            } else {
                if(item instanceof Equipment){
                    if(isEquipped((Equipment) item)){
                        unEquip((Equipment) item);
                    }
                }
                inventory.add(index, null);
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
                int index = findFirstEmptySlot();
                inventory.add(index, item);
                if (item instanceof Equipment && equipped.get(item.getClass().getSimpleName()) == null) {
                    equipItem(index);
                }
                System.out.println(item.getName() + " adicionado ao inventário");
                return true;
            } else {
                System.out.println("Sem espaço no inventário");
                return false;
            }
        }
    }

    private int findFirstEmptySlot(){
        int i;
        for(i = 0; i < inventory.size(); i++){
            if(inventory.get(i)==null){
                return i;
            }
        }
        return i;
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

    public CoinBag getCoinBag() {
        return (CoinBag) inventory.get(getIndexByName("Moedas"));
    }

    public void printInventory() {
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i)!=null){
                System.out.println(i + ": "+ inventory.get(i).getName());
            }
        }
    }

    public void printEquipped() {
        int i = 0;
        for(Equipment equipment : equipped.values()){
            System.out.println(i + ": "+ equipment.getName());
            i++;
        }
    }

    public String getKey() {
        if (name.contains(" ")) {
            return name.substring(0, name.indexOf(" "));
        } else {
            return name;
        }
    }

    public void equipItem(int index){
        Item tbEquipped = inventory.get(index);

        if(tbEquipped != null){
            if(tbEquipped instanceof Equipment){
                if(tbEquipped instanceof Armor){
                    Armor alreadyIn = (Armor) equipped.get(tbEquipped.getClass().getSimpleName());
                    if (alreadyIn.bonusType() == Bonus.Type.WEIGHT) {
                        if (this.curWeight > (this.getMaxWeight() - alreadyIn.bonusAmount() + (((Armor) tbEquipped).bonusType() == Bonus.Type.WEIGHT ? ((Armor) tbEquipped).bonusAmount() : 0))) {
                            System.out.println("Não foi possível equipar " + tbEquipped.getName() + ", pois desequipando " + alreadyIn.getName() + " seu peso além da sua capacisade");
                            return;
                        }
                    }
                }
                System.out.println(equipped.put(tbEquipped.getClass().getSimpleName(), (Equipment) tbEquipped).getName() + " desequipado") ;
                System.out.println(tbEquipped.getName() + " equipado com sucesso!");
            } else {
                System.out.println("Item não equipavel");
            }
        } else {
            System.out.println("Item inexistente");
        }
    }

    public void takeDamage(int energyDecrease){
        this.decreaseEnergy(energyDecrease);
        this.damageEquipped();
    }

    public void damageEquipped(){
        for (Equipment equip : equipped.values()) {
            damageItem(equip);
        }
    }

    public void damageEquippedWeapon(){
        damageItem(equipped.get(Weapon.class.getSimpleName()));
    }

    public void damageItem(Equipment equip){
        equip.takeAHit();
        if(equip.isBroken()){
            System.out.println(equip.getName()+ " foi quebrado");
            unEquip(equip);
        }
    }

     public void unEquip(Equipment equip){
         System.out.println(equip.getName()+ " foi desequipado");
        equipped.remove(equip.getClass().getSimpleName());
     }

     public void unEquip(int index){
        if(index < equipped.values().size()){
            int i = 0;
            for(Equipment equipment : equipped.values()) {
                if (i < index) {
                    i++;
                } else {
                    unEquip(equipment);
                    return;
                }
            }
        }
    }

     public boolean isOverweight(){
        return curWeight > getMaxWeight();
     }

     public boolean isEquipped(Equipment equip){
        return equipped.get(equip.getClass().getSimpleName()) != null;
     }
}
