package view;

import javax.swing.*;
import java.awt.*;

public abstract class BaseView extends JPanel {

    public BaseView() {
        setLayout(null);
        setPreferredSize(new Dimension(1280, 720));
        setFocusable(true);
        setBackground(UIManager.getColor("Panel.background")); // Color por defecto del sistema
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Borde negro estándar
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    protected ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        return util.ResourceLoader.scaleImage(icon, width, height);
    }

	public void initComponents() {
		
	}
}
