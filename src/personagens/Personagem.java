/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personagens;

import java.io.Serializable;
import java.util.Random;

public abstract class Personagem implements Serializable {
    private String nome;
    private int energia;

    public Personagem(String nome, int energia) {
        this.nome = nome;
        this.energia = energia;
    }
   
    public int getEnergia() {
        return energia;
    }

    public String getNome() {
        return nome;
    }
    
    public void incremento() {
        energia += energia < 10 ? 1 : 0;
    }
    
    public void decremento() {
        energia -= 1;
        if (energia <= 0) {
            //morreu
        }
    }
    
    public int sorte() {
        return new Random().nextInt(6) + 1;
    }
    
    public void imprimir() {
        System.out.println("#####################");
        System.out.println("# Nome: " + nome);
        System.out.println("# Energia: " + energia);
        System.out.println("#####################");
    }
    
}
