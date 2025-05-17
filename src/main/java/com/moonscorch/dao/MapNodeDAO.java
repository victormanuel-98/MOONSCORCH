
package com.moonscorch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.moonscorch.model.MapNode;

/**
 * DAO para gestionar nodos del mapa en la base de datos.
 */
public class MapNodeDAO extends AbstractDAO<MapNode> {
    
    private static final String FIND_BY_ID = "SELECT * FROM map_node WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM map_node";
    private static final String FIND_BY_REGION = "SELECT * FROM map_node WHERE region = ?";
    private static final String FIND_CONNECTED_NODES = "SELECT n.* FROM map_node n JOIN map_connection c ON n.id = c.target_node_id WHERE c.source_node_id = ?";
    private static final String INSERT = "INSERT INTO map_node (name, description, node_type, x_coord, y_coord, region, has_treasure, has_enemy, difficulty_level) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE map_node SET name = ?, description = ?, node_type = ?, x_coord = ?, y_coord = ?, region = ?, has_treasure = ?, has_enemy = ?, difficulty_level = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM map_node WHERE id = ?";
    private static final String ADD_CONNECTION = "INSERT INTO map_connection (source_node_id, target_node_id) VALUES (?, ?)";
    private static final String REMOVE_CONNECTION = "DELETE FROM map_connection WHERE source_node_id = ? AND target_node_id = ?";
    
    @Override
    protected MapNode mapRow(ResultSet rs) throws SQLException {
        MapNode mapNode = new MapNode();
        mapNode.setId(rs.getInt("id"));
        mapNode.setName(rs.getString("name"));
        mapNode.setDescription(rs.getString("description"));
        mapNode.setNodeType(rs.getString("node_type"));
        mapNode.setXCoord(rs.getInt("x_coord"));
        mapNode.setYCoord(rs.getInt("y_coord"));
        mapNode.setRegion(rs.getString("region"));
        mapNode.setHasTreasure(rs.getBoolean("has_treasure"));
        mapNode.setHasEnemy(rs.getBoolean("has_enemy"));
        mapNode.setDifficultyLevel(rs.getInt("difficulty_level"));
        return mapNode;
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
    public boolean save(MapNode mapNode) {
        return executeUpdate(INSERT, 
                mapNode.getName(), 
                mapNode.getDescription(), 
                mapNode.getNodeType(), 
                mapNode.getXCoord(), 
                mapNode.getYCoord(), 
                mapNode.getRegion(), 
                mapNode.isHasTreasure(), 
                mapNode.isHasEnemy(), 
                mapNode.getDifficultyLevel());
    }
    
    @Override
    public boolean update(MapNode mapNode) {
        return executeUpdate(UPDATE, 
                mapNode.getName(), 
                mapNode.getDescription(), 
                mapNode.getNodeType(), 
                mapNode.getXCoord(), 
                mapNode.getYCoord(), 
                mapNode.getRegion(), 
                mapNode.isHasTreasure(), 
                mapNode.isHasEnemy(), 
                mapNode.getDifficultyLevel(), 
                mapNode.getId());
    }
    
    @Override
    public boolean delete(int id) {
        return executeUpdate(DELETE, id);
    }
    
    /**
     * Busca nodos por región.
     * 
     * @param region Nombre de la región
     * @return Lista de nodos en la región especificada
     */
    public List<MapNode> findByRegion(String region) {
        return executeQuery(FIND_BY_REGION, region);
    }
    
    /**
     * Busca nodos conectados a un nodo específico.
     * 
     * @param nodeId ID del nodo origen
     * @return Lista de nodos conectados
     */
    public List<MapNode> findConnectedNodes(int nodeId) {
        return executeQuery(FIND_CONNECTED_NODES, nodeId);
    }
    
    /**
     * Añade una conexión entre dos nodos.
     * 
     * @param sourceNodeId ID del nodo origen
     * @param targetNodeId ID del nodo destino
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean addConnection(int sourceNodeId, int targetNodeId) {
        return executeUpdate(ADD_CONNECTION, sourceNodeId, targetNodeId);
    }
    
    /**
     * Elimina una conexión entre dos nodos.
     * 
     * @param sourceNodeId ID del nodo origen
     * @param targetNodeId ID del nodo destino
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean removeConnection(int sourceNodeId, int targetNodeId) {
        return executeUpdate(REMOVE_CONNECTION, sourceNodeId, targetNodeId);
    }
    
    /**
     * Busca nodos que contienen tesoros.
     * 
     * @return Lista de nodos con tesoros
     */
    public List<MapNode> findNodesWithTreasure() {
        return executeQuery("SELECT * FROM map_node WHERE has_treasure = 1");
    }
    
    /**
     * Busca nodos que contienen enemigos.
     * 
     * @return Lista de nodos con enemigos
     */
    public List<MapNode> findNodesWithEnemies() {
        return executeQuery("SELECT * FROM map_node WHERE has_enemy = 1");
    }
    
    /**
     * Busca nodos por tipo (ciudad, mazmorra, bosque, etc.).
     * 
     * @param nodeType Tipo de nodo
     * @return Lista de nodos del tipo especificado
     */
    public List<MapNode> findByNodeType(String nodeType) {
        return executeQuery("SELECT * FROM map_node WHERE node_type = ?", nodeType);
    }
}
