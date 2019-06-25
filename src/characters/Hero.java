package characters;

import item.*;
import item.model.Armor;
import item.model.Equipment;
import item.model.Item;

public class Hero extends Character {
    private Item[] equipped;
    private Weapon weapon;
    private Armor armor;

    public Hero(OnDie onDie) {
        super("Cleytinho", 10, 20, 10, 0, 10, onDie);
        this.equipped = new Item[2];

        putItem(new Heal("Poção", 1, 2));
    }

    public void equipItem(String equip){
        Item tbEquipped = this.inventory.get(equip);

        if(tbEquipped != null){
            if(tbEquipped instanceof Equipment){
                if (tbEquipped instanceof Weapon) {
                    this.equipped[0] = tbEquipped;
                } else {
                    if (tbEquipped instanceof Armor) {
                        this.equipped[1] = tbEquipped;
                    }
                }
                System.out.println(tbEquipped.getName() + " equipado com sucesso!");
            } else {
                System.out.println("Item não equipavel");
            }
        } else {
            System.out.println("Item inexistente");
        }
    }

}
