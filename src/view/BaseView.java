package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import util.ResourceLoader;

public abstract class BaseView extends JPanel {

    protected JButton fullscreenButton;
    protected BufferedImage fondo;

    public BaseView() {
        setLayout(null);
        setPreferredSize(new Dimension(1280, 720));
        setFocusable(true);

        // Cargar fondo como imagen real (no ImageIcon)
        fondo = ResourceLoader.loadBufferedImage("reyAmarillo.png");

        if (fondo == null) {
            System.err.println("[BaseView] ⚠ Fondo no cargado. Se usará patrón por defecto.");
        }

        fullscreenButton = new JButton("⛶");
        fullscreenButton.setBounds(1220, 10, 40, 30);
        add(fullscreenButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Patrón de respaldo
            int tileSize = 40;
            Color c1 = new Color(240, 240, 240);
            Color c2 = new Color(210, 210, 210);

            for (int y = 0; y < getHeight(); y += tileSize) {
                for (int x = 0; x < getWidth(); x += tileSize) {
                    g.setColor(((x / tileSize + y / tileSize) % 2 == 0) ? c1 : c2);
                    g.fillRect(x, y, tileSize, tileSize);
                }
            }

            g.setColor(Color.RED);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }

    protected ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        return ResourceLoader.scaleImage(icon, width, height);
    }

    public JButton getFullscreenButton() {
        return fullscreenButton;
    }
}
