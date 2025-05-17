// Archivo: src/dao/EnemyDAO.java

package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import model.Enemy;

/**
 * DAO para gestionar enemigos en la base de datos.
 */
public class EnemyDAO extends AbstractDAO<Enemy> {
    
    private static final Logger LOGGER = Logger.getLogger(EnemyDAO.class.getName());
    
    private static final String FIND_BY_ID = "SELECT * FROM enemy WHERE enemy_id = ?";
    private static final String FIND_ALL = "SELECT * FROM enemy";
    private static final String FIND_BY_NAME = "SELECT * FROM enemy WHERE name = ?";
    private static final String INSERT = "INSERT INTO enemy (name, description, hp, mp, atk, def, eva, luk, exp_reward, gold_reward, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE enemy SET name = ?, description = ?, hp = ?, mp = ?, atk = ?, def = ?, eva = ?, luk = ?, exp_reward = ?, gold_reward = ?, image_path = ? WHERE enemy_id = ?";
    private static final String DELETE = "DELETE FROM enemy WHERE enemy_id = ?";
    
    @Override
    protected Enemy mapRow(ResultSet rs) throws SQLException {
        Enemy enemy = new Enemy();
        enemy.setId(rs.getInt("enemy_id"));
        enemy.setName(rs.getString("name"));
        enemy.setDescription(rs.getString("description"));
        enemy.setHp(rs.getInt("hp"));
        enemy.setMp(rs.getInt("mp"));
        enemy.setAtk(rs.getInt("atk"));
        enemy.setDef(rs.getInt("def"));
        enemy.setEva(rs.getInt("eva"));
        enemy.setLuk(rs.getInt("luk"));
        enemy.setExpReward(rs.getInt("exp_reward"));
        enemy.setGoldReward(rs.getInt("gold_reward"));
        enemy.setImagePath(rs.getString("image_path"));
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
                enemy.getHp(), 
                enemy.getMp(), 
                enemy.getAtk(), 
                enemy.getDef(), 
                enemy.getEva(), 
                enemy.getLuk(), 
                enemy.getExpReward(), 
                enemy.getGoldReward(), 
                enemy.getImagePath());
    }
    
    @Override
    public boolean update(Enemy enemy) {
        return executeUpdate(UPDATE, 
                enemy.getName(), 
                enemy.getDescription(), 
                enemy.getHp(), 
                enemy.getMp(), 
                enemy.getAtk(), 
                enemy.getDef(), 
                enemy.getEva(), 
                enemy.getLuk(), 
                enemy.getExpReward(), 
                enemy.getGoldReward(), 
                enemy.getImagePath(), 
                enemy.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca un enemigo por su nombre.
     * 
     * @param name Nombre del enemigo a buscar
     * @return Enemigo encontrado o null si no existe
     */
    public Enemy findByName(String name) {
        return executeSingleResultQuery(FIND_BY_NAME, name);
    }
}