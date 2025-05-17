
package com.moonscorch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.moonscorch.model.Enemy;

/**
 * DAO para gestionar enemigos en la base de datos.
 */
public class EnemyDAO extends AbstractDAO<Enemy> {
    
    private static final String FIND_BY_ID = "SELECT * FROM enemy WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM enemy";
    private static final String FIND_BY_DIFFICULTY = "SELECT * FROM enemy WHERE difficulty_level = ?";
    private static final String FIND_BY_TYPE = "SELECT * FROM enemy WHERE enemy_type = ?";
    private static final String INSERT = "INSERT INTO enemy (name, description, enemy_type, atk, def, eva, hp, mp, luk, difficulty_level, xp_reward, gold_reward) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE enemy SET name = ?, description = ?, enemy_type = ?, atk = ?, def = ?, eva = ?, hp = ?, mp = ?, luk = ?, difficulty_level = ?, xp_reward = ?, gold_reward = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM enemy WHERE id = ?";
    
    @Override
    protected Enemy mapRow(ResultSet rs) throws SQLException {
        Enemy enemy = new Enemy();
        enemy.setId(rs.getInt("id"));
        enemy.setName(rs.getString("name"));
        enemy.setDescription(rs.getString("description"));
        enemy.setEnemyType(rs.getString("enemy_type"));
        enemy.setAtk(rs.getInt("atk"));
        enemy.setDef(rs.getInt("def"));
        enemy.setEva(rs.getInt("eva"));
        enemy.setHp(rs.getInt("hp"));
        enemy.setMp(rs.getInt("mp"));
        enemy.setLuk(rs.getInt("luk"));
        enemy.setDifficultyLevel(rs.getInt("difficulty_level"));
        enemy.setXpReward(rs.getInt("xp_reward"));
        enemy.setGoldReward(rs.getInt("gold_reward"));
        return enemy;
    }
    
    @Override
    public Enemy findById(int id) {
        return executeSingleResultQuery(FIND_BY_ID, id);
    }
    
    @Override
    public List<Enemy> findAll() {
        return executeQuery(FIND_ALL);
    }
    
    @Override
    public boolean save(Enemy enemy) {
        return executeUpdate(INSERT, 
                enemy.getName(), 
                enemy.getDescription(), 
                enemy.getEnemyType(), 
                enemy.getAtk(), 
                enemy.getDef(), 
                enemy.getEva(), 
                enemy.getHp(), 
                enemy.getMp(), 
                enemy.getLuk(), 
                enemy.getDifficultyLevel(), 
                enemy.getXpReward(), 
                enemy.getGoldReward());
    }
    
    @Override
    public boolean update(Enemy enemy) {
        return executeUpdate(UPDATE, 
                enemy.getName(), 
                enemy.getDescription(), 
                enemy.getEnemyType(), 
                enemy.getAtk(), 
                enemy.getDef(), 
                enemy.getEva(), 
                enemy.getHp(), 
                enemy.getMp(), 
                enemy.getLuk(), 
                enemy.getDifficultyLevel(), 
                enemy.getXpReward(), 
                enemy.getGoldReward(), 
                enemy.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca enemigos por nivel de dificultad.
     * 
     * @param difficultyLevel Nivel de dificultad
     * @return Lista de enemigos con el nivel de dificultad especificado
     */
    public List<Enemy> findByDifficulty(int difficultyLevel) {
        return executeQuery(FIND_BY_DIFFICULTY, difficultyLevel);
    }
    
    /**
     * Busca enemigos por tipo (humanoide, bestia, no-muerto, etc.).
     * 
     * @param enemyType Tipo de enemigo
     * @return Lista de enemigos del tipo especificado
     */
    public List<Enemy> findByType(String enemyType) {
        return executeQuery(FIND_BY_TYPE, enemyType);
    }
    
    /**
     * Busca enemigos con recompensas de experiencia altas.
     * 
     * @param minXp Experiencia mínima
     * @return Lista de enemigos con recompensas de experiencia altas
     */
    public List<Enemy> findByHighXpReward(int minXp) {
        return executeQuery("SELECT * FROM enemy WHERE xp_reward >= ? ORDER BY xp_reward DESC", minXp);
    }
    
    /**
     * Busca enemigos con recompensas de oro altas.
     * 
     * @param minGold Oro mínimo
     * @return Lista de enemigos con recompensas de oro altas
     */
    public List<Enemy> findByHighGoldReward(int minGold) {
        return executeQuery("SELECT * FROM enemy WHERE gold_reward >= ? ORDER BY gold_reward DESC", minGold);
    }
    
    /**
     * Busca enemigos aleatorios para un nivel de dificultad específico.
     * 
     * @param difficultyLevel Nivel de dificultad
     * @param limit Número máximo de enemigos a devolver
     * @return Lista de enemigos aleatorios
     */
    public List<Enemy> findRandomByDifficulty(int difficultyLevel, int limit) {
        return executeQuery("SELECT * FROM enemy WHERE difficulty_level = ? ORDER BY RAND() LIMIT ?", difficultyLevel, limit);
    }
}
