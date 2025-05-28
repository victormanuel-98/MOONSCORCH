package view;

import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

public class GameOverView extends BaseView {
    
    private Image backgroundImage;
    // Color rojo oscuro para los elementos
    private final Color ROJO_OSCURO = new Color(139, 0, 0);

    public GameOverView() {
        super();
        setLayout(null);
        
        // Cargar imagen de fondo
        backgroundImage = cargarImagenFondo("/Resources/screens/Void_background2.png");
        
        // Configurar el panel para mostrar la imagen de fondo
        setOpaque(false);
        
        // Título
        JLabel titulo = new JLabel("GAME OVER", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 48));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 100, 1280, 70);
        add(titulo);

        // Guardar progreso simulado
        GameState.guardarProgresoSimulado();

        // Restaurar vida al máximo
        GameState.jugadorActual.restaurarVidaTotal();

        // Mensaje
        JLabel mensaje = new JLabel("Has muerto...", SwingConstants.CENTER);
        mensaje.setFont(new Font("SansSerif", Font.PLAIN, 24));
        mensaje.setForeground(Color.WHITE);
        mensaje.setBounds(0, 180, 1280, 40);
        add(mensaje);

        // Botón: Volver al menú
        JButton btnMenu = crearBoton("MENÚ PRINCIPAL");
        btnMenu.setBounds(490, 370, 300, 50);
        btnMenu.addActionListener(e -> {
            ((GameFrame) SwingUtilities.getWindowAncestor(this)).mostrarCharacterMenu();
        });
        add(btnMenu);
        
        // Botón: Salir del juego
        JButton btnSalir = crearBoton("SALIR DEL JUEGO");
        btnSalir.setBounds(490, 440, 300, 50);
        btnSalir.addActionListener(e -> {
            System.exit(0);
        });
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
        btn.setBackground(Color.BLACK);
        btn.setForeground(ROJO_OSCURO);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ROJO_OSCURO, 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        // Efecto hover
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(new Color(20, 20, 20));
                btn.setForeground(new Color(180, 0, 0));
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(Color.BLACK);
                btn.setForeground(ROJO_OSCURO);
            }
        });
        
        return btn;
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
                "Resources/screens/game_over_background.png",
                "./Resources/screens/game_over_background.png"
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
}