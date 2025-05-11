package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import util.ResourceLoader;

public class ReadyForBattle extends BaseView {

    private JLabel mensajeLabel;
    private JLabel personajeImg;
    private JButton comenzarBtn;

    public ReadyForBattle(String nombreHeroe, String personaje) {
        super();
        setLayout(null);

        // Imagen del personaje
        personajeImg = new JLabel(resizeImage(
                cargarImagenPersonaje(personaje), 200, 200));
        personajeImg.setBounds(100, 200, 200, 200);
        add(personajeImg);

        // Mensaje introductorio
        String mensaje = "<html><center>Valiente <b>" + nombreHeroe + "</b>,<br>" +
                "tu viaje comienza ahora...<br><br>" +
                "Prepárate para tu primera batalla.</center></html>";

        mensajeLabel = new JLabel(mensaje, SwingConstants.CENTER);
        mensajeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        mensajeLabel.setForeground(Color.WHITE);
        mensajeLabel.setBounds(350, 180, 600, 150);
        add(mensajeLabel);

        // Botón para iniciar la batalla
        comenzarBtn = new JButton("Comenzar Batalla");
        comenzarBtn.setBounds(550, 400, 200, 50);
        add(comenzarBtn);
    }

    private ImageIcon cargarImagenPersonaje(String nombre) {
        switch (nombre.toLowerCase()) {
            case "ladron":
                return ResourceLoader.loadImageIcon("ladron.png");
            case "caballero":
                return ResourceLoader.loadImageIcon("caballero.png");
            case "clerigo":
            case "mago":
                return ResourceLoader.loadImageIcon("mago.png");
            default:
                return new ImageIcon();
        }
    }

    public void setComenzarAction(ActionListener listener) {
        comenzarBtn.addActionListener(listener);
    }

    public void setFullscreenAction(ActionListener listener) {
        fullscreenButton.addActionListener(listener);
    }
}
