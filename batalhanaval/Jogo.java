import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Jogo {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private final int BOARD_SIZE = 10;
    private Jogador jogador1, jogador2;
    private boolean vsComputer = true;
    private boolean player1Turn = true;
    private JLabel statusLabel;
    private JPanel playerBoardPanel;
    private JPanel opponentBoardPanel;
    private JButton[][] playerButtons;
    private JButton[][] opponentButtons;

    public Jogo() {
        frame = new JFrame("Batalha Naval - Azul Minimal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setMinimumSize(new Dimension(900, 600));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(createStartPanel(), "start");
        mainPanel.add(createGamePanel(), "game");

        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
    }

    public void mostrarTelaInicial() {
        frame.setVisible(true);
        cardLayout.show(mainPanel, "start");
    }

    // =================== TELA INICIAL ===================
    private JPanel createStartPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(10, 40, 80));
        JLabel title = new JLabel("BATALHA NAVAL", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));

        JPanel choices = new JPanel(new GridLayout(5, 1, 10, 10));
        choices.setOpaque(false);
        choices.setBorder(BorderFactory.createEmptyBorder(40, 200, 40, 200));

        JButton pvcBtn = makeBlueButton("Humano vs Computador (PvC)");
        JButton pvpBtn = makeBlueButton("Humano vs Humano (PvP)");
        JButton comoJogar = makeBlueButton("Como jogar");
        JButton sair = makeBlueButton("Sair");

        pvcBtn.addActionListener(e -> {
            vsComputer = true;
            iniciarPreparacao();
        });
        pvpBtn.addActionListener(e -> {
            vsComputer = false;
            iniciarPreparacao();
        });
        comoJogar.addActionListener(e -> showComoJogar());
        sair.addActionListener(e -> frame.dispose());

        choices.add(pvcBtn);
        choices.add(pvpBtn);
        choices.add(makeSpacer());
        choices.add(comoJogar);
        choices.add(sair);

        p.add(title, BorderLayout.NORTH);
        p.add(choices, BorderLayout.CENTER);
        return p;
    }

    private void showComoJogar() {
        String msg = """
                Regras básicas:
                - Cada jogador tem um tabuleiro 10x10.
                - Navios: 5, 4, 3, 3, 2 (tamanhos)
                - Clique no tabuleiro adversário para atacar.
                - Azul escuro: campo não atingido
                - Vermelho: acerto
                - Cinza: erro

                Bom jogo!
                """;
        JOptionPane.showMessageDialog(frame, msg, "Como jogar", JOptionPane.INFORMATION_MESSAGE);
    }

    private JButton makeBlueButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(20, 60, 120));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return b;
    }

    private Component makeSpacer() {
        JLabel l = new JLabel("");
        l.setPreferredSize(new Dimension(10, 20));
        return l;
    }

    // =================== TELA DE JOGO ===================
    private JPanel createGamePanel() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(4, 30, 65));
        statusLabel = new JLabel("Status", SwingConstants.CENTER);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        root.add(statusLabel, BorderLayout.NORTH);

        JPanel boardsContainer = new JPanel(new GridLayout(1, 2, 20, 0));
        boardsContainer.setOpaque(false);
        boardsContainer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        playerBoardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        opponentBoardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        playerButtons = new JButton[BOARD_SIZE][BOARD_SIZE];
        opponentButtons = new JButton[BOARD_SIZE][BOARD_SIZE];

        initBoards();

        boardsContainer.add(playerBoardPanel);
        boardsContainer.add(opponentBoardPanel);

        root.add(boardsContainer, BorderLayout.CENTER);
        return root;
    }

    private void initBoards() {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                JButton b1 = new JButton();
                JButton b2 = new JButton();
                b1.setPreferredSize(new Dimension(40, 40));
                b2.setPreferredSize(new Dimension(40, 40));
                b1.setBackground(new Color(20, 40, 90));
                b2.setBackground(new Color(5, 25, 60));
                b1.setEnabled(false); // apenas visual
                b2.setFocusPainted(false);
                b2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                playerButtons[r][c] = b1;
                opponentButtons[r][c] = b2;
                playerBoardPanel.add(b1);
                opponentBoardPanel.add(b2);
            }
        }
    }

    // =================== LÓGICA DO JOGO ===================
    private void iniciarPreparacao() {
        jogador1 = new Jogador("Jogador 1", BOARD_SIZE);
        jogador2 = vsComputer ? new Jogador("Computador", BOARD_SIZE) : new Jogador("Jogador 2", BOARD_SIZE);

        int[] sizes = {5, 4, 3, 3, 2};
        jogador1.getTabuleiro().autoPosicionarTodos(sizes);
        jogador2.getTabuleiro().autoPosicionarTodos(sizes);

        iniciarJogo();
    }

    private void iniciarJogo() {
        player1Turn = true;
        cardLayout.show(mainPanel, "game");
        statusLabel.setText(jogador1.getNome() + ", é a sua vez!");
        atualizarTabuleiros();
    }

    private void atualizarTabuleiros() {
        Tabuleiro t1 = jogador1.getTabuleiro();
        Tabuleiro t2 = jogador2.getTabuleiro();

        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {

                // ---- Tabuleiro do jogador ----
                if (t1.hasShipAt(r, c)) {
                    if (t1.isHit(r, c)) {
                        playerButtons[r][c].setBackground(Color.RED);
                    } else {
                        playerButtons[r][c].setBackground(new Color(0, 70, 150)); // navio azul
                    }
                } else if (t1.isHit(r, c)) {
                    playerButtons[r][c].setBackground(Color.GRAY);
                } else {
                    playerButtons[r][c].setBackground(new Color(20, 40, 90));
                }

                // ---- Tabuleiro do oponente ----
                JButton oppCell = opponentButtons[r][c];
                for (ActionListener al : oppCell.getActionListeners())
                    oppCell.removeActionListener(al); // limpa antigos listeners

                if (t2.isHit(r, c)) {
                    if (t2.hasShipAt(r, c)) {
                        oppCell.setBackground(Color.RED);
                    } else {
                        oppCell.setBackground(Color.GRAY);
                    }
                    oppCell.setEnabled(false);
                } else {
                    oppCell.setBackground(new Color(5, 25, 60));
                    oppCell.setEnabled(true);
                }

                int rr = r, cc = c;
                oppCell.addActionListener(e -> {
                    if (!player1Turn) return;

                    if (t2.alreadyShot(rr, cc)) return;
                    boolean acerto = t2.receiveShot(rr, cc);
                    atualizarTabuleiros();

                    if (t2.allShipsSunk()) {
                        JOptionPane.showMessageDialog(frame, jogador1.getNome() + " venceu!");
                        mostrarTelaInicial();
                        return;
                    }

                    if (acerto) {
                        statusLabel.setText("💥 Acertou! Jogue novamente.");
                    } else {
                        statusLabel.setText("💧 Errou! Vez do adversário.");
                        player1Turn = false;
                        if (vsComputer) {
                            jogarComputador();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Vez do " + jogador2.getNome());
                            player1Turn = true; // troca de turno no PvP
                        }
                    }
                });
            }
        }
    }

    private void jogarComputador() {
        SwingUtilities.invokeLater(() -> {
            Random rnd = new Random();
            Tabuleiro tabJ1 = jogador1.getTabuleiro();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }

            Point tiro = tabJ1.generateRandomShot(tabJ1);
            if (tiro == null) return;

            boolean acerto = tabJ1.receiveShot(tiro.x, tiro.y);
            atualizarTabuleiros();

            if (jogador1.allShipsSunk()) {
                JOptionPane.showMessageDialog(frame, "O computador venceu!");
                mostrarTelaInicial();
                return;
            }

            if (acerto) {
                statusLabel.setText("Computador acertou! Joga de novo...");
                jogarComputador();
            } else {
                statusLabel.setText("Computador errou! Sua vez.");
                player1Turn = true;
            }
        });
    }
}
