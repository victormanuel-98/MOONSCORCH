package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.GameState;
import java.net.URL;
import java.io.File;
import java.util.List;
import java.util.Map;

public class LaunchView extends BaseView {

    private JButton newGameButton;
    private JButton continuarButton;
    private JButton exitButton;
    private Image backgroundImage;
    // Color rojo oscuro para los elementos
    private final Color ROJO_OSCURO = new Color(139, 0, 0);

    public LaunchView() {
        super();
        setLayout(null);
        
        // Cargar imagen de fondo
        try {
            // Intenta múltiples formas de cargar la imagen
            String imagePath = "/Resources/images/reyAmarillo.png";
            
            // Método 1: Usando getResource
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                backgroundImage = new ImageIcon(imageUrl).getImage();
                System.out.println("Imagen cargada con getResource");
            } 
            // Método 2: Usando ClassLoader
            else {
                imageUrl = getClass().getClassLoader().getResource(imagePath.substring(1));
                if (imageUrl != null) {
                    backgroundImage = new ImageIcon(imageUrl).getImage();
                    System.out.println("Imagen cargada con ClassLoader");
                }
                // Método 3: Cargar desde archivo
                else {
                    File file = new File("." + imagePath);
                    if (file.exists()) {
                        backgroundImage = new ImageIcon(file.getAbsolutePath()).getImage();
                        System.out.println("Imagen cargada desde archivo: " + file.getAbsolutePath());
                    } else {
                        System.err.println("No se pudo encontrar la imagen en: " + file.getAbsolutePath());
                        // Intenta buscar en otras ubicaciones
                        String[] paths = {
                            "./Resources/images/reyAmarillo.png",
                            "Resources/images/reyAmarillo.png",
                            "./resources/images/reyAmarillo.png",
                            "resources/images/reyAmarillo.png"
                        };
                        
                        for (String path : paths) {
                            file = new File(path);
                            if (file.exists()) {
                                backgroundImage = new ImageIcon(file.getAbsolutePath()).getImage();
                                System.out.println("Imagen cargada desde: " + file.getAbsolutePath());
                                break;
                            }
                        }
                    }
                }
            }
            
            if (backgroundImage == null) {
                System.err.println("No se pudo cargar la imagen de fondo");
                setBackground(new Color(30, 30, 50)); // Fondo oscuro alternativo
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
            e.printStackTrace();
            setBackground(new Color(30, 30, 50)); // Fondo oscuro alternativo
        }
        
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Título con estilo gótico y más grande
        JLabel titulo = new JLabel("MOONSCORCH", SwingConstants.CENTER);
        // Intentar cargar fuentes góticas disponibles en el sistema
        String[] fuentesGoticas = {"Blackadder ITC", "Old English Text MT", "Gothic", "Medieval", "Luminari", "Papyrus"};
        Font fuenteGotica = null;
        
        // Intentar encontrar una fuente gótica disponible
        for (String nombreFuente : fuentesGoticas) {
            Font fuente = new Font(nombreFuente, Font.BOLD, 80); // Tamaño aumentado a 72
            if (!fuente.getFamily().equals("Dialog")) { // Si no es la fuente por defecto
                fuenteGotica = fuente;
                break;
            }
        }
        
        // Si no se encuentra ninguna fuente gótica, usar una fuente serif con estilo
        if (fuenteGotica == null) {
            fuenteGotica = new Font("Serif", Font.BOLD, 72);
        }
        
        titulo.setFont(fuenteGotica);
        titulo.setForeground(ROJO_OSCURO); // Color rojo oscuro
        titulo.setBounds(0, 80, 1280, 80); // Ajustado para el tamaño mayor
        add(titulo);

        // Desplazar los botones más abajo
        int baseY = 350; // Posición Y inicial más baja
        int spacing = 80; // Mayor espaciado entre botones

        // Botón: Nueva Partida
        newGameButton = crearBoton("NUEVA PARTIDA");
        newGameButton.setBounds(500, baseY, 280, 50);
        newGameButton.addActionListener(e -> iniciarNuevaPartida());
        add(newGameButton);
        baseY += spacing;

        // Botón: Salir
        exitButton = crearBoton("SALIR");
        exitButton.setBounds(500, baseY, 280, 50);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);

        // Botón: Continuar (solo si hay partida guardada)
        if (GameState.hayPartidaGuardada()) {
            continuarButton = crearBoton("CONTINUAR");
            continuarButton.setBounds(500, baseY - spacing/2, 280, 50); // Entre los dos botones
            continuarButton.addActionListener(e -> continuarPartida());
            add(continuarButton);
        }

        // Controles de ventana
        WindowControls controles = new WindowControls((JFrame) SwingUtilities.getWindowAncestor(this));
        add(controles);
    }

    private void iniciarNuevaPartida() {
        GameFrame gameFrame = (GameFrame) SwingUtilities.getWindowAncestor(this);
        if (gameFrame != null) {
            // Si hay un usuario activo, mostrar la introducción
            if (GameState.idUsuarioActual > 0) {
                gameFrame.mostrarGameIntroScene();
            } else {
                // Si no hay usuario activo, mostrar login
                gameFrame.mostrarLogin();
            }
        } else {
            System.err.println("Error: No se pudo obtener la ventana principal");
        }
    }

    private void continuarPartida() {
        GameFrame gameFrame = (GameFrame) SwingUtilities.getWindowAncestor(this);
        if (gameFrame != null) {
            // Obtener personajes del usuario
            List<Map<String, Object>> personajes = model.database.DatabaseManager.obtenerPersonajesUsuario(GameState.idUsuarioActual);
            
            if (personajes.isEmpty()) {
                // No tiene personajes, ir a crear uno
                gameFrame.mostrarCharacterMenu();
            } else {
                // Mostrar selección de personaje
                gameFrame.mostrarSeleccionPersonaje(personajes);
            }
        } else {
            System.err.println("Error: No se pudo obtener la ventana principal");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Dibujar la imagen de fondo
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setBackground(Color.BLACK); // Fondo negro
        btn.setForeground(ROJO_OSCURO); // Texto rojo oscuro
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ROJO_OSCURO, 2), // Borde rojo oscuro
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Efecto hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(20, 20, 20)); // Negro ligeramente más claro al pasar el ratón
                btn.setForeground(new Color(180, 0, 0)); // Rojo más brillante
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.BLACK);
                btn.setForeground(ROJO_OSCURO);
            }
        });
        
        return btn;
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getContinuarButton() {
        return continuarButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public void setNewGameAction(ActionListener listener) {
        newGameButton.addActionListener(listener);
    }

    public void setContinuarAction(ActionListener listener) {
        if (continuarButton != null) {
            continuarButton.addActionListener(listener);
        }
    }

    public void setExitAction(ActionListener listener) {
        exitButton.addActionListener(listener);
    }
}