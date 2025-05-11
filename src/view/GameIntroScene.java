package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import util.ResourceLoader;

public class GameIntroScene extends BaseView {

    private JLabel introTexto;
    private JButton continuarBtn;

    public GameIntroScene() {
        super();
        setLayout(null);

        // Texto de introducción
        String texto = "<html><center>Hace mucho tiempo, en las tierras de MoonScorch,<br>" +
                "una oscuridad ancestral comenzó a despertar...<br><br>" +
                "Los ecos de la guerra se acercan.<br>" +
                "Elige tu destino con sabiduría.</center></html>";

        introTexto = new JLabel(texto, SwingConstants.CENTER);
        introTexto.setFont(new Font("Serif", Font.ITALIC, 24));
        introTexto.setForeground(Color.WHITE);
        introTexto.setBounds(200, 150, 880, 200);
        add(introTexto);

        // Botón continuar
        continuarBtn = new JButton("Continuar");
        continuarBtn.setBounds(550, 400, 180, 40);
        add(continuarBtn);
    }

    public void setContinuarAction(ActionListener listener) {
        continuarBtn.addActionListener(listener);
    }

    public void setFullscreenAction(ActionListener listener) {
        fullscreenButton.addActionListener(listener);
    }
}
