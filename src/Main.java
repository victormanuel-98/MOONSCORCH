import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import util.DBConnection;
import model.GameState;
import view.LaunchView;
import view.GameFrame;
import controller.LaunchController;

/**
 * Punto de entrada principal para el juego MOONSCORCH.
 */
public class Main {
    
    /**
     * Método principal que inicia la aplicación.
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        System.out.println("Iniciando MOONSCORCH...");
        
        try {
            // Establecer el Look and Feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Ejecutar la aplicación en el hilo de eventos de Swing
            SwingUtilities.invokeLater(() -> {
                try {
                    // Comprobar conexión a la base de datos
                    Connection connection = DBConnection.getInstance().getConnection();
                    System.out.println("Conexión a la base de datos establecida correctamente.");
                    
                    // Inicializar el estado del juego
                    GameState gameState = new GameState();
                    
                    // Crear el frame principal
                    GameFrame gameFrame = new GameFrame();
                    
                    // Iniciar la interfaz gráfica
                    LaunchView launchView = new LaunchView();
                    
                    // Inicializar el controlador para la vista de inicio
                    LaunchController launchController = new LaunchController(launchView, gameState, gameFrame);
                    
                    // Añadir la vista al frame y mostrarla
                    gameFrame.cambiarVista(launchView);
                    gameFrame.setVisible(true);
                    
                } catch (Exception e) {
                    System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                    e.printStackTrace();
                }
            });
            
        } catch (Exception e) {
            System.err.println("Error al configurar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}