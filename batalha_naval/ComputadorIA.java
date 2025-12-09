import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class ComputadorIA {

    private List<Point> alvosPendentes = new ArrayList<>();
    private List<Point> tirosFeitos = new ArrayList<>();
    private int boardSize;

    public ComputadorIA(int size) {
        this.boardSize = size;
    }

    public Point escolherTiro(Tabuleiro tabJ1) {
        return getProximoTiro(tabJ1);
    }

    private Point gerarTiroAleatorio(Tabuleiro tab) {
        List<Point> possiveis = new ArrayList<>();
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                if (!tab.alreadyShot(r, c)) {
                    possiveis.add(new Point(r, c));
                }
            }
        }
        if (possiveis.isEmpty()) return null;
        return possiveis.get((int) (Math.random() * possiveis.size()));
    }

    private Point getProximoTiro(Tabuleiro tab) {
        Point tiro = null;
        if (!alvosPendentes.isEmpty()) {
            tiro = alvosPendentes.remove(0);
        }
        if (tiro == null) {
            tiro = gerarTiroAleatorio(tab);
        }
        if (tiro != null) tirosFeitos.add(tiro);
        return tiro;
    }

    public void registrarAcerto(Point tiro, Tabuleiro tab) {
        adicionarVizinhosComoAlvo(tiro, tab);
    }

    private void adicionarVizinhosComoAlvo(Point p, Tabuleiro tab) {
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : dirs) {
            int nx = p.x + d[0];
            int ny = p.y + d[1];
            if (nx < 0 || ny < 0 || nx >= boardSize || ny >= boardSize) continue;
            Point v = new Point(nx, ny);
            if (!tab.alreadyShot(nx, ny) && !alvosPendentes.contains(v)) {
                alvosPendentes.add(v);
            }
        }
    }
}
