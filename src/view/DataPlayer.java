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
    private JLabel nivelLabel;
    private JTextField nameField;

    private JRadioButton opcion1, opcion2, opcion3;
    private ButtonGroup grupoOpciones;

    private JButton resetBtn;
    private JButton continuarBtn;
    private JButton volverBtn;

    private JSpinner[] statSpinners;
    private final String[] statNames = {"ATK", "DEF", "EVA", "HP", "MP", "LUK"};
    private int[] statsBase;
    private int puntosDisponibles = 5;
    private JLabel puntosLabel;

    private JPanel asignacionPanel;

    public DataPlayer(String personaje, int[] baseStats) {
        super();
        this.statsBase = baseStats.clone();

        setLayout(null);

        // Imagen del personaje
        personajeImg = new JLabel(resizeImage(
                cargarImagenPersonaje(personaje), 200, 200));
        personajeImg.setBounds(100, 100, 200, 200);
        add(personajeImg);

        // Nivel inicial
        nivelLabel = new JLabel("Nivel: 1");
        nivelLabel.setForeground(Color.WHITE);
        nivelLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        nivelLabel.setBounds(350, 50, 200, 30);
        add(nivelLabel);

        // Campo nombre
        nameLabel = new JLabel("Nombre del héroe:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nameLabel.setBounds(350, 90, 200, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(520, 90, 200, 30);
        add(nameField);

        // Mostrar stats iniciales
        statsLabel = new JLabel(formatoStats(statsBase));
        statsLabel.setForeground(Color.WHITE);
        statsLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
        statsLabel.setBounds(350, 130, 600, 60);
        add(statsLabel);

        // Opciones de mejora
        opcion1 = new JRadioButton("Repartir 5 puntos entre ATK, DEF, EVA, HP, MP");
        opcion2 = new JRadioButton("+1 Suerte (LUK)");
        opcion3 = new JRadioButton("+20 HP");

        grupoOpciones = new ButtonGroup();
        grupoOpciones.add(opcion1);
        grupoOpciones.add(opcion2);
        grupoOpciones.add(opcion3);

        int y = 200;
        for (JRadioButton btn : new JRadioButton[]{opcion1, opcion2, opcion3}) {
            btn.setOpaque(false);
            btn.setForeground(Color.WHITE);
            btn.setBounds(350, y, 400, 30);
            add(btn);
            y += 40;
        }

        opcion1.addActionListener(e -> mostrarAsignacionStats(true));
        opcion2.addActionListener(e -> mostrarAsignacionStats(false));
        opcion3.addActionListener(e -> mostrarAsignacionStats(false));

        // Panel para asignación de stats
        asignacionPanel = new JPanel();
        asignacionPanel.setLayout(new GridLayout(6, 2, 5, 5));
        asignacionPanel.setBounds(800, 200, 300, 180);
        asignacionPanel.setOpaque(false);
        add(asignacionPanel);
        asignacionPanel.setVisible(false);

        statSpinners = new JSpinner[6];
        for (int i = 0; i < statNames.length; i++) {
            JLabel label = new JLabel(statNames[i]);
            label.setForeground(Color.WHITE);
            asignacionPanel.add(label);

            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, puntosDisponibles, 1);
            statSpinners[i] = new JSpinner(model);
            if (i == 5) statSpinners[i].setEnabled(false); // LUK no editable
            asignacionPanel.add(statSpinners[i]);
        }

        // Puntos restantes
        puntosLabel = new JLabel("Puntos restantes: 5");
        puntosLabel.setForeground(Color.WHITE);
        puntosLabel.setBounds(800, 390, 200, 30);
        add(puntosLabel);
        puntosLabel.setVisible(false);

        // Botones
        resetBtn = new JButton("Resetear elección");
        resetBtn.setBounds(350, 330, 200, 30);
        add(resetBtn);

        continuarBtn = new JButton("Continuar");
        continuarBtn.setBounds(1050, 600, 150, 40);
        add(continuarBtn);

        volverBtn = new JButton("Volver");
        volverBtn.setBounds(880, 600, 150, 40);
        add(volverBtn);

        // Actualiza en tiempo real los puntos disponibles
        Timer timer = new Timer(100, e -> actualizarPuntos());
        timer.start();
    }

    private void mostrarAsignacionStats(boolean mostrar) {
        asignacionPanel.setVisible(mostrar);
        puntosLabel.setVisible(mostrar);
    }

    private void actualizarPuntos() {
        if (!asignacionPanel.isVisible()) return;

        int total = 0;
        for (int i = 0; i < statSpinners.length; i++) {
            total += (int) statSpinners[i].getValue();
        }
        puntosLabel.setText("Puntos restantes: " + (puntosDisponibles - total));
        continuarBtn.setEnabled((puntosDisponibles - total) == 0);
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

    public int[] getStatsFinales() {
        int[] finales = statsBase.clone();

        if (opcion1.isSelected()) {
            for (int i = 0; i < statSpinners.length; i++) {
                finales[i] += (int) statSpinners[i].getValue();
            }
        } else if (opcion2.isSelected()) {
            finales[5] += 1; // LUK
        } else if (opcion3.isSelected()) {
            finales[3] += 20; // +20 HP
        }

        return finales;
    }

    public void resetOpciones() {
        grupoOpciones.clearSelection();
        for (JSpinner spinner : statSpinners) {
            spinner.setValue(0);
        }
        nameField.setText("");
        mostrarAsignacionStats(false);
        continuarBtn.setEnabled(false);
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
