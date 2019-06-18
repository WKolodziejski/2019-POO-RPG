package item;

import java.util.Random;
import java.util.Scanner;

public class Chest {
    //Inventario
    private int password;
    private int chances;
    private int base;
    private boolean opened;

    public Chest() {
        this.base = new Random().nextInt(6);
        this.password = base + new Random().nextInt(4);
        this.chances = 2;
    }

    public void open() {
        if (chances > 0) {
            if (!opened) {
                System.out.println("A senha é um número entre " + base + " e " + (base + 3));
                Scanner reader = new Scanner(System.in);
                int tryal = reader.nextInt();

                if (tryal == password) {
                    opened = true;
                    System.out.println("Você abriu o baú");
                } else {
                    chances--;
                    System.out.println("Você não conseguiu open o baú");
                }
            } else {
                //Inventário
            }
        } else {
            System.out.println("Você quebrou a fechadura e agora não é mais possível open o baú");
        }
    }

    public void take(/*Item*/) {
        if (opened) {

        } else {
            System.out.println("O baú está trancado");
        }
    }

}
