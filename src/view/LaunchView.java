package view;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class LaunchView {

    private JFrame frame;
    private BackgroundPanel backgroundPanel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LaunchView window = new LaunchView();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public LaunchView() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("MoonScorch");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 1024, 600);

        backgroundPanel = new BackgroundPanel("./Resources/images/reyAmarillo.png");
        backgroundPanel.setLayout(null);
        frame.setContentPane(backgroundPanel);

        // Etiqueta del título
        JLabel lblTitulo = new JLabel("MoonScorch");
        lblTitulo.setForeground(Color.RED);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(262, 30, 500, 100);
        try {
            // Usa fuente del sistema estilo gótico
            Font gothic = new Font("Old English Text MT", Font.PLAIN, 64);
            lblTitulo.setFont(gothic);
        } catch (Exception e) {
            lblTitulo.setFont(new Font("Serif", Font.BOLD, 64)); // fallback
        }
        backgroundPanel.add(lblTitulo);

        // Botones
        JButton btnNueva = createResponsiveButton("Nueva Partida");
        btnNueva.setBounds(390, 160, 250, 50);
        backgroundPanel.add(btnNueva);
        btnNueva.addActionListener(e->{
        	new CharacterMenu().setVisible(true);
        	frame.dispose();
        }
);

        JButton btnCargar = createResponsiveButton("Cargar Partida");
        btnCargar.setBounds(390, 230, 250, 50);
        backgroundPanel.add(btnCargar);

        JButton btnSalir = createResponsiveButton("Salir");
        btnSalir.setBounds(390, 300, 250, 50);
        btnSalir.addActionListener(e -> System.exit(0));
        backgroundPanel.add(btnSalir);

        // Pantalla completa
        JButton btnFull = new JButton("⛶");
        btnFull.setFont(new Font("Dialog", Font.BOLD, 20));
        btnFull.setForeground(Color.WHITE);
        btnFull.setBackground(new Color(0, 0, 0, 180));
        btnFull.setBounds(940, 500, 50, 50);
        btnFull.setFocusPainted(false);
        btnFull.setBorderPainted(false);
        btnFull.setOpaque(true);
        btnFull.addActionListener(e -> {
            frame.dispose();
            frame.setUndecorated(!frame.isUndecorated());
            frame.setVisible(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
        backgroundPanel.add(btnFull);

        // Listener para redibujar todo cuando se redimensiona
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int w = frame.getWidth();
                int baseX = (w - 250) / 2;
                btnNueva.setBounds(baseX, 160, 250, 50);
                btnCargar.setBounds(baseX, 230, 250, 50);
                btnSalir.setBounds(baseX, 300, 250, 50);
                lblTitulo.setBounds(baseX - 100, 30, 500, 100);
                btnFull.setBounds(frame.getWidth() - 70, frame.getHeight() - 90, 50, 50);
            }
        });
    }

    // Botones con efecto hover
    private JButton createResponsiveButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(80, 0, 0));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(new Color(200, 0, 0), 2));
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(120, 0, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(80, 0, 0));
            }
        });

        return button;
    }

    // Panel de fondo escalable
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String path) {
            try {
                BufferedImage img = ImageIO.read(new File(path));
                backgroundImage = img;
            } catch (Exception e) {
                System.err.println("No se pudo cargar la imagen: " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}