package view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.io.File;

public class ConfirmPlayer extends BaseView {

    private final String personaje;
    private final GameFrame frame;
    private Image characterImage;
    // Color rojo oscuro para los elementos
    private final Color ROJO_OSCURO = new Color(139, 0, 0);

    public ConfirmPlayer(String personaje, GameFrame frame) {
        super();
        this.personaje = personaje;
        this.frame = frame;

        setLayout(null);
        setBackground(Color.BLACK); // Fondo negro para toda la pantalla
        
        // Cargar imagen del personaje seleccionado
        cargarImagenPersonaje();

        Font textoFont = new Font("SansSerif", Font.BOLD, 22);

        // Color según personaje
        Color colorPersonaje;
        switch (personaje.toLowerCase()) {
            case "ladron" -> colorPersonaje = new Color(180, 172, 172);
            case "caballero" -> colorPersonaje = new Color(46, 180, 235);
            case "mago", "clerigo" -> colorPersonaje = new Color(132, 239, 111);
            default -> colorPersonaje = Color.GRAY;
        }

        // Mensaje superior con estilo mejorado
        JLabel mensaje = new JLabel("¿SERÁS UN NOBLE " + personaje.toUpperCase() + "?", SwingConstants.CENTER);
        mensaje.setFont(textoFont);
        mensaje.setForeground(ROJO_OSCURO);
        mensaje.setBounds(0, 60, 1280, 40);
        add(mensaje);

        // Panel para la imagen del personaje
        JPanel panelImagen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar fondo
                g.setColor(new Color(30, 30, 30)); // Fondo oscuro
                g.fillRect(0, 0, getWidth(), getHeight());
                
                // Dibujar borde
                g.setColor(ROJO_OSCURO);
                g.drawRect(0, 0, getWidth()-1, getHeight()-1);
                g.drawRect(1, 1, getWidth()-3, getHeight()-3);
                
                // Dibujar imagen si existe
                if (characterImage != null) {
                    g.drawImage(characterImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Si no hay imagen, mostrar un texto
                    g.setColor(colorPersonaje);
                    g.setFont(new Font("SansSerif", Font.BOLD, 64));
                    FontMetrics fm = g.getFontMetrics();
                    String letra = "";
                    switch (personaje.toLowerCase()) {
                        case "ladron" -> letra = "A";
                        case "caballero" -> letra = "C";
                        case "mago", "clerigo" -> letra = "CL";
                        default -> letra = "?";
                    }
                    int textWidth = fm.stringWidth(letra);
                    int textHeight = fm.getHeight();
                    g.drawString(letra, (getWidth() - textWidth) / 2, 
                                 (getHeight() - textHeight) / 2 + fm.getAscent());
                }
            }
        };
        panelImagen.setBounds(515, 120, 250, 300);
        add(panelImagen);
        
        // Descripción del personaje
        String descripcion = getDescripcionPersonaje();
        JLabel descLabel = new JLabel("<html><div style='text-align: center;'>" + descripcion + "</div></html>", SwingConstants.CENTER);
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descLabel.setForeground(Color.WHITE);
        descLabel.setBounds(340, 430, 600, 60);
        add(descLabel);

        // Botón Volver con estilo consistente
        JButton btnVolver = crearBoton("VOLVER");
        btnVolver.setBounds(440, 500, 160, 40);
        btnVolver.addActionListener(e -> frame.mostrarCharacterMenu());
        add(btnVolver);

        // Botón Confirmar con estilo consistente
        JButton btnConfirmar = crearBoton("CONFIRMAR");
        btnConfirmar.setBounds(680, 500, 160, 40);
        btnConfirmar.addActionListener(e -> frame.mostrarDataPlayer(personaje));
        add(btnConfirmar);

        // Controles de ventana
        WindowControls controles = new WindowControls(frame);
        add(controles);
    }
    
    private void cargarImagenPersonaje() {
        String rutaImagen = "";
        
        // Determinar la ruta de la imagen según el personaje
        switch (personaje.toLowerCase()) {
            case "ladron" -> rutaImagen = "/Resources/images/ladron.png";
            case "caballero" -> rutaImagen = "/Resources/images/caballero.png";
            case "mago", "clerigo" -> rutaImagen = "/Resources/images/mago.png";
            default -> rutaImagen = "";
        }
        
        if (!rutaImagen.isEmpty()) {
            characterImage = cargarImagen(rutaImagen);
        }
    }
    
    private Image cargarImagen(String rutaImagen) {
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
                rutaSinBarra
            };
            
            for (String ruta : alternativas) {
                file = new File(ruta);
                if (file.exists()) {
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
    
    private String getDescripcionPersonaje() {
        switch (personaje.toLowerCase()) {
            case "ladron":
                return "Ágil y sigiloso. Experto en encontrar tesoros ocultos y evitar trampas. Habilidoso con dagas y arcos.";
            case "caballero":
                return "Fuerte y resistente. Maestro del combate cuerpo a cuerpo. Puede usar todo tipo de armas y armaduras pesadas.";
            case "mago", "clerigo":
                return "Sabio y místico. Domina las artes arcanas y la curación. Débil en combate físico pero poderoso a distancia.";
            default:
                return "Un aventurero misterioso con habilidades desconocidas.";
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
}