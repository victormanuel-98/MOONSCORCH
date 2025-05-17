package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import util.ResourceLoader;

public class CharacterDetails extends BaseView {

    private JPanel characterPanel;
    private JTextField nameField;
    private JButton continuarBtn;
    private JButton volverBtn;
    
    private final String personaje;

    public CharacterDetails(String personajeSeleccionado) {
        super();
        this.personaje = personajeSeleccionado;
        
        setLayout(null);
        setBackground(Color.BLACK);
        
        // Título principal
        JLabel titleLabel = new JLabel("INFORMACIÓN DE PERSONAJE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Press Start 2P", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 80, 1280, 50);
        add(titleLabel);
        
        // Tipo de personaje y nivel
        Color personajeColor;
        String personajeMostrado;
        String letter;
        
        if (personajeSeleccionado.equalsIgnoreCase("caballero")) {
            personajeMostrado = "CABALLERO";
            personajeColor = new Color(64, 164, 223); // Azul
            letter = "C";
        } else if (personajeSeleccionado.equalsIgnoreCase("ladron")) {
            personajeMostrado = "LADRÓN";
            personajeColor = new Color(180, 180, 180); // Gris
            letter = "A";
        } else {
            personajeMostrado = "CLÉRIGO";
            personajeColor = new Color(118, 213, 87); // Verde
            letter = "C";
        }
        
        // Etiqueta del personaje (centrada)
        JLabel personajeLabel = new JLabel(personajeMostrado, SwingConstants.CENTER);
        personajeLabel.setFont(new Font("Press Start 2P", Font.BOLD, 24));
        personajeLabel.setForeground(personajeColor);
        personajeLabel.setBounds(540, 165, 200, 30);
        add(personajeLabel);
        
        // Nivel (a la derecha del personaje)
        JLabel nivelLabel = new JLabel("NV 1", SwingConstants.CENTER);
        nivelLabel.setFont(new Font("Press Start 2P", Font.BOLD, 24));
        nivelLabel.setForeground(Color.WHITE);
        nivelLabel.setBounds(740, 165, 100, 30);
        add(nivelLabel);
        
        // Panel del personaje (cuadrado con letra)
        characterPanel = new JPanel();
        characterPanel.setLayout(new BorderLayout());
        characterPanel.setBackground(personajeColor);
        characterPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        characterPanel.setBounds(515, 205, 250, 250);
        
        JLabel letterLabel = new JLabel(letter, SwingConstants.CENTER);
        letterLabel.setFont(new Font("Arial", Font.BOLD, 150));
        letterLabel.setForeground(Color.WHITE);
        characterPanel.add(letterLabel, BorderLayout.CENTER);
        
        add(characterPanel);
        
        // Etiqueta "NOMBRE"
        JLabel nombreLabel = new JLabel("NOMBRE", SwingConstants.CENTER);
        nombreLabel.setFont(new Font("Press Start 2P", Font.BOLD, 24));
        nombreLabel.setForeground(Color.WHITE);
        nombreLabel.setBounds(0, 485, 1280, 30);
        add(nombreLabel);
        
        // Campo de texto para el nombre
        nameField = new JTextField("SIR JHONSONS BABY");
        nameField.setFont(new Font("Press Start 2P", Font.PLAIN, 18));
        nameField.setForeground(Color.WHITE);
        nameField.setBackground(Color.BLACK);
        nameField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBounds(440, 535, 400, 40);
        nameField.setCaretColor(Color.WHITE);
        add(nameField);
        
        // Botones de navegación
        volverBtn = new JButton("VOLVER");
        volverBtn.setFont(new Font("Press Start 2P", Font.PLAIN, 18));
        volverBtn.setForeground(Color.WHITE);
        volverBtn.setBackground(Color.BLACK);
        volverBtn.setBorder(BorderFactory.createLineBorder(new Color(255, 70, 70), 2));
        volverBtn.setFocusPainted(false);
        volverBtn.setContentAreaFilled(false);
        volverBtn.setOpaque(false);
        volverBtn.setBounds(440, 680, 200, 40);
        add(volverBtn);
        
        continuarBtn = new JButton("CONTINUAR");
        continuarBtn.setFont(new Font("Press Start 2P", Font.PLAIN, 18));
        continuarBtn.setForeground(Color.WHITE);
        continuarBtn.setBackground(Color.BLACK);
        continuarBtn.setBorder(BorderFactory.createLineBorder(new Color(70, 255, 70), 2));
        continuarBtn.setFocusPainted(false);
        continuarBtn.setContentAreaFilled(false);
        continuarBtn.setOpaque(false);
        continuarBtn.setBounds(640, 680, 200, 40);
        add(continuarBtn);
    }
    
    public void setContinuarAction(ActionListener listener) {
        continuarBtn.addActionListener(listener);
    }
    
    public void setVolverAction(ActionListener listener) {
        volverBtn.addActionListener(listener);
    }

    public void setFullscreenAction(ActionListener listener) {
        fullscreenButton.addActionListener(listener);
    }
    
    public String getPlayerName() {
        return nameField.getText();
    }
}