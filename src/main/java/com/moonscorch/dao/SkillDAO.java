
package com.moonscorch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.moonscorch.model.Skill;

/**
 * DAO para gestionar habilidades en la base de datos.
 */
public class SkillDAO extends AbstractDAO<Skill> {
    
    private static final String FIND_BY_ID = "SELECT * FROM skill WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM skill";
    private static final String FIND_BY_CHARACTER_CLASS = "SELECT s.* FROM skill s JOIN character_class_skill cs ON s.id = cs.skill_id WHERE cs.character_class_id = ?";
    private static final String FIND_BY_NAME = "SELECT * FROM skill WHERE name LIKE ?";
    private static final String INSERT = "INSERT INTO skill (name, description, mp_cost, effect_type, effect_value, target_type, skill_level) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE skill SET name = ?, description = ?, mp_cost = ?, effect_type = ?, effect_value = ?, target_type = ?, skill_level = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM skill WHERE id = ?";
    private static final String ASSIGN_TO_CLASS = "INSERT INTO character_class_skill (character_class_id, skill_id) VALUES (?, ?)";
    private static final String REMOVE_FROM_CLASS = "DELETE FROM character_class_skill WHERE character_class_id = ? AND skill_id = ?";
    
    @Override
    protected Skill mapRow(ResultSet rs) throws SQLException {
        Skill skill = new Skill();
        skill.setId(rs.getInt("id"));
        skill.setName(rs.getString("name"));
        skill.setDescription(rs.getString("description"));
        skill.setMpCost(rs.getInt("mp_cost"));
        skill.setEffectType(rs.getString("effect_type"));
        skill.setEffectValue(rs.getInt("effect_value"));
        skill.setTargetType(rs.getString("target_type"));
        skill.setSkillLevel(rs.getInt("skill_level"));
        return skill;
    }
    
    @Override
    public Skill findById(int id) {
        return executeSingleResultQuery(FIND_BY_ID, id);
    }
    
    @Override
    public List<Skill> findAll() {
        return executeQuery(FIND_ALL);
    }
    
    @Override
    public boolean save(Skill skill) {
        return executeUpdate(INSERT, 
                skill.getName(), 
                skill.getDescription(), 
                skill.getMpCost(), 
                skill.getEffectType(), 
                skill.getEffectValue(), 
                skill.getTargetType(), 
                skill.getSkillLevel());
    }
    
    @Override
    public boolean update(Skill skill) {
        return executeUpdate(UPDATE, 
                skill.getName(), 
                skill.getDescription(), 
                skill.getMpCost(), 
                skill.getEffectType(), 
                skill.getEffectValue(), 
                skill.getTargetType(), 
                skill.getSkillLevel(), 
                skill.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca habilidades por clase de personaje.
     * 
     * @param characterClassId ID de la clase de personaje
     * @return Lista de habilidades disponibles para la clase
     */
    public List<Skill> findByCharacterClass(int characterClassId) {
        return executeQuery(FIND_BY_CHARACTER_CLASS, characterClassId);
    }
    
    /**
     * Busca habilidades por nombre (búsqueda parcial).
     * 
     * @param name Nombre o parte del nombre a buscar
     * @return Lista de habilidades que coinciden con el criterio de búsqueda
     */
    public List<Skill> findByName(String name) {
        return executeQuery(FIND_BY_NAME, "%" + name + "%");
    }
    
    /**
     * Asigna una habilidad a una clase de personaje.
     * 
     * @param characterClassId ID de la clase de personaje
     * @param skillId ID de la habilidad
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean assignToClass(int characterClassId, int skillId) {
        return executeUpdate(ASSIGN_TO_CLASS, characterClassId, skillId);
    }
    
    /**
     * Elimina una habilidad de una clase de personaje.
     * 
     * @param characterClassId ID de la clase de personaje
     * @param skillId ID de la habilidad
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean removeFromClass(int characterClassId, int skillId) {
        return executeUpdate(REMOVE_FROM_CLASS, characterClassId, skillId);
    }
    
    /**
     * Busca habilidades por tipo de efecto (daño, curación, buff, etc.).
     * 
     * @param effectType Tipo de efecto
     * @return Lista de habilidades con el tipo de efecto especificado
     */
    public List<Skill> findByEffectType(String effectType) {
        return executeQuery("SELECT * FROM skill WHERE effect_type = ?", effectType);
    }
    
    /**
     * Busca habilidades por tipo de objetivo (uno, todos, aliado, enemigo, etc.).
     * 
     * @param targetType Tipo de objetivo
     * @return Lista de habilidades con el tipo de objetivo especificado
     */
    public List<Skill> findByTargetType(String targetType) {
        return executeQuery("SELECT * FROM skill WHERE target_type = ?", targetType);
    }
    
    /**
     * Busca habilidades por nivel de habilidad.
     * 
     * @param skillLevel Nivel de habilidad
     * @return Lista de habilidades con el nivel especificado
     */
    public List<Skill> findBySkillLevel(int skillLevel) {
        return executeQuery("SELECT * FROM skill WHERE skill_level = ?", skillLevel);
    }
}
