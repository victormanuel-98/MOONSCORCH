package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CharacterStats extends JFrame {

    private static final Map<String, int[]> STATS = new HashMap<>();
    static {
        STATS.put("Ladrón",     new int[] {8, 3, 9, 80, 30});
        STATS.put("Caballero",  new int[] {6, 9, 3, 120, 20});
        STATS.put("Clérigo",    new int[] {9, 2, 8, 90, 6});
    }

    private BackgroundPanel contentPanel;
    private JButton btnFull;

    public CharacterStats(String personaje, String imagenPath) {
        setTitle("Estadísticas de " + personaje);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 600);
        setMinimumSize(new Dimension(800, 500));

        contentPanel = new BackgroundPanel("./Resources/images/reyAmarillo.png");
        contentPanel.setLayout(null);
        setContentPane(contentPanel);

        // Imagen del personaje
        JLabel lblImagen = new JLabel();
        lblImagen.setBounds(50, 80, 300, 400);
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            Image img = ImageIO.read(new File(imagenPath)).getScaledInstance(300, 400, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblImagen.setText("Sin imagen");
            lblImagen.setForeground(Color.WHITE);
        }
        contentPanel.add(lblImagen);

        // Panel de estadísticas
        JPanel panelStats = new JPanel(new GridLayout(5, 2, 10, 10));
        panelStats.setBounds(400, 100, 300, 220);
        TitledBorder border = new TitledBorder(BorderFactory.createLineBorder(Color.WHITE, 2), "Estadísticas");
        border.setTitleColor(Color.WHITE);
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 16));
        panelStats.setBorder(border);
        panelStats.setOpaque(false);

        String[] etiquetas = {"Ataque", "Defensa", "Evasión", "HP", "MP"};
        int[] valores = STATS.getOrDefault(personaje, new int[]{0, 0, 0, 0, 0});
        for (int i = 0; i < etiquetas.length; i++) {
            JLabel lbl = new JLabel(etiquetas[i] + ":");
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            JLabel val = new JLabel(String.valueOf(valores[i]));
            val.setForeground(Color.WHITE);
            val.setFont(new Font("Segoe UI", Font.BOLD, 14));
            panelStats.add(lbl);
            panelStats.add(val);
        }
        contentPanel.add(panelStats);

        // Botón Continuar
        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.setBounds(400, 350, 130, 40);
        btnContinuar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnContinuar.setBackground(new Color(0, 100, 0));
        btnContinuar.setForeground(Color.WHITE);
        btnContinuar.setFocusPainted(false);
        btnContinuar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        contentPanel.add(btnContinuar);
        btnContinuar.addActionListener(e -> {
            int[] baseStats = STATS.get(personaje); // ← usa tu mapa
            new DataPlayer(personaje, baseStats, imagenPath).setVisible(true);
            dispose();
        });

        // Botón Volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(570, 350, 130, 40);
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVolver.setBackground(new Color(100, 0, 0));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> {
            new CharacterMenu().setVisible(true);
            dispose();
        });
        contentPanel.add(btnVolver);

        // Botón pantalla completa
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

        // Responsividad manual
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int w = getWidth();
                int h = getHeight();
                btnFull.setBounds(w - 70, h - 90, 50, 50);
            }
        });
    }

    // Panel de fondo redimensionable
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
