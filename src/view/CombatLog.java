package view;

import javax.swing.*;
import java.awt.*;

public class CombatLog extends JPanel {

    private final JTextArea area;

    public CombatLog() {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setForeground(Color.YELLOW);
        area.setBackground(Color.BLACK);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scroll, BorderLayout.CENTER);
    }

    public void agregarLinea(String texto) {
        area.append(texto + "\n");
        area.setCaretPosition(area.getDocument().getLength());
    }

    public void limpiar() {
        area.setText("");
    }
}
