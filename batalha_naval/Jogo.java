import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


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

    // ===== IA DO COMPUTADOR =====
    private IAComputador ia;



    // =====================================================
    //  CONSTRUTOR
    // =====================================================
    public Jogo() {

        frame = new JFrame("Batalha Naval");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setMinimumSize(new Dimension(900, 600));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createSplashPanel(), "splash");
        mainPanel.add(createStartPanel(), "start");
        mainPanel.add(createGamePanel(), "game");
        mainPanel.add(createTrocaPanel(), "troca"); // <-- ADICIONADO
        

        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
    }

    public void mostrarTelaInicial() {
        frame.setVisible(true);
        cardLayout.show(mainPanel, "splash");
    }


    // =====================================================
    // SPLASH SCREEN
    // =====================================================
    private JPanel createSplashPanel() {

        JPanel p = new JPanel();
        p.setBackground(Design.AZUL_ESCURO);
        p.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel title = new JLabel("BATALHA NAVAL");
        title.setFont(new Font("Segoe UI", Font.BOLD, 48));
        title.setForeground(Design.AZUL_CLARO);

        JButton iniciar = Design.criarBotao("INICIAR");
        iniciar.setPreferredSize(new Dimension(260, 60));
        iniciar.addActionListener(e -> cardLayout.show(mainPanel, "start"));

        JButton comoJogar = Design.criarBotaoLink("Como Jogar");
        comoJogar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        comoJogar.addActionListener(e -> showComoJogar());

        gbc.gridy = 0;
        p.add(title, gbc);

        gbc.gridy = 1;
        p.add(iniciar, gbc);

        gbc.gridy = 2;
        p.add(comoJogar, gbc);

        return p;
    }


    // =====================================================
    // TELA INICIAL
    // =====================================================
    private JPanel createStartPanel() {

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Design.AZUL_ESCURO);
        p.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));

        JLabel title = new JLabel("FORMA DE JOGO", SwingConstants.CENTER);
        title.setForeground(Design.AZUL_CLARO);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));

        JPanel choices = new JPanel(new GridLayout(5, 1, 10, 10));
        choices.setOpaque(false);
        choices.setBorder(BorderFactory.createEmptyBorder(100, 200, 40, 200));

        JButton pvcBtn = Design.criarBotao("Jogar com a Máquina");
        JButton pvpBtn = Design.criarBotao("Jogar com um Amigo");
        JButton sair = Design.criarBotaoLink("Sair");

        pvcBtn.addActionListener(e -> {
            vsComputer = true;
            iniciarPreparacao();
        });

        pvpBtn.addActionListener(e -> {
            vsComputer = false;
            iniciarPreparacao();
        });

        sair.addActionListener(e -> frame.dispose());

        choices.add(pvcBtn);
        choices.add(pvpBtn);
        choices.add(new JLabel(""));
        choices.add(sair);

        p.add(title, BorderLayout.NORTH);
        p.add(choices, BorderLayout.CENTER);

        return p;
    }


    // =====================================================
    // TELA DO JOGO
    // =====================================================
    private JPanel createGamePanel() {

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Design.CIANO_ESCURO);

        statusLabel = new JLabel("Status", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        statusLabel.setForeground(Design.AZUL_CLARO);
        root.add(statusLabel, BorderLayout.NORTH);

        JPanel boards = new JPanel(new GridLayout(1, 2, 20, 0));
        boards.setOpaque(false);
        boards.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        playerBoardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        opponentBoardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        playerButtons = new JButton[BOARD_SIZE][BOARD_SIZE];
        opponentButtons = new JButton[BOARD_SIZE][BOARD_SIZE];
        initBoards();

        boards.add(playerBoardPanel);
        boards.add(opponentBoardPanel);

        root.add(boards, BorderLayout.CENTER);

        return root;
    }


    private void initBoards() {

        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {

                JButton b1 = new JButton();
                JButton b2 = new JButton();

                b1.setPreferredSize(new Dimension(40, 40));
                b2.setPreferredSize(new Dimension(40, 40));

                b1.setBackground(Design.AZUL_ESCURO);
                b1.setEnabled(false);

                b2.setBackground(Design.AZUL_ESCURO);
                b2.setFocusPainted(false);

                playerButtons[r][c] = b1;
                opponentButtons[r][c] = b2;

                playerBoardPanel.add(b1);
                opponentBoardPanel.add(b2);
            }
        }
    }


    // =====================================================
    //  PREPARAÇÃO DO JOGO
    // =====================================================
    private void iniciarPreparacao() {

        jogador1 = new Jogador("Você", BOARD_SIZE);
        jogador2 = new Jogador(vsComputer ? "Adversário" : "Adversário", BOARD_SIZE);

        int[] sizes = {5, 4, 3, 3, 2};

        jogador1.getTabuleiro().autoPosicionarTodos(sizes);
        jogador2.getTabuleiro().autoPosicionarTodos(sizes);

        ia = new IAComputador(BOARD_SIZE);

        iniciarJogo();
    }


    private void iniciarJogo() {
        player1Turn = true;
        cardLayout.show(mainPanel, "game");
        mostrarTelaDoJogadorAtual();
    }


    // =====================================================
    // COMO JOGAR
    // =====================================================
    private void showComoJogar() {
        new ComoJogarDialog(frame).setVisible(true);
    }

    // =====================================================
    //  TELA DE TROCA DE JOGADOR (PvP)
    // =====================================================
    private JPanel createTrocaPanel() {

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Design.AZUL_ESCURO);

        JLabel msg = new JLabel("PASSAR PARA O PRÓXIMO JOGADOR",
                SwingConstants.CENTER);
        msg.setForeground(Design.AZUL_CLARO);
        msg.setFont(new Font("Segoe UI", Font.BOLD, 32));

        JButton continuar = Design.criarBotao("CONTINUAR");
        continuar.setPreferredSize(new Dimension(260, 60));
        continuar.addActionListener(e -> {
            cardLayout.show(mainPanel, "game");
            mostrarTelaDoJogadorAtual();
        });

        p.add(msg, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.add(continuar);

        p.add(bottom, BorderLayout.SOUTH);

        return p;
    }



    // =====================================================
    // ATUALIZAÇÃO DO JOGO
    // =====================================================
    private void mostrarTelaDoJogadorAtual() {

        Jogador atual = player1Turn ? jogador1 : jogador2;
        Jogador alvo  = player1Turn ? jogador2 : jogador1;

        destacarJogador(atual.getNome());
        atualizarTabuleiros(atual, alvo);
    }


    private void destacarJogador(String nome) {

        statusLabel.setText(nome.toUpperCase());
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Design.AZUL_CLARO);
        statusLabel.setForeground(Design.AZUL_ESCURO);
    }


    private void atualizarTabuleiros(Jogador atual, Jogador alvo) {

        Tabuleiro meu = atual.getTabuleiro();
        Tabuleiro inimigo = alvo.getTabuleiro();

        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {

                // ======= MEU TABULEIRO =======
                if (meu.hasShipAt(r, c)) {
                    playerButtons[r][c].setBackground(
                        meu.isHit(r, c) ? Design.VERDE : Design.AZUL_CLARO
                    );
                } else if (meu.isHit(r, c)) {
                    playerButtons[r][c].setBackground(Design.CIANO_CLARO);
                } else {
                    playerButtons[r][c].setBackground(Design.AZUL_ESCURO);
                }

                // ======= TABULEIRO INIMIGO =======
                JButton opp = opponentButtons[r][c];

                for (ActionListener al : opp.getActionListeners())
                    opp.removeActionListener(al);

                if (inimigo.isHit(r, c)) {
                    opp.setBackground(
                        inimigo.hasShipAt(r, c) ? Design.VERDE : Design.CIANO_CLARO
                    );
                    opp.setEnabled(false);
                } else {
                    opp.setBackground(Design.AZUL_ESCURO);
                    opp.setEnabled(true);
                }

                int rr = r, cc = c;

                opp.addActionListener(e -> {

                    if (inimigo.alreadyShot(rr, cc))
                        return;

                    boolean acerto = inimigo.receiveShot(rr, cc);

                    if (inimigo.allShipsSunk()) {
                        // Cria o diálogo de vitória
                        VitoriaDialog vitoriaDialog = new VitoriaDialog(frame, atual.getNome(), () -> {
                            // Ação a ser realizada quando o diálogo for fechado
                            mostrarTelaInicial();
                        });
                        
                        // Exibe o diálogo
                        vitoriaDialog.setVisible(true);
                        return;
                    }
                    

                    if (acerto) {
                        statusLabel.setText("Acertou! Jogue novamente.");
                        atualizarTabuleiros(atual, alvo);
                        return;
                    }
                    
                    // Errou — troca de jogador
                    player1Turn = !player1Turn;
                    
                    if (vsComputer) {
                        statusLabel.setText("Adversário jogando...");
                        jogarComputador();
                    } else {
                        // Mostra tela de troca
                        cardLayout.show(mainPanel, "troca");
                    }
                    
                });
            }
        }
    }


    // =====================================================
    // IA DO COMPUTADOR
    // =====================================================
    private void jogarComputador() {
        SwingUtilities.invokeLater(() -> {
            
            try { Thread.sleep(500); } catch (Exception ignored) {}

            Tabuleiro tabJ1 = jogador1.getTabuleiro();
            if (ia == null) {
                ia = new IAComputador(BOARD_SIZE); // garante inicialização se esqueceu
            }

            Point tiro = ia.escolherTiro(tabJ1);   // <-- chama escolherTiro
            if (tiro == null) return;

            boolean acerto = tabJ1.receiveShot(tiro.x, tiro.y);
            atualizarTabuleiros(jogador1, jogador2);

            // informa a IA do resultado (muito importante)
            if (acerto) {
                ia.registrarAcerto(tiro, tabJ1);   // <-- chama registrarAcerto
            }

            if (jogador1.allShipsSunk()) {
                // Criar e exibir o diálogo de vitória
                VitoriaDialog vitoriaDialog = new VitoriaDialog(frame, "Adversário", () -> {
                    // Ação a ser realizada quando o diálogo for fechado
                    mostrarTelaInicial();
                });
                vitoriaDialog.setVisible(true);
                return;
            }
            

            if (!acerto) {
                player1Turn = true;
                statusLabel.setText("Adversário errou! Sua vez.");
                return;
            }

            statusLabel.setText("Adversário acertou novamente!");
            jogarComputador();
        });
    }


    // =====================================================
    // ======== CLASSE ESTÁTICA IA (OPÇÃO B) ===============
    // =====================================================
    private static class IAComputador {

        private List<Point> alvosPendentes = new ArrayList<>();
        private List<Point> tirosFeitos = new ArrayList<>();

        private int boardSize;

        IAComputador(int size) {
            this.boardSize = size;
        }

        public Point escolherTiro(Tabuleiro tabJ1) {
            return getProximoTiro(tabJ1);
        }

        // gera tiro aleatório inteligente
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

            return possiveis.get((int)(Math.random() * possiveis.size()));
        }

        // escolhe o próximo tiro
        Point getProximoTiro(Tabuleiro tab) {

            Point tiro = null;

            if (!alvosPendentes.isEmpty()) {
                tiro = alvosPendentes.remove(0);
            }

            if (tiro == null) {
                tiro = gerarTiroAleatorio(tab);
            }

            if (tiro != null)
                tirosFeitos.add(tiro);

            return tiro;
        }

        // registra o resultado do tiro
        // registra um acerto para melhorar a estratégia de ataque
        void registrarAcerto(Point tiro, Tabuleiro tab) {
            adicionarVizinhosComoAlvo(tiro, tab);
        }
        // adiciona vizinhos como alvo
        private void adicionarVizinhosComoAlvo(Point p, Tabuleiro tab) {
            int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

            for (int[] d : dirs) {

                int nx = p.x + d[0];
                int ny = p.y + d[1];

                if (nx < 0 || ny < 0 || nx >= boardSize || ny >= boardSize)
                    continue;

                Point v = new Point(nx, ny);

                if (!tab.alreadyShot(nx, ny) && !alvosPendentes.contains(v)) {
                    alvosPendentes.add(v);
                }
            }
        }
    }
}
