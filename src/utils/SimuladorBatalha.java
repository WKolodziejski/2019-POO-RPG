package utils;

import personagens.Heroi;
import personagens.Personagem;
import personagens.Vilao;

public class SimuladorBatalha {
    
    public static void main(String[] args) {
        lutar(new Heroi("Heroi", 10, 0), new Vilao(10));
    }
    
    public static void lutar(Heroi heroi, Personagem adversario) {
        while (heroi.getEnergia() > 0 && adversario.getEnergia() > 0) {
            heroi.lutar(adversario);
            heroi.imprimir();
            adversario.imprimir();
        }
        
        System.out.println("Vencedor da batalha: "
                + (heroi.getEnergia() > 0 ? heroi.getNome() : adversario.getNome()));
    }
    
}
