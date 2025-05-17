package view;

import util.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConfirmPlayer extends BaseView {

    private final String personaje;
    private final GameFrame frame;
    private JButton btnConfirmar;
    private JButton btnVolver;

    public ConfirmPlayer(String personaje, GameFrame frame) {
        super();
        this.personaje = personaje;
        this.frame = frame;

        setLayout(null);
        setBackground(Color.BLACK);

        // Título en una sola línea con formato especial
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setBounds(0, 100, 1280, 60);
        
        // Primera parte del título
        JLabel preguntaLabel = new JLabel("¿SERÁS UN NOBLE");
        preguntaLabel.setFont(new Font("Press Start 2P", Font.BOLD, 36));
        preguntaLabel.setForeground(Color.WHITE);
        titlePanel.add(preguntaLabel);
        
        // Nombre del personaje (con color)
        String nombreMostrado;
        Color colorPersonaje;
        
        if (personaje.equalsIgnoreCase("caballero")) {
            nombreMostrado = "CABALLERO";
            colorPersonaje = new Color(64, 164, 223); // Azul
        } else if (personaje.equalsIgnoreCase("ladron")) {
            nombreMostrado = "LADRÓN";
            colorPersonaje = new Color(180, 180, 180); // Gris
        } else {
            nombreMostrado = "CLERIGO"; // Sin tilde para evitar problemas de codificación
            colorPersonaje = new Color(118, 213, 87); // Verde
        }
        
        JLabel personajeLabel = new JLabel(nombreMostrado);
        personajeLabel.setFont(new Font("Press Start 2P", Font.BOLD, 36));
        personajeLabel.setForeground(colorPersonaje);
        titlePanel.add(personajeLabel);
        
        // Signo de interrogación
        JLabel interrogacionLabel = new JLabel("?");
        interrogacionLabel.setFont(new Font("Press Start 2P", Font.BOLD, 36));
        interrogacionLabel.setForeground(Color.WHITE);
        titlePanel.add(interrogacionLabel);
        
        add(titlePanel);

        // Panel para la letra del personaje
        JPanel letterPanel = new JPanel();
        letterPanel.setLayout(new BorderLayout());
        letterPanel.setBounds(490, 200, 300, 300);
        letterPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        
        // Determinar color y letra según el personaje
        Color bgColor;
        String letter;
        
        if (personaje.equalsIgnoreCase("caballero")) {
            bgColor = new Color(64, 164, 223); // Azul
            letter = "C";
        } else if (personaje.equalsIgnoreCase("ladron")) {
            bgColor = new Color(180, 180, 180); // Gris
            letter = "A";
        } else {
            bgColor = new Color(118, 213, 87); // Verde
            letter = "C"; // La imagen muestra una C para el clérigo
        }
        
        letterPanel.setBackground(bgColor);
        
        JLabel letterLabel = new JLabel(letter, SwingConstants.CENTER);
        letterLabel.setFont(new Font("Arial", Font.BOLD, 180));
        letterLabel.setForeground(Color.WHITE);
        letterPanel.add(letterLabel, BorderLayout.CENTER);
        
        add(letterPanel);

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBounds(0, 550, 1280, 60);
        
        // Botón Volver
        btnVolver = new JButton("VOLVER");
        btnVolver.setFont(new Font("Press Start 2P", Font.PLAIN, 18));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBackground(new Color(0, 0, 0, 0));
        btnVolver.setOpaque(false);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorder(BorderFactory.createLineBorder(new Color(255, 70, 70), 2)); // Rojo
        btnVolver.setFocusPainted(false);
        btnVolver.setPreferredSize(new Dimension(200, 50));
        
        // Botón Confirmar
        btnConfirmar = new JButton("CONFIRMAR");
        btnConfirmar.setFont(new Font("Press Start 2P", Font.PLAIN, 18));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setBackground(new Color(0, 0, 0, 0));
        btnConfirmar.setOpaque(false);
        btnConfirmar.setContentAreaFilled(false);
        btnConfirmar.setBorder(BorderFactory.createLineBorder(new Color(70, 255, 70), 2)); // Verde
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setPreferredSize(new Dimension(200, 50));
        
        buttonPanel.add(btnVolver);
        buttonPanel.add(btnConfirmar);
        add(buttonPanel);

        // Configurar acciones de los botones
        btnConfirmar.addActionListener(e -> frame.mostrarDataPlayer(personaje));
        btnVolver.addActionListener(e -> frame.mostrarCharacterMenu());
    }

    public void setFullscreenAction(ActionListener listener) {
        fullscreenButton.addActionListener(listener);
    }
}