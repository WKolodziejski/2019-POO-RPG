/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personagens;

import utils.Generator;

import java.io.Serializable;

/**
 *
 * @author Aluno
 */
public class Vilao extends Personagem {
    
    public Vilao(int energia) {
        super(Generator.generateName(), energia);
    }
    
}
