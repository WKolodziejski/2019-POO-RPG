package utils;

public enum Item_Category {
        Arm_Piece,
        Chest_Piece,
        Helmet,
        Leg_Piece,
        CoinBag,
        Heal,
        Ring,
        Weapon,
        Repair_Piece;

        public static Item_Category get(int i) {
                return values()[i];
        }

}