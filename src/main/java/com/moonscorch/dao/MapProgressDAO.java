
package com.moonscorch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.moonscorch.model.MapProgress;

/**
 * DAO para gestionar el progreso en el mapa de los personajes en la base de datos.
 */
public class MapProgressDAO extends AbstractDAO<MapProgress> {
    
    private static final String FIND_BY_ID = "SELECT * FROM map_progress WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM map_progress";
    private static final String FIND_BY_CHARACTER_ID = "SELECT * FROM map_progress WHERE character_id = ?";
    private static final String FIND_BY_CHARACTER_AND_NODE = "SELECT * FROM map_progress WHERE character_id = ? AND node_id = ?";
    private static final String INSERT = "INSERT INTO map_progress (character_id, node_id, visited, treasure_looted, enemy_defeated, visit_date) VALUES (?, ?, ?, ?, ?, NOW())";
    private static final String UPDATE = "UPDATE map_progress SET character_id = ?, node_id = ?, visited = ?, treasure_looted = ?, enemy_defeated = ?, visit_date = NOW() WHERE id = ?";
    private static final String DELETE = "DELETE FROM map_progress WHERE id = ?";
    private static final String UPDATE_NODE_STATUS = "UPDATE map_progress SET visited = ?, treasure_looted = ?, enemy_defeated = ?, visit_date = NOW() WHERE character_id = ? AND node_id = ?";
    
    @Override
    protected MapProgress mapRow(ResultSet rs) throws SQLException {
        MapProgress mapProgress = new MapProgress();
        mapProgress.setId(rs.getInt("id"));
        mapProgress.setCharacterId(rs.getInt("character_id"));
        mapProgress.setNodeId(rs.getInt("node_id"));
        mapProgress.setVisited(rs.getBoolean("visited"));
        mapProgress.setTreasureLooted(rs.getBoolean("treasure_looted"));
        mapProgress.setEnemyDefeated(rs.getBoolean("enemy_defeated"));
        mapProgress.setVisitDate(rs.getTimestamp("visit_date"));
        return mapProgress;
    }
    
    @Override
    public MapProgress findById(int id) {
        return executeSingleResultQuery(FIND_BY_ID, id);
    }
    
    @Override
    public List<MapProgress> findAll() {
        return executeQuery(FIND_ALL);
    }
    
    @Override
    public boolean save(MapProgress mapProgress) {
        return executeUpdate(INSERT, 
                mapProgress.getCharacterId(), 
                mapProgress.getNodeId(), 
                mapProgress.isVisited(), 
                mapProgress.isTreasureLooted(), 
                mapProgress.isEnemyDefeated());
    }
    
    @Override
    public boolean update(MapProgress mapProgress) {
        return executeUpdate(UPDATE, 
                mapProgress.getCharacterId(), 
                mapProgress.getNodeId(), 
                mapProgress.isVisited(), 
                mapProgress.isTreasureLooted(), 
                mapProgress.isEnemyDefeated(), 
                mapProgress.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca el progreso en el mapa de un personaje.
     * 
     * @param characterId ID del personaje
     * @return Lista de registros de progreso en el mapa
     */
    public List<MapProgress> findByCharacterId(int characterId) {
        return executeQuery(FIND_BY_CHARACTER_ID, characterId);
    }
    
    /**
     * Busca el progreso de un personaje en un nodo específico.
     * 
     * @param characterId ID del personaje
     * @param nodeId ID del nodo
     * @return Registro de progreso o null si no existe
     */
    public MapProgress findByCharacterAndNode(int characterId, int nodeId) {
        return executeSingleResultQuery(FIND_BY_CHARACTER_AND_NODE, characterId, nodeId);
    }
    
    /**
     * Actualiza el estado de un nodo para un personaje.
     * 
     * @param characterId ID del personaje
     * @param nodeId ID del nodo
     * @param visited Si el nodo ha sido visitado
     * @param treasureLooted Si el tesoro ha sido saqueado
     * @param enemyDefeated Si el enemigo ha sido derrotado
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean updateNodeStatus(int characterId, int nodeId, boolean visited, boolean treasureLooted, boolean enemyDefeated) {
        MapProgress existingProgress = findByCharacterAndNode(characterId, nodeId);
        
        if (existingProgress != null) {
            // Actualizar registro existente
            return executeUpdate(UPDATE_NODE_STATUS, visited, treasureLooted, enemyDefeated, characterId, nodeId);
        } else {
            // Crear nuevo registro
            MapProgress newProgress = new MapProgress();
            newProgress.setCharacterId(characterId);
            newProgress.setNodeId(nodeId);
            newProgress.setVisited(visited);
            newProgress.setTreasureLooted(treasureLooted);
            newProgress.setEnemyDefeated(enemyDefeated);
            return save(newProgress);
        }
    }
    
    /**
     * Marca un nodo como visitado.
     * 
     * @param characterId ID del personaje
     * @param nodeId ID del nodo
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean markNodeAsVisited(int characterId, int nodeId) {
        MapProgress progress = findByCharacterAndNode(characterId, nodeId);
        
        if (progress != null) {
            progress.setVisited(true);
            return update(progress);
        } else {
            return updateNodeStatus(characterId, nodeId, true, false, false);
        }
    }
    
    /**
     * Marca un tesoro como saqueado.
     * 
     * @param characterId ID del personaje
     * @param nodeId ID del nodo
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean markTreasureAsLooted(int characterId, int nodeId) {
        MapProgress progress = findByCharacterAndNode(characterId, nodeId);
        
        if (progress != null) {
            progress.setTreasureLooted(true);
            return update(progress);
        } else {
            return updateNodeStatus(characterId, nodeId, true, true, false);
        }
    }
    
    /**
     * Marca un enemigo como derrotado.
     * 
     * @param characterId ID del personaje
     * @param nodeId ID del nodo
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean markEnemyAsDefeated(int characterId, int nodeId) {
        MapProgress progress = findByCharacterAndNode(characterId, nodeId);
        
        if (progress != null) {
            progress.setEnemyDefeated(true);
            return update(progress);
        } else {
            return updateNodeStatus(characterId, nodeId, true, false, true);
        }
    }
}
