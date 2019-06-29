package item;

import item.model.Item;

import java.util.Scanner;

public class CoinBag extends Item {
    private int amount;

    public CoinBag(int amount) {
        super("Moedas", 0);
        this.amount = amount;
    }

    @Override
    public int getWeight() {
        return (int) Math.ceil(((double)amount)/1000.0);
    }

    @Override
    public String getName() {
        return amount + " moedas";
    }

    @Override
    public int getPrice() {
        return -1;
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

    public boolean grabCoins(CoinBag coinBag, int charCurWeight, int maxWeight) {
        int newAmount = this.amount + coinBag.getAmount();
        int newWeight = (int) Math.ceil(((double)newAmount)/1000.0);

        if ((charCurWeight - getWeight() + newWeight) <= maxWeight) {
            this.amount = newAmount;
            return true;
        } else {
            newAmount = (maxWeight - charCurWeight) * 1000;
            coinBag.useCoins(newAmount - this.amount);
            this.amount = newAmount;
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
        return getName() + " (" + getWeight() + " kg)";
    }

}
