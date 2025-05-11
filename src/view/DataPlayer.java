package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import util.ResourceLoader;

public class DataPlayer extends BaseView {

    private JLabel personajeImg;
    private JLabel statsLabel;
    private JLabel nameLabel;
    private JTextField nameField;

    private JRadioButton opcion1, opcion2, opcion3;
    private ButtonGroup grupoOpciones;

    private JButton resetBtn;
    private JButton continuarBtn;
    private JButton volverBtn;

    public DataPlayer(String personaje, int[] stats) {
        super();
        setLayout(null);

        // Imagen del personaje
        personajeImg = new JLabel(resizeImage(
                cargarImagenPersonaje(personaje), 200, 200));
        personajeImg.setBounds(100, 100, 200, 200);
        add(personajeImg);

        // Stats
        statsLabel = new JLabel(formatoStats(stats));
        statsLabel.setForeground(Color.WHITE);
        statsLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
        statsLabel.setBounds(350, 100, 500, 100);
        add(statsLabel);

        // Campo de nombre
        nameLabel = new JLabel("Nombre del héroe:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nameLabel.setBounds(350, 210, 200, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(520, 210, 200, 30);
        add(nameField);

        // Opciones de mejora
        opcion1 = new JRadioButton("Repartir 5 puntos entre ATK/DEF/EVA");
        opcion2 = new JRadioButton("+1 Suerte (LUK)");
        opcion3 = new JRadioButton("+20 HP");

        grupoOpciones = new ButtonGroup();
        grupoOpciones.add(opcion1);
        grupoOpciones.add(opcion2);
        grupoOpciones.add(opcion3);

        opcion1.setBounds(350, 260, 400, 30);
        opcion2.setBounds(350, 300, 400, 30);
        opcion3.setBounds(350, 340, 400, 30);

        for (JRadioButton btn : new JRadioButton[]{opcion1, opcion2, opcion3}) {
            btn.setOpaque(false);
            btn.setForeground(Color.WHITE);
            add(btn);
        }

        // Botón reset
        resetBtn = new JButton("Resetear elección");
        resetBtn.setBounds(350, 390, 200, 30);
        add(resetBtn);

        // Botones navegación
        continuarBtn = new JButton("Continuar");
        continuarBtn.setBounds(1050, 600, 150, 40);
        add(continuarBtn);

        volverBtn = new JButton("Volver");
        volverBtn.setBounds(880, 600, 150, 40);
        add(volverBtn);
    }

    private String formatoStats(int[] stats) {
        return String.format("<html>ATK: %d &nbsp;&nbsp; DEF: %d &nbsp;&nbsp; EVA: %d<br>HP: %d &nbsp;&nbsp; MP: %d &nbsp;&nbsp; LUK: %d</html>",
                stats[0], stats[1], stats[2], stats[3], stats[4], stats[5]);
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

    public String getNombreHeroe() {
        return nameField.getText().trim();
    }

    public String getOpcionSeleccionada() {
        Enumeration<AbstractButton> buttons = grupoOpciones.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton btn = buttons.nextElement();
            if (btn.isSelected()) {
                return btn.getText();
            }
        }
        return null;
    }

    public void resetOpciones() {
        grupoOpciones.clearSelection();
        nameField.setText("");
    }

    public void setResetAction(ActionListener listener) {
        resetBtn.addActionListener(listener);
    }

    public void setContinuarAction(ActionListener listener) {
        continuarBtn.addActionListener(listener);
    }

    public void setVolverAction(ActionListener listener) {
        volverBtn.addActionListener(listener);
    }

    public void setFullscreenAction(ActionListener listener) {
        fullscreenButton.addActionListener(listener);
    }
}
