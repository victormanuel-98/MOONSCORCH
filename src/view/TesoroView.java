package view;

import model.GameItem;
import model.GameState;
import model.Treasure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TesoroView extends BaseView {
    private GameState gameState;
    private Treasure tesoro;
    private JButton recogerButton;
    private JButton dejarButton;
    
    public TesoroView(GameState gameState, Treasure tesoro) {
        this.gameState = gameState;
        this.tesoro = tesoro;
        initComponents();
    }
    
    @Override
    protected void initComponents() {
        super.initComponents();
        setLayout(new BorderLayout());
        
        // Panel central
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Título
        JLabel titleLabel = new JLabel("¡Has encontrado un tesoro!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        centerPanel.add(titleLabel, gbc);
        
        // Imagen del tesoro (placeholder)
        JLabel imageLabel = new JLabel("💰", SwingConstants.CENTER);
        imageLabel.setFont(new Font("Arial", Font.PLAIN, 72));
        centerPanel.add(imageLabel, gbc);
        
        // Descripción del tesoro
        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        if (tesoro != null) {
            // Tipo de tesoro
            JLabel typeLabel = new JLabel("Tipo: " + tesoro.getTreasureType());
            infoPanel.add(typeLabel);
            
            // Oro
            if (tesoro.getGoldValue() > 0) {
                JLabel goldLabel = new JLabel("Oro: " + tesoro.getGoldValue());
                goldLabel.setForeground(new Color(218, 165, 32)); // Gold color
                infoPanel.add(goldLabel);
            }
            
            // Item (si hay)
            if (tesoro.getItemId() > 0) {
                GameItem item = gameState.getGameItem(tesoro.getItemId());
                if (item != null) {
                    JLabel itemLabel = new JLabel("Item: " + item.getName());
                    infoPanel.add(itemLabel);
                    
                    JLabel itemDescLabel = new JLabel(item.getDescription());
                    itemDescLabel.setFont(new Font("Arial", Font.ITALIC, 12));
                    infoPanel.add(itemDescLabel);
                }
            }
        }
        
        centerPanel.add(infoPanel, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        recogerButton = new JButton("Recoger Tesoro");
        recogerButton.addActionListener(e -> recogerTesoro());
        buttonPanel.add(recogerButton);
        
        dejarButton = new JButton("Dejar");
        dejarButton.addActionListener(e -> dejarTesoro());
        buttonPanel.add(dejarButton);
        
        centerPanel.add(buttonPanel, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void recogerTesoro() {
        if (tesoro != null && gameState != null) {
            // Añadir oro
            if (tesoro.getGoldValue() > 0 && gameState.getCurrentGame() != null) {
                int nuevoOro = gameState.getCurrentGame().getGold() + tesoro.getGoldValue();
                gameState.getCurrentGame().setGold(nuevoOro);
                gameState.saveGame();
            }
            
            // Añadir item (si hay)
            if (tesoro.getItemId() > 0) {
                GameItem item = gameState.getGameItem(tesoro.getItemId());
                if (item != null) {
                    gameState.addItemToInventory(item, 1);
                }
            }
            
            // Marcar nodo como completado
            if (gameState.getCurrentNode() != null) {
                gameState.completeNode(gameState.getCurrentNode().getId());
            }
            
            // Volver al mapa
            volverAlMapa();
        }
    }
    
    private void dejarTesoro() {
        // Simplemente volver al mapa sin recoger el tesoro
        volverAlMapa();
    }
    
    private void volverAlMapa() {
        // Volver al mapa
        JPanel mapOverview = gameState.getMapOverview();
        if (mapOverview != null) {
            Container parent = getParent();
            if (parent instanceof JPanel) {
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, mapOverview.getClass().getName());
            }
        }
    }
}