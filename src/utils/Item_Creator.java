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

    public static Item getRandom(){
        return get(new Random().nextInt(8));
    }

    public static Item get(int category){
        switch (category){
            case 0: return createCoinBag();
            case 1: return createHeal();
            case 2: return createRing();
            case 3: return createWeapon();
            case 4: return createArm_Piece();
            case 5: return createChest_Piece();
            case 6: return createHelmet();
            case 7: return createLeg_Piece();
            default: return null;
        }

    }

    public static Item createCoinBag(){
        return new CoinBag("Saco de moedas velho", new Random().nextInt(95) + 5);
    }

    public static Item createHeal(){
        return new Heal("Poção velha", 1, new Random().nextInt(4) + 1);
    }

    public static Item createRing() {
        Random r = new Random();
        return new Ring("Anel de " + Generator.get(), Bonus.Type.values()[r.nextInt(3)], r.nextInt(4) + 1);
    }

    public static Item createWeapon(){
        Random r = new Random();
        return new Weapon("Arma de " + Generator.get(), r.nextInt(3) + 1, r.nextInt(4) + 1);
    }

    public static Item createArm_Piece(){
        Random r = new Random();
        return new Arm_Piece("Manoplas de " + Generator.get(), r.nextInt(2) + 1, r.nextInt(4) + 1, r.nextInt(4) + 1, Bonus.Type.values()[r.nextInt(3)]);
    }

    public static Item createChest_Piece(){
        Random r = new Random();
        return new Chest_Piece("Peitoral de " + Generator.get(), r.nextInt(4) + 1, r.nextInt(9) + 1, r.nextInt(9) + 1, Bonus.Type.values()[r.nextInt(3)]);
    }

    public static Item createHelmet(){
        Random r = new Random();
        return new Helmet("Capacete de " + Generator.get(), r.nextInt(2) + 1, r.nextInt(4) + 1, r.nextInt(4) + 1, Bonus.Type.values()[r.nextInt(3)]);
    }

    public static Item createLeg_Piece(){
        Random r = new Random();
        return new Leg_Piece("Pernal de " + Generator.get(), r.nextInt(3) + 1, r.nextInt(4) + 1, r.nextInt(4) + 1, Bonus.Type.values()[r.nextInt(3)]);
    }

}
