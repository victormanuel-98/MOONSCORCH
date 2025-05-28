package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

public class EndgameView extends BaseView {
    
    private Image backgroundImage;
    // Color dorado para los elementos
    private final Color DORADO = new Color(218, 165, 32);
    private final Color DORADO_CLARO = new Color(255, 215, 0);
    
    public EndgameView() {
        super();
        setLayout(null);
        
        // Cargar imagen de fondo
        backgroundImage = cargarImagenFondo("/Resources/screens/endgame.png");
        
        // Configurar el panel para mostrar la imagen de fondo
        setOpaque(false);
        
        // Título con estilo gótico y más grande
        JLabel titulo = new JLabel("Has sobrevivido a la noche", SwingConstants.CENTER);
        // Intentar cargar fuentes góticas disponibles en el sistema
        String[] fuentesGoticas = {"Blackadder ITC", "Old English Text MT", "Gothic", "Medieval", "Luminari", "Papyrus"};
        Font fuenteGotica = null;
        
        // Mensaje principal
        JLabel mensaje = new JLabel("Has derrotado al Rey Amarillo", SwingConstants.CENTER);
        mensaje.setFont(new Font("SansSerif", Font.BOLD, 30));
        mensaje.setForeground(Color.BLACK);
        mensaje.setBounds(0, 230, 1280, 50);
        add(mensaje);
        
        // Mensaje secundario
        JLabel submensaje = new JLabel("La oscuridad ha sido desterrada... por ahora", SwingConstants.CENTER);
        submensaje.setFont(new Font("SansSerif", Font.ITALIC, 24));
        submensaje.setForeground(Color.BLACK);
        submensaje.setBounds(0, 290, 1280, 40);
        add(submensaje);
        
        // Botón: Volver al menú principal
        JButton btnMenu = crearBoton("MENÚ PRINCIPAL");
        btnMenu.setBounds(490, 400, 300, 50);
        btnMenu.addActionListener(e -> {
            ((GameFrame) SwingUtilities.getWindowAncestor(this)).mostrarCharacterMenu();
        });
        add(btnMenu);
        
        // Botón: Salir del juego
        JButton btnSalir = crearBoton("SALIR DEL JUEGO");
        btnSalir.setBounds(490, 470, 300, 50);
        btnSalir.addActionListener(this::salirJuego);
        add(btnSalir);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar la imagen de fondo
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Si no hay imagen, usar un color de fondo negro
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setBackground(new Color(20, 20, 20));
        btn.setForeground(DORADO);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DORADO, 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        // Efecto hover
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(new Color(40, 40, 40));
                btn.setForeground(DORADO_CLARO);
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(new Color(20, 20, 20));
                btn.setForeground(DORADO);
            }
        });
        
        return btn;
    }
    
    private void salirJuego(ActionEvent e) {
        System.exit(0);
    }
    
    private Image cargarImagenFondo(String rutaImagen) {
        try {
            // Método 1: Usando getResource
            URL imageUrl = getClass().getResource(rutaImagen);
            if (imageUrl != null) {
                return new ImageIcon(imageUrl).getImage();
            } 
            // Método 2: Usando ClassLoader
            imageUrl = getClass().getClassLoader().getResource(rutaImagen.substring(1));
            if (imageUrl != null) {
                return new ImageIcon(imageUrl).getImage();
            }
            // Método 3: Cargar desde archivo
            File file = new File("." + rutaImagen);
            if (file.exists()) {
                return new ImageIcon(file.getAbsolutePath()).getImage();
            }
            
            // Si no se encuentra, buscar en otras ubicaciones
            String rutaSinBarra = rutaImagen.substring(1);
            String[] alternativas = {
                "./" + rutaSinBarra,
                rutaSinBarra,
                "Resources/screens/victory_background.png",
                "./Resources/screens/victory_background.png"
            };
            
            for (String ruta : alternativas) {
                file = new File(ruta);
                if (file.exists()) {
                    System.out.println("Imagen cargada desde archivo: " + file.getAbsolutePath());
                    return new ImageIcon(file.getAbsolutePath()).getImage();
                }
            }
            
            System.err.println("No se pudo cargar la imagen: " + rutaImagen);
            return null;
            
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen " + rutaImagen + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    // Implementación propia del método onWindowResize sin llamar a super
    public void onWindowResize(boolean maximized) {
        if (maximized) {
            // Obtener el tamaño actual del contenedor padre
            Container parent = getParent();
            if (parent != null) {
                int currentWidth = parent.getWidth();
                int currentHeight = parent.getHeight();
                
                // Calcular el factor de escala
                float scaleX = (float) currentWidth / 1280;
                float scaleY = (float) currentHeight / 720;
                float scaleFactor = Math.min(scaleX, scaleY);
                
                // Centrar la vista en el contenedor
                int xOffset = (currentWidth - (int)(1280 * scaleFactor)) / 2;
                int yOffset = (currentHeight - (int)(720 * scaleFactor)) / 2;
                
                // Aplicar escala a todos los componentes
                Component[] components = getComponents();
                for (Component comp : components) {
                    Rectangle originalBounds = comp.getBounds();
                    int newX = (int)(originalBounds.x * scaleFactor) + xOffset;
                    int newY = (int)(originalBounds.y * scaleFactor) + yOffset;
                    int newWidth = (int)(originalBounds.width * scaleFactor);
                    int newHeight = (int)(originalBounds.height * scaleFactor);
                    
                    comp.setBounds(newX, newY, newWidth, newHeight);
                    
                    // Escalar la fuente si es un JComponent
                    if (comp instanceof JComponent) {
                        Font currentFont = ((JComponent) comp).getFont();
                        if (currentFont != null) {
                            float newSize = Math.max(12, currentFont.getSize() * scaleFactor);
                            ((JComponent) comp).setFont(currentFont.deriveFont(newSize));
                        }
                    }
                }
            }
        } else {
            // Restaurar tamaño original
            Component[] components = getComponents();
            for (Component comp : components) {
                if (comp instanceof JLabel && "¡VICTORIA!".equals(((JLabel) comp).getText())) {
                    comp.setBounds(0, 150, 1280, 70);
                    ((JLabel) comp).setFont(new Font("SansSerif", Font.BOLD, 48));
                } else if (comp instanceof JLabel && "Has derrotado al Rey Amarillo".equals(((JLabel) comp).getText())) {
                    comp.setBounds(0, 230, 1280, 50);
                    ((JLabel) comp).setFont(new Font("SansSerif", Font.BOLD, 30));
                } else if (comp instanceof JLabel && "La oscuridad ha sido desterrada... por ahora".equals(((JLabel) comp).getText())) {
                    comp.setBounds(0, 290, 1280, 40);
                    ((JLabel) comp).setFont(new Font("SansSerif", Font.ITALIC, 24));
                } else if (comp instanceof JButton && "MENÚ PRINCIPAL".equals(((JButton) comp).getText())) {
                    comp.setBounds(490, 400, 300, 50);
                    ((JButton) comp).setFont(new Font("SansSerif", Font.BOLD, 18));
                } else if (comp instanceof JButton && "SALIR DEL JUEGO".equals(((JButton) comp).getText())) {
                    comp.setBounds(490, 470, 300, 50);
                    ((JButton) comp).setFont(new Font("SansSerif", Font.BOLD, 18));
                }
            }
        }
        
        // Asegurarse de que la imagen de fondo se redibuje correctamente
        revalidate();
        repaint();
    }
}