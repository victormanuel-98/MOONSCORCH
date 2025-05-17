// Archivo: src/dao/MapProgressDAO.java

package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import model.MapProgress;

/**
 * DAO para gestionar el progreso en el mapa en la base de datos.
 */
public class MapProgressDAO extends AbstractDAO<MapProgress> {
    
    private static final Logger LOGGER = Logger.getLogger(MapProgressDAO.class.getName());
    
    private static final String FIND_BY_ID = "SELECT * FROM map_progress WHERE progress_id = ?";
    private static final String FIND_ALL = "SELECT * FROM map_progress";
    private static final String FIND_BY_SAVE = "SELECT * FROM map_progress WHERE save_id = ?";
    private static final String FIND_BY_SAVE_AND_NODE = "SELECT * FROM map_progress WHERE save_id = ? AND node_id = ?";
    private static final String INSERT = "INSERT INTO map_progress (save_id, node_id, visited, completed, notes) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE map_progress SET save_id = ?, node_id = ?, visited = ?, completed = ?, notes = ? WHERE progress_id = ?";
    private static final String DELETE = "DELETE FROM map_progress WHERE progress_id = ?";
    private static final String DELETE_BY_SAVE = "DELETE FROM map_progress WHERE save_id = ?";
    
    @Override
    protected MapProgress mapRow(ResultSet rs) throws SQLException {
        MapProgress progress = new MapProgress();
        progress.setId(rs.getInt("progress_id"));
        progress.setSaveId(rs.getInt("save_id"));
        progress.setNodeId(rs.getInt("node_id"));
        progress.setVisited(rs.getBoolean("visited"));
        progress.setCompleted(rs.getBoolean("completed"));
        progress.setNotes(rs.getString("notes"));
        return progress;
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
    public boolean save(MapProgress progress) {
        return executeUpdate(INSERT, 
                progress.getSaveId(), 
                progress.getNodeId(), 
                progress.isVisited(), 
                progress.isCompleted(), 
                progress.getNotes());
    }
    
    @Override
    public boolean update(MapProgress progress) {
        return executeUpdate(UPDATE, 
                progress.getSaveId(), 
                progress.getNodeId(), 
                progress.isVisited(), 
                progress.isCompleted(), 
                progress.getNotes(), 
                progress.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca el progreso del mapa para una partida guardada.
     * 
     * @param saveId ID de la partida guardada
     * @return Lista de progresos de mapa para la partida
     */
    public List<MapProgress> findBySaveId(int saveId) {
        return executeQuery(FIND_BY_SAVE, saveId);
    }
    
    /**
     * Busca el progreso de un nodo específico en una partida guardada.
     * 
     * @param saveId ID de la partida guardada
     * @param nodeId ID del nodo
     * @return Progreso del nodo o null si no existe
     */
    public MapProgress findBySaveAndNode(int saveId, int nodeId) {
        return executeSingleResultQuery(FIND_BY_SAVE_AND_NODE, saveId, nodeId);
    }
    
    /**
     * Elimina todo el progreso del mapa para una partida guardada.
     * 
     * @param saveId ID de la partida guardada
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean deleteBySaveId(int saveId) {
        return executeUpdate(DELETE_BY_SAVE, saveId);
    }
}