import java.util.*;
import java.util.List;

class Jogador {
    private String nome;
    private Tabuleiro tabuleiro;
    private List<Navio> navios;

    public Jogador(String nome, int tamanhoTab) {
        this.nome = nome;
        this.tabuleiro = new Tabuleiro(tamanhoTab);
        this.navios = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public List<Navio> getNavios() {
        return navios;
    }

    public boolean allShipsSunk() {
        return tabuleiro.allShipsSunk();
    }

    private boolean posicionamentoAutomatico = false;

    public boolean isPosicionamentoAutomatico() {
        return posicionamentoAutomatico;
    }

    public void setPosicionarAutomaticamente(boolean valor) {
        this.posicionamentoAutomatico = valor;
    }

}