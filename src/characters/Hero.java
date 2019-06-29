package characters;

import item.*;
import item.model.Equipment;
import item.model.Item;
import utils.Console;

import java.util.ArrayList;
import java.util.List;

public class Hero extends Character {

    public Hero(OnDie onDie) {
        super("Cleytinho", 10, 20, 10, 5,1000, 10, onDie);

        inventory.add(new Heal("Poção", 1, 2));
    }
    
    public int getCurWeight() {
        int weight = 0;
        for (Item item : inventory) {
            weight += item.getWeight();
        }
        return weight;
    }

    @Override
    public void printLife() {
        Console.print(Console.BLACK, "#Vida de " + getName() + ": " + (getEnergy() < 0 ? 0 : getEnergy()) + " de " + getEnergyCap());
    }

    public void printInventory() {
        Console.print(Console.BLACK_UNDERLINED, "------INVENTÁRIO------");
        for (int i = 0; i < inventory.size(); i++) {
            Console.print(Console.BLACK, i + ": " + inventory.get(i).getDetails());
        }
    }

    public void printEquipped() {
        Console.print(Console.BLACK, "------ITENS EQUIPADOS------");
        int i = 0;
        if(equipped.size()!=0){
            for(Equipment equipment : equipped.values()){
                Console.print(Console.BLACK, i + ": "+ equipment.getDetails());
                i++;
            }
        } else {
            Console.print(Console.BLACK, "Nada equipado");
        }
    }

    public void increaseEnergy(Heal item) {
        int amount = item.getHealAmount();

        if (energy + amount <= energyCap) {
            energy += amount;
            inventory.remove(item);
            Console.print(Console.BLACK, "Recuperou " + amount + " pontos de vida");
            printInventory();
        } else {
            if (energy ==  energyCap) {
                Console.print(Console.BLACK, "Vida já está no máximo");
                //inventory.add(item);
            } else {
                Console.print(Console.BLACK, "Recuperou " + (energyCap - energy) + " pontos de vida");
                energy = energyCap;
                inventory.remove(item);
                printInventory();
            }
        }
        printLife();
    }

    public List<RepairPiece> getPieces() {
        List<RepairPiece> pieces = new ArrayList<>();
        for (Item item : inventory) {
            if (item instanceof RepairPiece) {
                pieces.add((RepairPiece) item);
                break;
            }
        }
        return pieces;
    }

    public int dropCoins() {
        return getCoinBag().dropCoins();
    }

    public boolean useCoins(int amount) {
        return getCoinBag().useCoins(amount);
    }

    public void unequipItem(int index) {
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
        Console.print(Console.BLACK, "Item não encontrado");
    }

    protected void damageItem(Equipment equip) {
        equip.takeAHit();
        if(equip.isBroken()){
            Console.print(Console.BLACK, equip.getName() + " foi quebrado");
            unEquip(equip);
        }
    }

    public boolean putItem(Item item) {
        if (item instanceof CoinBag && getCoinBag()!=null) {
            if(getCoinBag().grabCoins(((CoinBag) item), getCurWeight(), getMaxWeight())){
                Console.print(Console.BLACK, "Pegou " + ((CoinBag) item).getAmount() + " moedas");
                return true;
            } else {
                Console.print(Console.BLACK, "Sem espaço para todas moedas");
                return false;
            }
        } else {

            if (getCurWeight() + item.getWeight() <= getMaxWeight()) {
                inventory.add(item);
                Console.print(Console.BLACK, item.getName() + " adicionado ao inventário");
                if (item instanceof Equipment && !isEquipped((Equipment) item)) {
                    equip((Equipment) item);
                }
                printInventory();
                return true;
            } else {
                Console.print(Console.BLACK, "Sem espaço no inventário");
                return false;
            }
        }
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

    public void equipItem(int index){
        Item tbEquipped = inventory.get(index);

        if(tbEquipped != null){
            if (tbEquipped instanceof Equipment){
                equip((Equipment) tbEquipped);
            } else {
                Console.print(Console.BLACK, "Item não equipavel");
            }
        } else {
            Console.print(Console.BLACK, "Item inexistente");
        }
    }

    protected void equip(Equipment equip){
        Equipment unequip = equipped.get(equip.getClass().getSimpleName());
        if(unequip == equip){
            Console.print(Console.BLACK, equip.getName() + " já equipado");
        } else {
            equipped.put(equip.getClass().getSimpleName(), equip);
            processBonus(equip.bonusType(), equip.bonusAmount());
            if (unequip != null) {
                processBonus(unequip.bonusType(), -unequip.bonusAmount());
                Console.print(Console.BLACK, unequip.getName() + " foi desequipado");
            }
            Console.print(Console.BLACK, equip.getName() + " equipado com sucesso!");
        }
    }

    protected void unEquip(Equipment equip) {
        equip = equipped.remove(equip.getClass().getSimpleName());
        if(equip!=null){
            Console.print(Console.BLACK, equip.getName() + " foi desequipado");
            processBonus(equip.bonusType(), -equip.bonusAmount());
            if (energyCap > energy) {
                this.energy = energy - (energyCap - energy);
            }
        }
    }

    public boolean isOverweight() {
        return getCurWeight() > getMaxWeight();
    }

}
