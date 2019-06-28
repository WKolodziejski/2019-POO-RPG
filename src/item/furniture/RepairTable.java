package item.furniture;

import item.RepairPiece;
import item.model.Equipment;
import item.model.Item;
import java.util.List;

public class RepairTable extends Furniture {

    public RepairTable() {

    }

    public void repairItem(Equipment equipment, List<Item> items) {
        RepairPiece repairPiece = null;
        for (Item item : items) {
            if (item instanceof RepairPiece) {
                repairPiece = (RepairPiece) item;
                break;
            }
        }
        if (repairPiece != null) {
            items.remove(repairPiece);
            equipment.setDurability(equipment.getLevel() * 10);
            System.out.println(equipment.getName() + " reparado");
        } else {
            System.out.println("Você não tem materiais para reparar esse item");
        }
    }

    public Item disassemblyItem(Equipment equipment) {
        return new RepairPiece(equipment.getLevel());
    }

}
