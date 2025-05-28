package view;

import model.GameState;
import model.PlayerCombatData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.io.File;

public class ReadyForBattle extends BaseView {

    private JButton volverBtn, adelanteBtn;
    // Color rojo oscuro para los elementos
    private final Color ROJO_OSCURO = new Color(139, 0, 0);
    private Image characterImage;

    public ReadyForBattle() {
        super();
        setLayout(null);
        setBackground(Color.BLACK); // Fondo negro para toda la pantalla

        PlayerCombatData jugador = GameState.jugadorActual;
        String nombre = jugador.nombre;
        String clase = jugador.clase;
        int[] stats = jugador.stats;
        
        // Cargar imagen del personaje
        cargarImagenPersonaje(clase);

        Font tituloFont = new Font("SansSerif", Font.BOLD, 22);
        Font statFont = new Font("SansSerif", Font.BOLD, 16);

        // Título principal
        JLabel tituloLabel = new JLabel("Comienza tu viaje...", SwingConstants.CENTER);
        tituloLabel.setFont(tituloFont);
        tituloLabel.setForeground(ROJO_OSCURO);
        tituloLabel.setBounds(0, 40, 1280, 40);
        add(tituloLabel);

        // Nombre del héroe
        JLabel nombreLabel = new JLabel(nombre.toUpperCase(), SwingConstants.CENTER);
        nombreLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        nombreLabel.setForeground(Color.WHITE);
        nombreLabel.setBounds(0, 80, 1280, 30);
        add(nombreLabel);

        // Clase del personaje
        Color colorPersonaje;
        switch (clase.toLowerCase()) {
            case "ladron":    colorPersonaje = new Color(180, 172, 172); break;
            case "caballero": colorPersonaje = new Color(46, 180, 235); break;
            default:          colorPersonaje = new Color(132, 239, 111); break;
        }

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
                    switch (clase.toLowerCase()) {
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
        panelImagen.setBounds(280, 120, 250, 250);
        add(panelImagen);

        // Panel de stats con estilo mejorado
        JPanel statsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar fondo semitransparente
                g.setColor(new Color(20, 20, 20, 200));
                g.fillRect(0, 0, getWidth(), getHeight());
                
                // Dibujar borde
                g.setColor(ROJO_OSCURO);
                g.drawRect(0, 0, getWidth()-1, getHeight()-1);
            }
        };
        statsPanel.setLayout(null);
        statsPanel.setBounds(580, 120, 300, 250);
        statsPanel.setOpaque(false);
        add(statsPanel);

        // Título del panel de stats
        JLabel statsTitle = new JLabel("ESTADÍSTICAS", SwingConstants.CENTER);
        statsTitle.setFont(statFont);
        statsTitle.setForeground(ROJO_OSCURO);
        statsTitle.setBounds(0, 10, 300, 30);
        statsPanel.add(statsTitle);

        // Stats con barras de progreso
        String[] names = {"HP", "PM", "ATK", "DEF", "EVA", "LUK"};
        int y = 50;
        for (int i = 0; i < names.length; i++) {
            JLabel sName = new JLabel(names[i] + ":");
            sName.setFont(statFont);
            sName.setForeground(ROJO_OSCURO);
            sName.setBounds(20, y, 100, 30);
            statsPanel.add(sName);

            JLabel sVal = new JLabel(String.valueOf(stats[i]));
            sVal.setFont(statFont);
            sVal.setForeground(Color.WHITE);
            sVal.setBounds(80, y, 60, 30);
            statsPanel.add(sVal);
            
            // Barra de progreso visual para la stat
            JProgressBar statBar = new JProgressBar(0, 100);
            statBar.setValue(Math.min(stats[i], 100)); // Limitar a 100 para visualización
            statBar.setForeground(getColorForStat(names[i]));
            statBar.setBackground(new Color(50, 50, 50));
            statBar.setBounds(150, y + 5, 120, 20);
            statsPanel.add(statBar);

            y += 35;
        }

        // Panel para la pregunta y botones
        JPanel accionPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar fondo semitransparente
                g.setColor(new Color(20, 20, 20, 150));
                g.fillRect(0, 0, getWidth(), getHeight());
                
                // Dibujar borde
                g.setColor(ROJO_OSCURO);
                g.drawRect(0, 0, getWidth()-1, getHeight()-1);
            }
        };
        accionPanel.setLayout(null);
        accionPanel.setBounds(280, 390, 600, 150);
        accionPanel.setOpaque(false);
        add(accionPanel);

        // Pregunta
        JLabel pregunta = new JLabel("¿DESEAS COMENZAR LA BATALLA?", SwingConstants.CENTER);
        pregunta.setFont(tituloFont);
        pregunta.setForeground(Color.WHITE);
        pregunta.setBounds(0, 20, 600, 40);
        accionPanel.add(pregunta);

        // Botones con estilo consistente
        volverBtn = crearBoton("VOLVER");
        volverBtn.setBounds(120, 80, 160, 40);
        accionPanel.add(volverBtn);

        adelanteBtn = crearBoton("ADELANTE");
        adelanteBtn.setBounds(320, 80, 160, 40);
        accionPanel.add(adelanteBtn);

        // Controles de ventana
        WindowControls controles = new WindowControls((JFrame) SwingUtilities.getWindowAncestor(this));
        add(controles);
    }
    
    private void cargarImagenPersonaje(String personaje) {
        String rutaImagen = "";
        
        switch (personaje.toLowerCase()) {
            case "ladron":
                rutaImagen = "/Resources/images/ladron.png";
                break;
            case "caballero":
                rutaImagen = "/Resources/images/caballero.png";
                break;
            case "mago", "clerigo":
                rutaImagen = "/Resources/images/mago.png";
                break;
            default:
                return;
        }
        
        try {
            // Método 1: Usando getResource
            URL imageUrl = getClass().getResource(rutaImagen);
            if (imageUrl != null) {
                characterImage = new ImageIcon(imageUrl).getImage();
                return;
            } 
            // Método 2: Usando ClassLoader
            imageUrl = getClass().getClassLoader().getResource(rutaImagen.substring(1));
            if (imageUrl != null) {
                characterImage = new ImageIcon(imageUrl).getImage();
                return;
            }
            // Método 3: Cargar desde archivo
            File file = new File("." + rutaImagen);
            if (file.exists()) {
                characterImage = new ImageIcon(file.getAbsolutePath()).getImage();
                return;
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
                    characterImage = new ImageIcon(file.getAbsolutePath()).getImage();
                    return;
                }
            }
            
            System.err.println("No se pudo cargar la imagen: " + rutaImagen);
            
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen " + rutaImagen + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private Color getColorForStat(String statName) {
        switch (statName) {
            case "HP": return new Color(50, 180, 50);   // Verde para vida
            case "PM": return new Color(180, 50, 220);  // Púrpura para maná
            case "ATK": return new Color(220, 50, 50);  // Rojo para ataque
            case "DEF": return new Color(50, 50, 220);  // Azul para defensa
            case "EVA": return new Color(220, 220, 50); // Amarillo para evasión
            case "LUK": return new Color(220, 150, 50); // Naranja para suerte
            default: return ROJO_OSCURO;
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

    public void setVolverAction(ActionListener listener) {
        volverBtn.addActionListener(listener);
    }

    public void setAdelanteAction(ActionListener listener) {
        adelanteBtn.addActionListener(listener);
    }
}