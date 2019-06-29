package item.furniture;

import item.RepairPiece;
import item.model.Equipment;

import java.util.List;

public class RepairTable extends Furniture {

    public RepairTable() {

    }

    public void repairItem(Equipment equipment, List<RepairPiece> pieces) {
        RepairPiece repairPiece = null;
        for (RepairPiece piece : pieces) {
            if (piece.getType() == equipment.getLevel()) {
                repairPiece = piece;
                break;
            }
        }

        if (repairPiece != null) {
            if (equipment.getDurability() < equipment.getLevel() * 5) {
                pieces.remove(repairPiece);
                equipment.lowerLevel();
                equipment.setDurability(equipment.getLevel() * 10);
                System.out.println(equipment.getName() + " reparado");
            } else {
                System.out.println("O item não está tão danificado");
            }
        } else {
            System.out.println("Você não tem materiais para reparar esse item");
        }
    }

    public RepairPiece disassemblyItem(Equipment equipment) {
        return new RepairPiece(equipment.getLevel());
    }

}
