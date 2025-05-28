package view;

import model.GameState;
import model.Jugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapOverview extends BaseView {

    private final List<String> nodos = new ArrayList<>();
    private final ArrayList<JButton> botones = new ArrayList<>();
    private int nodoActual = 0;
    private int mapaActual = 1;
    private final GameFrame frame;
    private JButton avanzarBtn;
    private JPanel panelScroll;
    private File archivoMapa;
    private Image backgroundImage;
    private JLabel lblZona;
    private String nombreZona;
    
    // Mapa para almacenar las imágenes originales de cada nodo
    private final Map<Integer, Image> imagenesOriginales = new HashMap<>();
    // Lista para rastrear los nodos de enemigos completados
    private final List<Integer> enemigosCompletados = new ArrayList<>();
    
    // Color rojo oscuro para los elementos
    private final Color ROJO_OSCURO = new Color(139, 0, 0);
	private GameState GameState;

    public MapOverview(GameFrame frame) {
        this(frame, "PISO_1.txt", "PRIMERA ZONA: calles abandonadas");
    }
    
    public MapOverview(GameFrame frame, String nombreMapa, String nombreZona) {
        super();
        this.frame = frame;
        this.nombreZona = nombreZona;
        setLayout(new BorderLayout());
        
        // Cargar imagen de fondo según la zona
        if (nombreZona.contains("PRIMERA ZONA")) {
            backgroundImage = cargarImagenFondo("/Resources/screens/Ancient_city_background.png");
        } else if (nombreZona.contains("SEGUNDA ZONA")) {
            backgroundImage = cargarImagenFondo("/Resources/screens/Gauntlet_background.png");
        } else if (nombreZona.contains("TERCERA ZONA")) {
            backgroundImage = cargarImagenFondo("/Resources/screens/Blood_pit_background.png");
        } else if (nombreZona.contains("ÚLTIMA ZONA")) {
            backgroundImage = cargarImagenFondo("/Resources/screens/godOfFear.jpg");
        } else {
            backgroundImage = cargarImagenFondo("/Resources/screens/Void_background2.png");
        }

        // Intentar encontrar el archivo de mapa en diferentes ubicaciones
        archivoMapa = new File("Resources/mapas/" + nombreMapa);
        if (!archivoMapa.exists()) {
            // Intentar con 'resources' en minúscula
            archivoMapa = new File("resources/mapas/" + nombreMapa);
            if (!archivoMapa.exists()) {
                // Intentar con la ruta completa incluyendo MOONSCORCH
                archivoMapa = new File("MOONSCORCH/Resources/mapas/" + nombreMapa);
                if (!archivoMapa.exists()) {
                    JOptionPane.showMessageDialog(this, "No se pudo encontrar el archivo del mapa. " + 
                        "Rutas probadas: Resources/mapas, resources/mapas, MOONSCORCH/Resources/mapas");
                }
            }
        }

        System.out.println("Archivo de mapa encontrado en: " + archivoMapa.getAbsolutePath());
        cargarMapaActual();

        // Panel para el título de la zona
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Etiqueta de zona en la esquina superior izquierda
        lblZona = new JLabel(nombreZona);
        lblZona.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblZona.setForeground(Color.BLACK);
        lblZona.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        panelTitulo.add(lblZona, BorderLayout.WEST);
        
        add(panelTitulo, BorderLayout.NORTH);

        // Crear un panel con fondo personalizado
        panelScroll = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar fondo
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Si no hay imagen, usar un color de fondo
                    g.setColor(new Color(20, 20, 20));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        
        int espacio = 280;  // Aumentado de 220 a 280
        int centroY = 300;  // Aumentado de 250 a 300 para centrar verticalmente
        int radio = 190;    // Aumentado de 80 a 120
        int margenInicial = 200;  // Margen inicial para centrar horizontalmente

        // Calcular ancho total necesario
        int anchoTotal = nodos.size() * espacio + margenInicial * 2;
        panelScroll.setPreferredSize(new Dimension(anchoTotal, 600)); // Aumentado de 500 a 600

        for (int i = 0; i < nodos.size(); i++) {
            JButton btn = crearBotonNodo(nodos.get(i), margenInicial + i * espacio + (espacio - radio)/2, centroY - radio/2, radio);
            botones.add(btn);
            panelScroll.add(btn);
        }

        JScrollPane scrollPane = new JScrollPane(panelScroll);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setBackground(Color.BLACK);
        scrollPane.setBorder(BorderFactory.createLineBorder(ROJO_OSCURO, 2));
        add(scrollPane, BorderLayout.CENTER);

        // Panel de controles con estilo consistente
        JPanel controles = new JPanel();
        controles.setBackground(Color.BLACK);
        controles.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, ROJO_OSCURO));
        
        avanzarBtn = crearBoton("INICIAR");
        avanzarBtn.addActionListener(this::accionAvanzar);
        controles.add(avanzarBtn);

        JButton btnInventario = crearBoton("INVENTARIO");
        btnInventario.addActionListener(e -> InventarioView.mostrarDesdeMapa(frame, this));
        controles.add(btnInventario);

        add(controles, BorderLayout.SOUTH);
        actualizarNodoActual();
    }
    
    private JButton crearBotonNodo(String tipoNodo, int x, int y, int tamanio) {
        JButton btn = new JButton();
        btn.setBounds(x, y, tamanio, tamanio);
        btn.setFocusPainted(false);
        btn.setEnabled(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        btn.setContentAreaFilled(false);
        
        // Cargar la imagen correspondiente según el tipo de nodo
        Image imagen = null;
        String rutaImagen = "";
        
        switch (tipoNodo) {
            case "JUGADOR":
                // Usar la imagen del personaje seleccionado
                String clasePersonaje = GameState.jugadorActual != null ? 
                    GameState.jugadorActual.clase.toLowerCase() : "caballero";
                
                switch (clasePersonaje) {
                    case "ladron":
                        rutaImagen = "/Resources/nodes/ladron.png";
                        break;
                    case "caballero":
                        rutaImagen = "/Resources/nodes/caballero.png";
                        break;
                    case "mago", "clerigo":
                        rutaImagen = "/Resources/nodes/mago.png";
                        break;
                    default:
                        rutaImagen = "/Resources/nodes/player_default.png";
                }
                break;
            case "ENEMIGO":
                rutaImagen = "/Resources/enemies/Red_man.png"; 
                break;
            case "ENEMIGO2":
                rutaImagen = "/Resources/enemies/Molded2.png";
                break;
            case "ENEMIGO3":
                rutaImagen = "/Resources/enemies/Skeleton_(Enemy2).png";
                break;
            case "ENEMIGO4":
                rutaImagen = "/Resources/enemies/Crow_Mauler2.png";
                break;
            case "ENEMIGO5":
                rutaImagen = "/Resources/enemies/Traces_of_Gro-goroth.png";
                break;
            case "ENEMIGO6":
                rutaImagen = "/Resources/enemies/Moonless_Beast.png";
                break;
            case "TESORO":
                rutaImagen = "/Resources/enemies/cofre.png";
                break;
            case "PUERTA":
                rutaImagen = "/Resources/enemies/Door_wooden1.png";
                break;
            case "JEFE FINAL":
                rutaImagen = "/Resources/enemies/Kaiser.png";
                break;
            default:
                rutaImagen = "/Resources/nodes/unknown.png";
        }
        
        imagen = cargarImagenFondo(rutaImagen);
        
        if (imagen != null) {
            Image imagenEscalada = imagen.getScaledInstance(tamanio, tamanio, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(imagenEscalada));
            
            // Guardar la imagen original en nuestro mapa
            int index = botones.size(); // El índice que tendrá este botón
            imagenesOriginales.put(index, imagenEscalada);
        } else {
            // Si no se puede cargar la imagen, usar colores como respaldo
            btn.setOpaque(true);
            Color c = switch (tipoNodo) {
                case "JUGADOR" -> new Color(0, 153, 51);  // Verde
                case "ENEMIGO" -> new Color(153, 0, 0);   // Rojo
                case "ENEMIGO2" -> new Color(204, 51, 0); // Naranja-rojizo
                case "ENEMIGO3" -> new Color(153, 51, 153); // Púrpura
                case "ENEMIGO4" -> new Color(51, 102, 0);   // Verde oscuro
                case "ENEMIGO5" -> new Color(0, 102, 153);  // Azul oscuro
                case "ENEMIGO6" -> new Color(102, 51, 0);   // Marrón
                case "TESORO" -> new Color(255, 204, 0);    // Amarillo
                case "PUERTA" -> new Color(0, 153, 255);    // Azul
                case "JEFE FINAL" -> new Color(102, 0, 153); // Púrpura oscuro
                default -> Color.GRAY;
            };
            btn.setBackground(c);
            btn.setText(tipoNodo.substring(0, 1));
            btn.setFont(new Font("SansSerif", Font.BOLD, 16));
            btn.setForeground(Color.WHITE);
        }
        
        // Añadir tooltip para identificar el nodo
        btn.setToolTipText(tipoNodo);
        
        return btn;
    }
    
    public void guardarProgreso() {
        if (GameState.idPersonajeActual > 0) {
            String nombreMapa = archivoMapa.getName();
            
            // Actualizar el estado global del juego
            GameState.nombreMapaActual = nombreMapa;
            GameState.nodoActual = nodoActual;
            GameState.nombreZonaActual = nombreZona;
            
            // Guardar en la base de datos
            model.database.DatabaseManager.guardarProgreso(
                GameState.idPersonajeActual,
                nombreMapa,
                nodoActual,
                nombreZona,
                false // completado
            );
            
            System.out.println("Progreso guardado: Mapa=" + nombreMapa + ", Nodo=" + nodoActual + ", Zona=" + nombreZona);
        } else {
            System.out.println("No se puede guardar progreso: No hay personaje activo en la base de datos");
        }
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
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(new Color(20, 20, 20)); // Negro ligeramente más claro al pasar el ratón
                btn.setForeground(new Color(180, 0, 0)); // Rojo más brillante
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(Color.BLACK);
                btn.setForeground(ROJO_OSCURO);
            }
        });
        
        return btn;
    }

    private void cargarMapaActual() {
        if (!archivoMapa.exists()) {
            JOptionPane.showMessageDialog(this, "No se encontró el archivo del mapa.");
            return;
        }
        
        // Limpiar los nodos anteriores si los hubiera
        nodos.clear();
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivoMapa))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("[MAPA_") && linea.contains("PASADO:NO")) {
                    mapaActual = Integer.parseInt(linea.substring(6, linea.indexOf(']')));
                    String contenido = linea.substring(linea.indexOf('(') + 1, linea.indexOf(')'));
                    for (String nodo : contenido.split(":")) {
                        switch (nodo) {
                            case "I" -> nodos.add("JUGADOR");
                            case "E" -> nodos.add("ENEMIGO");
                            case "E2" -> nodos.add("ENEMIGO2");
                            case "E3" -> nodos.add("ENEMIGO3");
                            case "E4" -> nodos.add("ENEMIGO4");
                            case "E5" -> nodos.add("ENEMIGO5");
                            case "E6" -> nodos.add("ENEMIGO6");
                            case "T" -> nodos.add("TESORO");
                            case "P" -> nodos.add("PUERTA");
                            case "B" -> nodos.add("JEFE FINAL");
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer mapa: " + e.getMessage());
        }
    }

    private void accionAvanzar(ActionEvent e) {
        avanzar();
    }

    public void avanzar() {
        if (nodoActual >= nodos.size()) return;
        String tipo = nodos.get(nodoActual);

        switch (tipo) {
            case "JUGADOR" -> {
                JOptionPane.showMessageDialog(this, "Eres " + GameState.jugadorActual.nombre + ". ¡Comienza tu recorrido!");
                nodoActual++;
                actualizarNodoActual();
            }
            case "ENEMIGO", "ENEMIGO2", "ENEMIGO3", "ENEMIGO4", "ENEMIGO5", "ENEMIGO6", "JEFE FINAL" -> {
                GameState.mapaActual = this;
                // Usar el tipo de nodo actual para que CombatView pueda mostrar la imagen correcta
                GameState.tipoEnemigoActual = tipo;
                ((GameFrame) SwingUtilities.getWindowAncestor(this)).mostrarCombatView(this);
            }
            case "TESORO" -> {
                nodoActual++;
                actualizarNodoActual();
                ((GameFrame) SwingUtilities.getWindowAncestor(this)).mostrarTesoro(this);
            }
            case "PUERTA" -> {
                nodoActual++;
                actualizarNodoActual();
                actualizarArchivoMapa();
                
                // Determinar cuál es la siguiente zona basada en el mapa actual
                String siguienteMapa;
                String siguienteZona;
                
                if (archivoMapa.getName().equals("PISO_1.txt")) {
                    siguienteMapa = "PISO_2.txt";
                    siguienteZona = "SEGUNDA ZONA: tuneles olvidados";
                } else if (archivoMapa.getName().equals("PISO_2.txt")) {
                    siguienteMapa = "PISO_3.txt";
                    siguienteZona = "TERCERA ZONA: laberinto de sangre";
                } else if (archivoMapa.getName().equals("PISO_3.txt")) {
                    siguienteMapa = "PISO_4.txt";
                    siguienteZona = "ÚLTIMA ZONA: altar de la oscuridad";
                } else {
                    // Si no hay más mapas definidos, volver al primero (no debería ocurrir)
                    siguienteMapa = "PISO_1.txt";
                    siguienteZona = "PRIMERA ZONA: calles abandonadas";
                }
                
                // Crear un nuevo MapOverview con el siguiente mapa y zona
                MapOverview siguienteNivel = new MapOverview(frame, siguienteMapa, siguienteZona);
                ((GameFrame) SwingUtilities.getWindowAncestor(this)).cambiarVista(siguienteNivel);
            }
        }
    }

    public void verificarFinDeMapa() {
        System.out.println("DEBUG: verificarFinDeMapa() - nodoActual=" + nodoActual + ", nodos.size()=" + nodos.size());
        
        if (nodos.size() > 0) {
            System.out.println("DEBUG: Último nodo es: " + nodos.get(nodos.size() - 1));
        }
        
        // Verificar si el último nodo completado era un JEFE FINAL
        if (nodoActual > 0 && nodoActual == nodos.size() && 
            nodos.size() > 0 && nodos.get(nodos.size() - 1).equals("JEFE FINAL")) {
            
            System.out.println("Has derrotado al jefe final. Mostrando pantalla de fin de juego.");
            
            // Actualizar el archivo para marcar el juego como completado
            try {
                List<String> lineas = new ArrayList<>();
                try (BufferedReader br = new BufferedReader(new FileReader(archivoMapa))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        if (linea.startsWith("[MAPA_" + mapaActual + "]")) {
                            linea = linea.replace("PASADO:NO", "PASADO:SI");
                        }
                        if (linea.startsWith("[END_GAME:NO")) {
                            linea = "[END_GAME:SI]";
                        }
                        lineas.add(linea);
                    }
                }

                try (PrintWriter pw = new PrintWriter(new FileWriter(archivoMapa))) {
                    for (String ln : lineas) pw.println(ln);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar estado del juego: " + e.getMessage());
            }
            
            // Mostrar la vista de fin de juego
            System.out.println("DEBUG: Intentando mostrar EndgameView desde verificarFinDeMapa");
            JFrame f = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (f instanceof GameFrame frame) {
                GameState dummyState = new GameState() {
    @Override
    public Jugador getJugador() {
        return GameState.convertirAJugador(GameState.jugadorActual);
    }

    @Override
    public LocalDateTime getGameStartTime() {
        return LocalDateTime.now().minusMinutes(47); // O usa una variable real si la tienes
    }

    @Override
    public Object getEnemiesDefeated() {
        return 20; // Sustituye con un dato real si lo guardas
    }

    @Override
    public Object getTreasuresFound() {
        return 5;
    }

    @Override
    public Object getPotionsUsed() {
        return 3;
    }
};

frame.cambiarVista(new GameCompletionView());
                System.out.println("DEBUG: EndgameView mostrada con éxito");
            } else {
                System.err.println("Error: No se pudo obtener la referencia a GameFrame");
                // Intento alternativo
                if (this.frame != null) {
                    this.GameState = new GameState() {
    @Override
    public Jugador getJugador() {
        return GameState.convertirAJugador(GameState.jugadorActual);
    }

    @Override
    public LocalDateTime getGameStartTime() {
        return LocalDateTime.now().minusMinutes(47); // O usa una variable real si la tienes
    }

    @Override
    public Object getEnemiesDefeated() {
        return 20; // Sustituye con un dato real si lo guardas
    }

    @Override
    public Object getTreasuresFound() {
        return 5;
    }

    @Override
    public Object getPotionsUsed() {
        return 3;
    }
};

frame.cambiarVista(new GameCompletionView());
                } else {
                    System.err.println("Error crítico: No hay referencia a GameFrame");
                    JOptionPane.showMessageDialog(this, "¡Felicidades! Has completado el juego.");
                }
            }
            return;
        }
    }
    
    private void actualizarArchivoMapa() {
        try {
            List<String> lineas = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(archivoMapa))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (linea.startsWith("[MAPA_" + mapaActual + "]")) {
                        linea = linea.replace("PASADO:NO", "PASADO:SI");
                    }
                    lineas.add(linea);
                }
            }

            try (PrintWriter pw = new PrintWriter(new FileWriter(archivoMapa))) {
                for (String ln : lineas) pw.println(ln);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar progreso: " + e.getMessage());
        }
    }

    private void actualizarNodoActual() {
        // Desactivar todos los botones primero
        for (int i = 0; i < botones.size(); i++) {
            JButton b = botones.get(i);
            b.setEnabled(false);
            b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            
            // Restaurar la imagen original para todos los nodos
            if (imagenesOriginales.containsKey(i)) {
                b.setIcon(new ImageIcon(imagenesOriginales.get(i)));
            }
            
            // Aplicar filtro gris solo a los enemigos derrotados
            if (enemigosCompletados.contains(i)) {
                String tipoNodo = nodos.get(i);
                if (tipoNodo.startsWith("ENEMIGO") || tipoNodo.equals("JEFE FINAL")) {
                    if (imagenesOriginales.containsKey(i)) {
                        Image grayImg = createGrayImage(imagenesOriginales.get(i));
                        b.setIcon(new ImageIcon(grayImg));
                    }
                }
            }
        }

        // Activar y resaltar solo el nodo actual
        if (nodoActual < botones.size()) {
            JButton actual = botones.get(nodoActual);
            actual.setEnabled(true);
            actual.setBorder(BorderFactory.createLineBorder(ROJO_OSCURO, 4));
        }

        avanzarBtn.setText(nodoActual == 0 ? "INICIAR" : "AVANZAR");
    }

    public void marcarNodoComoCompletado(int idx) {
        if (idx >= 0 && idx < botones.size()) {
            String tipoNodo = nodos.get(idx);
            
            // Solo marcar como completado si es un enemigo
            if (tipoNodo.startsWith("ENEMIGO") || tipoNodo.equals("JEFE FINAL")) {
                // Añadir a la lista de enemigos completados
                if (!enemigosCompletados.contains(idx)) {
                    enemigosCompletados.add(idx);
                }
                
                // Aplicar filtro gris
                JButton b = botones.get(idx);
                if (imagenesOriginales.containsKey(idx)) {
                    Image grayImg = createGrayImage(imagenesOriginales.get(idx));
                    b.setIcon(new ImageIcon(grayImg));
                }
            }
        }
    }
    
    private Image createGrayImage(Image original) {
        // Crear una versión semitransparente y oscurecida de la imagen
        int width = original.getWidth(null);
        int height = original.getHeight(null);
        
        // Si no podemos obtener las dimensiones, devolver la imagen original
        if (width <= 0 || height <= 0) {
            return original;
        }
        
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = result.createGraphics();
        
        // Dibujar la imagen original
        g2d.drawImage(original, 0, 0, null);
        
        // Aplicar un overlay semitransparente gris
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.setColor(new Color(100, 100, 100, 128));
        g2d.fillRect(0, 0, width, height);
        
        g2d.dispose();
        return result;
    }

    public void incrementarNodoActual() {
        System.out.println("DEBUG: incrementarNodoActual() - Antes: nodoActual=" + nodoActual);
        
        // Solo marcar como completado si es un enemigo
        String tipoNodo = nodos.get(nodoActual);
        if (tipoNodo.startsWith("ENEMIGO") || tipoNodo.equals("JEFE FINAL")) {
            marcarNodoComoCompletado(nodoActual);
        }
        
        // Incrementar el nodo actual
        nodoActual++;
        actualizarNodoActual();
        
        System.out.println("DEBUG: incrementarNodoActual() - Después: nodoActual=" + nodoActual);
    }
    
    public List<String> getNodos() {
        return nodos;
    }
    
    // Método para obtener el tipo de un nodo específico
    public String getTipoNodo(int indice) {
        if (indice >= 0 && indice < nodos.size()) {
            return nodos.get(indice);
        }
        return "";
    }

    public int getNodoActual() {
        return nodoActual;
    }
    
    public String getNombreZona() {
        return nombreZona;
    }
}