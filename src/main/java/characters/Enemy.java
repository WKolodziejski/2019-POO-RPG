package characters;

import item.CoinBag;
import item.Weapon;
import item.model.Armor;
import item.model.Equipment;
import item.model.Item;
import utils.Console;
import utils.Generator;
import utils.Item_Creator;

public class Enemy extends Character {

    public Enemy(OnDie onDie) {
        super(Generator.get().name(),
                Generator.get().number(10) + 1,
                Generator.get().number(10) + 1,
                Generator.get().number(10) + 1,
                Generator.get().number(10) + 1,
                Generator.get().number(100) + 1,
                Generator.get().number(10) + 1,
                onDie);

        for (int i = 0; i < Generator.get().number(3); i++) {
            Item item = Item_Creator.get().getRandom();
            putItem(item);
        }
    }

    protected Enemy(String name, int energy, int attack, int defense, int speed, int coins, int maxWeight, OnDie onDie) {
        super(name, energy, attack, defense, speed, coins, maxWeight, onDie);
    }

    @Override
    protected void unEquip(Equipment equip) {
        equip = equipped.remove(equip.getClass().getSimpleName());
        if (equip != null) {
            processBonus(equip.bonusType(), -equip.bonusAmount());
            if (energyCap > energy) {
                this.energy = energy - (energyCap - energy);
            }
            if (equip instanceof Armor) {
                processArmor(-((Armor) equip).getDefense());
            } else {
                if (equip instanceof Weapon) {
                    processWeapon(-((Weapon) equip).getDamage());
                }
            }
        }
    }

    @Override
    protected void damageItem(Equipment equip) {
        equip.takeAHit();
        if (equip.isBroken()) {
            unEquip(equip);
        }
    }

    @Override
    public boolean putItem(Item item) {
        if (item instanceof CoinBag) {
            return (getCoinBag().grabCoins(((CoinBag) item), 0, getMaxWeight()));
        } else {
            inventory.add(item);
            if (item instanceof Equipment && !isEquipped((Equipment) item)) {
                equip((Equipment) item);
            }
            return true;
        }
    }

    @Override
    protected void equip(Equipment equip) {
        Equipment unequip = equipped.get(equip.getClass().getSimpleName());
        if (unequip != equip) {
            processBonus(equip.bonusType(), equip.bonusAmount());
            if (unequip != null) {
                unEquip(unequip);
            }
            equipped.put(equip.getClass().getSimpleName(), equip);
            if (equip instanceof Armor) {
                processArmor(((Armor) equip).getDefense());
            } else {
                if (equip instanceof Weapon) {
                    processWeapon(((Weapon) equip).getDamage());
                }
            }
        }
    }

}
