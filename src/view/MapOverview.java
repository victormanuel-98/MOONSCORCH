package view;

import model.GameState;
import model.MapNode;
import model.PlayerCombatData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MapOverview extends BaseView {
    private GameState gameState;
    private JPanel mapPanel;
    private JLabel playerInfoLabel;
    private int nodoActual = 1;
    
    // Constructor que recibe GameState
    public MapOverview(GameState gameState) {
        this.gameState = gameState;
        initComponents();
        cargarDatosJugador();
        cargarMapa();
    }
    
    @Override
    protected void initComponents() {
        super.initComponents();
        setLayout(new BorderLayout());
        
        // Panel de información del jugador
        playerInfoLabel = new JLabel("Información del jugador");
        add(playerInfoLabel, BorderLayout.NORTH);
        
        // Panel del mapa
        mapPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        add(mapPanel, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton inventarioButton = new JButton("Inventario");
        inventarioButton.addActionListener(e -> mostrarInventario());
        buttonPanel.add(inventarioButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void cargarDatosJugador() {
        PlayerCombatData jugador = gameState.getPlayerCombatData();
        if (jugador != null) {
            playerInfoLabel.setText(
                String.format("Héroe: %s | Clase: %s | Nivel: %d | HP: %d/%d | MP: %d/%d",
                    jugador.getName(),
                    jugador.getClase(),
                    jugador.getNivel(),
                    jugador.getCurrentHp(),
                    jugador.getMaxHp(),
                    jugador.getCurrentMp(),
                    jugador.getMaxMp()
                )
            );
        }
    }
    
    private void cargarMapa() {
        mapPanel.removeAll();
        
        // Aquí deberías cargar los nodos del mapa desde gameState
        // Por ahora, crearemos nodos de ejemplo
        for (int i = 1; i <= 9; i++) {
            JButton nodoButton = new JButton("Nodo " + i);
            final int nodoId = i;
            
            // Marcar el nodo actual
            if (i == nodoActual) {
                nodoButton.setBackground(Color.GREEN);
                nodoButton.setText("Nodo " + i + " (Actual)");
            }
            
            nodoButton.addActionListener(e -> visitarNodo(nodoId));
            mapPanel.add(nodoButton);
        }
        
        mapPanel.revalidate();
        mapPanel.repaint();
    }
    
    private void visitarNodo(int nodoId) {
        // Aquí deberías implementar la lógica para visitar un nodo
        // Por ahora, solo actualizamos el nodo actual
        nodoActual = nodoId;
        cargarMapa();
        
        // Ejemplo: Si hay un enemigo, mostrar combate
        if (nodoId % 3 == 0) {
            JOptionPane.showMessageDialog(this, "¡Has encontrado un enemigo en este nodo!");
            // Aquí iniciarías el combate
        } 
        // Ejemplo: Si hay un tesoro, mostrarlo
        else if (nodoId % 2 == 0) {
            JOptionPane.showMessageDialog(this, "¡Has encontrado un tesoro en este nodo!");
            // Aquí mostrarías la vista de tesoro
        }
    }
    
    private void mostrarInventario() {
        // Aquí deberías mostrar la vista de inventario
        JOptionPane.showMessageDialog(this, "Abriendo inventario...");
        // Ejemplo: gameFrame.cambiarVista(new InventarioView(gameState));
    }
    
    public void incrementarNodoActual() {
        nodoActual++;
        cargarMapa();
    }
    
    public void marcarNodoComoCompletado(int nodoId) {
        // Implementar lógica para marcar nodo como completado
        JOptionPane.showMessageDialog(this, "Nodo " + nodoId + " completado");
        cargarMapa();
    }
}