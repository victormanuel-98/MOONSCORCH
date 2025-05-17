// Archivo: src/dao/GameItemDAO.java

package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.GameItem;
import model.GameItem.ItemType;

/**
 * DAO para gestionar ítems del juego en la base de datos.
 */
public class GameItemDAO extends AbstractDAO<GameItem> {
    
    private static final Logger LOGGER = Logger.getLogger(GameItemDAO.class.getName());
    
    private static final String FIND_BY_ID = "SELECT * FROM game_item WHERE item_id = ?";
    private static final String FIND_ALL = "SELECT * FROM game_item";
    private static final String FIND_BY_NAME = "SELECT * FROM game_item WHERE name = ?";
    private static final String FIND_BY_TYPE = "SELECT * FROM game_item WHERE item_type_id = ?";
    private static final String INSERT = "INSERT INTO game_item (name, description, item_type_id, value, atk_mod, def_mod, eva_mod, hp_mod, mp_mod, luk_mod, is_equippable, is_consumable, is_key_item, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE game_item SET name = ?, description = ?, item_type_id = ?, value = ?, atk_mod = ?, def_mod = ?, eva_mod = ?, hp_mod = ?, mp_mod = ?, luk_mod = ?, is_equippable = ?, is_consumable = ?, is_key_item = ?, image_path = ? WHERE item_id = ?";
    private static final String DELETE = "DELETE FROM game_item WHERE item_id = ?";
    
    @Override
    protected GameItem mapRow(ResultSet rs) throws SQLException {
        GameItem item = new GameItem();
        item.setId(rs.getInt("item_id"));
        item.setName(rs.getString("name"));
        item.setDescription(rs.getString("description"));
        
        // Manejar el campo item_type_id
        item.setItemTypeId(rs.getInt("item_type_id"));
        
        // Manejar los campos de modificadores
        item.setValue(rs.getInt("value"));
        
        try {
            item.setAtkMod(rs.getInt("atk_mod"));
            item.setDefMod(rs.getInt("def_mod"));
            item.setEvaMod(rs.getInt("eva_mod"));
            item.setHpMod(rs.getInt("hp_mod"));
            item.setMpMod(rs.getInt("mp_mod"));
            item.setLukMod(rs.getInt("luk_mod"));
            item.setEquippable(rs.getBoolean("is_equippable"));
            item.setConsumable(rs.getBoolean("is_consumable"));
            item.setKeyItem(rs.getBoolean("is_key_item"));
        } catch (SQLException e) {
            // Si alguna columna no existe, simplemente ignoramos el error
            LOGGER.log(Level.WARNING, "Algunas columnas no existen en la tabla game_item", e);
        }
        
        // Manejar el campo image_path
        try {
            String imagePath = rs.getString("image_path");
            if (imagePath != null) {
                item.setImagePath(imagePath);
            }
        } catch (SQLException e) {
            // Si la columna no existe, simplemente ignoramos el error
            LOGGER.log(Level.WARNING, "La columna image_path no existe en la tabla game_item", e);
        }
        
        return item;
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
    public boolean save(GameItem item) {
        return executeUpdate(INSERT, 
                item.getName(), 
                item.getDescription(), 
                item.getItemTypeId(),
                item.getValue(),
                item.getAtkMod(),
                item.getDefMod(),
                item.getEvaMod(),
                item.getHpMod(),
                item.getMpMod(),
                item.getLukMod(),
                item.isEquippable(),
                item.isConsumable(),
                item.isKeyItem(),
                item.getImagePath());
    }
    
    @Override
    public boolean update(GameItem item) {
        return executeUpdate(UPDATE, 
                item.getName(), 
                item.getDescription(), 
                item.getItemTypeId(),
                item.getValue(),
                item.getAtkMod(),
                item.getDefMod(),
                item.getEvaMod(),
                item.getHpMod(),
                item.getMpMod(),
                item.getLukMod(),
                item.isEquippable(),
                item.isConsumable(),
                item.isKeyItem(),
                item.getImagePath(),
                item.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca un ítem por su nombre.
     * 
     * @param name Nombre del ítem a buscar
     * @return Ítem encontrado o null si no existe
     */
    public GameItem findByName(String name) {
        return executeSingleResultQuery(FIND_BY_NAME, name);
    }
    
    /**
     * Busca ítems por su tipo.
     * 
     * @param type Tipo de ítem a buscar
     * @return Lista de ítems del tipo especificado
     */
    public List<GameItem> findByType(ItemType type) {
        return executeQuery(FIND_BY_TYPE, type.getId());
    }
    
    /**
     * Busca ítems por su ID de tipo.
     * 
     * @param typeId ID del tipo de ítem a buscar
     * @return Lista de ítems del tipo especificado
     */
    public List<GameItem> findByTypeId(int typeId) {
        return executeQuery(FIND_BY_TYPE, typeId);
    }
}