import javax.swing.*;
import java.awt.*;

public class VitoriaDialog extends JDialog {

    public VitoriaDialog(JFrame parent, String vencedor, Runnable aoFechar) {
        super(parent, "Vitória!", true);

        setSize(500, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Design.AZUL_ESCURO);

        // TÍTULO
        JLabel titulo = new JLabel("FIM DE JOGO!", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Design.AZUL_CLARO);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // TEXTO CENTRAL
        JTextArea texto = new JTextArea(vencedor +" venceu a partida!\n\n" + "Todos os navios foram afundados.");
        texto.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        texto.setBackground(Design.AZUL_ESCURO);
        texto.setForeground(Color.WHITE);
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        add(texto, BorderLayout.CENTER);

        // BOTÃO FECHAR / CONTINUAR
        JButton fechar = Design.criarBotao("VOLTAR AO MENU");
        fechar.setPreferredSize(new Dimension(260, 60));
        fechar.addActionListener(e -> {
            dispose();
            if (aoFechar != null) aoFechar.run();
        });

        JPanel painelBotao = new JPanel();
        painelBotao.setBackground(Design.AZUL_ESCURO);
        painelBotao.add(fechar);
        add(painelBotao, BorderLayout.SOUTH);
    }
}
