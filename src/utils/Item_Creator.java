package utils;

import item.Armor_Parts.Arm_Piece;
import item.Armor_Parts.Chest_Piece;
import item.Armor_Parts.Helmet;
import item.Armor_Parts.Leg_Piece;
import item.CoinBag;
import item.Heal;
import item.Ring;
import item.Weapon;
import item.model.Bonus;
import item.model.Item;
import java.util.Random;

public class Item_Creator{
    private Random r;
    private String[] levels;

    public Item_Creator() {
        this.r = new Random();
        this.levels = new String[]{"pessimo","ruim", "bom", "ótimo", "lendário"};
    }

    public Item getRandom(){
        return get(r.nextInt(8), r.nextInt(5), "" + Generator.get());
    }

    public Item get(int category, int lvl, String owner){
        switch (category){
            case 0: return createCoinBag(lvl, owner);
            case 1: return createHeal(lvl);
            case 2: return createRing(lvl, owner);
            case 3: return createWeapon(lvl, owner);
            case 4: return createArm_Piece(lvl, owner);
            case 5: return createChest_Piece(lvl, owner);
            case 6: return createHelmet(lvl, owner);
            case 7: return createLeg_Piece(lvl, owner);
            default: return null;
        }

    }

    public Item createCoinBag(int lvl, String owner){
        return new CoinBag("Moedas do " + owner, (r.nextInt(15) + 5)*(lvl+1));
    }

    public Item createHeal(int lvl){
        return new Heal("Poção " + levels[lvl], 1, (r.nextInt(2) + 1)*(lvl+1));
    }

    public Item createRing(int lvl, String owner) {
        return new Ring("Anel " + levels[lvl] + " de " + owner, Bonus.Type.values()[r.nextInt(3)], (r.nextInt(2) + 1)*(lvl+1));
    }

    public Item createWeapon(int lvl, String owner){
        return new Weapon("Arma " + levels[lvl] + "de " + owner, r.nextInt(3) + 1, (r.nextInt(1) + 1)*(lvl+1));
    }

    public Item createArm_Piece(int lvl, String owner){
        return new Arm_Piece("Manoplas " + levels[lvl] + " de " + owner, r.nextInt(2) + 1, (r.nextInt(1) + 1)*(lvl+1), (r.nextInt(1) + 1)*(lvl+1), Bonus.Type.values()[r.nextInt(3)]);
    }

    public Item createChest_Piece(int lvl, String owner){
        return new Chest_Piece("Peitoral " + levels[lvl] + " de " + owner, r.nextInt(4) + 1, (r.nextInt(2) + 1)*(lvl+1), (r.nextInt(2) + 1)*(lvl+1), Bonus.Type.values()[r.nextInt(3)]);
    }

    public Item createHelmet(int lvl, String owner){
        return new Helmet("Capacete " + levels[lvl] + " de " + owner, r.nextInt(2) + 1, (r.nextInt(1) + 1)*(lvl+1), (r.nextInt(1) + 1)*(lvl+1), Bonus.Type.values()[r.nextInt(3)]);
    }

    public Item createLeg_Piece(int lvl, String owner){
        return new Leg_Piece("Pernal " + levels[lvl] + " de " + owner, r.nextInt(3) + 1, (r.nextInt(1) + 1)*(lvl+1), (r.nextInt(1) + 1)*(lvl+1), Bonus.Type.values()[r.nextInt(3)]);
    }

}
