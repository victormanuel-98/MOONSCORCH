package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class CharacterMenu extends JFrame {

    private JPanel[] charPanels = new JPanel[3];
    private JButton btnFull;

    public CharacterMenu() {
        setTitle("Selecciona tu personaje");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 600);
        setMinimumSize(new Dimension(800, 500));

        BackgroundPanel contentPanel = new BackgroundPanel("./Resources/images/reyAmarillo.png");
        contentPanel.setLayout(null);
        setContentPane(contentPanel);

        // Crear personajes
        createCharacterPanel("Ladrón", "./Resources/images/ladron.png", 0, contentPanel);
        createCharacterPanel("Caballero", "./Resources/images/caballero.png", 1, contentPanel);
        createCharacterPanel("Clérigo", "./Resources/images/mago.png", 2, contentPanel);

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

        // Responsividad
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int w = getWidth();
                int h = getHeight();
                int margin = (w - 720) / 4;

                setCharacterBounds(0, margin);
                setCharacterBounds(1, margin * 2 + 240);
                setCharacterBounds(2, margin * 3 + 480);
                btnFull.setBounds(w - 70, h - 90, 50, 50);
            }
        });
    }

    private void createCharacterPanel(String name, String imgPath, int index, JPanel parent) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(80 + index * 310, 100, 240, 340);
        panel.setBackground(Color.DARK_GRAY);
        panel.setBorder(new LineBorder(Color.WHITE, 3));
        parent.add(panel);
        charPanels[index] = panel;

        JLabel lblImage = new JLabel();
        lblImage.setBounds(10, 10, 220, 250);
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        try {
            Image img = ImageIO.read(new File(imgPath)).getScaledInstance(220, 250, Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            lblImage.setText("Imagen no encontrada");
            lblImage.setForeground(Color.WHITE);
        }
        panel.add(lblImage);

        JButton btn = new JButton(name);
        btn.setBounds(40, 280, 160, 40);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(80, 0, 0));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(120, 0, 0));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(80, 0, 0));
            }
        });

        // → Abrir ventana de stats
        btn.addActionListener(e -> {
            new CharacterStats(name, imgPath).setVisible(true);
            dispose();
        });

        panel.add(btn);
    }

    private void setCharacterBounds(int index, int x) {
        if (charPanels[index] != null) {
            charPanels[index].setBounds(x, 100, 240, 340);
        }
    }

    // Panel de fondo que escala imagen
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String path) {
            try {
                backgroundImage = ImageIO.read(new File(path));
            } catch (IOException e) {
                System.err.println("No se pudo cargar la imagen de fondo: " + e.getMessage());
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
