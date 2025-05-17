package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para gestionar la conexión a la base de datos.
 * Implementa el patrón Singleton.
 */
public class DBConnection {
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
    
    // Configuración de la base de datos
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/rpg_moonscorch?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "guerragalaxias";
    
    // Instancia única
    private static DBConnection instance;
    
    // Conexión a la base de datos
    private Connection connection;
    
    /**
     * Constructor privado para implementar Singleton.
     * Inicializa la conexión a la base de datos.
     */
    private DBConnection() {
        try {
            // Cargar el driver JDBC
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                LOGGER.info("Driver JDBC cargado correctamente");
            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "Error al cargar el driver JDBC", e);
                
                // Intento alternativo con el driver antiguo
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    LOGGER.info("Driver JDBC alternativo cargado correctamente");
                } catch (ClassNotFoundException ex) {
                    LOGGER.log(Level.SEVERE, "Error al cargar el driver JDBC alternativo", ex);
                    throw new RuntimeException("Error al inicializar la conexión a la base de datos", e);
                }
            }
            
            // Establecer la conexión
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            LOGGER.info("Conexión a la base de datos establecida correctamente");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al conectar a la base de datos", e);
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }
    
    /**
     * Obtiene la instancia única de la clase.
     * 
     * @return Instancia de DBConnection
     */
    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
    
    /**
     * Obtiene la conexión a la base de datos.
     * 
     * @return Conexión a la base de datos
     * @throws SQLException si ocurre un error al obtener la conexión
     */
    public Connection getConnection() throws SQLException {
        // Verificar si la conexión está cerrada o es nula
        if (connection == null || connection.isClosed()) {
            // Reestablecer la conexión
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            LOGGER.info("Conexión a la base de datos reestablecida");
        }
        return connection;
    }
    
    /**
     * Cierra la conexión a la base de datos.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                LOGGER.info("Conexión a la base de datos cerrada correctamente");
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar la conexión a la base de datos", e);
            }
        }
    }
}