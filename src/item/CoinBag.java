package item;

import item.model.Item;

public class CoinBag extends Item {
    private int amount;

    public CoinBag(String name, int amount) {
        super(name, (int) Math.ceil(amount/1000));
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.setWeight((int) Math.ceil(this.amount/1000));
    }

    @Override
    public String getName() {
        return amount + " moedas";
    }

    @Override
    public boolean isConsumable() {
        return false;
    }

}
