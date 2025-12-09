import javax.swing.*;
import java.awt.*;

public class Design {

    // Cores
    public static final Color CIANO_CLARO = Color.decode("#70cbcf");
    public static final Color CIANO_ESCURO = Color.decode("#58959b");
    public static final Color AZUL_ESCURO = Color.decode("#284864");
    public static final Color AZUL_CLARO = Color.decode("#e5f9fa");
    public static final Color VERDE = Color.decode("#006c45");

    // Fonte padrão
    public static final Font FONTE_TITULO = new Font("SansSerif", Font.BOLD, 28);
    public static final Font FONTE_BOTAO = new Font("SansSerif", Font.BOLD, 20);

    // BOTÃO GENÉRICO
    public static JButton criarBotao(String texto) {
        JButton b = new JButton(texto);
        b.setFont(FONTE_BOTAO);
        b.setBackground(AZUL_CLARO);
        b.setForeground(AZUL_ESCURO);
        b.setFocusPainted(false);
        return b;
    }

    public static JButton criarBotaoLink(String texto) {
    JButton link = new JButton("<html><u>" + texto + "</u></html>");

    link.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    link.setForeground(AZUL_CLARO);

    link.setBackground(new Color(0, 0, 0, 0)); // transparente
    link.setBorderPainted(false);
    link.setFocusPainted(false);
    link.setContentAreaFilled(false);
    link.setCursor(new Cursor(Cursor.HAND_CURSOR));

    return link;
    }
}
