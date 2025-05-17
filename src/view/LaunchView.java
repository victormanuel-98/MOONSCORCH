package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import util.ResourceLoader;

public class LaunchView extends BaseView {

    private JButton newGameButton;
    private JButton continueButton;
    private JButton exitButton;

    public LaunchView() {
        super();
        setLayout(null);
        
        // Fondo negro
        setBackground(Color.BLACK);
        
        // Título del juego
        JLabel titulo = new JLabel("MOONSCORCH", SwingConstants.CENTER);
        titulo.setFont(new Font("Press Start 2P", Font.BOLD, 80));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(200, 100, 880, 100);
        add(titulo);

        // Panel para botones (para mantenerlos centrados)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 20));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBounds(450, 300, 380, 200);
        
        // Botón: Nueva Partida - borde blanco, texto blanco, sin fondo
        newGameButton = createStyledButton("NUEVA PARTIDA", Color.WHITE, Color.WHITE);
        
        // Botón: Continuar - borde blanco, texto blanco, sin fondo
        continueButton = createStyledButton("CONTINUAR", Color.WHITE, Color.WHITE);
        
        // Botón: Salir - borde rojo, texto rojo, sin fondo
        exitButton = createStyledButton("SALIR", new Color(255, 58, 58), new Color(255, 58, 58));
        
        // Añadir botones al panel
        buttonPanel.add(newGameButton);
        buttonPanel.add(continueButton);
        buttonPanel.add(exitButton);
        
        add(buttonPanel);
    }
    
    private JButton createStyledButton(String text, Color textColor, Color borderColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Press Start 2P", Font.PLAIN, 16));
        button.setForeground(textColor);
        button.setBackground(new Color(0, 0, 0, 0)); // Transparente
        button.setOpaque(false); // Sin fondo
        button.setContentAreaFilled(false); // Sin relleno
        button.setBorder(BorderFactory.createLineBorder(borderColor, 2));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(350, 50));
        
        return button;
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }
    
    public JButton getContinueButton() {
        return continueButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public void setNewGameAction(ActionListener listener) {
        newGameButton.addActionListener(listener);
    }
    
    public void setContinueAction(ActionListener listener) {
        continueButton.addActionListener(listener);
    }

    public void setExitAction(ActionListener listener) {
        exitButton.addActionListener(listener);
    }
}