package view;

import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.Map;

public class InventarioView extends BaseView {

    private JButton salirBtn;
    private boolean volverACombate;
    private MapOverview mapaOrigen;
    private CombatView combateOrigen;
    
    // Color rojo oscuro para los elementos
    private final Color ROJO_OSCURO = new Color(139, 0, 0);
    private final Color ROJO_CLARO = new Color(178, 34, 34);
    private Image backgroundImage;
    private Image characterImage;

    public static void mostrarDesdeMapa(GameFrame frame, MapOverview origen) {
        InventarioView v = new InventarioView(false);
        v.setMapaOrigen(origen);
        frame.cambiarVista(v);
    }

    public static void mostrarDesdeCombate(GameFrame frame, CombatView origen) {
        InventarioView v = new InventarioView(true);
        v.setCombateOrigen(origen);
        frame.cambiarVista(v);
    }

    public InventarioView(boolean desdeCombate) {
        super();
        this.volverACombate = desdeCombate;
        setLayout(new BorderLayout());
        
        // Cargar imagen de fondo
        backgroundImage = cargarImagen("/Resources/screens/inventory_background.png");
        if (backgroundImage == null) {
            // Intentar con fondo alternativo
            backgroundImage = cargarImagen("/Resources/screens/map_background.png");
        }
        
        // Cargar imagen del personaje según la clase
        String clasePersonaje = GameState.jugadorActual != null ? 
            GameState.jugadorActual.clase.toLowerCase() : "caballero";
        
        switch (clasePersonaje) {
            case "ladron":
                characterImage = cargarImagen("/Resources/images/ladron.png");
                break;
            case "caballero":
                characterImage = cargarImagen("/Resources/images/caballero.png");
                break;
            case "clerigo":
                characterImage = cargarImagen("/Resources/images/mago.png");
                break;
            default:
                characterImage = cargarImagen("/Resources/characters/caballero_full.png");
        }

        // Panel principal con fondo personalizado
        JPanel panelPrincipal = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar fondo
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Si no hay imagen, usar un color de fondo
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelPrincipal.setOpaque(false);
        
        // Panel para la imagen del personaje (lado izquierdo)
        JPanel panelPersonaje = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (characterImage != null) {
                    // Calcular dimensiones para mantener la proporción
                    int imgWidth = characterImage.getWidth(this);
                    int imgHeight = characterImage.getHeight(this);
                    
                    if (imgWidth > 0 && imgHeight > 0) {
                        double ratio = (double) imgHeight / imgWidth;
                        int newWidth = Math.min(getWidth() - 40, 300);
                        int newHeight = (int)(newWidth * ratio);
                        
                        // Centrar la imagen en el panel
                        int x = (getWidth() - newWidth) / 2;
                        int y = (getHeight() - newHeight) / 2;
                        
                        g.drawImage(characterImage, x, y, newWidth, newHeight, this);
                    }
                }
            }
        };
        panelPersonaje.setOpaque(false);
        panelPersonaje.setPreferredSize(new Dimension(300, 600));
        
        // Panel para el inventario (lado derecho)
        JPanel panelInventario = new JPanel();
        panelInventario.setLayout(new BoxLayout(panelInventario, BoxLayout.Y_AXIS));
        panelInventario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 50));
        panelInventario.setOpaque(false);

        // Título y nombre
        JLabel titulo = new JLabel("INVENTARIO", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInventario.add(titulo);

        JLabel nombre = new JLabel(GameState.jugadorActual.nombre.toUpperCase(), SwingConstants.CENTER);
        nombre.setFont(new Font("SansSerif", Font.BOLD, 22));
        nombre.setForeground(ROJO_CLARO);
        nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInventario.add(Box.createVerticalStrut(10));
        panelInventario.add(nombre);

        // Capacidad
        Map<String,Integer> inv = GameState.jugadorActual.getInventario();
        int ocupados = inv.values().stream().mapToInt(Integer::intValue).sum();
        JLabel cap = new JLabel("CAPACIDAD " + ocupados + "/10", SwingConstants.CENTER);
        cap.setFont(new Font("SansSerif", Font.BOLD, 18));
        cap.setForeground(Color.WHITE);
        cap.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInventario.add(Box.createVerticalStrut(10));
        panelInventario.add(cap);

        panelInventario.add(Box.createVerticalStrut(30));

        // Lista de objetos
        for (var entry : inv.entrySet()) {
            String key = entry.getKey();
            int cantidad = entry.getValue();

            // OCULTAR GEMA SI ESTÁS EN COMBATE
            if (volverACombate && key.equalsIgnoreCase("Gema de la Suerte")) continue;

            JPanel fila = new JPanel();
            fila.setLayout(new BoxLayout(fila, BoxLayout.X_AXIS));
            fila.setOpaque(false);
            fila.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            fila.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel itemLbl = new JLabel(key.toUpperCase());
            itemLbl.setFont(new Font("SansSerif", Font.BOLD, 16));
            itemLbl.setForeground(Color.WHITE);
            itemLbl.setPreferredSize(new Dimension(200, 30));
            
            JLabel qtyLbl = new JLabel("x" + cantidad);
            qtyLbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
            qtyLbl.setForeground(Color.WHITE);
            qtyLbl.setPreferredSize(new Dimension(50, 30));

            JButton usar = crearBoton("USAR");
            usar.addActionListener(e -> {
                // Verifica si el objeto tendría efecto
                boolean tieneEfecto = true;
                if (key.equalsIgnoreCase("Poción de Vida")) {
                    int hp = GameState.jugadorActual.getHPActual();
                    int hpMax = GameState.jugadorActual.getHPMaximo();
                    if (hp == hpMax) {
                        JOptionPane.showMessageDialog(this, "No tiene efecto. Ya tienes la vida al máximo.");
                        tieneEfecto = false;
                    }
                }

                if (tieneEfecto) {
                    GameState.jugadorActual.usarObjeto(key);
                    GameFrame fr = (GameFrame) SwingUtilities.getWindowAncestor(this);
                    if (volverACombate && combateOrigen != null) {
                        combateOrigen.regresarDeInventario(true);
                        fr.cambiarVista(combateOrigen);
                    } else {
                        fr.cambiarVista(new InventarioView(false));
                    }
                }
            });

            JButton tirar = crearBoton("TIRAR");
            tirar.addActionListener(e -> {
                GameState.jugadorActual.eliminarObjeto(key);
                GameFrame fr = (GameFrame) SwingUtilities.getWindowAncestor(this);
                InventarioView nueva = new InventarioView(volverACombate);
                nueva.setCombateOrigen(combateOrigen);
                nueva.setMapaOrigen(mapaOrigen);
                fr.cambiarVista(nueva);
            });

            fila.add(itemLbl);
            fila.add(Box.createHorizontalStrut(10));
            fila.add(qtyLbl);
            fila.add(Box.createHorizontalStrut(20));
            fila.add(usar);
            fila.add(Box.createHorizontalStrut(10));
            fila.add(tirar);
            fila.add(Box.createHorizontalGlue());
            
            panelInventario.add(fila);
            panelInventario.add(Box.createVerticalStrut(10));
        }

        panelInventario.add(Box.createVerticalGlue());

        // Botón salir
        String textoSalir = volverACombate ? "VOLVER A LA BATALLA" : "SALIR";
        salirBtn = crearBoton(textoSalir);
        salirBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        salirBtn.addActionListener(e -> volver());
        
        JPanel panelBotonSalir = new JPanel();
        panelBotonSalir.setOpaque(false);
        panelBotonSalir.add(salirBtn);
        
        panelInventario.add(Box.createVerticalStrut(20));
        panelInventario.add(panelBotonSalir);

        // Añadir los paneles al panel principal
        panelPrincipal.add(panelPersonaje, BorderLayout.WEST);
        panelPrincipal.add(panelInventario, BorderLayout.CENTER);
        
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(Color.BLACK);
        btn.setForeground(ROJO_OSCURO);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ROJO_OSCURO, 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
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

    private void volver() {
        GameFrame frame = (GameFrame) SwingUtilities.getWindowAncestor(this);
        if (volverACombate && combateOrigen != null) {
            combateOrigen.regresarDeInventario(false);
            frame.cambiarVista(combateOrigen);
        } else if (mapaOrigen != null) {
            frame.cambiarVista(mapaOrigen);
        } else {
            frame.cambiarVista(GameState.mapaActual);
        }
    }

    public void setVolverACombate(boolean flag) { this.volverACombate = flag; }
    public void setMapaOrigen(MapOverview ori) { this.mapaOrigen = ori; }
    public void setCombateOrigen(CombatView ori) { this.combateOrigen = ori; }
    public void setSalirAction(ActionListener al) { salirBtn.addActionListener(al); }
}