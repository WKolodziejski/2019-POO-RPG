package characters;

import item.CoinBag;
import item.model.Equipment;
import item.model.Item;
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

    protected Enemy(String name, int energy, int attack, int defense, int speed, int coins, int maxWeight, OnDie onDie){
        super(name, energy, attack, defense, speed, coins, maxWeight, onDie);

    }

    protected void unEquip(Equipment equip) {
        equip = equipped.remove(equip.getClass().getSimpleName());
        if(equip!=null){
            processBonus(equip.bonusType(), -equip.bonusAmount());
            if (energyCap > energy) {
                this.energy = energy - (energyCap - energy);
            }
        }
    }

    protected void damageItem(Equipment equip) {
        equip.takeAHit();
        if(equip.isBroken()){
            unEquip(equip);
        }
    }

    public boolean putItem(Item item) {
        if (item instanceof CoinBag){
            return (getCoinBag().grabCoins(((CoinBag) item), 0, getMaxWeight()));
        } else {
                inventory.add(item);
                if(item instanceof Equipment && !isEquipped((Equipment) item)) {
                    equip((Equipment) item);
                }
                return true;
        }
    }

    protected void equip(Equipment equip){
        unEquip(equip);
        equipped.put(equip.getClass().getSimpleName(), equip);
        processBonus(equip.bonusType(), equip.bonusAmount());
    }

}
