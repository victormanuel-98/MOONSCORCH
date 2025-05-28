package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class PanelConFondo extends JPanel {
    private Image imagenFondo;
    private boolean estirar = true;
    
    public PanelConFondo(String rutaImagen) {
        try {
            imagenFondo = ImageIO.read(new File(rutaImagen));
        } catch (IOException e) {
            System.err.println("Error al cargar imagen de fondo: " + e.getMessage());
            // Intentar con ruta alternativa
            try {
                imagenFondo = ImageIO.read(new File("MOONSCORCH/" + rutaImagen));
            } catch (IOException e2) {
                System.err.println("Error al cargar imagen de fondo (ruta alternativa): " + e2.getMessage());
            }
        }
        setOpaque(false);
    }
    
    public void setEstirarImagen(boolean estirar) {
        this.estirar = estirar;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            if (estirar) {
                // Estirar la imagen para que cubra todo el panel
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            } else {
                // Centrar la imagen sin estirar
                int x = (getWidth() - imagenFondo.getWidth(this)) / 2;
                int y = (getHeight() - imagenFondo.getHeight(this)) / 2;
                g.drawImage(imagenFondo, x, y, this);
            }
        } else {
            // Si no hay imagen, dibujar un fondo de color
            g.setColor(new Color(50, 50, 50));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}