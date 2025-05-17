package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

import model.GameState;
import view.LaunchView;
import view.CharacterMenu;
import view.GameFrame;

/**
 * Controlador para la vista de inicio del juego.
 */
public class LaunchController {
    
    private LaunchView view;
    private GameState gameState;
    private GameFrame gameFrame;
    
    /**
     * Constructor del controlador.
     * 
     * @param view Vista de inicio
     * @param gameState Estado del juego
     * @param gameFrame Frame principal del juego
     */
    public LaunchController(LaunchView view, GameState gameState, GameFrame gameFrame) {
        this.view = view;
        this.gameState = gameState;
        this.gameFrame = gameFrame;
        
        // Configurar acciones para los botones
        setupActions();
    }
    
    /**
     * Configura las acciones para los botones de la vista.
     */
    private void setupActions() {
        // Acción para el botón de nueva partida
        view.setNewGameAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        
        // Acción para el botón de salir
        view.setExitAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGame();
            }
        });
        
        // Acción para el botón de pantalla completa
        view.setFullscreenAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleFullscreen();
            }
        });
    }
    
    /**
     * Inicia una nueva partida.
     */
    private void startNewGame() {
        // Usar el GameFrame para cambiar a la vista de selección de personaje
        if (gameFrame != null) {
            gameFrame.mostrarCharacterMenu();
        } else {
            // Fallback al método anterior si gameFrame no está disponible
            view.setVisible(false);
            
            // Crear y mostrar la vista de selección de personaje
            CharacterMenu characterMenu = new CharacterMenu();
            CharacterMenuController characterController = new CharacterMenuController(characterMenu, gameState);
            characterMenu.setVisible(true);
        }
    }
    
    /**
     * Sale del juego.
     */
    private void exitGame() {
        System.out.println("Cerrando MOONSCORCH...");
        System.exit(0);
    }
    
    /**
     * Alterna entre modo pantalla completa y ventana.
     */
    private void toggleFullscreen() {
        // Verificar si tenemos acceso al GameFrame
        if (gameFrame != null) {
            if ((gameFrame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
                gameFrame.setExtendedState(JFrame.NORMAL);
            } else {
                gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        } else {
            // Buscar el JFrame padre de la vista
            JFrame parentFrame = null;
            java.awt.Container parent = view.getParent();
            while (parent != null) {
                if (parent instanceof JFrame) {
                    parentFrame = (JFrame) parent;
                    break;
                }
                parent = parent.getParent();
            }
            
            // Si encontramos el JFrame padre, alternar pantalla completa
            if (parentFrame != null) {
                if ((parentFrame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
                    parentFrame.setExtendedState(JFrame.NORMAL);
                } else {
                    parentFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                }
            } else {
                System.out.println("No se pudo encontrar el JFrame padre para alternar pantalla completa");
            }
        }
    }
}