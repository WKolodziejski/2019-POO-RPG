package utils;

import item.*;
import item.Armor_Parts.Arm_Piece;
import item.Armor_Parts.Chest_Piece;
import item.Armor_Parts.Helmet;
import item.Armor_Parts.Leg_Piece;
import item.model.Bonus;
import item.model.Item;
import java.util.Random;

public class Item_Creator implements ItemList {
    private Random r;
    private String[] levels;

    public Item_Creator() {
        this.r = new Random();
        this.levels = new String[]{"pessimo","ruim", "bom", "ótimo", "lendário"};
    }

    public Item getRandom(){
        return get(ItemList.Category.values()[r.nextInt(Category.values().length)], r.nextInt(levels.length), "" + Generator.get());
    }

    public Item get(ItemList.Category category, int lvl, String owner){
        switch (category){
            case CoinBag: return createCoinBag(lvl, owner);
            case Heal: return createHeal(lvl);
            case Ring: return createRing(lvl, owner);
            case Weapon: return createWeapon(lvl, owner);
            case Arm_Piece: return createArm_Piece(lvl, owner);
            case Chest_Piece: return createChest_Piece(lvl, owner);
            case Helmet: return createHelmet(lvl, owner);
            case Leg_Piece: return createLeg_Piece(lvl, owner);
            default: return null;
        }

    }

    private Item createCoinBag(int lvl, String owner){
        return new CoinBag("Moedas do " + owner, (r.nextInt(15) + 5)*(lvl+1));
    }

    private Item createHeal(int lvl){
        return new Heal("Poção " + levels[lvl], 1, (r.nextInt(2) + 1)*(lvl+1));
    }

    private Item createRing(int lvl, String owner) {
        return new Ring("Anel " + levels[lvl] + " de " + owner, Bonus.Type.values()[r.nextInt(3)], (r.nextInt(2) + 1)*(lvl+1));
    }

    private Item createWeapon(int lvl, String owner){
        return new Weapon("Arma " + levels[lvl] + "de " + owner, r.nextInt(3) + 1, (r.nextInt(1) + 1)*(lvl+1));
    }

    private Item createArm_Piece(int lvl, String owner){
        return new Arm_Piece("Manoplas " + levels[lvl] + " de " + owner, r.nextInt(2) + 1, (r.nextInt(1) + 1)*(lvl+1), (r.nextInt(1) + 1)*(lvl+1), Bonus.Type.values()[r.nextInt(3)]);
    }

    private Item createChest_Piece(int lvl, String owner){
        return new Chest_Piece("Peitoral " + levels[lvl] + " de " + owner, r.nextInt(4) + 1, (r.nextInt(2) + 1)*(lvl+1), (r.nextInt(2) + 1)*(lvl+1), Bonus.Type.values()[r.nextInt(3)]);
    }

    private Item createHelmet(int lvl, String owner){
        return new Helmet("Capacete " + levels[lvl] + " de " + owner, r.nextInt(2) + 1, (r.nextInt(1) + 1)*(lvl+1), (r.nextInt(1) + 1)*(lvl+1), Bonus.Type.values()[r.nextInt(3)]);
    }

    private Item createLeg_Piece(int lvl, String owner){
        return new Leg_Piece("Pernal " + levels[lvl] + " de " + owner, r.nextInt(3) + 1, (r.nextInt(1) + 1)*(lvl+1), (r.nextInt(1) + 1)*(lvl+1), Bonus.Type.values()[r.nextInt(3)]);
    }

}
