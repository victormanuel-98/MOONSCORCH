// Archivo: src/dao/MapNodeDAO.java

package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import model.MapNode;

/**
 * DAO para gestionar nodos del mapa en la base de datos.
 */
public class MapNodeDAO extends AbstractDAO<MapNode> {
    
    private static final Logger LOGGER = Logger.getLogger(MapNodeDAO.class.getName());
    
    private static final String FIND_BY_ID = "SELECT * FROM map_node WHERE node_id = ?";
    private static final String FIND_ALL = "SELECT * FROM map_node";
    private static final String FIND_BY_NAME = "SELECT * FROM map_node WHERE name = ?";
    private static final String FIND_CONNECTED = "SELECT n.* FROM map_node n JOIN map_connection c ON n.node_id = c.target_node_id WHERE c.source_node_id = ?";
    private static final String INSERT = "INSERT INTO map_node (name, description, node_type, background_image, enemy_id, treasure_id, event_script) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE map_node SET name = ?, description = ?, node_type = ?, background_image = ?, enemy_id = ?, treasure_id = ?, event_script = ? WHERE node_id = ?";
    private static final String DELETE = "DELETE FROM map_node WHERE node_id = ?";
    
    @Override
    protected MapNode mapRow(ResultSet rs) throws SQLException {
        MapNode node = new MapNode();
        node.setId(rs.getInt("node_id"));
        node.setName(rs.getString("name"));
        node.setDescription(rs.getString("description"));
        node.setNodeType(MapNode.NodeType.valueOf(rs.getString("node_type")));
        node.setBackgroundImage(rs.getString("background_image"));
        
        // Manejar posibles valores NULL
        int enemyId = rs.getInt("enemy_id");
        if (!rs.wasNull()) {
            node.setEnemyId(enemyId);
        }
        
        int treasureId = rs.getInt("treasure_id");
        if (!rs.wasNull()) {
            node.setTreasureId(treasureId);
        }
        
        node.setEventScript(rs.getString("event_script"));
        return node;
    }
    
    @Override
    public MapNode findById(int id) {
        return executeSingleResultQuery(FIND_BY_ID, id);
    }
    
    @Override
    public List<MapNode> findAll() {
        return executeQuery(FIND_ALL);
    }
    
    @Override
    public boolean save(MapNode node) {
        return executeUpdate(INSERT, 
                node.getName(), 
                node.getDescription(), 
                node.getNodeType().toString(), 
                node.getBackgroundImage(), 
                node.getEnemyId() > 0 ? node.getEnemyId() : null, 
                node.getTreasureId() > 0 ? node.getTreasureId() : null, 
                node.getEventScript());
    }
    
    @Override
    public boolean update(MapNode node) {
        return executeUpdate(UPDATE, 
                node.getName(), 
                node.getDescription(), 
                node.getNodeType().toString(), 
                node.getBackgroundImage(), 
                node.getEnemyId() > 0 ? node.getEnemyId() : null, 
                node.getTreasureId() > 0 ? node.getTreasureId() : null, 
                node.getEventScript(), 
                node.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca un nodo del mapa por su nombre.
     * 
     * @param name Nombre del nodo a buscar
     * @return Nodo encontrado o null si no existe
     */
    public MapNode findByName(String name) {
        return executeSingleResultQuery(FIND_BY_NAME, name);
    }
    
    /**
     * Busca nodos conectados a un nodo específico.
     * 
     * @param nodeId ID del nodo origen
     * @return Lista de nodos conectados
     */
    public List<MapNode> findConnectedNodes(int nodeId) {
        return executeQuery(FIND_CONNECTED, nodeId);
    }
}