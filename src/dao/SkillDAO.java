// Archivo: src/dao/SkillDAO.java

package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import model.Skill;

/**
 * DAO para gestionar habilidades en la base de datos.
 */
public class SkillDAO extends AbstractDAO<Skill> {
    
    private static final Logger LOGGER = Logger.getLogger(SkillDAO.class.getName());
    
    private static final String FIND_BY_ID = "SELECT * FROM skill WHERE skill_id = ?";
    private static final String FIND_ALL = "SELECT * FROM skill";
    private static final String FIND_BY_NAME = "SELECT * FROM skill WHERE name = ?";
    private static final String FIND_BY_CLASS = "SELECT s.* FROM skill s JOIN class_skill cs ON s.skill_id = cs.skill_id WHERE cs.class_id = ?";
    private static final String FIND_BY_PLAYER = "SELECT s.* FROM skill s JOIN player_skill ps ON s.skill_id = ps.skill_id WHERE ps.save_id = ?";
    private static final String INSERT = "INSERT INTO skill (name, description, skill_type, mp_cost, effect_value, target_type) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE skill SET name = ?, description = ?, skill_type = ?, mp_cost = ?, effect_value = ?, target_type = ? WHERE skill_id = ?";
    private static final String DELETE = "DELETE FROM skill WHERE skill_id = ?";
    
    @Override
    protected Skill mapRow(ResultSet rs) throws SQLException {
        Skill skill = new Skill();
        skill.setId(rs.getInt("skill_id"));
        skill.setName(rs.getString("name"));
        skill.setDescription(rs.getString("description"));
        skill.setSkillType(Skill.SkillType.valueOf(rs.getString("skill_type")));
        skill.setMpCost(rs.getInt("mp_cost"));
        skill.setEffectValue(rs.getInt("effect_value"));
        skill.setTargetType(rs.getString("target_type"));
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
                skill.getSkillType().toString(), 
                skill.getMpCost(), 
                skill.getEffectValue(), 
                skill.getTargetType());
    }
    
    @Override
    public boolean update(Skill skill) {
        return executeUpdate(UPDATE, 
                skill.getName(), 
                skill.getDescription(), 
                skill.getSkillType().toString(), 
                skill.getMpCost(), 
                skill.getEffectValue(), 
                skill.getTargetType(), 
                skill.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca una habilidad por su nombre.
     * 
     * @param name Nombre de la habilidad a buscar
     * @return Habilidad encontrada o null si no existe
     */
    public Skill findByName(String name) {
        return executeSingleResultQuery(FIND_BY_NAME, name);
    }
    
    /**
     * Busca habilidades disponibles para una clase de personaje.
     * 
     * @param classId ID de la clase de personaje
     * @return Lista de habilidades disponibles para la clase
     */
    public List<Skill> findByClassId(int classId) {
        return executeQuery(FIND_BY_CLASS, classId);
    }
    
    /**
     * Busca habilidades aprendidas por un jugador en una partida guardada.
     * 
     * @param saveId ID de la partida guardada
     * @return Lista de habilidades aprendidas por el jugador
     */
    public List<Skill> findByPlayerId(int saveId) {
        return executeQuery(FIND_BY_PLAYER, saveId);
    }
}