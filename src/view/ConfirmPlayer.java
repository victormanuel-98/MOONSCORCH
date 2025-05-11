package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import util.ResourceLoader;

public class ConfirmPlayer extends BaseView {

    private JLabel personajeImg;
    private JLabel textoConfirmacion;
    private JButton btnConfirmar;
    private JButton btnVolver;

    private String personaje;

    public ConfirmPlayer(String personaje) {
        super();
        this.personaje = personaje;

        setLayout(null);

        // Imagen del personaje seleccionada
        personajeImg = new JLabel(resizeImage(
                cargarImagenPersonaje(personaje), 200, 200));
        personajeImg.setBounds(100, 200, 200, 200);
        add(personajeImg);

        // Texto de confirmación
        textoConfirmacion = new JLabel("¿Deseas jugar como " + personaje + "?", SwingConstants.CENTER);
        textoConfirmacion.setFont(new Font("Serif", Font.BOLD, 24));
        textoConfirmacion.setForeground(Color.WHITE);
        textoConfirmacion.setBounds(350, 200, 600, 50);
        add(textoConfirmacion);

        // Botón confirmar
        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(550, 300, 150, 40);
        add(btnConfirmar);

        // Botón volver
        btnVolver = new JButton("Volver");
        btnVolver.setBounds(550, 360, 150, 40);
        add(btnVolver);
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

    public void setConfirmarAction(ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    public void setFullscreenAction(ActionListener listener) {
        fullscreenButton.addActionListener(listener);
    }

    public JButton getVolverButton() {
        return btnVolver;
    }
}
