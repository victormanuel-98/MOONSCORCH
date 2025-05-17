
package com.moonscorch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.moonscorch.model.InventoryItem;

/**
 * DAO para gestionar el inventario de los personajes en la base de datos.
 */
public class InventoryItemDAO extends AbstractDAO<InventoryItem> {
    
    private static final String FIND_BY_ID = "SELECT * FROM inventory_item WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM inventory_item";
    private static final String FIND_BY_CHARACTER_ID = "SELECT * FROM inventory_item WHERE character_id = ?";
    private static final String INSERT = "INSERT INTO inventory_item (character_id, item_id, quantity, equipped) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE inventory_item SET character_id = ?, item_id = ?, quantity = ?, equipped = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM inventory_item WHERE id = ?";
    private static final String DELETE_BY_CHARACTER_AND_ITEM = "DELETE FROM inventory_item WHERE character_id = ? AND item_id = ?";
    private static final String UPDATE_QUANTITY = "UPDATE inventory_item SET quantity = ? WHERE character_id = ? AND item_id = ?";
    private static final String FIND_BY_CHARACTER_AND_ITEM = "SELECT * FROM inventory_item WHERE character_id = ? AND item_id = ?";
    
    @Override
    protected InventoryItem mapRow(ResultSet rs) throws SQLException {
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setId(rs.getInt("id"));
        inventoryItem.setCharacterId(rs.getInt("character_id"));
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
                inventoryItem.getCharacterId(), 
                inventoryItem.getItemId(), 
                inventoryItem.getQuantity(), 
                inventoryItem.isEquipped());
    }
    
    @Override
    public boolean update(InventoryItem inventoryItem) {
        return executeUpdate(UPDATE, 
                inventoryItem.getCharacterId(), 
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
     * Busca todos los objetos en el inventario de un personaje.
     * 
     * @param characterId ID del personaje
     * @return Lista de objetos en el inventario del personaje
     */
    public List<InventoryItem> findByCharacterId(int characterId) {
        return executeQuery(FIND_BY_CHARACTER_ID, characterId);
    }
    
    /**
     * Busca un objeto específico en el inventario de un personaje.
     * 
     * @param characterId ID del personaje
     * @param itemId ID del objeto
     * @return El objeto en el inventario o null si no existe
     */
    public InventoryItem findByCharacterAndItem(int characterId, int itemId) {
        return executeSingleResultQuery(FIND_BY_CHARACTER_AND_ITEM, characterId, itemId);
    }
    
    /**
     * Actualiza la cantidad de un objeto en el inventario.
     * 
     * @param characterId ID del personaje
     * @param itemId ID del objeto
     * @param quantity Nueva cantidad
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean updateQuantity(int characterId, int itemId, int quantity) {
        return executeUpdate(UPDATE_QUANTITY, quantity, characterId, itemId);
    }
    
    /**
     * Elimina un objeto del inventario de un personaje.
     * 
     * @param characterId ID del personaje
     * @param itemId ID del objeto
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean deleteByCharacterAndItem(int characterId, int itemId) {
        return executeUpdate(DELETE_BY_CHARACTER_AND_ITEM, characterId, itemId);
    }
    
    /**
     * Añade un objeto al inventario o incrementa su cantidad si ya existe.
     * 
     * @param characterId ID del personaje
     * @param itemId ID del objeto
     * @param quantity Cantidad a añadir
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean addItem(int characterId, int itemId, int quantity) {
        InventoryItem existingItem = findByCharacterAndItem(characterId, itemId);
        
        if (existingItem != null) {
            // El objeto ya existe, actualizar cantidad
            int newQuantity = existingItem.getQuantity() + quantity;
            return updateQuantity(characterId, itemId, newQuantity);
        } else {
            // El objeto no existe, crear nuevo registro
            InventoryItem newItem = new InventoryItem();
            newItem.setCharacterId(characterId);
            newItem.setItemId(itemId);
            newItem.setQuantity(quantity);
            newItem.setEquipped(false);
            return save(newItem);
        }
    }
    
    /**
     * Obtiene todos los objetos equipados por un personaje.
     * 
     * @param characterId ID del personaje
     * @return Lista de objetos equipados
     */
    public List<InventoryItem> findEquippedItems(int characterId) {
        return executeQuery("SELECT * FROM inventory_item WHERE character_id = ? AND equipped = 1", characterId);
    }
}
