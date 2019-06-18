package utils;

import item.Chest;
import personagens.Hero;
import personagens.Character;

public class BattleSimulator {
    
    public static void main(String[] args) {
        //fight(new Hero("Hero", 10, 0), new Enemy(10));
        Chest chest = new Chest();
        chest.open();
        chest.open();
        chest.open();
    }
    
    public static void lutar(Hero hero, Character adversario) {
        while (hero.getEnergy() > 0 && adversario.getEnergy() > 0) {
            hero.fight(adversario);
            hero.print();
            adversario.print();
        }
        
        System.out.println("Vencedor da batalha: "
                + (hero.getEnergy() > 0 ? hero.getName() : adversario.getName()));
    }
    
}
