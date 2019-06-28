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
    public int getPrice() {
        return (type + 1) * 10;
    }

    @Override
    public String getDetails() {
        return getName() + " (" + getWeight() + " kg)";
    }

    public int getType() {
        return type;
    }

}
