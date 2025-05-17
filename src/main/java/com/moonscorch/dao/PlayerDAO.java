
package com.moonscorch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.moonscorch.model.Player;

/**
 * DAO para gestionar jugadores/usuarios en la base de datos.
 */
public class PlayerDAO extends AbstractDAO<Player> {
    
    private static final Logger LOGGER = Logger.getLogger(PlayerDAO.class.getName());
    
    private static final String FIND_BY_ID = "SELECT * FROM player WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM player";
    private static final String FIND_BY_USERNAME = "SELECT * FROM player WHERE username = ?";
    private static final String INSERT = "INSERT INTO player (username, password, email, created_at) VALUES (?, ?, ?, NOW())";
    private static final String UPDATE = "UPDATE player SET username = ?, password = ?, email = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM player WHERE id = ?";
    
    @Override
    protected Player mapRow(ResultSet rs) throws SQLException {
        Player player = new Player();
        player.setId(rs.getInt("id"));
        player.setUsername(rs.getString("username"));
        player.setPassword(rs.getString("password"));
        player.setEmail(rs.getString("email"));
        player.setCreatedAt(rs.getTimestamp("created_at"));
        return player;
    }
    
    @Override
    public Player findById(int id) {
        return executeSingleResultQuery(FIND_BY_ID, id);
    }
    
    @Override
    public List<Player> findAll() {
        return executeQuery(FIND_ALL);
    }
    
    @Override
    public boolean save(Player player) {
        return executeUpdate(INSERT, 
                player.getUsername(), 
                player.getPassword(), 
                player.getEmail());
    }
    
    @Override
    public boolean update(Player player) {
        return executeUpdate(UPDATE, 
                player.getUsername(), 
                player.getPassword(), 
                player.getEmail(), 
                player.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca un jugador por su nombre de usuario.
     * 
     * @param username Nombre de usuario a buscar
     * @return Jugador encontrado o null si no existe
     */
    public Player findByUsername(String username) {
        return executeSingleResultQuery(FIND_BY_USERNAME, username);
    }
    
    /**
     * Verifica las credenciales de un jugador para el inicio de sesión.
     * 
     * @param username Nombre de usuario
     * @param password Contraseña
     * @return Jugador autenticado o null si las credenciales son inválidas
     */
    public Player authenticate(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Player player = null;
        
        try {
            conn = dbConnection.getConnection();
            stmt = conn.prepareStatement(FIND_BY_USERNAME);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                // En una aplicación real, la contraseña debería estar hasheada
                String storedPassword = rs.getString("password");
                if (password.equals(storedPassword)) {
                    player = mapRow(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al autenticar usuario", e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return player;
    }
}
