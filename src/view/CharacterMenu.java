package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.io.File;
import java.util.List;
import java.util.Map;

import model.GameState;
import model.Jugador;
import model.PlayerCombatData;
import model.database.DatabaseManager;

public class CharacterMenu extends BaseView {

    private JButton ladronBtn;
    private JButton caballeroBtn;
    private JButton clerigoBtn;
    private final Color ROJO_OSCURO = new Color(139, 0, 0);
    private Image ladronImage;
    private Image caballeroImage;
    private Image clerigoImage;
    
    private JTextField txtNombre;

    public CharacterMenu() {
        super();
        setLayout(null);
        setBackground(Color.BLACK); 
        
        cargarImagenes();

        // Título de la ventana
        
        JLabel titulo = new JLabel("SELECCIONA UN PERSONAJE", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(ROJO_OSCURO);
        titulo.setBounds(0, 60, 1280, 40);
        add(titulo);
        
        // Panel para el nombre del personaje
        
        JPanel panelNombre = new JPanel(null);
        panelNombre.setOpaque(false);
        panelNombre.setBounds(440, 120, 400, 60);
        
        JLabel lblNombre = new JLabel("NOMBRE DEL PERSONAJE:");
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(0, 0, 250, 25);
        panelNombre.add(lblNombre);
        
        txtNombre = new JTextField();
        txtNombre.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtNombre.setBounds(0, 30, 400, 30);
        txtNombre.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ROJO_OSCURO, 2),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        txtNombre.setBackground(Color.BLACK);
        txtNombre.setForeground(Color.WHITE);
        txtNombre.setCaretColor(Color.WHITE);
        panelNombre.add(txtNombre);
        
        add(panelNombre);

        JPanel ladronPanel = crearPanelClase(ladronImage, new Color(50, 50, 50), "LADRÓN", 150);
        add(ladronPanel);

        JPanel caballeroPanel = crearPanelClase(caballeroImage, new Color(50, 50, 50), "CABALLERO", 540);
        add(caballeroPanel);

        JPanel clerigoPanel = crearPanelClase(clerigoImage, new Color(50, 50, 50), "CLÉRIGO", 930);
        add(clerigoPanel);

        WindowControls controles = new WindowControls((JFrame) SwingUtilities.getWindowAncestor(this));
        add(controles);
        
        configurarAcciones();
    }
    
    private void configurarAcciones() {

        ladronBtn.addActionListener(e -> crearPersonaje("Ladrón"));
        
        caballeroBtn.addActionListener(e -> crearPersonaje("Caballero"));
        
        clerigoBtn.addActionListener(e -> crearPersonaje("Clérigo"));
    }
    
    private void crearPersonaje(String clase) {
        String nombre = txtNombre.getText().trim();
        
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un nombre para tu personaje.", 
                                         "Nombre requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int[] stats = new int[6];
        
        switch (clase) {
            case "Ladrón":
                stats[0] = 15; // Ataque
                stats[1] = 5;  // Defesna
                stats[2] = 8;  // Evasion
                stats[3] = 80; // Vitalidad
                stats[4] = 5;  // Puntos de magia
                stats[5] = 10; // Suerte
                break;
            case "Caballero":
                stats[0] = 10; 
                stats[1] = 10; 
                stats[2] = 3;  
                stats[3] = 120; 
                stats[4] = 5;   
                stats[5] = 5;   
                break;
            case "Clérigo":
                stats[0] = 8;  
                stats[1] = 7;  
                stats[2] = 5;  
                stats[3] = 150; 
                stats[4] = 15;  
                stats[5] = 7;   
                break;
        }
        
        PlayerCombatData jugadorCombat = new PlayerCombatData(nombre, clase, 1, stats);
        GameState.jugadorActual = jugadorCombat;
        
        GameState.asignarObjetosIniciales();
        
        // Guardar el personaje en la base de datos si hay un usuario activo
        
        if (GameState.idUsuarioActual > 0) {

            Jugador jugadorDB = new Jugador(nombre, clase);
            
            jugadorDB.setHPMaximo(stats[3]);
            jugadorDB.setHPActual(stats[3]);
            jugadorDB.setAtaque(stats[0]);
            jugadorDB.setDefensa(stats[1]);
            
            // Guardar el personaje en la base de datos
            
            boolean guardado = DatabaseManager.guardarPersonaje(GameState.idUsuarioActual, jugadorDB);
            
            if (guardado) {
                System.out.println("Personaje guardado en la base de datos correctamente.");
                
                // Obtener el ID del personaje recién creado
                
                List<Map<String, Object>> personajes = DatabaseManager.obtenerPersonajesUsuario(GameState.idUsuarioActual);
                for (Map<String, Object> personaje : personajes) {
                    if (((String) personaje.get("nombre")).equals(nombre)) {
                        GameState.idPersonajeActual = (int) personaje.get("id");
                        System.out.println("ID del personaje: " + GameState.idPersonajeActual);
                        break;
                    }
                }
                
                // Mapa inicial
                
                ((GameFrame) SwingUtilities.getWindowAncestor(this)).mostrarMapaInicial();
            } else {
                System.err.println("Error al guardar el personaje en la base de datos.");
                JOptionPane.showMessageDialog(this, "Error al guardar el personaje. Inténtalo de nuevo.", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.err.println("No hay usuario activo. No se puede guardar el personaje.");
            
            // Continuar sin guardar en la base de datos (modo sin conexión)
            
            ((GameFrame) SwingUtilities.getWindowAncestor(this)).mostrarMapaInicial();
        }
    }
    
    private void cargarImagenes() {
    	
        String[] rutas = {
            "/Resources/images/ladron.png",
            "/Resources/images/caballero.png",
            "/Resources/images/mago.png"
        };
        
        ladronImage = cargarImagen(rutas[0]);
        caballeroImage = cargarImagen(rutas[1]);
        clerigoImage = cargarImagen(rutas[2]);
    }
    
    private Image cargarImagen(String rutaImagen) {
        try {
        	
            URL imageUrl = getClass().getResource(rutaImagen);
            if (imageUrl != null) {
                return new ImageIcon(imageUrl).getImage();
            } 
            
            imageUrl = getClass().getClassLoader().getResource(rutaImagen.substring(1));
            if (imageUrl != null) {
                return new ImageIcon(imageUrl).getImage();
            }

            File file = new File("." + rutaImagen);
            if (file.exists()) {
                return new ImageIcon(file.getAbsolutePath()).getImage();
            }
            
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

    private JPanel crearPanelClase(Image imagen, Color fondo, String nombre, int x) {
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);
        panel.setBounds(x, 200, 200, 300); // Ajustado la posición Y 

        JPanel imagenPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(fondo);
                g.fillRect(0, 0, getWidth(), getHeight());
                
                g.setColor(ROJO_OSCURO);
                g.drawRect(0, 0, getWidth()-1, getHeight()-1);
                g.drawRect(1, 1, getWidth()-3, getHeight()-3);
                
                if (imagen != null) {
                    g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
                } else {

                    g.setColor(Color.WHITE);
                    g.setFont(new Font("SansSerif", Font.BOLD, 18));
                    FontMetrics fm = g.getFontMetrics();
                    String texto = nombre.substring(0, 1);
                    int textWidth = fm.stringWidth(texto);
                    int textHeight = fm.getHeight();
                    g.drawString(texto, (getWidth() - textWidth) / 2, 
                                 (getHeight() - textHeight) / 2 + fm.getAscent());
                }
            }
        };
        imagenPanel.setBounds(0, 0, 200, 200);
        panel.add(imagenPanel);

        JButton boton = crearBoton(nombre);
        boton.setBounds(20, 220, 160, 40);
        panel.add(boton);

        switch (nombre) {
            case "LADRÓN" -> ladronBtn = boton;
            case "CABALLERO" -> caballeroBtn = boton;
            case "CLÉRIGO" -> clerigoBtn = boton;
        }

        return panel;
    }
    
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setBackground(Color.BLACK); 
        btn.setForeground(ROJO_OSCURO); 
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ROJO_OSCURO, 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Efecto hover
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(20, 20, 20));
                btn.setForeground(new Color(180, 0, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.BLACK);
                btn.setForeground(ROJO_OSCURO);
            }
        });
        
        return btn;
    }

    // Estos métodos se mantienen para compatibilidad con código existente
    
    public void setLadronAction(ActionListener listener) {
        ladronBtn.addActionListener(listener);
    }

    public void setCaballeroAction(ActionListener listener) {
        caballeroBtn.addActionListener(listener);
    }

    public void setClerigoAction(ActionListener listener) {
        clerigoBtn.addActionListener(listener);
    }
}