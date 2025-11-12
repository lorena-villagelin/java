import javax.swing.*;

/**
 * BatalhaNaval.java
 * Jogo de Batalha Naval com GUI Swing minimalista (tons de azul).
 *
 * Contém as classes:
 * - Jogo (controla fluxo)
 * - Jogador (nome, tabuleiro, navios)
 * - Tabuleiro (posições, marcações)
 * - Navio (tamanho, posições, estado)
 *
 * Modo: PvC (humano vs computador) e PvP (humano vs humano)
 *
 * Compilar e executar:
 * javac BatalhaNaval.java
 * java BatalhaNaval
 *
 * Autora: Assistant (adaptar livremente)
 */
public class BatalhaNaval {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Jogo janela = new Jogo();
            janela.mostrarTelaInicial();
        });
    }
}
