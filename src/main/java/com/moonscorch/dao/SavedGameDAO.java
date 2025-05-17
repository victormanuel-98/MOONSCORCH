
package com.moonscorch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.moonscorch.model.SavedGame;

/**
 * DAO para gestionar partidas guardadas en la base de datos.
 */
public class SavedGameDAO extends AbstractDAO<SavedGame> {
    
    private static final String FIND_BY_ID = "SELECT * FROM saved_game WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM saved_game";
    private static final String FIND_BY_PLAYER_ID = "SELECT * FROM saved_game WHERE player_id = ? ORDER BY save_date DESC";
    private static final String INSERT = "INSERT INTO saved_game (player_id, character_id, save_name, save_date, game_data, play_time) VALUES (?, ?, ?, NOW(), ?, ?)";
    private static final String UPDATE = "UPDATE saved_game SET player_id = ?, character_id = ?, save_name = ?, save_date = NOW(), game_data = ?, play_time = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM saved_game WHERE id = ?";
    
    @Override
    protected SavedGame mapRow(ResultSet rs) throws SQLException {
        SavedGame savedGame = new SavedGame();
        savedGame.setId(rs.getInt("id"));
        savedGame.setPlayerId(rs.getInt("player_id"));
        savedGame.setCharacterId(rs.getInt("character_id"));
        savedGame.setSaveName(rs.getString("save_name"));
        savedGame.setSaveDate(rs.getTimestamp("save_date"));
        savedGame.setGameData(rs.getString("game_data"));
        savedGame.setPlayTime(rs.getInt("play_time"));
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
                savedGame.getCharacterId(), 
                savedGame.getSaveName(), 
                savedGame.getGameData(), 
                savedGame.getPlayTime());
    }
    
    @Override
    public boolean update(SavedGame savedGame) {
        return executeUpdate(UPDATE, 
                savedGame.getPlayerId(), 
                savedGame.getCharacterId(), 
                savedGame.getSaveName(), 
                savedGame.getGameData(), 
                savedGame.getPlayTime(), 
                savedGame.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca todas las partidas guardadas de un jugador.
     * 
     * @param playerId ID del jugador
     * @return Lista de partidas guardadas ordenadas por fecha (más reciente primero)
     */
    public List<SavedGame> findByPlayerId(int playerId) {
        return executeQuery(FIND_BY_PLAYER_ID, playerId);
    }
    
    /**
     * Obtiene la partida guardada más reciente de un jugador.
     * 
     * @param playerId ID del jugador
     * @return La partida guardada más reciente o null si no hay ninguna
     */
    public SavedGame findMostRecentByPlayerId(int playerId) {
        List<SavedGame> savedGames = findByPlayerId(playerId);
        return savedGames.isEmpty() ? null : savedGames.get(0);
    }
}
