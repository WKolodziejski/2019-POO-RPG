/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

import item.Item;
import utils.Generator;

import java.util.Random;

public class Enemy extends Character {
    
    public Enemy(String name, OnDie onDie) {
        super(name, new Random().nextInt(10) + 1, new Random().nextInt(10) + 1, new Random().nextInt(10) + 1, new Random().nextInt(101), onDie);
    }

    public void checkInventory() {
        inventory.forEach((s, item) -> {
            System.out.println(s);
        });
    }

    public Item takeItem(String key) {
        if (getEnergy() <= 0) {
            return inventory.get(key);
        } else return null;
    }
    
}
