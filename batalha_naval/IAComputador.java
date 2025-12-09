import java.awt.Point;
import java.util.*;

public class IAComputador {

    private final int boardSize;

    private final Set<Point> tirosFeitos = new HashSet<>();
    private final List<Point> alvosPendentes = new ArrayList<>();

    // Guarda sequência de acertos para detectar orientação
    private final List<Point> acertosRecentes = new ArrayList<>();

    public IAComputador(int boardSize) {
        this.boardSize = boardSize;
    }

    public Point escolherTiro(Tabuleiro tab) {

        // 1 — Modo TRACE: seguir direção detectada
        Point p = tentarSeguirOrientacao(tab);
        if (p != null) {
            tirosFeitos.add(p);
            return p;
        }

        // 2 — Modo TARGET: há vizinhos pendentes
        if (!alvosPendentes.isEmpty()) {
            p = alvosPendentes.remove(0);
            tirosFeitos.add(p);
            return p;
        }

        // 3 — Modo HUNT
        p = gerarTiroAleatorioInteligente(tab);
        if (p != null) tirosFeitos.add(p);
        return p;
    }


    // ============================================================
    // REGISTRA ACERTO E DEFINE MODO DA IA
    // ============================================================
    public void registrarAcerto(Point p, Tabuleiro tab) {
        acertosRecentes.add(p);
        adicionarVizinhosComoAlvos(p, tab);
    }


    // ============================================================
    // MODO TRACE — DETECTA DIREÇÃO APÓS 2 ACERTOS
    // ============================================================
    private Point tentarSeguirOrientacao(Tabuleiro tab) {

        if (acertosRecentes.size() < 2)
            return null;

        // Últimos dois acertos
        Point p1 = acertosRecentes.get(acertosRecentes.size() - 2);
        Point p2 = acertosRecentes.get(acertosRecentes.size() - 1);

        boolean horizontal = (p1.y == p2.y);
        boolean vertical   = (p1.x == p2.x);

        if (!horizontal && !vertical)
            return null; // ainda não dá pra saber

        // DESCOBRIU ORIENTAÇÃO → tenta seguir na linha
        int dx = p2.x - p1.x;
        int dy = p2.y - p1.y;

        Point próximo = new Point(p2.x + dx, p2.y + dy);

        if (validoParaAtirar(próximo, tab))
            return próximo;

        // Se não funciona na frente, tenta para trás
        Point anterior = new Point(p1.x - dx, p1.y - dy);

        if (validoParaAtirar(anterior, tab))
            return anterior;

        return null; // volta para TARGET
    }


    private boolean validoParaAtirar(Point p, Tabuleiro tab) {
        if (p.x < 0 || p.y < 0 || p.x >= boardSize || p.y >= boardSize)
            return false;
        return !tab.alreadyShot(p.x, p.y) && !tirosFeitos.contains(p);
    }


    // ============================================================
    // TARGET: adiciona vizinhos
    // ============================================================
    private void adicionarVizinhosComoAlvos(Point p, Tabuleiro tab) {

        int[][] dirs = { {1,0}, {-1,0}, {0,1}, {0,-1} };

        for (int[] d : dirs) {
            int x = p.x + d[0];
            int y = p.y + d[1];

            Point alvo = new Point(x, y);

            if (x < 0 || y < 0 || x >= boardSize || y >= boardSize)
                continue;
            if (tab.alreadyShot(x, y))
                continue;
            if (tirosFeitos.contains(alvo))
                continue;

            alvosPendentes.add(alvo);
        }
    }


    // ============================================================
    // HUNT MODE
    // ============================================================
    private Point gerarTiroAleatorioInteligente(Tabuleiro tab) {

        List<Point> possiveis = new ArrayList<>();

        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {

                Point p = new Point(r, c);

                if (!tab.alreadyShot(r, c) && !tirosFeitos.contains(p)) {
                    possiveis.add(p);
                }
            }
        }

        if (possiveis.isEmpty()) return null;

        Collections.shuffle(possiveis);
        return possiveis.get(0);
    }
}
