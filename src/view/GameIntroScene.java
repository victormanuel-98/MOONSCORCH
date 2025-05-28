package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.io.File;
import java.util.List;
import java.util.Map;
import model.database.DatabaseManager;
import model.GameState;

public class GameIntroScene extends BaseView {

    private JButton continuarBtn;
    private Image backgroundImage;
    // Color rojo oscuro para los elementos
    private final Color ROJO_OSCURO = new Color(139, 0, 0);

    public GameIntroScene() {
        super();
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        // Cargar imagen de fondo
        try {
            // Intenta múltiples formas de cargar la imagen
            String imagePath = "/Resources/screens/Ma'habreScreen.png";
            
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
                            "./Resources/screens/Ma'habreScreen.png",
                            "Resources/screens/Ma'habreScreen.png"
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

        // Título y Fecha con estilo mejorado
        JLabel izquierda = new JLabel("Eleum Loyce");
        izquierda.setFont(new Font("SansSerif", Font.ITALIC, 20));
        izquierda.setForeground(ROJO_OSCURO); // Color rojo oscuro
        izquierda.setBounds(100, 40, 400, 30);
        add(izquierda);

        JLabel derecha = new JLabel("??/??/????", SwingConstants.RIGHT);
        derecha.setFont(new Font("SansSerif", Font.ITALIC, 20));
        derecha.setForeground(ROJO_OSCURO); // Color rojo oscuro
        derecha.setBounds(780, 40, 400, 30);
        add(derecha);

        // Panel semitransparente para el texto
        JPanel panelTexto = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 120)); // Negro semitransparente
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelTexto.setLayout(null); // Usar layout null para control preciso
        panelTexto.setBounds(200, 100, 880, 400);
        panelTexto.setOpaque(false); // Importante para la transparencia
        add(panelTexto);

        // Texto narrativo con estilo mejorado
        String texto = "<html><div style='text-align: justify; padding: 20px;'>"
            + "<span style='color: #DDDDDD;'>"
            + "Hace siglos, bañada bajo la luz eterna de una luna moribunda, el Rey Amarillo alzó la ciudad de Eleum Loyce, "
            + "una imponente ciudad levantada en el confín del mundo. Dicen que gobernó con sabiduría, pero un día su soberbia enfureció a la Luna...<br><br>"
            + "Lo que alguna vez fue luz se convirtió en la más terrible corrupción. "
            + "Eleum Loyce fue consumida por un fuego que no arde: la <b><span style='color: #8A2BE2;'>Quemadura Lunar</span></b>, una plaga que tuerce la carne y quiebra la mente. "
            + "Ahora, la que antaño fue la cúspide de la civilización, se retuerce bajo un cielo negro, custodiada por horrores que susurran en lenguas olvidadas.<br><br>"
            + "Tú eres uno de los pocos lo bastante desesperados —<b>o valientes</b>— para cruzar sus decrépitas murallas.<br>"
            + "El Rey Amarillo aún susurra desde su trono quebrado. Y la luna te está observando."
            + "</span></div></html>";

        JLabel introTexto = new JLabel(texto);
        introTexto.setFont(new Font("SansSerif", Font.ITALIC, 20));
        introTexto.setForeground(Color.WHITE);
        introTexto.setBounds(20, 20, 840, 360);
        panelTexto.add(introTexto);

        // Botón continuar con el mismo estilo que LaunchView
        continuarBtn = crearBoton("CONTINUAR");
        continuarBtn.setBounds(550, 560, 180, 40);
        // Conectar el botón directamente al método continuarASeleccionPersonaje
        continuarBtn.addActionListener(e -> continuarASeleccionPersonaje());
        add(continuarBtn);

        // Controles de ventana
        WindowControls controles = new WindowControls((JFrame) SwingUtilities.getWindowAncestor(this));
        add(controles);
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

    public void setContinuarAction(ActionListener listener) {
        continuarBtn.addActionListener(listener);
    }
    
    // Método para manejar el clic en el botón "Continuar"
    private void continuarASeleccionPersonaje() {
        try {
            // Obtener personajes del usuario actual
            List<Map<String, Object>> personajes = DatabaseManager.obtenerPersonajesUsuario(GameState.idUsuarioActual);
            
            GameFrame gameFrame = (GameFrame) SwingUtilities.getWindowAncestor(this);
            if (gameFrame != null) {
                if (personajes == null || personajes.isEmpty()) {
                    // No tiene personajes, ir a crear uno
                    System.out.println("No hay personajes, mostrando menú de creación");
                    gameFrame.mostrarCharacterMenu();
                } else {
                    // Mostrar selección de personaje
                    System.out.println("Hay personajes, mostrando selección");
                    gameFrame.mostrarSeleccionPersonaje(personajes);
                }
            } else {
                System.err.println("Error: No se pudo obtener la ventana principal");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener personajes: " + e.getMessage());
            e.printStackTrace();
            
            // En caso de error, ir directamente al menú de creación de personaje
            GameFrame gameFrame = (GameFrame) SwingUtilities.getWindowAncestor(this);
            if (gameFrame != null) {
                gameFrame.mostrarCharacterMenu();
            }
        }
    }
}