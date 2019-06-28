package utils;

public enum Item_Category {
        Arm_Piece,
        Chest_Piece,
        Helmet,
        Leg_Piece,
        CoinBag,
        Heal,
        Ring,
        Weapon;

        public static Item_Category get(int i) {
                return values()[i];
        }

}