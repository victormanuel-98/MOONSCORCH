package view;

import model.database.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import model.GameState;

public class RegisterView extends BaseView {
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private Image backgroundImage;
    
    // Color rojo oscuro para los elementos
    private final Color ROJO_OSCURO = new Color(139, 0, 0);
    private final Color ROJO_CLARO = new Color(178, 34, 34);
    
    public RegisterView() {
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
        
        // Panel de registro
        JPanel panelRegistro = new JPanel();
        panelRegistro.setLayout(new BoxLayout(panelRegistro, BoxLayout.Y_AXIS));
        panelRegistro.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        panelRegistro.setOpaque(false);
        
        // Título
        JLabel titulo = new JLabel("MOONSCORCH", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        titulo.setForeground(ROJO_CLARO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelRegistro.add(titulo);
        
        JLabel subtitulo = new JLabel("Registro de Usuario", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        subtitulo.setForeground(Color.WHITE);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelRegistro.add(Box.createVerticalStrut(20));
        panelRegistro.add(subtitulo);
        panelRegistro.add(Box.createVerticalStrut(50));
        
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
        
        panelRegistro.add(panelUsuario);
        panelRegistro.add(Box.createVerticalStrut(20));
        
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
        
        panelRegistro.add(panelPassword);
        panelRegistro.add(Box.createVerticalStrut(20));
        
        // Campo de confirmar contraseña
        JPanel panelConfirmPassword = new JPanel();
        panelConfirmPassword.setLayout(new BoxLayout(panelConfirmPassword, BoxLayout.Y_AXIS));
        panelConfirmPassword.setOpaque(false);
        panelConfirmPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblConfirmPassword = new JLabel("Confirmar Contraseña:");
        lblConfirmPassword.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblConfirmPassword.setForeground(Color.WHITE);
        panelConfirmPassword.add(lblConfirmPassword);
        
        txtConfirmPassword = new JPasswordField(20);
        txtConfirmPassword.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtConfirmPassword.setMaximumSize(new Dimension(300, 30));
        panelConfirmPassword.add(Box.createVerticalStrut(5));
        panelConfirmPassword.add(txtConfirmPassword);
        
        panelRegistro.add(panelConfirmPassword);
        panelRegistro.add(Box.createVerticalStrut(40));
        
        // Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton btnRegistrar = crearBoton("REGISTRARSE");
        btnRegistrar.addActionListener(e -> registrarUsuario());
        
        JButton btnVolver = crearBoton("VOLVER");
        btnVolver.addActionListener(e -> volverLogin());
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(Box.createHorizontalStrut(20));
        panelBotones.add(btnVolver);
        
        panelRegistro.add(panelBotones);
        
     // Contenedor intermedio para centrar horizontal y verticalmente
        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.setOpaque(false);
        centeringPanel.add(panelRegistro); // Centra automáticamente el panelRegistro

        panelPrincipal.add(centeringPanel, BorderLayout.CENTER);

        
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
    private void registrarUsuario() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        
        // Validaciones
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.");
            return;
        }
        
        try {
            // Intentar registrar el usuario
            int idUsuario = DatabaseManager.registrarUsuario(username, password);
            
            if (idUsuario > 0) {
                // Registro exitoso
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
                
                // Guardar el ID del usuario en el estado global
                GameState.idUsuarioActual = idUsuario;
                
                // Redirigir a LaunchView
                GameFrame gameFrame = (GameFrame) SwingUtilities.getWindowAncestor(this);
                if (gameFrame != null) {
                    gameFrame.mostrarLaunchView();
                } else {
                    System.err.println("Error: No se pudo obtener la ventana principal");
                }
            } else {
                // Registro fallido
                JOptionPane.showMessageDialog(this, "Error al registrar el usuario. El nombre de usuario podría estar en uso.");
            }
        } catch (Exception e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            
            // Modo sin conexión para desarrollo
            GameState.idUsuarioActual = 1; // ID ficticio
            JOptionPane.showMessageDialog(this, "Modo sin conexión: Usuario registrado con ID temporal.");
            
            // Redirigir a LaunchView
            GameFrame gameFrame = (GameFrame) SwingUtilities.getWindowAncestor(this);
            if (gameFrame != null) {
                gameFrame.mostrarLaunchView();
            } else {
                System.err.println("Error: No se pudo obtener la ventana principal");
            }
        }
    }
    
    private void volverLogin() {
        GameFrame gameFrame = (GameFrame) SwingUtilities.getWindowAncestor(this);
        if (gameFrame != null) {
            gameFrame.mostrarLogin();
        } else {
            System.err.println("Error: No se pudo obtener la ventana principal");
        }
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