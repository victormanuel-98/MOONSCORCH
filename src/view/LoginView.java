package view;

import model.GameState;
import model.database.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class LoginView extends BaseView {
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private Image backgroundImage;
    
    // Color rojo oscuro para los elementos
    private final Color ROJO_OSCURO = new Color(139, 0, 0);
    private final Color ROJO_CLARO = new Color(178, 34, 34);
    
    public LoginView() {
        super();
        setLayout(new BorderLayout());
        
        // Cargar imagen de fondo
        backgroundImage = cargarImagen("/Resources/screens/login_background.png");
        if (backgroundImage == null) {
            // Intentar con fondo alternativo
            backgroundImage = cargarImagen("/Resources/screens/map_background.png");
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
        
        // Panel de login
        JPanel panelLogin = new JPanel();
        panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.Y_AXIS));
        panelLogin.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        panelLogin.setOpaque(false);
        
        // Título
        JLabel titulo = new JLabel("MOONSCORCH", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        titulo.setForeground(ROJO_CLARO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLogin.add(titulo);
        
        JLabel subtitulo = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        subtitulo.setForeground(Color.WHITE);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLogin.add(Box.createVerticalStrut(20));
        panelLogin.add(subtitulo);
        panelLogin.add(Box.createVerticalStrut(50));
        
        // Campo de usuario
        JPanel panelUsuario = new JPanel();
        panelUsuario.setLayout(new BoxLayout(panelUsuario, BoxLayout.Y_AXIS));
        panelUsuario.setOpaque(false);
        panelUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblUsuario.setForeground(Color.WHITE);
        panelUsuario.add(lblUsuario);
        
        txtUsername = new JTextField(20);
        txtUsername.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtUsername.setMaximumSize(new Dimension(300, 30));
        panelUsuario.add(Box.createVerticalStrut(5));
        panelUsuario.add(txtUsername);
        
        panelLogin.add(panelUsuario);
        panelLogin.add(Box.createVerticalStrut(20));
        
        // Campo de contraseña
        JPanel panelPassword = new JPanel();
        panelPassword.setLayout(new BoxLayout(panelPassword, BoxLayout.Y_AXIS));
        panelPassword.setOpaque(false);
        panelPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblPassword.setForeground(Color.WHITE);
        panelPassword.add(lblPassword);
        
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtPassword.setMaximumSize(new Dimension(300, 30));
        panelPassword.add(Box.createVerticalStrut(5));
        panelPassword.add(txtPassword);
        
        panelLogin.add(panelPassword);
        panelLogin.add(Box.createVerticalStrut(40));
        
        // Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton btnLogin = crearBoton("INICIAR SESIÓN");
        btnLogin.addActionListener(e -> iniciarSesion());
        
        JButton btnRegistro = crearBoton("REGISTRARSE");
        btnRegistro.addActionListener(e -> mostrarRegistro());
        
        panelBotones.add(btnLogin);
        panelBotones.add(Box.createHorizontalStrut(20));
        panelBotones.add(btnRegistro);
        
        panelLogin.add(panelBotones);
        
     // Contenedor intermedio para centrar horizontalmente
        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.setOpaque(false);
        centeringPanel.add(panelLogin); // Centra automáticamente el panelLogin

        panelPrincipal.add(centeringPanel, BorderLayout.CENTER);

        
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
 // En LoginView
    private void iniciarSesion() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.");
            return;
        }
        
        int idUsuario = DatabaseManager.verificarLogin(username, password);
        
        if (idUsuario > 0) {
            // Login exitoso
            GameState.idUsuarioActual = idUsuario;
            
            // Obtener personajes del usuario
            List<Map<String, Object>> personajes = DatabaseManager.obtenerPersonajesUsuario(idUsuario);
            
            if (personajes.isEmpty()) {
                // No tiene personajes, ir a crear uno
                ((GameFrame) SwingUtilities.getWindowAncestor(this)).mostrarCharacterMenu();
            } else {
                // Crear y mostrar la vista de selección de personaje directamente
                CharacterSelectView selectView = new CharacterSelectView(personajes);
                ((GameFrame) SwingUtilities.getWindowAncestor(this)).cambiarVista(selectView);
            }
        } else {
            // Login fallido
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
        }
    }
    
    private void mostrarRegistro() {
        ((GameFrame) SwingUtilities.getWindowAncestor(this)).mostrarRegistro();
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
}