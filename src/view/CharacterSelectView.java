package view;

import model.GameState;
import model.Jugador;
import model.database.DatabaseManager;
import model.PlayerCombatData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class CharacterSelectView extends BaseView {
    
    private List<Map<String, Object>> personajes;
    private Image backgroundImage;
    
    private final Color ROJO_OSCURO = new Color(139, 0, 0);
    private final Color ROJO_CLARO = new Color(178, 34, 34);
    
    public CharacterSelectView(List<Map<String, Object>> personajes) {
        super();
        this.personajes = personajes;
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelPrincipal.setOpaque(false);
        

        JPanel panelSeleccion = new JPanel();
        panelSeleccion.setLayout(new BoxLayout(panelSeleccion, BoxLayout.Y_AXIS));
        panelSeleccion.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panelSeleccion.setOpaque(false);
        

        JLabel titulo = new JLabel("SELECCIONA TU PERSONAJE", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(ROJO_CLARO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSeleccion.add(titulo);
        panelSeleccion.add(Box.createVerticalStrut(30));
        

        JPanel panelPersonajes = new JPanel();
        panelPersonajes.setLayout(new BoxLayout(panelPersonajes, BoxLayout.Y_AXIS));
        panelPersonajes.setOpaque(false);
        panelPersonajes.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        if (personajes.isEmpty()) {
            JLabel lblNoPersonajes = new JLabel("No tienes personajes. Crea uno nuevo.");
            lblNoPersonajes.setFont(new Font("SansSerif", Font.BOLD, 18));
            lblNoPersonajes.setForeground(Color.WHITE);
            lblNoPersonajes.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPersonajes.add(lblNoPersonajes);
        } else {
            for (Map<String, Object> personaje : personajes) {
                JPanel panelPersonaje = new JPanel();
                panelPersonaje.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
                panelPersonaje.setOpaque(false);
                panelPersonaje.setBorder(BorderFactory.createLineBorder(ROJO_OSCURO, 2));
                panelPersonaje.setMaximumSize(new Dimension(500, 80));
                
                String nombre = (String) personaje.get("nombre");
                String clase = (String) personaje.get("clase");
                int nivel = (int) personaje.get("nivel");
                
                JLabel lblNombre = new JLabel(nombre);
                lblNombre.setFont(new Font("SansSerif", Font.BOLD, 18));
                lblNombre.setForeground(Color.WHITE);
                
                JLabel lblClase = new JLabel("Clase: " + clase);
                lblClase.setFont(new Font("SansSerif", Font.PLAIN, 16));
                lblClase.setForeground(Color.WHITE);
                
                JLabel lblNivel = new JLabel("Nivel: " + nivel);
                lblNivel.setFont(new Font("SansSerif", Font.PLAIN, 16));
                lblNivel.setForeground(Color.WHITE);
                
                JButton btnSeleccionar = crearBoton("SELECCIONAR");
                btnSeleccionar.addActionListener(e -> seleccionarPersonaje((int) personaje.get("id")));
                
                panelPersonaje.add(lblNombre);
                panelPersonaje.add(lblClase);
                panelPersonaje.add(lblNivel);
                panelPersonaje.add(btnSeleccionar);
                
                panelPersonajes.add(panelPersonaje);
                panelPersonajes.add(Box.createVerticalStrut(10));
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(panelPersonajes);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(500, 300));
        
        panelSeleccion.add(scrollPane);
        panelSeleccion.add(Box.createVerticalStrut(30));
        
        // Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton btnNuevo = crearBoton("CREAR NUEVO PERSONAJE");
        btnNuevo.addActionListener(e -> crearNuevoPersonaje());
        
        JButton btnVolver = crearBoton("VOLVER");
        btnVolver.addActionListener(e -> volverLogin());
        
        panelBotones.add(btnNuevo);
        panelBotones.add(Box.createHorizontalStrut(20));
        panelBotones.add(btnVolver);
        
        panelSeleccion.add(panelBotones);
        
        // Añadir panel de selección al panel principal
        panelPrincipal.add(panelSeleccion, BorderLayout.CENTER);
        
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
    private void seleccionarPersonaje(int idPersonaje) {
        GameState.idPersonajeActual = idPersonaje;
        
        // Cargar el personaje
        Jugador jugador = DatabaseManager.cargarPersonaje(idPersonaje);
        
        if (jugador != null) {
        	
            // Convertir Jugador a PlayerCombatData
        	
            int[] stats = new int[6];
            stats[0] = jugador.getAtaque();    // Ataque
            stats[1] = jugador.getDefensa();   // Defensa
            stats[2] = 5;                      // Evasion
            stats[3] = jugador.getHPMaximo();  // Vida
            stats[4] = 10;                     // Puntos de maná
            stats[5] = 5;                      // Suerte
            
            // Crear PlayerCombatData con el constructor correcto
            
            PlayerCombatData playerData = new PlayerCombatData(
                jugador.nombre,
                jugador.clase,
                jugador.nivel,
                stats
            );
            
            // Copiar inventario si es necesario
            
            for (Map.Entry<String, Integer> entry : jugador.getInventario().entrySet()) {
                playerData.agregarObjeto(entry.getKey(), entry.getValue());
            }
            
            // Asignar a GameState
            
            GameState.jugadorActual = playerData;
            
            // Cargar progreso
            
            Map<String, Object> progreso = DatabaseManager.cargarProgreso(idPersonaje);
            
            if (!progreso.isEmpty()) {
                String mapa = (String) progreso.get("mapa");
                int nodoActual = (int) progreso.get("nodoActual");
                String zona = (String) progreso.get("zona");
                boolean completado = (boolean) progreso.get("completado");
                
                // Actualizar el estado global
                
                GameState.nombreMapaActual = mapa;
                GameState.nodoActual = nodoActual;
                GameState.nombreZonaActual = zona;
                
                // Crear el mapa con el progreso guardado
                
                MapOverview mapaView = new MapOverview((GameFrame) SwingUtilities.getWindowAncestor(this), mapa, zona);
                
                // Establecer el nodo actual
                
                for (int i = 0; i < nodoActual; i++) {
                    mapaView.incrementarNodoActual();
                }
                
                // Mostrar el mapa
                
                ((GameFrame) SwingUtilities.getWindowAncestor(this)).cambiarVista(mapaView);
            } else {
                // Si no hay progreso, mostrar el mapa inicial
            	
                ((GameFrame) SwingUtilities.getWindowAncestor(this)).mostrarMapaInicial();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error al cargar el personaje.");
        }
    }
    
    private void crearNuevoPersonaje() {
        ((GameFrame) SwingUtilities.getWindowAncestor(this)).mostrarCharacterMenu();
    }
    
    private void volverLogin() {
        ((GameFrame) SwingUtilities.getWindowAncestor(this)).mostrarLogin();
    }
    
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
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
                btn.setForeground(ROJO_CLARO);
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(Color.BLACK);
                btn.setForeground(ROJO_OSCURO);
            }
        });
        
        return btn;
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
}