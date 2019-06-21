package personagens;

import item.*;

import java.util.Scanner;

public class Hero extends Character {
    private int maxWeight;
    private int actualWeight;
    private Item[] equipped;

    public Hero(String name, int energy) {
        super(name, energy, 0);
        this.maxWeight = 10;
        this.equipped = new Item[2];
    }

    public boolean putItem(Item item) {
        if(item instanceof CoinBag){
            return grabCoins((CoinBag) item);
        } else {
            if (increaseWeightBy(item.getWeight())) {
                inventory.put(item.getName(), item);
                return true;
            } else {
                return false;
            }
        }
    }

    public Item removeItem(String name) {
        Item item = inventory.get(name);
        if (item != null) {
            if(item instanceof CoinBag){
                return dropCoins();
            } else {
                inventory.remove(item);
                actualWeight -= item.getWeight();
                return item;
            }
        } else return null;
    }

    public int getActualWeight() {
        return actualWeight;
    }

    public void feed() {
        increaseEnergy();
        increaseEnergy();
    }

    public boolean increaseWeightBy(int weight) {
        if(this.actualWeight + weight <= this.maxWeight){
            this.actualWeight += weight;
            return true;
        }
        return false;
    }

    public boolean grabCoins(CoinBag pickedBag){  ///retorna se eu peguei todas as moedas da coinbag;
        CoinBag herosBag = this.getCoinBag();
        this.actualWeight -= herosBag.getWeight();

        int tmp = herosBag.getAmount() + pickedBag.getAmount();

        if(increaseWeightBy((int) Math.ceil(tmp/1000))){
            herosBag.setAmount(tmp);
            return true;
        } else {
            int maxAmount = (this.maxWeight - this.actualWeight) * 1000;
            herosBag.setAmount(maxAmount);
            increaseWeightBy(herosBag.getWeight());
            pickedBag.setAmount(maxAmount - tmp);
            return false;
        }
    }

    public boolean useCoins(int coins){
        CoinBag herosBag = this.getCoinBag();
        if(herosBag.getAmount() - coins < 0){
            return false;
        } else {
            this.actualWeight -= herosBag.getWeight();
            herosBag.setAmount(herosBag.getAmount() - coins);
            this.increaseWeightBy(herosBag.getWeight());
            return true;
        }
    }

    public CoinBag dropCoins(){
        System.out.println("Quantas moedas deseja dropar?");
        int amount = new Scanner(System.in).nextInt();
        if (amount > 0) {
            if (!useCoins(amount)) {
                amount = this.getCoinBag().getAmount();
                this.useCoins(amount);
            }
            return new CoinBag("Moedas de " + this.getName(), amount);
        } else {
            return null;
        }

    }

    public void equipItem(String equip){
        Item tbEquipped = this.inventory.get(equip);

        if(tbEquipped != null){
            if(tbEquipped instanceof Equipment){
                if (tbEquipped instanceof Weapon) {
                    this.equipped[0] = tbEquipped;
                } else {
                    if (tbEquipped instanceof Armor) {
                        this.equipped[1] = tbEquipped;
                    }
                }
                System.out.println(tbEquipped.getName() + " equipado com sucesso!");
            } else {
                System.out.println("Item não equipavel");
            }
        } else {
            System.out.println("Item inexistente");
        }

    };

}
