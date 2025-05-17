
package com.moonscorch.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase de utilidad para gestionar la conexión a la base de datos MySQL.
 * Implementa el patrón Singleton para asegurar una única instancia de conexión.
 */
public class DBConnection {
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
    
    // Configuración de la conexión a la base de datos
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/moonscorch?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "password"; // Cambiar según configuración
    
    private static DBConnection instance;
    
    /**
     * Constructor privado para evitar instanciación directa (patrón Singleton).
     */
    private DBConnection() {
        try {
            // Registrar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error al cargar el driver JDBC", e);
            throw new RuntimeException("Error al inicializar la conexión a la base de datos", e);
        }
    }
    
    /**
     * Obtiene la única instancia de la clase DBConnection.
     * 
     * @return Instancia única de DBConnection
     */
    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
    
    /**
     * Establece una conexión con la base de datos.
     * 
     * @return Objeto Connection para interactuar con la base de datos
     * @throws SQLException Si ocurre un error al conectar con la base de datos
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }
    
    /**
     * Cierra de forma segura una conexión a la base de datos.
     * 
     * @param connection Conexión a cerrar
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar la conexión a la base de datos", e);
            }
        }
    }
}
