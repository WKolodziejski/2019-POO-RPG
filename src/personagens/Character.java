package personagens;

import java.util.Random;

public abstract class Character {
    private String name;
    private int energy;

    public Character(String name, int energy) {
        this.name = name;
        this.energy = energy;
    }
   
    public int getEnergy() {
        return energy;
    }

    public String getName() {
        return name;
    }
    
    public void increaseEnergy() {
        energy += energy < 10 ? 1 : 0;
    }
    
    public void decreaseEnergy() {
        energy -= 1;
        if (energy <= 0) {
            //morreu
        }
    }
    
    public int luck() {
        return new Random().nextInt(6) + 1;
    }
    
    public void print() {
        System.out.println("#####################");
        System.out.println("# Nome: " + name);
        System.out.println("# Energia: " + energy);
        System.out.println("#####################");
    }
    
}
