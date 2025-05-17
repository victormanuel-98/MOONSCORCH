
package com.moonscorch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.moonscorch.model.Treasure;

/**
 * DAO para gestionar tesoros en la base de datos.
 */
public class TreasureDAO extends AbstractDAO<Treasure> {
    
    private static final String FIND_BY_ID = "SELECT * FROM treasure WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM treasure";
    private static final String FIND_BY_NODE_ID = "SELECT * FROM treasure WHERE node_id = ?";
    private static final String FIND_BY_RARITY = "SELECT * FROM treasure WHERE rarity = ?";
    private static final String INSERT = "INSERT INTO treasure (node_id, name, description, gold_amount, rarity, requires_key) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE treasure SET node_id = ?, name = ?, description = ?, gold_amount = ?, rarity = ?, requires_key = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM treasure WHERE id = ?";
    private static final String ADD_ITEM_TO_TREASURE = "INSERT INTO treasure_item (treasure_id, item_id, quantity) VALUES (?, ?, ?)";
    private static final String REMOVE_ITEM_FROM_TREASURE = "DELETE FROM treasure_item WHERE treasure_id = ? AND item_id = ?";
    private static final String FIND_ITEMS_IN_TREASURE = "SELECT i.*, ti.quantity FROM game_item i JOIN treasure_item ti ON i.id = ti.item_id WHERE ti.treasure_id = ?";
    
    @Override
    protected Treasure mapRow(ResultSet rs) throws SQLException {
        Treasure treasure = new Treasure();
        treasure.setId(rs.getInt("id"));
        treasure.setNodeId(rs.getInt("node_id"));
        treasure.setName(rs.getString("name"));
        treasure.setDescription(rs.getString("description"));
        treasure.setGoldAmount(rs.getInt("gold_amount"));
        treasure.setRarity(rs.getInt("rarity"));
        treasure.setRequiresKey(rs.getBoolean("requires_key"));
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
                treasure.getNodeId(), 
                treasure.getName(), 
                treasure.getDescription(), 
                treasure.getGoldAmount(), 
                treasure.getRarity(), 
                treasure.isRequiresKey());
    }
    
    @Override
    public boolean update(Treasure treasure) {
        return executeUpdate(UPDATE, 
                treasure.getNodeId(), 
                treasure.getName(), 
                treasure.getDescription(), 
                treasure.getGoldAmount(), 
                treasure.getRarity(), 
                treasure.isRequiresKey(), 
                treasure.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca tesoros por nodo del mapa.
     * 
     * @param nodeId ID del nodo
     * @return Lista de tesoros en el nodo especificado
     */
    public List<Treasure> findByNodeId(int nodeId) {
        return executeQuery(FIND_BY_NODE_ID, nodeId);
    }
    
    /**
     * Busca tesoros por rareza.
     * 
     * @param rarity Nivel de rareza
     * @return Lista de tesoros con la rareza especificada
     */
    public List<Treasure> findByRarity(int rarity) {
        return executeQuery(FIND_BY_RARITY, rarity);
    }
    
    /**
     * Añade un objeto a un tesoro.
     * 
     * @param treasureId ID del tesoro
     * @param itemId ID del objeto
     * @param quantity Cantidad del objeto
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean addItemToTreasure(int treasureId, int itemId, int quantity) {
        return executeUpdate(ADD_ITEM_TO_TREASURE, treasureId, itemId, quantity);
    }
    
    /**
     * Elimina un objeto de un tesoro.
     * 
     * @param treasureId ID del tesoro
     * @param itemId ID del objeto
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean removeItemFromTreasure(int treasureId, int itemId) {
        return executeUpdate(REMOVE_ITEM_FROM_TREASURE, treasureId, itemId);
    }
    
    /**
     * Busca objetos en un tesoro.
     * 
     * @param treasureId ID del tesoro
     * @return Lista de objetos en el tesoro con sus cantidades
     */
    public List<Object[]> findItemsInTreasure(int treasureId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Object[]> result = new ArrayList<>();
        
        try {
            conn = dbConnection.getConnection();
            stmt = conn.prepareStatement(FIND_ITEMS_IN_TREASURE);
            stmt.setInt(1, treasureId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                // Crear un objeto GameItem
                GameItem item = new GameItem();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setItemType(rs.getString("item_type"));
                item.setEffectValue(rs.getInt("effect_value"));
                item.setRarity(rs.getInt("rarity"));
                item.setUsableInCombat(rs.getBoolean("usable_in_combat"));
                
                // Obtener la cantidad
                int quantity = rs.getInt("quantity");
                
                // Añadir el par [item, cantidad] a la lista de resultados
                result.add(new Object[]{item, quantity});
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar objetos en tesoro", e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return result;
    }
    
    /**
     * Busca tesoros que requieren llave.
     * 
     * @return Lista de tesoros que requieren llave
     */
    public List<Treasure> findTreasuresRequiringKey() {
        return executeQuery("SELECT * FROM treasure WHERE requires_key = 1");
    }
    
    /**
     * Busca tesoros con grandes cantidades de oro.
     * 
     * @param minGold Cantidad mínima de oro
     * @return Lista de tesoros con grandes cantidades de oro
     */
    public List<Treasure> findTreasuresWithHighGold(int minGold) {
        return executeQuery("SELECT * FROM treasure WHERE gold_amount >= ? ORDER BY gold_amount DESC", minGold);
    }
}
