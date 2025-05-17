package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class BaseView extends JPanel {
    protected JButton fullscreenButton;
    
    public BaseView() {
        initComponents();
    }
    
    protected void initComponents() {
        setLayout(new BorderLayout());
        fullscreenButton = new JButton("Pantalla Completa");
        // Añadir el botón en alguna posición, por ejemplo:
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(fullscreenButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public JButton getFullscreenButton() {
        return fullscreenButton;
    }
    
    public void setFullscreenAction(ActionListener action) {
        fullscreenButton.addActionListener(action);
    }
    
    // Método para redimensionar imágenes (usado por CharacterMenu)
    protected ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
    
    // Método alternativo para redimensionar usando BufferedImage
    protected BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return resizedImage;
    }
}