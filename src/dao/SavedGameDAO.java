// Archivo: src/dao/SavedGameDAO.java

package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import model.SavedGame;

/**
 * DAO para gestionar partidas guardadas en la base de datos.
 */
public class SavedGameDAO extends AbstractDAO<SavedGame> {
    
    private static final Logger LOGGER = Logger.getLogger(SavedGameDAO.class.getName());
    
    private static final String FIND_BY_ID = "SELECT * FROM saved_game WHERE save_id = ?";
    private static final String FIND_ALL = "SELECT * FROM saved_game";
    private static final String FIND_BY_PLAYER = "SELECT * FROM saved_game WHERE player_id = ?";
    private static final String INSERT = "INSERT INTO saved_game (player_id, character_class_id, character_name, current_atk, current_def, current_eva, current_hp, current_max_hp, current_mp, current_max_mp, current_luk, experience_points, level, gold, play_time_seconds, current_node_id, saved_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
    private static final String UPDATE = "UPDATE saved_game SET player_id = ?, character_class_id = ?, character_name = ?, current_atk = ?, current_def = ?, current_eva = ?, current_hp = ?, current_max_hp = ?, current_mp = ?, current_max_mp = ?, current_luk = ?, experience_points = ?, level = ?, gold = ?, play_time_seconds = ?, current_node_id = ?, saved_at = NOW() WHERE save_id = ?";
    private static final String DELETE = "DELETE FROM saved_game WHERE save_id = ?";
    
    @Override
    protected SavedGame mapRow(ResultSet rs) throws SQLException {
        SavedGame savedGame = new SavedGame();
        savedGame.setId(rs.getInt("save_id"));
        savedGame.setPlayerId(rs.getInt("player_id"));
        savedGame.setCharacterClassId(rs.getInt("character_class_id"));
        savedGame.setCharacterName(rs.getString("character_name"));
        savedGame.setCurrentAtk(rs.getInt("current_atk"));
        savedGame.setCurrentDef(rs.getInt("current_def"));
        savedGame.setCurrentEva(rs.getInt("current_eva"));
        savedGame.setCurrentHp(rs.getInt("current_hp"));
        savedGame.setCurrentMaxHp(rs.getInt("current_max_hp"));
        savedGame.setCurrentMp(rs.getInt("current_mp"));
        savedGame.setCurrentMaxMp(rs.getInt("current_max_mp"));
        savedGame.setCurrentLuk(rs.getInt("current_luk"));
        savedGame.setExperiencePoints(rs.getInt("experience_points"));
        savedGame.setLevel(rs.getInt("level"));
        savedGame.setGold(rs.getInt("gold"));
        savedGame.setPlayTimeSeconds(rs.getInt("play_time_seconds"));
        
        // Manejar posibles valores NULL
        int nodeId = rs.getInt("current_node_id");
        if (!rs.wasNull()) {
            savedGame.setCurrentNodeId(nodeId);
        }
        
        savedGame.setSavedAt(rs.getTimestamp("saved_at"));
        return savedGame;
    }
    
    @Override
    public SavedGame findById(int id) {
        return executeSingleResultQuery(FIND_BY_ID, id);
    }
    
    @Override
    public List<SavedGame> findAll() {
        return executeQuery(FIND_ALL);
    }
    
    @Override
    public boolean save(SavedGame savedGame) {
        return executeUpdate(INSERT, 
                savedGame.getPlayerId(), 
                savedGame.getCharacterClassId(), 
                savedGame.getCharacterName(), 
                savedGame.getCurrentAtk(), 
                savedGame.getCurrentDef(), 
                savedGame.getCurrentEva(), 
                savedGame.getCurrentHp(), 
                savedGame.getCurrentMaxHp(), 
                savedGame.getCurrentMp(), 
                savedGame.getCurrentMaxMp(), 
                savedGame.getCurrentLuk(), 
                savedGame.getExperiencePoints(), 
                savedGame.getLevel(), 
                savedGame.getGold(), 
                savedGame.getPlayTimeSeconds(), 
                savedGame.getCurrentNodeId() > 0 ? savedGame.getCurrentNodeId() : null);
    }
    
    @Override
    public boolean update(SavedGame savedGame) {
        return executeUpdate(UPDATE, 
                savedGame.getPlayerId(), 
                savedGame.getCharacterClassId(), 
                savedGame.getCharacterName(), 
                savedGame.getCurrentAtk(), 
                savedGame.getCurrentDef(), 
                savedGame.getCurrentEva(), 
                savedGame.getCurrentHp(), 
                savedGame.getCurrentMaxHp(), 
                savedGame.getCurrentMp(), 
                savedGame.getCurrentMaxMp(), 
                savedGame.getCurrentLuk(), 
                savedGame.getExperiencePoints(), 
                savedGame.getLevel(), 
                savedGame.getGold(), 
                savedGame.getPlayTimeSeconds(), 
                savedGame.getCurrentNodeId() > 0 ? savedGame.getCurrentNodeId() : null, 
                savedGame.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca partidas guardadas por ID de jugador.
     * 
     * @param playerId ID del jugador
     * @return Lista de partidas guardadas del jugador
     */
    public List<SavedGame> findByPlayerId(int playerId) {
        return executeQuery(FIND_BY_PLAYER, playerId);
    }
}