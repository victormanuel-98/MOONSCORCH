package view;

import model.GameItem;
import model.GameState;
import model.InventoryItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InventarioView extends BaseView {
    private GameState gameState;
    private JPanel itemsPanel;
    private JLabel goldLabel;
    
    public InventarioView(GameState gameState) {
        this.gameState = gameState;
        initComponents();
        cargarInventario();
    }
    
    @Override
    protected void initComponents() {
        super.initComponents();
        setLayout(new BorderLayout());
        
        // Título
        JLabel titleLabel = new JLabel("Inventario", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        // Panel de oro
        goldLabel = new JLabel("Oro: 0", SwingConstants.RIGHT);
        goldLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(goldLabel, BorderLayout.SOUTH);
        
        // Panel de items
        itemsPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        add(scrollPane, BorderLayout.CENTER);
        
        // Botón para volver
        JButton volverButton = new JButton("Volver al Mapa");
        volverButton.addActionListener(e -> volverAlMapa());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(volverButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void cargarInventario() {
        itemsPanel.removeAll();
        
        // Actualizar oro
        if (gameState.getCurrentGame() != null) {
            goldLabel.setText("Oro: " + gameState.getCurrentGame().getGold());
        }
        
        // Cargar items
        List<InventoryItem> inventario = gameState.getInventory();
        if (inventario != null && !inventario.isEmpty()) {
            for (InventoryItem item : inventario) {
                GameItem gameItem = gameState.getGameItem(item.getItemId());
                if (gameItem != null) {
                    JPanel itemPanel = createItemPanel(gameItem, item);
                    itemsPanel.add(itemPanel);
                }
            }
        } else {
            JLabel emptyLabel = new JLabel("No tienes objetos en tu inventario", SwingConstants.CENTER);
            itemsPanel.add(emptyLabel);
        }
        
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }
    
    private JPanel createItemPanel(GameItem gameItem, InventoryItem inventoryItem) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Nombre del item
        JLabel nameLabel = new JLabel(gameItem.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(nameLabel, BorderLayout.NORTH);
        
        // Imagen (placeholder)
        JLabel imageLabel = new JLabel("🎒", SwingConstants.CENTER);
        imageLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        panel.add(imageLabel, BorderLayout.CENTER);
        
        // Cantidad
        JLabel quantityLabel = new JLabel("Cantidad: " + inventoryItem.getQuantity(), SwingConstants.CENTER);
        panel.add(quantityLabel, BorderLayout.SOUTH);
        
        // Si está equipado, marcar
        if (inventoryItem.isEquipped()) {
            panel.setBackground(new Color(200, 255, 200));
            panel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        }
        
        // Añadir listener para usar/equipar
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mostrarOpcionesItem(gameItem, inventoryItem);
            }
        });
        
        return panel;
    }
    
    private void mostrarOpcionesItem(GameItem gameItem, InventoryItem inventoryItem) {
        String[] options = {"Usar", "Equipar/Desequipar", "Descartar", "Cancelar"};
        int choice = JOptionPane.showOptionDialog(
            this,
            "¿Qué quieres hacer con " + gameItem.getName() + "?",
            "Opciones de Item",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        switch (choice) {
            case 0: // Usar
                usarItem(gameItem, inventoryItem);
                break;
            case 1: // Equipar/Desequipar
                equiparItem(inventoryItem);
                break;
            case 2: // Descartar
                descartarItem(inventoryItem);
                break;
        }
    }
    
    private void usarItem(GameItem gameItem, InventoryItem inventoryItem) {
        // Implementar lógica para usar item
        JOptionPane.showMessageDialog(this, "Has usado " + gameItem.getName());
        gameState.removeItemFromInventory(inventoryItem.getItemId(), 1);
        cargarInventario();
    }
    
    private void equiparItem(InventoryItem inventoryItem) {
        boolean nuevoEstado = !inventoryItem.isEquipped();
        gameState.equipItem(inventoryItem.getId(), nuevoEstado);
        cargarInventario();
    }
    
    private void descartarItem(InventoryItem inventoryItem) {
        int cantidad = 1;
        if (inventoryItem.getQuantity() > 1) {
            String input = JOptionPane.showInputDialog(
                this,
                "¿Cuántos quieres descartar? (1-" + inventoryItem.getQuantity() + ")",
                "Cantidad",
                JOptionPane.QUESTION_MESSAGE
            );
            try {
                cantidad = Integer.parseInt(input);
                if (cantidad < 1 || cantidad > inventoryItem.getQuantity()) {
                    cantidad = 1;
                }
            } catch (NumberFormatException e) {
                cantidad = 1;
            }
        }
        
        gameState.removeItemFromInventory(inventoryItem.getItemId(), cantidad);
        cargarInventario();
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