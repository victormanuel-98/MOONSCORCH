package view;

import model.GameState;
import model.PlayerCombatData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ReadyForBattle extends BaseView {
    private JButton comenzarButton;
    private GameState gameState;
    
    public ReadyForBattle() {
        initComponents();
    }
    
    @Override
    protected void initComponents() {
        super.initComponents();
        setLayout(new BorderLayout());
        
        // Panel central con mensaje
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("¡Prepárate para la aventura!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        centerPanel.add(titleLabel, gbc);
        
        JLabel messageLabel = new JLabel("Tu héroe está listo para comenzar su viaje.");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        centerPanel.add(messageLabel, gbc);
        
        // Botón para comenzar
        comenzarButton = new JButton("¡Comenzar Aventura!");
        comenzarButton.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.insets = new Insets(30, 10, 10, 10);
        centerPanel.add(comenzarButton, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    public void setComenzarAction(ActionListener action) {
        comenzarButton.addActionListener(action);
    }
    
    // Método para establecer el GameState
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        actualizarVista();
    }
    
    private void actualizarVista() {
        if (gameState != null) {
            PlayerCombatData jugador = gameState.getPlayerCombatData();
            if (jugador != null) {
                // Actualizar la vista con datos del jugador
                // Por ejemplo, mostrar nombre y clase
            }
        }
    }
}