package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import util.ResourceLoader;

public class CharacterStats extends BaseView {

    private JLabel personajeImg;
    private JLabel[] statLabels;

    private JButton continuarBtn;

    private final String[] statNames = {"ATK", "DEF", "EVA", "HP", "MP", "LUK"};

    public CharacterStats(String personajeSeleccionado, int[] statValues) {
        super();
        setLayout(null);

        // Imagen del personaje
        personajeImg = new JLabel(resizeImage(
                cargarImagenPersonaje(personajeSeleccionado), 200, 200));
        personajeImg.setBounds(100, 200, 200, 200);
        add(personajeImg);

        // Stats
        statLabels = new JLabel[6];
        for (int i = 0; i < statNames.length; i++) {
            statLabels[i] = new JLabel(statNames[i] + ": " + statValues[i]);
            statLabels[i].setForeground(Color.WHITE);
            statLabels[i].setFont(new Font("Monospaced", Font.BOLD, 20));
            statLabels[i].setBounds(350, 200 + i * 40, 300, 40);
            add(statLabels[i]);
        }

        // Botón continuar
        continuarBtn = new JButton("Continuar");
        continuarBtn.setBounds(1050, 600, 150, 40);
        add(continuarBtn);
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

    public void setContinuarAction(ActionListener listener) {
        continuarBtn.addActionListener(listener);
    }

    public void setFullscreenAction(ActionListener listener) {
        fullscreenButton.addActionListener(listener);
    }
}