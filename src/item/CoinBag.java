package item;

import item.model.Item;
import java.util.Scanner;

public class CoinBag extends Item {
    private int amount;

    public CoinBag(int amount) {
        super("Moedas", (int) Math.ceil(amount / 1000));
        this.amount = amount;
    }

    @Override
    public int getWeight() {
        return (int) Math.ceil(this.amount/1000);
    }

    @Override
    public String getName() {
        return amount + " moedas";
    }

    @Override
    public boolean isConsumable() {
        return false;
    }

    @Override
    public int getPrice() {
        return -1;
    }

    @Override
    public String getKey() {
        return "Moedas";
    }

    public int dropCoins() {
        System.out.println("Quantas moedas deseja dropar?");

        int amount = new Scanner(System.in).nextInt();

        if (amount > 0) {
            if (this.amount < amount) {
                amount = this.amount;
            }

            this.amount -= amount;

            System.out.println("Dropou " + amount + " moedas");

            return amount;

        } else {
            System.out.println("Tá de brincadera com o Zoio?");
            return 0;
        }
    }

    public boolean grabCoins(int amount, int actualWeight, int maxWeight) {
        int newAmount = this.amount + amount;
        int newWeight = (int) Math.ceil(newAmount / 1000);

        if ((actualWeight - getWeight() + newWeight) <= maxWeight) {
            this.amount += amount;
            System.out.println("Pegou " + amount + " moedas");
            return true;

        } else {
            System.out.println("Sem espaço para todas moedas");
            return false;
        }
    }

    public boolean useCoins(int amount) {
        if (amount > 0) {
            if (amount > this.amount) {
                System.out.println("Saldo insuficiente");
                return false;
            } else {
                this.amount -= amount;
                System.out.println("Gastou " + amount + " moedas");
                return true;
            }
        } else {
            System.out.println("Tá de brincadera com o Zoio?");
            return false;
        }
    }

    public int getAmount() {
        return amount;
    }

    public String getDetails(){
        return getName() + " - " + getWeight() + "kg";
    }

}
