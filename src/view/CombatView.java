package view;

import controller.CombatController;
import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombatView extends BaseView {

    // Color rojo oscuro para los elementos
	
    private final Color ROJO_OSCURO = new Color(139, 0, 0);
    
    public final CombatLog log;
    private final JButton btnLanzarDado;
    private final JButton btnInventario;
    private final JPanel panelAcciones;
    private final JLabel lblTurno;
    private final JLabel lblDadoJugador;
    private final JLabel lblDadoEnemigo;
    private final JProgressBar barraVidaJugador;
    private final JProgressBar barraExpJugador;
    private final JLabel lblNombreJugador;
    private final JLabel lblNombreEnemigo;
    private final JProgressBar barraVidaEnemigo;
    
    // Nuevos componentes para imágenes
    
    private final JLabel imagenJugador;
    private final JLabel imagenEnemigo;

    private final CombatController controller;
    private final MapOverview origen;

    public String accionJugador = null;
    private String accionEnemigo = null;
    private int puntosEspecial = 0;
    private boolean esperandoResolucionTurno = false;
    private boolean usoObjeto = false;
    
    // Imagen de fondo
    
    private Image backgroundImage;

    public CombatView(MapOverview origen) {
        super();
        this.origen = origen;
        setLayout(null);
        
        // Cargar imagen de fondo
        backgroundImage = cargarImagenFondo("/Resources/backgrounds/combat_background.png");
        
        // Establecer el panel con fondo personalizado
        
        setOpaque(false);
        
        controller = new CombatController(GameState.jugadorActual, GameState.generarEnemigo());

        // Estilo para todos los componentes
        
        UIManager.put("ProgressBar.foreground", ROJO_OSCURO);
        UIManager.put("ProgressBar.background", new Color(30, 30, 30));
        UIManager.put("ProgressBar.selectionForeground", Color.WHITE);
        UIManager.put("ProgressBar.selectionBackground", ROJO_OSCURO);

        lblTurno = crearLabel("TURNO DE COMBATE", 540, 20, 200, 30, 20);
        lblTurno.setHorizontalAlignment(SwingConstants.CENTER);
        lblTurno.setForeground(ROJO_OSCURO);
        add(lblTurno);

        // Panel del jugador (izquierda)
        
        JPanel panelJugador = new JPanel(null);
        panelJugador.setBounds(30, 70, 300, 300);
        panelJugador.setOpaque(false);
        add(panelJugador);
        
        lblNombreJugador = crearLabel(GameState.jugadorActual.nombre, 0, 0, 200, 30, 18);
        lblNombreJugador.setForeground(Color.WHITE);
        panelJugador.add(lblNombreJugador);

        barraVidaJugador = new JProgressBar(0, GameState.jugadorActual.getHPMaximo());
        barraVidaJugador.setValue(GameState.jugadorActual.getHPActual());
        barraVidaJugador.setStringPainted(true);
        barraVidaJugador.setBounds(0, 40, 200, 25);
        barraVidaJugador.setForeground(ROJO_OSCURO);
        barraVidaJugador.setBorder(BorderFactory.createLineBorder(ROJO_OSCURO, 1));
        panelJugador.add(barraVidaJugador);

        barraExpJugador = new JProgressBar(0, 100);
        barraExpJugador.setValue(GameState.jugadorActual.getExperiencia());
        barraExpJugador.setStringPainted(true);
        barraExpJugador.setBounds(0, 75, 200, 20);
        barraExpJugador.setForeground(new Color(0, 102, 204)); // Azul para EXP
        barraExpJugador.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1));
        panelJugador.add(barraExpJugador);

        btnInventario = crearBoton("INVENTARIO");
        btnInventario.setBounds(0, 105, 200, 35);
        btnInventario.setVisible(false);
        btnInventario.addActionListener(e -> abrirInventario());
        panelJugador.add(btnInventario);

        lblDadoJugador = crearLabel("Dado Jugador: -", 0, 150, 200, 25, 14);
        lblDadoJugador.setHorizontalAlignment(SwingConstants.CENTER);
        lblDadoJugador.setForeground(Color.WHITE);
        panelJugador.add(lblDadoJugador);
        
        // Imagen del jugador
        
        imagenJugador = new JLabel();
        imagenJugador.setBounds(0, 180, 200, 200);
        imagenJugador.setHorizontalAlignment(SwingConstants.CENTER);
        panelJugador.add(imagenJugador);
        
        // Cargar imagen del jugador según su clase
        
        cargarImagenJugador();

        // Panel del enemigo (derecha)
        
        JPanel panelEnemigo = new JPanel(null);
        panelEnemigo.setBounds(950, 70, 300, 300);
        panelEnemigo.setOpaque(false);
        add(panelEnemigo);
        
        lblNombreEnemigo = crearLabel(controller.getEnemigo().getNombre(), 100, 0, 200, 30, 18);
        lblNombreEnemigo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNombreEnemigo.setForeground(Color.WHITE);
        panelEnemigo.add(lblNombreEnemigo);

        barraVidaEnemigo = new JProgressBar(0, controller.getEnemigo().getHPMaximo());
        barraVidaEnemigo.setValue(controller.getEnemigo().getHPActual());
        barraVidaEnemigo.setStringPainted(true);
        barraVidaEnemigo.setBounds(100, 40, 200, 25);
        barraVidaEnemigo.setForeground(ROJO_OSCURO);
        barraVidaEnemigo.setBorder(BorderFactory.createLineBorder(ROJO_OSCURO, 1));
        panelEnemigo.add(barraVidaEnemigo);

        lblDadoEnemigo = crearLabel("Dado Enemigo: -", 100, 150, 200, 25, 14);
        lblDadoEnemigo.setHorizontalAlignment(SwingConstants.CENTER);
        lblDadoEnemigo.setForeground(Color.WHITE);
        panelEnemigo.add(lblDadoEnemigo);
        
        // Imagen del enemigo
        
        imagenEnemigo = new JLabel();
        imagenEnemigo.setBounds(100, 180, 200, 200);
        imagenEnemigo.setHorizontalAlignment(SwingConstants.CENTER);
        panelEnemigo.add(imagenEnemigo);
        
        // Cargar imagen del enemigo según su tipo
        
        cargarImagenEnemigo();

        btnLanzarDado = crearBoton("LANZAR DADO");
        btnLanzarDado.setBounds(540, 500, 200, 40);
        btnLanzarDado.addActionListener(e -> lanzarDado());
        add(btnLanzarDado);

        // Panel de acciones con estilo
        
        panelAcciones = new JPanel(new GridLayout(2, 3, 10, 10));
        panelAcciones.setBounds(400, 550, 480, 100);
        panelAcciones.setOpaque(false);
        add(panelAcciones);

        // Log de combate con estilo
        
        log = new CombatLog();
        log.setBounds(300, 270, 680, 220);
        log.setBackground(new Color(10, 10, 10, 200)); // Fondo semi-transparente
        log.setBorder(BorderFactory.createLineBorder(ROJO_OSCURO, 2));
        add(log);

        add(new WindowControls((JFrame) SwingUtilities.getWindowAncestor(this)));
    }
    
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
    
    private void cargarImagenJugador() {
        String clasePersonaje = GameState.jugadorActual != null ? 
            GameState.jugadorActual.clase.toLowerCase() : "caballero";
        
        String rutaImagen = "";
        switch (clasePersonaje) {
            case "ladron":
                rutaImagen = "/Resources/nodes/ladron.png";
                break;
            case "caballero":
                rutaImagen = "/Resources/nodes/caballero.png";
                break;
            case "clerigo":
                rutaImagen = "/Resources/nodes/mago.png";
                break;
            default:
                rutaImagen = "/Resources/characters/default_combat.png";
        }
        
        ImageIcon iconoJugador = cargarIcono(rutaImagen);
        if (iconoJugador != null) {
            imagenJugador.setIcon(iconoJugador);
        }
    }
    
    private void cargarImagenEnemigo() {
        String rutaImagen = "";
        
        // Imagenes según el tipo de enemigo
        
        if (GameState.tipoEnemigoActual != null) {
            switch (GameState.tipoEnemigoActual) {
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
                    rutaImagen = "/Resources/enemies/Traces_of_Gro_goroth.png";
                    break;
                case "ENEMIGO6":
                    rutaImagen = "/Resources/enemies/Moonless_Beast.png";
                    break;
                case "JEFE FINAL":
                    rutaImagen = "/Resources/enemies/Kaiser.png";
                    break;
                default:
                    rutaImagen = "/Resources/enemies/default_combat.png";
            }
        } else {
        	
            // Si no hay tipo específico, usar imagen por defecto
        	
            rutaImagen = "/Resources/enemies/default_combat.png";
        }
        
        ImageIcon iconoEnemigo = cargarIcono(rutaImagen);
        if (iconoEnemigo != null) {
            imagenEnemigo.setIcon(iconoEnemigo);
        }
    }
    
    private Image cargarImagenFondo(String rutaImagen) {
        ImageIcon icon = cargarIcono(rutaImagen);
        return icon != null ? icon.getImage() : null;
    }
    
    private ImageIcon cargarIcono(String rutaImagen) {
        try {
            // Método 1: Usando getResource
            URL imageUrl = getClass().getResource(rutaImagen);
            if (imageUrl != null) {
                return new ImageIcon(imageUrl);
            } 
            // Método 2: Usando ClassLoader
            imageUrl = getClass().getClassLoader().getResource(rutaImagen.substring(1));
            if (imageUrl != null) {
                return new ImageIcon(imageUrl);
            }
            // Método 3: Cargar desde archivo
            File file = new File("." + rutaImagen);
            if (file.exists()) {
                return new ImageIcon(file.getAbsolutePath());
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
                    return new ImageIcon(file.getAbsolutePath());
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

    public void regresarDeInventario(boolean objetoUsado) {
        this.usoObjeto = objetoUsado;
        if (objetoUsado) {
            log.agregarLinea("Has usado un objeto y pierdes tu turno.");
            accionJugador = "--INVENTARIO--";
            esperarDecisionEnemigo(1, 1);
        } else {
            actualizarEstado();
        }
    }

    private JLabel crearLabel(String txt, int x, int y, int w, int h, int size) {
        JLabel lbl = new JLabel(txt, SwingConstants.LEFT);
        lbl.setFont(new Font("SansSerif", Font.BOLD, size));
        lbl.setBounds(x, y, w, h);
        lbl.setForeground(Color.WHITE); // Texto blanco por defecto
        return lbl;
    }

    private void lanzarDado() {
        if (esperandoResolucionTurno) return;

        btnInventario.setVisible(false);

        Random rand = new Random();
        int dadoJugador = rand.nextInt(6) + 1;
        int dadoEnemigo = rand.nextInt(6) + 1;

        lblDadoJugador.setText("Jugador: " + dadoJugador);
        lblDadoEnemigo.setText("Enemigo: " + dadoEnemigo);

        log.agregarLinea("Dado jugador: " + dadoJugador);
        log.agregarLinea("Dado enemigo: " + dadoEnemigo);

        esperarDecisionJugador(dadoJugador, dadoEnemigo);
    }

    private void esperarDecisionJugador(int dadoJugador, int dadoEnemigo) {
        panelAcciones.removeAll();
        List<String> opciones = generarAcciones(dadoJugador);

        for (String acc : opciones) {
            JButton btn = crearBoton(acc);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));
            btn.addActionListener(e -> {
                accionJugador = acc;
                btnInventario.setVisible(false);
                esperarDecisionEnemigo(dadoJugador, dadoEnemigo);
            });
            panelAcciones.add(btn);
        }

        btnInventario.setVisible(true);

        panelAcciones.revalidate();
        panelAcciones.repaint();
    }

    private void esperarDecisionEnemigo(int dadoJugador, int dadoEnemigo) {
        accionEnemigo = controller.elegirAccionEnemigo();
        log.agregarLinea("El enemigo eligió su acción.");

        esperandoResolucionTurno = true;

        javax.swing.Timer t = new javax.swing.Timer(500, e -> resolverTurno(dadoJugador, dadoEnemigo));
        t.setRepeats(false);
        t.start();
    }

    private void resolverTurno(int dadoJugador, int dadoEnemigo) {
        log.agregarLinea("Resolviendo acciones...");

        if (dadoJugador >= dadoEnemigo) {
            ejecutarAccionJugador();
            ejecutarAccionEnemigo();
        } else {
            ejecutarAccionEnemigo();
            ejecutarAccionJugador();
        }

        actualizarEstado();
    }

    private void ejecutarAccionJugador() {
        if (accionJugador == null) return;

        switch (accionJugador) {
            case "Atacar" -> controller.atacarJugador(log);
            case "Defender" -> controller.defenderJugador();
            case "Evadir" -> controller.evadeJugador();
            case "Cargar Especial" -> {
                puntosEspecial = Math.min(2, puntosEspecial + 1);
                log.agregarLinea("Jugador cargó poder especial. Total: " + puntosEspecial);
            }
            case "Especial I" -> {
                puntosEspecial--;
                controller.especial1Jugador(log);
            }
            case "Especial II" -> {
                puntosEspecial -= 2;
                controller.especial2Jugador(log);
            }
            case "--INVENTARIO--" -> log.agregarLinea("Turno perdido por usar inventario.");
        }
    }

    private void ejecutarAccionEnemigo() {
        log.agregarLinea("El enemigo elige: " + accionEnemigo);
        controller.turnoEnemigoAccion(accionEnemigo, log);
    }

    private void actualizarEstado() {
        esperandoResolucionTurno = false;
        accionJugador = null;
        accionEnemigo = null;
        usoObjeto = false;

        btnInventario.setVisible(false);

        barraVidaJugador.setValue(GameState.jugadorActual.getHPActual());
        barraExpJugador.setValue(GameState.jugadorActual.getExperiencia());
        barraVidaEnemigo.setValue(controller.getEnemigo().getHPActual());

        if (controller.hayGanador()) {
            finalizarCombate();
        } else {
            panelAcciones.removeAll();
            panelAcciones.repaint();
            btnLanzarDado.setEnabled(true);
        }
    }

    private void finalizarCombate() {
        System.out.println("DEBUG: Iniciando finalizarCombate(), victoria: " + controller.jugadorGano());
        String msg = controller.jugadorGano() ? "¡Has vencido!" : "Has sido derrotado...";
        log.agregarLinea(msg);
        JOptionPane.showMessageDialog(this, msg);

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (controller.jugadorGano()) {
            controller.recompensas(log);
            GameState.jugadorActual.ganarExperiencia(10);
            
            // Verificar si este es el último nodo y es un JEFE FINAL
            boolean esBossFinal = false;
            if (origen != null) {
                int nodoActual = origen.getNodoActual();
                System.out.println("DEBUG: Nodo actual: " + nodoActual);
                
                // Verificar si estamos en el último nodo y es un JEFE FINAL
                if (nodoActual == origen.getNodos().size() - 1) {
                    String tipoNodo = origen.getTipoNodo(nodoActual);
                    System.out.println("DEBUG: Tipo del último nodo: " + tipoNodo);
                    if ("JEFE FINAL".equals(tipoNodo)) {
                        esBossFinal = true;
                        System.out.println("DEBUG: ¡Jefe final derrotado!");
                    }
                }
                
                // Marcar el nodo como completado y avanzar
                origen.marcarNodoComoCompletado(nodoActual);
                origen.incrementarNodoActual();
            }
            
            if (esBossFinal) {
                // Mostrar directamente la pantalla de fin de juego
                System.out.println("DEBUG: Mostrando EndgameView directamente");
                if (frame instanceof GameFrame f) {
                    f.cambiarVista(new EndgameView());
                }
            } else {
                // Verificar si hay más nodos o mapas
                if (origen != null) {
                    System.out.println("DEBUG: Llamando a verificarFinDeMapa()");
                    origen.verificarFinDeMapa();
                }
                
                // Volver al mapa
                System.out.println("DEBUG: Cambiando vista a origen");
                if (frame instanceof GameFrame f) {
                    f.cambiarVista(origen);
                }
            }
        } else {
            if (frame instanceof GameFrame f) f.cambiarVista(new GameOverView());
        }
    }
    
    private List<String> generarAcciones(int dado) {
        List<String> lista = new ArrayList<>();
        lista.add("Atacar");
        lista.add("Defender");
        lista.add("Evadir");
        if (puntosEspecial < 2) lista.add("Cargar Especial");
        if (puntosEspecial >= 1) lista.add("Especial I");
        if (puntosEspecial >= 2) lista.add("Especial II");
        while (lista.size() > dado) lista.remove(new Random().nextInt(lista.size()));
        return lista;
    }

    private void abrirInventario() {
        if (!esperandoResolucionTurno && accionJugador == null) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame instanceof GameFrame f) {
                f.mostrarInventarioDesdeCombate(this);
            }
        }
    }
}