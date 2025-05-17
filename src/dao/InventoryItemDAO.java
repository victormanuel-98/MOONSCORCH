// Archivo: src/dao/InventoryItemDAO.java

package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import model.InventoryItem;

/**
 * DAO para gestionar ítems de inventario en la base de datos.
 */
public class InventoryItemDAO extends AbstractDAO<InventoryItem> {
    
    private static final Logger LOGGER = Logger.getLogger(InventoryItemDAO.class.getName());
    
    private static final String FIND_BY_ID = "SELECT * FROM inventory_item WHERE inventory_id = ?";
    private static final String FIND_ALL = "SELECT * FROM inventory_item";
    private static final String FIND_BY_SAVE_ID = "SELECT * FROM inventory_item WHERE save_id = ?";
    private static final String FIND_BY_SAVE_AND_ITEM = "SELECT * FROM inventory_item WHERE save_id = ? AND item_id = ?";
    private static final String INSERT = "INSERT INTO inventory_item (save_id, item_id, quantity, equipped) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE inventory_item SET save_id = ?, item_id = ?, quantity = ?, equipped = ? WHERE inventory_id = ?";
    private static final String DELETE = "DELETE FROM inventory_item WHERE inventory_id = ?";
    private static final String DELETE_BY_SAVE = "DELETE FROM inventory_item WHERE save_id = ?";
    
    @Override
    protected InventoryItem mapRow(ResultSet rs) throws SQLException {
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setId(rs.getInt("inventory_id"));
        inventoryItem.setSaveId(rs.getInt("save_id"));
        inventoryItem.setItemId(rs.getInt("item_id"));
        inventoryItem.setQuantity(rs.getInt("quantity"));
        inventoryItem.setEquipped(rs.getBoolean("equipped"));
        return inventoryItem;
    }
    
    @Override
    public InventoryItem findById(int id) {
        return executeSingleResultQuery(FIND_BY_ID, id);
    }
    
    @Override
    public List<InventoryItem> findAll() {
        return executeQuery(FIND_ALL);
    }
    
    @Override
    public boolean save(InventoryItem inventoryItem) {
        return executeUpdate(INSERT, 
                inventoryItem.getSaveId(), 
                inventoryItem.getItemId(), 
                inventoryItem.getQuantity(), 
                inventoryItem.isEquipped());
    }
    
    @Override
    public boolean update(InventoryItem inventoryItem) {
        return executeUpdate(UPDATE, 
                inventoryItem.getSaveId(), 
                inventoryItem.getItemId(), 
                inventoryItem.getQuantity(), 
                inventoryItem.isEquipped(), 
                inventoryItem.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca ítems de inventario por ID de partida guardada.
     * 
     * @param saveId ID de la partida guardada
     * @return Lista de ítems de inventario de la partida
     */
    public List<InventoryItem> findBySaveId(int saveId) {
        return executeQuery(FIND_BY_SAVE_ID, saveId);
    }
    
    /**
     * Busca un ítem de inventario específico en una partida guardada.
     * 
     * @param saveId ID de la partida guardada
     * @param itemId ID del ítem
     * @return Ítem de inventario encontrado o null si no existe
     */
    public InventoryItem findBySaveAndItem(int saveId, int itemId) {
        return executeSingleResultQuery(FIND_BY_SAVE_AND_ITEM, saveId, itemId);
    }
    
    /**
     * Elimina todos los ítems de inventario de una partida guardada.
     * 
     * @param saveId ID de la partida guardada
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean deleteBySaveId(int saveId) {
        return executeUpdate(DELETE_BY_SAVE, saveId);
    }
}