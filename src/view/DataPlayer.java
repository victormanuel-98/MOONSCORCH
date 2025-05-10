package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DataPlayer extends JFrame {
    private BackgroundPanel contentPanel;
    private JButton btnFull;
    private JTextField nameField;
    private final Map<String, Integer> statPoints = new HashMap<>();
    private final Map<String, JLabel> resumenLabels = new HashMap<>();
    private String personaje;
    private String imagenPath;
    private int[] baseStats;
    private JRadioButton rbDistribuir, rbSuerte, rbHP;

    public DataPlayer(String personaje, int[] baseStats, String imagenPath) {
        this.personaje = personaje;
        this.baseStats = baseStats;
        this.imagenPath = imagenPath;

        setTitle("Datos del Jugador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 600);
        setMinimumSize(new Dimension(800, 500));

        contentPanel = new BackgroundPanel("./Resources/images/reyAmarillo.png");
        contentPanel.setLayout(null);
        setContentPane(contentPanel);

        JLabel lblNombre = new JLabel("Nombre del jugador:");
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(30, 20, 150, 25);
        contentPanel.add(lblNombre);

        nameField = new JTextField();
        nameField.setBounds(180, 20, 200, 25);
        contentPanel.add(nameField);

        JLabel lblImagen = new JLabel();
        lblImagen.setBounds(30, 60, 250, 350);
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            Image img = ImageIO.read(new File(imagenPath)).getScaledInstance(250, 350, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblImagen.setText("Sin imagen");
            lblImagen.setForeground(Color.WHITE);
        }
        contentPanel.add(lblImagen);

        JButton btnClase = new JButton(personaje);
        btnClase.setBounds(30, 420, 250, 30);
        btnClase.setEnabled(false);
        btnClase.setBackground(new Color(80, 0, 0));
        btnClase.setForeground(Color.WHITE);
        contentPanel.add(btnClase);

        // Panel de opciones y stats
        JPanel opciones = new JPanel(null);
        opciones.setBounds(300, 60, 400, 400);
        opciones.setOpaque(false);
        contentPanel.add(opciones);

        JPanel resumen = new JPanel(new GridLayout(6, 1, 5, 5));
        resumen.setBounds(0, 0, 400, 100);
        resumen.setOpaque(false);
        TitledBorder border = new TitledBorder("STATS");
        border.setTitleColor(Color.WHITE);
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        resumen.setBorder(border);
        opciones.add(resumen);

        String[] stats = {"ATK", "DEF", "EVA", "HP", "MP"};
        int[] bonificados = baseStats.clone();
        bonificados[3] += 20;
        for (int i = 0; i < stats.length; i++) {
            statPoints.put(stats[i], bonificados[i]);
            JLabel lbl = new JLabel(stats[i] + ": " + bonificados[i]);
            lbl.setForeground(Color.WHITE);
            resumenLabels.put(stats[i], lbl);
            resumen.add(lbl);
        }
        JLabel lblLuck = new JLabel("LUK: 0");
        lblLuck.setForeground(Color.WHITE);
        resumenLabels.put("LUK", lblLuck);
        resumen.add(lblLuck);

        rbDistribuir = new JRadioButton("5 PUNTOS ALEATORIOS ENTRE ATK/DEF/EVA");
        rbSuerte = new JRadioButton("+1 SUERTE");
        rbHP = new JRadioButton("+20 HP");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbDistribuir);
        bg.add(rbSuerte);
        bg.add(rbHP);
        rbDistribuir.setBounds(10, 120, 380, 30);
        rbSuerte.setBounds(10, 160, 380, 30);
        rbHP.setBounds(10, 200, 380, 30);
        rbDistribuir.setForeground(Color.WHITE);
        rbSuerte.setForeground(Color.WHITE);
        rbHP.setForeground(Color.WHITE);
        rbDistribuir.setOpaque(false);
        rbSuerte.setOpaque(false);
        rbHP.setOpaque(false);
        opciones.add(rbDistribuir);
        opciones.add(rbSuerte);
        opciones.add(rbHP);

        JButton btnContinuar = new JButton("CONTINUAR");
        btnContinuar.setBounds(300, 480, 150, 30);
        btnContinuar.setBackground(new Color(0, 100, 0));
        btnContinuar.setForeground(Color.WHITE);
        contentPanel.add(btnContinuar);
        btnContinuar.addActionListener(e -> {
            String nombre = nameField.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce un nombre.");
                return;
            }
            if (!rbDistribuir.isSelected() && !rbSuerte.isSelected() && !rbHP.isSelected()) {
                JOptionPane.showMessageDialog(this, "Debes elegir una mejora antes de continuar.");
                return;
            }
            Map<String, Integer> finalStats = new HashMap<>();
            resumenLabels.forEach((k, v) -> {
                String valor = v.getText().split(": ")[1];
                finalStats.put(k, Integer.parseInt(valor));
            });
            new ConfirmPlayer(nombre, personaje, imagenPath, finalStats).setVisible(true);
            dispose();
        });

        JButton btnReset = new JButton("RESETEAR");
        btnReset.setBounds(460, 480, 150, 30);
        btnReset.setBackground(new Color(120, 0, 0));
        btnReset.setForeground(Color.WHITE);
        contentPanel.add(btnReset);

        JButton btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(620, 480, 150, 30);
        btnVolver.setBackground(new Color(120, 0, 0));
        btnVolver.setForeground(Color.WHITE);
        contentPanel.add(btnVolver);

        btnFull = new JButton("⛶");
        btnFull.setFont(new Font("Dialog", Font.BOLD, 20));
        btnFull.setForeground(Color.WHITE);
        btnFull.setBackground(new Color(0, 0, 0, 180));
        btnFull.setFocusPainted(false);
        btnFull.setBorderPainted(false);
        btnFull.setOpaque(true);
        btnFull.setBounds(940, 500, 50, 50);
        btnFull.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnFull.addActionListener(e -> {
            dispose();
            setUndecorated(!isUndecorated());
            setVisible(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
        contentPanel.add(btnFull);

        // Listeners
        rbDistribuir.addActionListener(e -> toggleOption("distribuir"));
        rbSuerte.addActionListener(e -> toggleOption("suerte"));
        rbHP.addActionListener(e -> toggleOption("hp"));
        btnReset.addActionListener(e -> resetPantalla());
        btnVolver.addActionListener(e -> {
            new CharacterStats(personaje, imagenPath).setVisible(true);
            dispose();
        });
        btnContinuar.addActionListener(e -> guardarDatos());

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int w = getWidth();
                int h = getHeight();
                btnFull.setBounds(w - 70, h - 90, 50, 50);
            }
        });
    }

    private void toggleOption(String opcion) {
        resumenLabels.get("LUK").setText("LUK: " + (opcion.equals("suerte") ? "1" : "0"));
        int baseHP = baseStats[3] + (opcion.equals("hp") ? 40 : 20);
        resumenLabels.get("HP").setText("HP: " + baseHP);
    }

    private void resetPantalla() {
        rbDistribuir.setSelected(false);
        rbSuerte.setSelected(false);
        rbHP.setSelected(false);
        resumenLabels.get("LUK").setText("LUK: 0");
        resumenLabels.get("HP").setText("HP: " + (baseStats[3] + 20));
    }

    private void guardarDatos() {
        String nombre = nameField.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduce un nombre.");
            return;
        }
        if (!rbDistribuir.isSelected() && !rbSuerte.isSelected() && !rbHP.isSelected()) {
            JOptionPane.showMessageDialog(this, "Debes elegir una mejora antes de continuar.");
            return;
        }
        System.out.println("Jugador: " + nombre);
        System.out.println("Clase: " + personaje);
        resumenLabels.forEach((k, v) -> System.out.println(k + ": " + v.getText().split(": ")[1]));
       
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;
        public BackgroundPanel(String path) {
            try {
                backgroundImage = ImageIO.read(new File(path));
            } catch (Exception e) {
                System.err.println("No se pudo cargar el fondo: " + e.getMessage());
            }
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}