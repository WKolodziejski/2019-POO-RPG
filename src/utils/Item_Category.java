package utils;

public enum Item_Category {
        ARM,
        CHEST,
        HELMET,
        LEG,
        COIN,
        HEAL,
        RING,
        WEAPON,
        REPAIR;

        public static Item_Category get(int i) {
                return values()[i];
        }

}