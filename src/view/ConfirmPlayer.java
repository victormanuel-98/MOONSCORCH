package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;

public class ConfirmPlayer extends JFrame {

    public ConfirmPlayer(String nombre, String clase, String imagenPath, Map<String, Integer> stats) {
        setTitle("Confirmar jugador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 600);
        setMinimumSize(new Dimension(800, 500));

        JPanel contentPanel = new BackgroundPanel("./Resources/images/reyAmarillo.png");
        contentPanel.setLayout(null);
        setContentPane(contentPanel);

        JLabel lblNombreClase = new JLabel("Sir " + clase + " " + nombre);
        lblNombreClase.setFont(new Font("Serif", Font.BOLD, 24));
        lblNombreClase.setForeground(Color.WHITE);
        lblNombreClase.setBounds(350, 30, 500, 30);
        contentPanel.add(lblNombreClase);

        JLabel lblImagen = new JLabel();
        lblImagen.setBounds(50, 100, 250, 350);
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        try {
            Image img = ImageIO.read(new File(imagenPath)).getScaledInstance(250, 350, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblImagen.setText("Sin imagen");
            lblImagen.setForeground(Color.WHITE);
        }
        contentPanel.add(lblImagen);

        JPanel panelStats = new JPanel(new GridLayout(6, 1, 10, 10));
        panelStats.setBounds(350, 100, 300, 220);
        panelStats.setOpaque(false);

        stats.forEach((k, v) -> {
            JLabel lbl = new JLabel(k + ": " + v);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            panelStats.add(lbl);
        });
        contentPanel.add(panelStats);

        JButton btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(350, 400, 130, 40);
        btnVolver.setBackground(new Color(120, 0, 0));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> {
            new DataPlayer(nombre, baseStatsFromMap(stats), imagenPath).setVisible(true);
            dispose();
        });
        contentPanel.add(btnVolver);

        JButton btnAventura = new JButton("COMENZAR AVENTURA");
        btnAventura.setBounds(500, 400, 200, 40);
        btnAventura.setBackground(new Color(0, 100, 0));
        btnAventura.setForeground(Color.WHITE);
        btnAventura.setFocusPainted(false);
        contentPanel.add(btnAventura);
        btnAventura.addActionListener(e->{
        	new GameIntroScene().setVisible(true);
        	dispose();
        });

        JButton btnFull = new JButton("⛶");
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
    }

    private int[] baseStatsFromMap(Map<String, Integer> stats) {
        return new int[]{
            stats.getOrDefault("ATK", 0),
            stats.getOrDefault("DEF", 0),
            stats.getOrDefault("EVA", 0),
            stats.getOrDefault("HP", 0) - 20,  // quitar los +20 para rehacer
            stats.getOrDefault("MP", 0)
        };
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
