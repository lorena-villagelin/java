import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.awt.Point;

class Tabuleiro {
    private int n;
    // -1 = water unknown, 0 = water miss, 1 = ship part unknown, 2 = hit
    private int[][] grid;
    private List<Navio> navios;
    private Random rnd = new Random();
    private Set<Point> shotsMade = new HashSet<>(); // for AI generation

    public Tabuleiro(int n) {
        this.n = n;
        grid = new int[n][n];
        navios = new ArrayList<>();
        clear();
    }

    public void clear() {
        navios.clear();
        shotsMade.clear();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                grid[i][j] = -1; // unknown
    }

    public boolean hasShipAt(int r, int c) {
        for (Navio s : navios) {
            if (s.occupies(r, c)) return true;
        }
        return false;
    }

    public boolean isHit(int r, int c) {
        return grid[r][c] == 2 || grid[r][c] == 0;
    }

    public boolean alreadyShot(int r, int c) {
        return grid[r][c] == 2 || grid[r][c] == 0;
    }

    public boolean receiveShot(int r, int c) {
        shotsMade.add(new Point(r, c));
        if (hasShipAt(r, c)) {
            grid[r][c] = 2;
            for (Navio s : navios) {
                if (s.occupies(r, c)) {
                    s.hit(r, c);
                    break;
                }
            }
            return true;
        } else {
            grid[r][c] = 0;
            return false;
        }
    }

    public boolean placeShipManual(int size, int r, int c, boolean horizontal) {
        // check bounds and collision
        int dr = horizontal ? 0 : 1;
        int dc = horizontal ? 1 : 0;
        int rr = r + dr * (size - 1);
        int cc = c + dc * (size - 1);
        if (rr < 0 || rr >= n || cc < 0 || cc >= n) return false;
        for (int k = 0; k < size; k++) {
            int cr = r + dr * k;
            int cc2 = c + dc * k;
            if (hasShipAt(cr, cc2)) return false;
        }
        // place
        Navio s = new Navio(size);
        for (int k = 0; k < size; k++) {
            int cr = r + dr * k;
            int cc2 = c + dc * k;
            s.addPosition(cr, cc2);
        }
        navios.add(s);
        return true;
    }

    public void autoPosicionarTodos(int[] sizes) {
        clear();
        for (int size : sizes) {
            boolean placed = false;
            int tries = 0;
            while (!placed && tries < 1000) {
                tries++;
                int r = rnd.nextInt(n);
                int c = rnd.nextInt(n);
                boolean horiz = rnd.nextBoolean();
                placed = placeShipManual(size, r, c, horiz);
            }
            if (!placed) {
                // restart placement if stuck
                clear();
                autoPosicionarTodos(sizes);
                return;
            }
        }
    }

    public boolean allShipsPlaced(int expected) {
        return navios.size() >= expected;
    }

    public boolean allShipsSunk() {
        for (Navio s : navios) {
            if (!s.isSunk()) return false;
        }
        return true;
    }

    // AI helper: generate a random shot not used yet
    public Point generateRandomShot(Tabuleiro opponent) {
        // find all available
        List<Point> avail = new ArrayList<>();
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (!opponent.alreadyShot(r, c)) avail.add(new Point(r, c));
            }
        }
        if (avail.isEmpty()) return null;
        return avail.get(new Random().nextInt(avail.size()));
    }
}