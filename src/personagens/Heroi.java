package personagens;

import item.Item;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Heroi extends Personagem {
    private HashMap<String, Item> inventario;
    private int limitePeso;
    private int pesoAtual;
    
    public Heroi(String nome, int energia, int limitePeso) {
        super(nome, energia);
        this.limitePeso = limitePeso;
        this.inventario = new HashMap<String, Item>();
    }
    
    public boolean inserirItem(Item item) {
        int novoPeso = pesoAtual + item.getPeso();
        if (novoPeso <= limitePeso) {
            inventario.put(item.getNome(), item);
            pesoAtual = novoPeso;
            return true;
        } else return false;
    }
    
    public Item removerItem(String nome) {
        Item item = inventario.get(nome);
        
        if (item != null) {
            inventario.remove(item);
            pesoAtual -= item.getPeso();
            return item;
        } else return null;
    }

    public int getPesoAtual() {
        return pesoAtual;
    }
    
    public void alimentar() {
        incremento();
        incremento();
    }
    
    public void lutar(Personagem adversario) {
        int h = sorte();
        int a = adversario.sorte();
        
        if (h == a) {
            decremento();
            adversario.decremento();
        } else {
            if (h > a) {
                incremento();
                adversario.decremento();
            } else {
                decremento();
                adversario.incremento();
            }
        }
    }
    
}
