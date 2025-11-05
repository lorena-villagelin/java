import java.util.Random;
import java.util.Scanner;

public class BatalhaNaval {
    private static final int SIZE = 5; // tamanho do tabuleiro (5x5)
    private static final int SHIPS = 3; // número de embarcações
    private static final int MAX_TURNS = 10; // número máximo de jogadas

    private char[][] board;       // o que o jogador vê (H = hit, M = miss, ~ = unknown)
    private boolean[][] ships;    // onde estão os navios

    private int shipsRemaining;

    public BatalhaNaval() {
        board = new char[SIZE][SIZE];
        ships = new boolean[SIZE][SIZE];
        shipsRemaining = SHIPS;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = '~';
                ships[i][j] = false;
            }
        }
        placeShipsRandomly();
    }

    private void placeShipsRandomly() {
        Random rand = new Random();
        int placed = 0;
        while (placed < SHIPS) {
            int r = rand.nextInt(SIZE);
            int c = rand.nextInt(SIZE);
            if (!ships[r][c]) {
                ships[r][c] = true;
                placed++;
            }
        }
    }

    private void printBoard() {
        System.out.println("\n  1 2 3 4 5");
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean attack(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            System.out.println("Coordenadas fora do tabuleiro. Tente novamente.");
            return false; // não conta como jogada válida
        }

        if (board[row][col] == 'H' || board[row][col] == 'M') {
            System.out.println("Você já atirou nessa posição. Escolha outra.");
            return false; // não conta como jogada válida
        }

        if (ships[row][col]) {
            board[row][col] = 'H';
            ships[row][col] = false; // navio atingido
            shipsRemaining--;
            System.out.println("ACERTOU! Navio destruído.");
        } else {
            board[row][col] = 'M';
            System.out.println("ÁGUA! Tente outra posição.");
        }
        return true; // jogada válida consumida
    }

    private void revealShips() {
        System.out.println("\nPosições dos navios (para conferência):");
        System.out.println("  1 2 3 4 5");
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                if (ships[i][j]) System.out.print('S' + " ");
                else System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- BATALHA NAVAL (Básico) ---");
        System.out.println("Tabuleiro: " + SIZE + "x" + SIZE + ", Navios: " + SHIPS + ", Jogadas: " + MAX_TURNS);
        int turns = 0;

        while (turns < MAX_TURNS && shipsRemaining > 0) {
            printBoard();
            System.out.println("\nJogada " + (turns + 1) + " de " + MAX_TURNS + ".");
            System.out.print("Digite a linha (1-" + SIZE + "): ");
            int row = readInt(scanner) - 1;
            System.out.print("Digite a coluna (1-" + SIZE + "): ");
            int col = readInt(scanner) - 1;

            boolean valid = attack(row, col);
            if (valid) turns++;
            System.out.println("Navios restantes: " + shipsRemaining + "\n");
        }

        if (shipsRemaining == 0) {
            System.out.println("PARABÉNS! Você afundou todos os navios em " + turns + " jogadas.");
        } else {
            System.out.println("Fim das jogadas. Você não conseguiu afundar todos os navios.");
            revealShips();
        }

        scanner.close();
    }

    private int readInt(Scanner scanner) {
        while (true) {
            String s = scanner.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                return v;
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um número: ");
            }
        }
    }

    public static void main(String[] args) {
        BatalhaNaval game = new BatalhaNaval();
        game.play();
    }
}
