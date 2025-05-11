package view;

import util.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConfirmPlayer extends BaseView {

    private final String personaje;
    private final GameFrame frame;

    public ConfirmPlayer(String personaje, GameFrame frame) {
        super();
        this.personaje = personaje;
        this.frame = frame;

        setLayout(null);

        JLabel mensaje = new JLabel("¿Confirmas elegir a: " + personaje.toUpperCase() + "?", SwingConstants.CENTER);
        mensaje.setFont(new Font("Serif", Font.BOLD, 22));
        mensaje.setForeground(Color.WHITE);
        mensaje.setBounds(300, 80, 700, 40);
        add(mensaje);

        ImageIcon icono = ResourceLoader.loadImageIcon(personaje.toLowerCase() + ".png");
        JLabel imagenPersonaje = new JLabel(resizeImage(icono, 250, 250));
        imagenPersonaje.setBounds(500, 140, 250, 250);
        add(imagenPersonaje);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(500, 450, 120, 40);
        add(btnConfirmar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(640, 450, 120, 40);
        add(btnVolver);

        btnConfirmar.addActionListener(e -> frame.mostrarDataPlayer(personaje));
        btnVolver.addActionListener(e -> frame.mostrarCharacterMenu());

        setFullscreenAction(e -> frame.setExtendedState(JFrame.MAXIMIZED_BOTH));
    }

    public void setFullscreenAction(ActionListener listener) {
        fullscreenButton.addActionListener(listener);
    }
}