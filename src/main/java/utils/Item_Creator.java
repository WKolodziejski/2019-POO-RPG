package utils;

import item.*;
import item.Armor_Parts.Arm_Piece;
import item.Armor_Parts.Chest_Piece;
import item.Armor_Parts.Helmet;
import item.Armor_Parts.Leg_Piece;
import item.model.Bonus;
import item.model.Item;
import java.util.Random;

public class Item_Creator {
    private Random r;
    private Item_Category[] categories;
    private Bonus.Type[] bonus;
    private Level[] levels;
    private static Item_Creator item_creator;

    private Item_Creator() {
        this.r = new Random();
        this.categories = Item_Category.values();
        this.bonus = Bonus.Type.values();
        this.levels = Level.values();
    }

    public static synchronized Item_Creator get() {
        if (item_creator == null) {
            item_creator = new Item_Creator();
        }
        return item_creator;
    }

    public Item getRandom(){
        return getSpec(categories[r.nextInt(categories.length)], r.nextInt(Generator.get().getMaterialAmount()));
    }

    public Item getSpec(Item_Category category, int lvl) {
        lvl++;
        switch (category){
            case COIN: return createCoinBag(lvl);
            case HEAL: return createHeal(lvl);
            case RING: return createRing(lvl);
            case WEAPON: return createWeapon(lvl);
            case ARM: return createArm_Piece(lvl);
            case CHEST: return createChest_Piece(lvl);
            case HELMET: return createHelmet(lvl);
            case LEG: return createLeg_Piece(lvl);
            case REPAIR: return createRepaiPiece(lvl);
            default: return null;
        }

    }

    private Item createRepaiPiece(int lvl) {
        return new RepairPiece(lvl);
    }

    private Item createCoinBag(int lvl){
        return new CoinBag((r.nextInt(15) + 5)*(lvl));
    }

    private Item createHeal(int lvl){
        return new Heal("Poção " + levels[(lvl<levels.length ? lvl : levels.length-1)], 1, (r.nextInt(2) + 1)*(lvl));
    }

    private Item createRing(int lvl) {
        return new Ring("Anel de " + Generator.get().getMaterialByLevel(lvl), bonus[r.nextInt(bonus.length)], (r.nextInt(2) + 1)*(lvl));
    }

    private Item createWeapon(int lvl){
        return new Weapon(Generator.get().weapon() + " de " + Generator.get().getMaterialByLevel(lvl), r.nextInt(3) + 1, (r.nextInt(1) + 1)*(lvl), (r.nextInt(1) + 1)*(lvl), bonus[r.nextInt(bonus.length)]);
    }

    private Item createArm_Piece(int lvl){
        return new Arm_Piece("Manoplas de " + Generator.get().getMaterialByLevel(lvl), r.nextInt(2) + 1, (r.nextInt(1) + 1)*(lvl), (r.nextInt(1) + 1)*(lvl), bonus[r.nextInt(bonus.length)]);
    }

    private Item createChest_Piece(int lvl){
        return new Chest_Piece("Peitoral de " + Generator.get().getMaterialByLevel(lvl), r.nextInt(4) + 1, (r.nextInt(2) + 1)*(lvl), (r.nextInt(2) + 1)*(lvl), bonus[r.nextInt(bonus.length)]);
    }

    private Item createHelmet(int lvl){
        return new Helmet("Capacete de " + Generator.get().getMaterialByLevel(lvl), r.nextInt(2) + 1, (r.nextInt(1) + 1)*(lvl), (r.nextInt(1) + 1)*(lvl), bonus[r.nextInt(bonus.length)]);
    }

    private Item createLeg_Piece(int lvl){
        return new Leg_Piece("Pernal de " + Generator.get().getMaterialByLevel(lvl), r.nextInt(3) + 1, (r.nextInt(1) + 1)*(lvl), (r.nextInt(1) + 1)*(lvl), bonus[r.nextInt(bonus.length)]);
    }

}
