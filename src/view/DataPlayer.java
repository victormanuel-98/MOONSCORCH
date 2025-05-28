package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.net.URL;
import java.io.File;

public class DataPlayer extends BaseView {

    private JTextField nameField;
    private JButton resetBtn, continuarBtn, volverBtn;
    private JRadioButton opcion1, opcion2, opcion3;
    private ButtonGroup grupoOpciones;
    private final String[] statNames = {"HP", "PM", "ATK", "DEF", "EVA", "LUK"};
    private JLabel[] statLabels;
    private int[] statsBase;

    private JButton[] botonesMas;
    private JButton[] botonesMenos;
    private int[] puntosAsignados;
    private final int puntosTotales = 5;
    
    // Color rojo oscuro para los elementos
    private final Color ROJO_OSCURO = new Color(139, 0, 0);
    private Image characterImage;

    public DataPlayer(String personaje, int[] baseStats) {
        super();
        this.statsBase = baseStats.clone();
        puntosAsignados = new int[5];

        setLayout(null);
        setBackground(Color.BLACK); // Fondo negro para toda la pantalla
        
        // Cargar imagen del personaje
        cargarImagenPersonaje(personaje);

        Font tituloFont = new Font("SansSerif", Font.BOLD, 20);
        Font statFont = new Font("SansSerif", Font.BOLD, 14);

        // Título
        JLabel titulo = new JLabel("INFORMACIÓN DE PERSONAJE", SwingConstants.CENTER);
        titulo.setFont(tituloFont.deriveFont(24f));
        titulo.setForeground(ROJO_OSCURO);
        titulo.setBounds(0, 30, 1280, 40);
        add(titulo);

        // Clase y nivel
        JLabel clase = new JLabel(personaje.toUpperCase());
        clase.setFont(tituloFont);
        clase.setForeground(Color.WHITE);
        clase.setBounds(200, 100, 200, 30);
        add(clase);

        JLabel nivel = new JLabel("NV 1");
        nivel.setFont(tituloFont);
        nivel.setForeground(ROJO_OSCURO);
        nivel.setBounds(330, 100, 100, 30);
        add(nivel);

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
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("SansSerif", Font.BOLD, 64));
                    FontMetrics fm = g.getFontMetrics();
                    String letra = "";
                    switch (personaje.toLowerCase()) {
                        case "ladron" -> letra = "A";
                        case "caballero" -> letra = "C";
                        case "clerigo", "mago" -> letra = "CL";
                        default -> letra = "?";
                    }
                    int textWidth = fm.stringWidth(letra);
                    int textHeight = fm.getHeight();
                    g.drawString(letra, (getWidth() - textWidth) / 2, 
                                 (getHeight() - textHeight) / 2 + fm.getAscent());
                }
            }
        };
        panelImagen.setBounds(180, 140, 200, 200);
        add(panelImagen);

        // Campo de nombre
        JLabel nombreLabel = new JLabel("NOMBRE:");
        nombreLabel.setFont(tituloFont);
        nombreLabel.setForeground(Color.WHITE);
        nombreLabel.setBounds(550, 100, 150, 30);
        add(nombreLabel);

        nameField = new JTextField();
        nameField.setBounds(660, 100, 300, 30);
        nameField.setFont(statFont);
        nameField.setBackground(new Color(30, 30, 30));
        nameField.setForeground(Color.WHITE);
        nameField.setCaretColor(Color.WHITE);
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ROJO_OSCURO, 2),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        add(nameField);

        // Panel para las estadísticas
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
        statsPanel.setBounds(500, 140, 400, 280);
        statsPanel.setOpaque(false);
        add(statsPanel);

        // Estadísticas
        statLabels = new JLabel[6];
        botonesMas = new JButton[5];
        botonesMenos = new JButton[5];
        int y = 20;
        for (int i = 0; i < statNames.length; i++) {
            JLabel stat = new JLabel(statNames[i] + ":");
            stat.setFont(tituloFont);
            stat.setForeground(ROJO_OSCURO);
            stat.setBounds(50, y, 100, 30);
            statsPanel.add(stat);

            statLabels[i] = new JLabel(String.valueOf(statsBase[i]));
            statLabels[i].setFont(tituloFont);
            statLabels[i].setForeground(Color.WHITE);
            statLabels[i].setBounds(160, y, 60, 30);
            statsPanel.add(statLabels[i]);

            if (i < 5) {
                botonesMenos[i] = crearBotonStat("-", 240, y, i, -1);
                botonesMas[i] = crearBotonStat("+", 290, y, i, 1);
                statsPanel.add(botonesMenos[i]);
                statsPanel.add(botonesMas[i]);
            }
            y += 40;
        }

        // Panel para las opciones
        JPanel opcionesPanel = new JPanel() {
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
        opcionesPanel.setLayout(null);
        opcionesPanel.setBounds(180, 440, 720, 100);
        opcionesPanel.setOpaque(false);
        add(opcionesPanel);

        // Opciones de mejora
        JLabel opcionesLabel = new JLabel("ELIGE UNA MEJORA:", SwingConstants.CENTER);
        opcionesLabel.setFont(tituloFont);
        opcionesLabel.setForeground(ROJO_OSCURO);
        opcionesLabel.setBounds(0, 10, 720, 30);
        opcionesPanel.add(opcionesLabel);

        opcion1 = crearOpcion("+5 PUNTOS", 100, 50);
        opcion2 = crearOpcion("+20 HP", 300, 50);
        opcion3 = crearOpcion("+1 LUCK", 500, 50);
        opcionesPanel.add(opcion1);
        opcionesPanel.add(opcion2);
        opcionesPanel.add(opcion3);

        grupoOpciones = new ButtonGroup();
        grupoOpciones.add(opcion1);
        grupoOpciones.add(opcion2);
        grupoOpciones.add(opcion3);

        // Botones
        resetBtn = crearBoton("RESETEAR");
        resetBtn.setBounds(550, 560, 180, 35);
        add(resetBtn);

        volverBtn = crearBoton("VOLVER");
        volverBtn.setBounds(440, 610, 160, 40);
        add(volverBtn);

        continuarBtn = crearBoton("CONTINUAR");
        continuarBtn.setBounds(680, 610, 160, 40);
        continuarBtn.setEnabled(false);
        add(continuarBtn);

        actualizarVisibilidadBotones(false);
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

    private JButton crearBotonStat(String texto, int x, int y, int idx, int incremento) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setBounds(x, y, 40, 30);
        boton.addActionListener(e -> ajustarPuntos(idx, incremento));
        
        // Estilo consistente
        boton.setBackground(Color.BLACK);
        boton.setForeground(ROJO_OSCURO);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(ROJO_OSCURO, 1));
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(20, 20, 20));
                boton.setForeground(new Color(180, 0, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.BLACK);
                boton.setForeground(ROJO_OSCURO);
            }
        });
        
        return boton;
    }

    private void ajustarPuntos(int idx, int incremento) {
        int usados = Arrays.stream(puntosAsignados).sum();
        if ((incremento > 0 && usados < puntosTotales) || (incremento < 0 && puntosAsignados[idx] > 0)) {
            puntosAsignados[idx] += incremento;
            statLabels[idx].setText(String.valueOf(statsBase[idx] + puntosAsignados[idx]));
        }
        continuarBtn.setEnabled(Arrays.stream(puntosAsignados).sum() == puntosTotales);
    }

    private JRadioButton crearOpcion(String texto, int x, int y) {
        JRadioButton btn = new JRadioButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBounds(x, y, 180, 40);
        btn.addActionListener(e -> actualizarVisibilidadBotones(btn == opcion1));
        
        // Estilo consistente
        btn.setBackground(new Color(20, 20, 20, 0));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        
        // Cambiar el icono del radio button
        Icon unselectedIcon = new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setColor(ROJO_OSCURO);
                g.drawOval(x, y, 16, 16);
            }
            @Override
            public int getIconWidth() { return 16; }
            @Override
            public int getIconHeight() { return 16; }
        };
        
        Icon selectedIcon = new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setColor(ROJO_OSCURO);
                g.drawOval(x, y, 16, 16);
                g.fillOval(x + 3, y + 3, 10, 10);
            }
            @Override
            public int getIconWidth() { return 16; }
            @Override
            public int getIconHeight() { return 16; }
        };
        
        btn.setIcon(unselectedIcon);
        btn.setSelectedIcon(selectedIcon);
        
        return btn;
    }

    private void actualizarVisibilidadBotones(boolean visibles) {
        for (int i = 0; i < 5; i++) {
            botonesMas[i].setVisible(visibles);
            botonesMenos[i].setVisible(visibles);
        }
        continuarBtn.setEnabled(!visibles || Arrays.stream(puntosAsignados).sum() == puntosTotales);
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

    public void setVolverAction(ActionListener listener) {
        volverBtn.addActionListener(listener);
    }

    public void setResetAction(ActionListener listener) {
        resetBtn.addActionListener(listener);
    }

    public String getNombreHeroe() {
        return nameField.getText().trim();
    }

    public int[] getStatsFinales() {
        int[] finales = statsBase.clone();
        if (opcion1.isSelected()) {
            for (int i = 0; i < 5; i++) finales[i] += puntosAsignados[i];
        } else if (opcion2.isSelected()) {
            finales[0] += 20;
        } else if (opcion3.isSelected()) {
            finales[5] += 1;
        }
        return finales;
    }

    public void resetOpciones() {
        grupoOpciones.clearSelection();
        nameField.setText("");
        for (int i = 0; i < 5; i++) {
            puntosAsignados[i] = 0;
            botonesMas[i].setVisible(false);
            botonesMenos[i].setVisible(false);
            statLabels[i].setText(String.valueOf(statsBase[i]));
        }
        continuarBtn.setEnabled(false);
    }
}