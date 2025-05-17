package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import util.ResourceLoader;

public class CharacterStats extends BaseView {

    private JLabel[] statLabels;
    private JLabel[] statValueLabels;
    private JButton[] increaseButtons;
    private JButton[] decreaseButtons;
    private JButton continuarBtn;
    private JButton volverBtn;
    private JButton[] ventajaButtons;
    
    private final String playerName;
    private final String personaje;
    private final int[] statValues;

    public CharacterStats(String playerName, String personajeSeleccionado, int[] initialStatValues) {
        super();
        this.playerName = playerName;
        this.personaje = personajeSeleccionado;
        this.statValues = initialStatValues;
        
        setLayout(null);
        setBackground(Color.BLACK);
        
        // Nombre del jugador en la parte superior
        Color personajeColor;
        if (personajeSeleccionado.equalsIgnoreCase("caballero")) {
            personajeColor = new Color(64, 164, 223); // Azul
        } else if (personajeSeleccionado.equalsIgnoreCase("ladron")) {
            personajeColor = new Color(180, 180, 180); // Gris
        } else {
            personajeColor = new Color(118, 213, 87); // Verde
        }
        
        JLabel playerNameLabel = new JLabel(playerName, SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Press Start 2P", Font.BOLD, 24));
        playerNameLabel.setForeground(personajeColor);
        playerNameLabel.setBounds(0, 80, 1280, 30);
        add(playerNameLabel);
        
        // Sección de ventajas
        JLabel ventajaLabel = new JLabel("SELECCIONA UNA VENTAJA", SwingConstants.CENTER);
        ventajaLabel.setFont(new Font("Press Start 2P", Font.BOLD, 24));
        ventajaLabel.setForeground(Color.WHITE);
        ventajaLabel.setBounds(0, 120, 1280, 30);
        add(ventajaLabel);
        
        // Botones de ventaja
        ventajaButtons = new JButton[3];
        String[] ventajaTexts = {"+5 PUNTOS", "+20 HP", "+1 LUCK"};
        int[] ventajaX = {428, 683, 938}; // Posiciones X para centrar los 3 botones
        
        for (int i = 0; i < ventajaTexts.length; i++) {
            ventajaButtons[i] = new JButton(ventajaTexts[i]);
            ventajaButtons[i].setFont(new Font("Press Start 2P", Font.PLAIN, 18));
            ventajaButtons[i].setForeground(Color.WHITE);
            ventajaButtons[i].setBackground(Color.BLACK);
            ventajaButtons[i].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
            ventajaButtons[i].setFocusPainted(false);
            ventajaButtons[i].setContentAreaFilled(false);
            ventajaButtons[i].setOpaque(false);
            ventajaButtons[i].setBounds(ventajaX[i] - 100, 170, 200, 40);
            add(ventajaButtons[i]);
        }
        
        // Stats y botones +/-
        String[] statNames = {"HP", "PM", "ATK", "DEF", "EVA", "LUK"};
        int[] exampleValues = {120, 60, 14, 42, 12, 0}; // Valores de ejemplo como en la imagen
        
        statLabels = new JLabel[6];
        statValueLabels = new JLabel[6];
        increaseButtons = new JButton[6];
        decreaseButtons = new JButton[6];
        
        for (int i = 0; i < statNames.length; i++) {
            // Botón - (cuadrado con borde azul)
            decreaseButtons[i] = createStyledButton("-", new Color(0, 174, 239));
            decreaseButtons[i].setBounds(428, 280 + i * 50, 40, 40);
            add(decreaseButtons[i]);
            
            // Etiqueta del stat
            statLabels[i] = new JLabel(statNames[i], SwingConstants.CENTER);
            statLabels[i].setFont(new Font("Press Start 2P", Font.BOLD, 24));
            statLabels[i].setForeground(Color.WHITE);
            statLabels[i].setBounds(540, 280 + i * 50, 100, 40);
            add(statLabels[i]);
            
            // Valor del stat
            int value = exampleValues[i];
            statValueLabels[i] = new JLabel(String.valueOf(value), SwingConstants.CENTER);
            statValueLabels[i].setFont(new Font("Press Start 2P", Font.BOLD, 24));
            statValueLabels[i].setForeground(Color.WHITE);
            statValueLabels[i].setBounds(640, 280 + i * 50, 100, 40);
            add(statValueLabels[i]);
            
            // Botón + (cuadrado con borde amarillo)
            increaseButtons[i] = createStyledButton("+", new Color(255, 242, 0));
            increaseButtons[i].setBounds(812, 280 + i * 50, 40, 40);
            add(increaseButtons[i]);
        }
        
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
    
    private JButton createStyledButton(String text, Color borderColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Press Start 2P", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(borderColor, 2));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        return button;
    }

    public void setContinuarAction(ActionListener listener) {
        continuarBtn.addActionListener(listener);
    }
    
    public void setVolverAction(ActionListener listener) {
        volverBtn.addActionListener(listener);
    }
    
    public void setVentajaAction(int index, ActionListener listener) {
        if (index >= 0 && index < ventajaButtons.length) {
            ventajaButtons[index].addActionListener(listener);
        }
    }
    
    public void setIncreaseAction(int index, ActionListener listener) {
        if (index >= 0 && index < increaseButtons.length) {
            increaseButtons[index].addActionListener(listener);
        }
    }
    
    public void setDecreaseAction(int index, ActionListener listener) {
        if (index >= 0 && index < decreaseButtons.length) {
            decreaseButtons[index].addActionListener(listener);
        }
    }

    public void setFullscreenAction(ActionListener listener) {
        fullscreenButton.addActionListener(listener);
    }
    
    public void updateStatValue(int index, int value) {
        if (index >= 0 && index < statValueLabels.length) {
            statValueLabels[index].setText(String.valueOf(value));
        }
    }
}