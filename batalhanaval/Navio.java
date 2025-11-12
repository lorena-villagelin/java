import java.awt.*;
import java.util.*;
import java.util.List;

class Navio {
    private int tamanho;
    private List<Point> positions;
    private Set<Point> hits = new HashSet<>();

    public Navio(int tamanho) {
        this.tamanho = tamanho;
        positions = new ArrayList<>();
    }

    public void addPosition(int r, int c) {
        positions.add(new Point(r, c));
    }

    public boolean occupies(int r, int c) {
        for (Point p : positions) {
            if (p.x == r && p.y == c) return true;
        }
        return false;
    }

    public void hit(int r, int c) {
        hits.add(new Point(r, c));
    }

    public boolean isSunk() {
        return hits.size() >= tamanho;
    }

    public int getTamanho() {
        return tamanho;
    }
}