
package com.moonscorch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.moonscorch.model.GameItem;

/**
 * DAO para gestionar objetos del juego en la base de datos.
 */
public class GameItemDAO extends AbstractDAO<GameItem> {
    
    private static final String FIND_BY_ID = "SELECT * FROM game_item WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM game_item";
    private static final String FIND_BY_TYPE = "SELECT * FROM game_item WHERE item_type = ?";
    private static final String FIND_BY_NAME = "SELECT * FROM game_item WHERE name LIKE ?";
    private static final String INSERT = "INSERT INTO game_item (name, description, item_type, effect_value, rarity, usable_in_combat) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE game_item SET name = ?, description = ?, item_type = ?, effect_value = ?, rarity = ?, usable_in_combat = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM game_item WHERE id = ?";
    
    @Override
    protected GameItem mapRow(ResultSet rs) throws SQLException {
        GameItem gameItem = new GameItem();
        gameItem.setId(rs.getInt("id"));
        gameItem.setName(rs.getString("name"));
        gameItem.setDescription(rs.getString("description"));
        gameItem.setItemType(rs.getString("item_type"));
        gameItem.setEffectValue(rs.getInt("effect_value"));
        gameItem.setRarity(rs.getInt("rarity"));
        gameItem.setUsableInCombat(rs.getBoolean("usable_in_combat"));
        return gameItem;
    }
    
    @Override
    public GameItem findById(int id) {
        return executeSingleResultQuery(FIND_BY_ID, id);
    }
    
    @Override
    public List<GameItem> findAll() {
        return executeQuery(FIND_ALL);
    }
    
    @Override
    public boolean save(GameItem gameItem) {
        return executeUpdate(INSERT, 
                gameItem.getName(), 
                gameItem.getDescription(), 
                gameItem.getItemType(), 
                gameItem.getEffectValue(), 
                gameItem.getRarity(), 
                gameItem.isUsableInCombat());
    }
    
    @Override
    public boolean update(GameItem gameItem) {
        return executeUpdate(UPDATE, 
                gameItem.getName(), 
                gameItem.getDescription(), 
                gameItem.getItemType(), 
                gameItem.getEffectValue(), 
                gameItem.getRarity(), 
                gameItem.isUsableInCombat(), 
                gameItem.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca objetos por tipo (poción, arma, armadura, etc.).
     * 
     * @param itemType Tipo de objeto a buscar
     * @return Lista de objetos del tipo especificado
     */
    public List<GameItem> findByType(String itemType) {
        return executeQuery(FIND_BY_TYPE, itemType);
    }
    
    /**
     * Busca objetos por nombre (búsqueda parcial).
     * 
     * @param name Nombre o parte del nombre a buscar
     * @return Lista de objetos que coinciden con el criterio de búsqueda
     */
    public List<GameItem> findByName(String name) {
        return executeQuery(FIND_BY_NAME, "%" + name + "%");
    }
    
    /**
     * Busca objetos usables en combate.
     * 
     * @return Lista de objetos usables en combate
     */
    public List<GameItem> findUsableInCombat() {
        return executeQuery("SELECT * FROM game_item WHERE usable_in_combat = 1");
    }
    
    /**
     * Busca objetos por rareza.
     * 
     * @param rarity Nivel de rareza (1-5 generalmente)
     * @return Lista de objetos con la rareza especificada
     */
    public List<GameItem> findByRarity(int rarity) {
        return executeQuery("SELECT * FROM game_item WHERE rarity = ?", rarity);
    }
}
