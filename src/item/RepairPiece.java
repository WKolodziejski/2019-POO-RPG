package item;

import item.model.Item;
import utils.Generator;

public class RepairPiece extends Item {
    private int type;

    public RepairPiece(int type) {
        super("Pedaço de " + Generator.get().getMaterialByLevel(type), type);
        this.type = type;
    }

    @Override
    public boolean isConsumable() {
        return false;
    }

    @Override
    public int getPrice() {
        return (type + 1) * 10;
    }

    public int getType() {
        return type;
    }

}
