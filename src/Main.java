import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import view.GameFrame;
import model.database.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Para iniciar la base de datos
            DatabaseManager.inicializarDB();
            
            // Aquí se muestra la pantalla principal de login
            GameFrame frame = new GameFrame();
            frame.mostrarLogin();
            frame.setVisible(true);
            
            // Aquí se cierra la conexión con la base de datos al cerrar el juego
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    DatabaseManager.cerrarConexion();
                    System.exit(0);
                }
            });
        });
    }
}