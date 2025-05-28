package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WindowControls extends JPanel {

    public JButton btnMinimizar;
    public JButton btnMaximizar;
    public JButton btnCerrar;
    private boolean isMaximized = false;
    private Rectangle prevBounds;
    private JFrame parentFrame; // Referencia al frame padre

    public WindowControls() {
        this(null); // Permitir crear sin frame inicial
    }

    public WindowControls(JFrame frame) {
        setLayout(null);
        setOpaque(false);
        setBounds(1180, 0, 100, 40); // Posición fija arriba a la derecha (ventana 1280px)
        
        this.parentFrame = frame;

        // Botón Minimizar
        btnMinimizar = createButton("-", Color.decode("#333333"));
        btnMinimizar.setBounds(0, 5, 30, 30);
        btnMinimizar.addActionListener(e -> {
            System.out.println("Minimizando ventana...");
            JFrame frameToUse = getParentFrame();
            if (frameToUse != null) {
                frameToUse.setState(JFrame.ICONIFIED);
            } else {
                System.err.println("Error: No se pudo encontrar el frame padre para minimizar");
            }
        });
        add(btnMinimizar);

        // Botón Maximizar
        btnMaximizar = createButton("□", Color.decode("#006699"));
        btnMaximizar.setBounds(35, 5, 30, 30);
        btnMaximizar.addActionListener(e -> {
            System.out.println("Cambiando estado de maximización...");
            JFrame frameToUse = getParentFrame();
            if (frameToUse != null) {
                if (isMaximized) {
                    // Restaurar tamaño anterior
                    if (prevBounds != null) {
                        frameToUse.setBounds(prevBounds);
                    }
                    btnMaximizar.setText("□");
                    isMaximized = false;
                } else {
                    // Guardar tamaño actual y maximizar
                    prevBounds = frameToUse.getBounds();
                    frameToUse.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    btnMaximizar.setText("❐");
                    isMaximized = true;
                }
            } else {
                System.err.println("Error: No se pudo encontrar el frame padre para maximizar");
            }
        });
        add(btnMaximizar);

        // Botón Cerrar
        btnCerrar = createButton("X", Color.decode("#800000"));
        btnCerrar.setBounds(70, 5, 30, 30);
        btnCerrar.addActionListener(e -> {
            System.out.println("Cerrando aplicación...");
            System.exit(0);
        });
        add(btnCerrar);
        
        // Asegurarse de que el panel sea visible
        setVisible(true);
    }
    
    // Método para obtener el frame padre, buscándolo si es necesario
    private JFrame getParentFrame() {
        if (parentFrame != null) {
            return parentFrame;
        }
        
        // Buscar el frame padre en la jerarquía de componentes
        Container parent = getParent();
        while (parent != null) {
            if (parent instanceof JFrame) {
                parentFrame = (JFrame) parent;
                return parentFrame;
            }
            parent = parent.getParent();
        }
        
        // Si no se encuentra, intentar obtenerlo a través de SwingUtilities
        try {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof JFrame) {
                parentFrame = (JFrame) window;
                return parentFrame;
            }
        } catch (Exception e) {
            System.err.println("Error al buscar frame padre: " + e.getMessage());
        }
        
        return null;
    }
    
    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btn.setContentAreaFilled(true);
        
        // Efecto hover
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(brighten(bgColor, 0.2f));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });
        
        return btn;
    }
    
    // Método para aclarar un color
    private Color brighten(Color color, float factor) {
        int r = Math.min(255, (int)(color.getRed() * (1 + factor)));
        int g = Math.min(255, (int)(color.getGreen() * (1 + factor)));
        int b = Math.min(255, (int)(color.getBlue() * (1 + factor)));
        return new Color(r, g, b);
    }
    
    // Método para actualizar la posición cuando cambia el tamaño del frame
    public void updatePosition(int frameWidth) {
        setBounds(frameWidth - 110, 0, 100, 40);
        revalidate();
    }
    
    // Método para actualizar la referencia al frame padre
    public void setParentFrame(JFrame frame) {
        this.parentFrame = frame;
    }
}