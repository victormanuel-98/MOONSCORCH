// Archivo: src/dao/CharacterClassDAO.java

package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import model.CharacterClass;

/**
 * DAO para gestionar clases de personajes en la base de datos.
 */
public class CharacterClassDAO extends AbstractDAO<CharacterClass> {
    
    private static final Logger LOGGER = Logger.getLogger(CharacterClassDAO.class.getName());
    
    private static final String FIND_BY_ID = "SELECT * FROM character_class WHERE class_id = ?";
    private static final String FIND_ALL = "SELECT * FROM character_class";
    private static final String FIND_BY_NAME = "SELECT * FROM character_class WHERE class_name = ?";
    private static final String INSERT = "INSERT INTO character_class (class_name, description, base_atk, base_def, base_eva, base_hp, base_mp, base_luk) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE character_class SET class_name = ?, description = ?, base_atk = ?, base_def = ?, base_eva = ?, base_hp = ?, base_mp = ?, base_luk = ? WHERE class_id = ?";
    private static final String DELETE = "DELETE FROM character_class WHERE class_id = ?";
    
    @Override
    protected CharacterClass mapRow(ResultSet rs) throws SQLException {
        CharacterClass characterClass = new CharacterClass();
        characterClass.setId(rs.getInt("class_id"));
        characterClass.setName(rs.getString("class_name"));
        characterClass.setDescription(rs.getString("description"));
        characterClass.setBaseAtk(rs.getInt("base_atk"));
        characterClass.setBaseDef(rs.getInt("base_def"));
        characterClass.setBaseEva(rs.getInt("base_eva"));
        characterClass.setBaseHp(rs.getInt("base_hp"));
        characterClass.setBaseMp(rs.getInt("base_mp"));
        characterClass.setBaseLuk(rs.getInt("base_luk"));
        return characterClass;
    }
    
    @Override
    public CharacterClass findById(int id) {
        return executeSingleResultQuery(FIND_BY_ID, id);
    }
    
    @Override
    public List<CharacterClass> findAll() {
        return executeQuery(FIND_ALL);
    }
    
    @Override
    public boolean save(CharacterClass characterClass) {
        return executeUpdate(INSERT, 
                characterClass.getName(), 
                characterClass.getDescription(), 
                characterClass.getBaseAtk(), 
                characterClass.getBaseDef(), 
                characterClass.getBaseEva(), 
                characterClass.getBaseHp(), 
                characterClass.getBaseMp(), 
                characterClass.getBaseLuk());
    }
    
    @Override
    public boolean update(CharacterClass characterClass) {
        return executeUpdate(UPDATE, 
                characterClass.getName(), 
                characterClass.getDescription(), 
                characterClass.getBaseAtk(), 
                characterClass.getBaseDef(), 
                characterClass.getBaseEva(), 
                characterClass.getBaseHp(), 
                characterClass.getBaseMp(), 
                characterClass.getBaseLuk(), 
                characterClass.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca una clase de personaje por su nombre.
     * 
     * @param name Nombre de la clase a buscar
     * @return Clase de personaje encontrada o null si no existe
     */
    public CharacterClass findByName(String name) {
        return executeSingleResultQuery(FIND_BY_NAME, name);
    }
}