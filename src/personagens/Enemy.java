/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personagens;

import utils.Generator;

import java.util.Random;

public class Enemy extends Character {
    
    public Enemy() {
        super(Generator.generateName(), new Random().nextInt(10) + 1, new Random().nextInt(101));
    }
    
}
