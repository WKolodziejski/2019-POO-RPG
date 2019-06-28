package characters;

import item.CoinBag;
import item.Heal;
import item.Weapon;
import item.model.Armor;
import item.model.Bonus;
import item.model.Equipment;
import item.model.Item;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Character {
    private OnDie onDie;
    protected ArrayList<Item> inventory;
    private HashMap<String, Equipment> equipped;
    private int maxWeight;
    private String name;
    private int energy;
    private int energyCap;
    private int attack;
    private int defense;

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
        CoinBag coinBag = new CoinBag(coins);
        inventory.add(coinBag);
    }

    public int getEnergy() {
        return energy;
    }

    public String getName() {
        return name;
    }

    public void printLife() {
        System.out.println("#Vida de " + name + ": " + (energy < 0 ? 0 : energy));
    }

    public Item findItem(int i) {
        if (i < 0 || i >= inventory.size()) return null;
        else return inventory.get(i);
    }

    public void printInventory() {
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println(i + ": " + inventory.get(i).getDetails());
        }
    }

    public void printEquipped() {
        int i = 0;
        if(equipped.size()!=0){
            for(Equipment equipment : equipped.values()){
                System.out.println(i + ": "+ equipment.getDetails());
                i++;
            }
        } else {
            System.out.println("Nada equipado");
        }
    }

    public void increaseEnergy(Heal item) {
        int amount = item.getHealAmount();

        if (energy + amount <= energyCap) {
            energy += amount;
            inventory.remove(item);
            System.out.println("Recuperou " + amount + " pontos de vida");
            printInventory();
        } else {
            if (energy == energyCap) {
                System.out.println("Vida já está no máximo");
                //inventory.add(item);
            } else {
                System.out.println("Recuperou " + (energyCap - energy) + " pontos de vida");
                energy = energyCap;
                inventory.remove(item);
                printInventory();
            }
        }
        printLife();
    }

    public void decreaseEnergy(int amount) {
        energy -= amount;
        if (energy <= 0) {
            onDie.onDie(inventory);
        }
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

    public void removeItem(Item item) {
        if (item != null) {
            if (item instanceof Equipment) {
                if (isEquipped((Equipment) item)) {
                    unEquip((Equipment) item);
                }
            }
            inventory.remove(item);
            printInventory();
        }
    }

    public int dropCoins() {
        return getCoinBag().dropCoins();
    }

    public boolean putItem(Item item) {
        if (item instanceof CoinBag) {
            return getCoinBag().grabCoins(((CoinBag) item).getAmount(), getCurWeight(), maxWeight);
        } else {
            int newWeight = getCurWeight() + item.getWeight();

            if (newWeight <= maxWeight) {
                int index = findFirstEmptySlot();
                inventory.add(index, item);
                System.out.println(item.getName() + " adicionado ao inventário");
                if (item instanceof Equipment && equipped.get(item.getClass().getSimpleName()) == null) {
                    equipItem(index);
                }
                printInventory();
                return true;
            } else {
                System.out.println("Sem espaço no inventário");
                return false;
            }
        }
    }

    private int findFirstEmptySlot() {
        int i;
        for (i = 0; i < inventory.size(); i++){
            if (inventory.get(i )== null){
                return i;
            }
        }
        return i;
    }

    private CoinBag getCoinBag() {
        for (Item item : inventory) {
            if (item instanceof CoinBag) {
                return (CoinBag) item;
            }
        }
        return null;
    }

    public boolean useCoins(int amount) {
        return getCoinBag().useCoins(amount);
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

        if( tbEquipped != null){
            if (tbEquipped instanceof Equipment){
                Equipment alreadyIn = equipped.get(tbEquipped.getClass().getSimpleName());
                if (alreadyIn != null) {
                    if (tbEquipped instanceof Armor) {
                        if (((Armor)alreadyIn).bonusType() == Bonus.Type.WEIGHT) {
                            if (getCurWeight() > (getMaxWeight() - ((Armor)alreadyIn).bonusAmount() + (((Armor) tbEquipped).bonusType() == Bonus.Type.WEIGHT ? ((Armor) tbEquipped).bonusAmount() : 0))) {
                                System.out.println("Não foi possível equipar " + tbEquipped.getName() + ", pois desequipando " + alreadyIn.getName() + " seu peso além da sua capacisade");
                                return;
                            }
                        }
                    }
                    unEquip(alreadyIn);
                }
                equipped.put(tbEquipped.getClass().getSimpleName(), (Equipment) tbEquipped);
                System.out.println(tbEquipped.getName() + " equipado com sucesso!");
            } else {
                System.out.println("Item não equipavel");
            }
        } else {
            System.out.println("Item inexistente");
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

    private void damageItem(Equipment equip) {
        equip.takeAHit();
        if(equip.isBroken()){
            System.out.println(equip.getName() + " foi quebrado");
            unEquip(equip);
        }
    }

     private void unEquip(Equipment equip) {
        System.out.println(equip.getName() + " foi desequipado");
        equipped.remove(equip.getClass().getSimpleName());
     }

     public void unEquip(int index) {
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
        System.out.println("Item não encontrado");
    }

    public int getCurWeight() {
        int weight = 0;
        for (Item item : inventory) {
            weight += item.getWeight();
        }
        return weight;
    }

     public boolean isOverweight() {
        return getCurWeight() > getMaxWeight();
     }

     private boolean isEquipped(Equipment equip) {
        return equipped.get(equip.getClass().getSimpleName()) != null;
     }

}
