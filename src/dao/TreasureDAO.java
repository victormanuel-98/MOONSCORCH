// Archivo: src/dao/TreasureDAO.java

package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import model.Treasure;

/**
 * DAO para gestionar tesoros en la base de datos.
 */
public class TreasureDAO extends AbstractDAO<Treasure> {
    
    private static final Logger LOGGER = Logger.getLogger(TreasureDAO.class.getName());
    
    private static final String FIND_BY_ID = "SELECT * FROM treasure WHERE treasure_id = ?";
    private static final String FIND_ALL = "SELECT * FROM treasure";
    private static final String FIND_BY_NAME = "SELECT * FROM treasure WHERE name = ?";
    private static final String INSERT = "INSERT INTO treasure (name, description, treasure_type, gold_value, item_id, quantity) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE treasure SET name = ?, description = ?, treasure_type = ?, gold_value = ?, item_id = ?, quantity = ? WHERE treasure_id = ?";
    private static final String DELETE = "DELETE FROM treasure WHERE treasure_id = ?";
    
    @Override
    protected Treasure mapRow(ResultSet rs) throws SQLException {
        Treasure treasure = new Treasure();
        treasure.setId(rs.getInt("treasure_id"));
        treasure.setName(rs.getString("name"));
        treasure.setDescription(rs.getString("description"));
        treasure.setTreasureType(Treasure.TreasureType.valueOf(rs.getString("treasure_type")));
        treasure.setGoldValue(rs.getInt("gold_value"));
        
        // Manejar posibles valores NULL
        int itemId = rs.getInt("item_id");
        if (!rs.wasNull()) {
            treasure.setItemId(itemId);
        }
        
        treasure.setQuantity(rs.getInt("quantity"));
        return treasure;
    }
    
    @Override
    public Treasure findById(int id) {
        return executeSingleResultQuery(FIND_BY_ID, id);
    }
    
    @Override
    public List<Treasure> findAll() {
        return executeQuery(FIND_ALL);
    }
    
    @Override
    public boolean save(Treasure treasure) {
        return executeUpdate(INSERT, 
                treasure.getName(), 
                treasure.getDescription(), 
                treasure.getTreasureType().toString(), 
                treasure.getGoldValue(), 
                treasure.getItemId() > 0 ? treasure.getItemId() : null, 
                treasure.getQuantity());
    }
    
    @Override
    public boolean update(Treasure treasure) {
        return executeUpdate(UPDATE, 
                treasure.getName(), 
                treasure.getDescription(), 
                treasure.getTreasureType().toString(), 
                treasure.getGoldValue(), 
                treasure.getItemId() > 0 ? treasure.getItemId() : null, 
                treasure.getQuantity(), 
                treasure.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca un tesoro por su nombre.
     * 
     * @param name Nombre del tesoro a buscar
     * @return Tesoro encontrado o null si no existe
     */
    public Treasure findByName(String name) {
        return executeSingleResultQuery(FIND_BY_NAME, name);
    }
}