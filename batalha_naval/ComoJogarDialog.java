import javax.swing.*;
import java.awt.*;

public class ComoJogarDialog extends JDialog {

    public ComoJogarDialog(JFrame parent) {
        super(parent, "Como Jogar", true);

        setSize(600, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Design.AZUL_ESCURO);

        // TÍTULO
        JLabel titulo = new JLabel("INSTRUÇÕES", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Design.AZUL_CLARO);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // TEXTO CENTRAL
        JTextArea texto = new JTextArea(
                "• Você e o computador têm um tabuleiro oculto de 10x10.\n\n" +
                "• Posicione seus navios estrategicamente.\n\n" +
                "• Clique nas células do tabuleiro inimigo para tentar acertar.\n\n" +
                "• Verde = acerto.\n" +
                "• Ciano = água.\n\n" +
                "• Vence quem afundar todos os navios do oponente primeiro!"
        );
        texto.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        texto.setBackground(Design.AZUL_ESCURO); 
        texto.setForeground(Color.WHITE);
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25)); 
        add(texto, BorderLayout.CENTER);

        // BOTÃO FECHAR
        JButton fechar = Design.criarBotao("FECHAR");
        fechar.setPreferredSize(new Dimension(260, 60));
        fechar.addActionListener(e -> dispose());

        JPanel painelBotao = new JPanel(); //
        painelBotao.setBackground(Design.AZUL_ESCURO);
        painelBotao.add(fechar);

        add(painelBotao, BorderLayout.SOUTH);
    }
}
