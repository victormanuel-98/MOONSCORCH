package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import util.ResourceLoader;

public class CharacterMenu extends BaseView {

    private JButton ladronBtn;
    private JButton caballeroBtn;
    private JButton clerigoBtn;
    private JLabel titleLabel;

    public CharacterMenu() {
        super();
        setLayout(null);
        setBackground(Color.BLACK);
        
        // Título
        titleLabel = new JLabel("SELECCIONA UN PERSONAJE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Press Start 2P", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 80, 1280, 50);
        add(titleLabel);

        // Panel para los personajes
        JPanel charactersPanel = new JPanel();
        charactersPanel.setLayout(new GridLayout(1, 3, 80, 0));
        charactersPanel.setBackground(Color.BLACK);
        charactersPanel.setBounds(140, 200, 1000, 350);
        
        // Ladrón
        JPanel ladronPanel = createCharacterPanel("A", new Color(180, 180, 180), "LADRÓN");
        
        // Caballero
        JPanel caballeroPanel = createCharacterPanel("C", new Color(64, 164, 223), "CABALLERO");
        
        // Clérigo
        JPanel clerigoPanel = createCharacterPanel("C", new Color(118, 213, 87), "CLÉRIGO");
        
        // Añadir paneles al panel principal
        charactersPanel.add(ladronPanel);
        charactersPanel.add(caballeroPanel);
        charactersPanel.add(clerigoPanel);
        
        add(charactersPanel);
    }
    
    private JPanel createCharacterPanel(String letter, Color bgColor, String characterName) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 20)); // Añadir espacio vertical de 20px entre componentes
        panel.setBackground(Color.BLACK);
        
        // Crear el panel con la letra
        JPanel letterPanel = new JPanel();
        letterPanel.setBackground(bgColor);
        letterPanel.setPreferredSize(new Dimension(250, 250));
        letterPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        letterPanel.setLayout(new BorderLayout());
        
        // Letra grande
        JLabel letterLabel = new JLabel(letter, SwingConstants.CENTER);
        letterLabel.setFont(new Font("Arial", Font.BOLD, 120));
        letterLabel.setForeground(Color.WHITE);
        letterPanel.add(letterLabel, BorderLayout.CENTER);
        
        // Botón con el nombre del personaje - SIN FONDO, BORDE BLANCO, TEXTO BLANCO
        JButton characterBtn = new JButton(characterName);
        characterBtn.setFont(new Font("Press Start 2P", Font.PLAIN, 18));
        characterBtn.setForeground(Color.WHITE);
        characterBtn.setBackground(new Color(0, 0, 0, 0)); // Transparente
        characterBtn.setOpaque(false); // Sin fondo
        characterBtn.setContentAreaFilled(false); // Sin relleno
        characterBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        characterBtn.setFocusPainted(false);
        characterBtn.setPreferredSize(new Dimension(250, 50));
        
        // Guardar referencia al botón
        if (characterName.equals("LADRÓN")) {
            ladronBtn = characterBtn;
        } else if (characterName.equals("CABALLERO")) {
            caballeroBtn = characterBtn;
        } else if (characterName.equals("CLÉRIGO")) {
            clerigoBtn = characterBtn;
        }
        
        // Añadir componentes al panel
        panel.add(letterPanel, BorderLayout.CENTER);
        
        // Panel adicional para el botón para añadir más espacio
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(characterBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    public void setLadronAction(ActionListener listener) {
        ladronBtn.addActionListener(listener);
    }

    public void setCaballeroAction(ActionListener listener) {
        caballeroBtn.addActionListener(listener);
    }

    public void setClerigoAction(ActionListener listener) {
        clerigoBtn.addActionListener(listener);
    }

    public void setFullscreenAction(ActionListener listener) {
        fullscreenButton.addActionListener(listener);
    }
}