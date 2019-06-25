package utils;

import characters.Enemy;

import java.util.Random;

public class Enemy_Creator {
    private Random r;
    private static Enemy_Creator enemy_creator;

    private Enemy_Creator(){
        this.r = new Random();
    }

    public static synchronized Enemy_Creator get() {
        if (enemy_creator == null) {
            enemy_creator = new Enemy_Creator();
        }
        return enemy_creator;
    }

    public Enemy getRandom(){
        int equipAmount = r.nextInt(2);
        Enemy enemy = new Enemy(Generator.get().name());
        while (equipAmount>0){
            enemy.putItem(Item_Creator.get().getRandom());
        }
        return enemy;
    }
}
