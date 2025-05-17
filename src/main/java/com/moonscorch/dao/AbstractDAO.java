
package com.moonscorch.dao;

import com.moonscorch.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase abstracta que implementa la interfaz DAO y proporciona funcionalidad común
 * para todas las clases DAO específicas.
 * 
 * @param <T> Tipo de entidad que maneja el DAO
 */
public abstract class AbstractDAO<T> implements DAO<T> {
    
    private static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    protected final DBConnection dbConnection;
    
    /**
     * Constructor que inicializa la conexión a la base de datos.
     */
    public AbstractDAO() {
        this.dbConnection = DBConnection.getInstance();
    }
    
    /**
     * Método abstracto que debe ser implementado por las subclases para
     * mapear un ResultSet a un objeto de dominio.
     * 
     * @param rs ResultSet con los datos de la entidad
     * @return Objeto de dominio mapeado
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet
     */
    protected abstract T mapRow(ResultSet rs) throws SQLException;
    
    /**
     * Ejecuta una consulta SQL y mapea los resultados a una lista de objetos.
     * 
     * @param sql Consulta SQL a ejecutar
     * @param params Parámetros para la consulta preparada
     * @return Lista de objetos mapeados
     */
    protected List<T> executeQuery(String sql, Object... params) {
        List<T> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            
            // Establecer parámetros en la consulta preparada
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al ejecutar consulta: " + sql, e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return result;
    }
    
    /**
     * Ejecuta una consulta SQL y devuelve un único objeto.
     * 
     * @param sql Consulta SQL a ejecutar
     * @param params Parámetros para la consulta preparada
     * @return Objeto mapeado o null si no se encuentra
     */
    protected T executeSingleResultQuery(String sql, Object... params) {
        List<T> results = executeQuery(sql, params);
        return results.isEmpty() ? null : results.get(0);
    }
    
    /**
     * Ejecuta una sentencia SQL de actualización (INSERT, UPDATE, DELETE).
     * 
     * @param sql Sentencia SQL a ejecutar
     * @param params Parámetros para la sentencia preparada
     * @return true si la operación fue exitosa, false en caso contrario
     */
    protected boolean executeUpdate(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = dbConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            
            // Establecer parámetros en la sentencia preparada
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al ejecutar actualización: " + sql, e);
        } finally {
            closeResources(conn, stmt, null);
        }
        
        return success;
    }
    
    /**
     * Cierra de forma segura los recursos de base de datos.
     * 
     * @param conn Conexión a cerrar
     * @param stmt Sentencia preparada a cerrar
     * @param rs ResultSet a cerrar
     */
    protected void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar ResultSet", e);
            }
        }
        
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar PreparedStatement", e);
            }
        }
        
        DBConnection.closeConnection(conn);
    }
}
