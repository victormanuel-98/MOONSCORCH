package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.GameState;
import view.CharacterMenu;
import view.DataPlayer;

/**
 * Controlador para la vista de selección de personaje.
 */
public class CharacterMenuController {
    
    private CharacterMenu view;
    private GameState gameState;
    
    /**
     * Constructor del controlador.
     * 
     * @param view Vista de selección de personaje
     * @param gameState Estado del juego
     */
    public CharacterMenuController(CharacterMenu view, GameState gameState) {
        this.view = view;
        this.gameState = gameState;
        
        // Configurar acciones para los botones
        setupActions();
    }
    
    /**
     * Configura las acciones para los botones de la vista.
     */
    private void setupActions() {
        // Configurar acciones específicas para la vista de selección de personaje
        // Esto dependerá de la implementación exacta de CharacterMenu
    }
}