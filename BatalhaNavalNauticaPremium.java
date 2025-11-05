import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;  // <-- adicione esta linha
import java.util.Random;

public class BatalhaNavalNauticaPremium extends JFrame {
    private static final int SIZE = 10; // 10x10
    private static final int SHIPS = 5;

    private CellButton[][] cells;
    private boolean[][] ships;
    private int shipsRemaining;

    private JLabel statusLabel;
    private JPanel boardContainer;

    public BatalhaNavalNauticaPremium() {
        super("⚓ Batalha Naval - Náutica Premium ⚓");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 900);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        // Top decorative panel (wood plaque + compass)
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(14, 18, 6, 18));

        JLabel plaque = new JLabel("BATALHA NAVAL", SwingConstants.CENTER);
        plaque.setOpaque(false);
        plaque.setFont(new Font("Georgia", Font.BOLD, 34));
        plaque.setForeground(new Color(230, 200, 140)); // dourado suave
        top.add(plaque, BorderLayout.CENTER);

        // Compass decoration (simple text-based)
        JLabel compass = new JLabel("🧭", SwingConstants.LEFT);
        compass.setFont(new Font("SansSerif", Font.PLAIN, 28));
        compass.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        top.add(compass, BorderLayout.WEST);

        add(top, BorderLayout.NORTH);

        // Center: board wrapped in a styled container (wood frame)
        boardContainer = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                // ocean gradient background (animated feel via subtle noise would be external)
                GradientPaint gp = new GradientPaint(0, 0, new Color(8, 34, 63), 0, getHeight(), new Color(2, 58, 89));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // wood frame border
                int margin = 12;
                RoundRectangle2D.Float frame = new RoundRectangle2D.Float(margin, margin, getWidth()-margin*2, getHeight()-margin*2, 20, 20);
                g2.setStroke(new BasicStroke(10f));
                g2.setColor(new Color(99, 61, 34));
                g2.draw(frame);

                g2.dispose();
            }
        };
        boardContainer.setOpaque(false);
        boardContainer.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel board = new JPanel(new GridLayout(SIZE, SIZE, 6, 6));
        board.setOpaque(false);
        board.setPreferredSize(new Dimension(720, 720));

        cells = new CellButton[SIZE][SIZE];
        ships = new boolean[SIZE][SIZE];
        shipsRemaining = SHIPS;

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                CellButton b = new CellButton(r, c);
                cells[r][c] = b;
                board.add(b);
            }
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(18,18,18,18);
        boardContainer.add(board, gbc);
        add(boardContainer, BorderLayout.CENTER);

        // Bottom control panel (status + actions)
        JPanel bottom = new JPanel(new BorderLayout(8,8));
        bottom.setOpaque(false);
        bottom.setBorder(BorderFactory.createEmptyBorder(8, 20, 18, 20));

        statusLabel = new JLabel("Navios restantes: " + shipsRemaining);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        statusLabel.setForeground(new Color(230, 230, 230));
        bottom.add(statusLabel, BorderLayout.CENTER);

        JButton reset = new JButton("⚓ Reiniciar");
        reset.setFocusPainted(false);
        reset.setFont(new Font("SansSerif", Font.BOLD, 16));
        reset.setBackground(new Color(200, 170, 120));
        reset.setForeground(new Color(30, 20, 10));
        reset.setBorder(new CompoundBorder(new LineBorder(new Color(120,80,40),2), new EmptyBorder(8,14,8,14)));
        reset.addActionListener(e -> resetGame());
        bottom.add(reset, BorderLayout.EAST);

        add(bottom, BorderLayout.SOUTH);

        placeShipsRandomly();
        setVisible(true);
    }

    private void placeShipsRandomly() {
        Random rnd = new Random();
        int placed = 0;
        while (placed < SHIPS) {
            int r = rnd.nextInt(SIZE);
            int c = rnd.nextInt(SIZE);
            if (!ships[r][c]) {
                ships[r][c] = true;
                placed++;
            }
        }
    }

    private void attackCell(int r, int c, CellButton btn) {
        if (!btn.isEnabled()) return;

        if (ships[r][c]) {
            ships[r][c] = false;
            shipsRemaining--;
            btn.showHit();
            statusLabel.setText("🔥 Acertou! Navios restantes: " + shipsRemaining);
            if (shipsRemaining == 0) showWin();
        } else {
            btn.showMiss();
            statusLabel.setText("💧 Água... Navios restantes: " + shipsRemaining);
        }
    }

    private void showWin() {
        statusLabel.setText("🎖️ Vitória! Todos os navios foram afundados.");
        // reveal rest of board subtle
        for (int i = 0; i < SIZE; i++) for (int j = 0; j < SIZE; j++) cells[i][j].setEnabled(false);
        JOptionPane.showMessageDialog(this, "Parabéns! Você venceu a batalha!", "Vitória", JOptionPane.PLAIN_MESSAGE);
    }

    private void resetGame() {
        ships = new boolean[SIZE][SIZE];
        shipsRemaining = SHIPS;
        placeShipsRandomly();
        for (int i = 0; i < SIZE; i++) for (int j = 0; j < SIZE; j++) cells[i][j].reset();
        statusLabel.setText("Navios restantes: " + shipsRemaining);
    }

    // Custom button with semi-3D style and animations
    private class CellButton extends JButton {
        private final int row, col;
        private float glow = 0f;

        public CellButton(int r, int c) {
            this.row = r; this.col = c;
            setOpaque(true);
            setFocusPainted(false);
            setBackground(new Color(45, 120, 170)); // button sea tone
            setForeground(Color.WHITE);
            setFont(new Font("SansSerif", Font.BOLD, 18));
            setBorder(new CompoundBorder(new LineBorder(new Color(20,60,90),2), BorderFactory.createEmptyBorder(6,6,6,6)));
            addActionListener(e -> attackCell(row, col, this));

            // Hover effects
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { if (isEnabled()) setBackground(new Color(70,150,200)); }
                @Override
                public void mouseExited(MouseEvent e) { if (isEnabled()) setBackground(new Color(45,120,170)); }
                @Override
                public void mousePressed(MouseEvent e) { if (isEnabled()) setBackground(new Color(30,100,150)); }
                @Override
                public void mouseReleased(MouseEvent e) { if (isEnabled()) setBackground(new Color(70,150,200)); }
            });
        }

        public void reset() {
            setEnabled(true);
            setText("");
            setBackground(new Color(45,120,170));
            setBorder(new CompoundBorder(new LineBorder(new Color(20,60,90),2), BorderFactory.createEmptyBorder(6,6,6,6)));
            glow = 0f;
            repaint();
        }

        public void showHit() {
            setEnabled(false);
            setText("💥");
            // sparkle animation using timer
            Timer t = new Timer(80, null);
            t.addActionListener(new ActionListener() {
                int count = 0;
                public void actionPerformed(ActionEvent e) {
                    if (count >= 6) { t.stop(); setBackground(new Color(180, 30, 30)); return; }
                    setBackground(count % 2 == 0 ? new Color(255,140,0) : new Color(180,30,30));
                    count++;
                }
            });
            t.setInitialDelay(0);
            t.start();
        }

        public void showMiss() {
            setEnabled(false);
            setText("💧");
            // small fade to darker color
            Timer t = new Timer(60, null);
            t.addActionListener(new ActionListener() {
                int step = 0;
                public void actionPerformed(ActionEvent e) {
                    if (step >= 6) { t.stop(); setBackground(new Color(70,70,70)); return; }
                    float f = 1f - (step / 8f);
                    int r = (int) (45 * f + 70 * (1 - f));
                    int g = (int) (120 * f + 70 * (1 - f));
                    int b = (int) (170 * f + 70 * (1 - f));
                    setBackground(new Color(Math.max(0,r), Math.max(0,g), Math.max(0,b)));
                    step++;
                }
            });
            t.setInitialDelay(0);
            t.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            // semi-3D look: draw subtle bevel and soft shadow
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // shadow
            g2.setColor(new Color(0,0,0,40));
            g2.fillRoundRect(4, 6, getWidth()-8, getHeight()-8, 12, 12);

            // main button background (already set by background color)
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth()-6, getHeight()-6, 12, 12);

            // highlight top edge
            GradientPaint gp = new GradientPaint(0,0,new Color(255,255,255,60),0,getHeight(), new Color(255,255,255,5));
            g2.setPaint(gp);
            g2.fillRoundRect(2,2,getWidth()-10,getHeight()/2,12,12);

            // inner border
            g2.setColor(new Color(255,255,255,40));
            g2.setStroke(new BasicStroke(1f));
            g2.drawRoundRect(1,1,getWidth()-8,getHeight()-8,12,12);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        // Use Nimbus look for nicer default controls
        try { for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            if ("Nimbus".equals(info.getName())) { UIManager.setLookAndFeel(info.getClassName()); break; }
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new BatalhaNavalNauticaPremium());
    }
}
