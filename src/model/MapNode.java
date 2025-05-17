package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un nodo en el mapa del juego.
 */
public class MapNode {
    public enum NodeType {
        START("Inicio"),
        NORMAL("Normal"),
        BOSS("Jefe"),
        SHOP("Tienda"),
        REST("Descanso"),
        TREASURE("Tesoro"),
        EVENT("Evento"),
        END("Final");
        
        private final String displayName;
        
        NodeType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        @Override
        public String toString() {
            return name(); // Devuelve el nombre del enum (START, NORMAL, etc.)
        }
    }
    
    private int id;
    private String name;
    private String description;
    private NodeType type;
    private int posX;
    private int posY;
    private List<MapConnection> connections;
    
    // Nuevos campos para compatibilidad con MapNodeDAO
    private String backgroundImage;
    private int enemyId;
    private int treasureId;
    private String eventScript;
    private int mapId; // Para compatibilidad con el DAO
    
    /**
     * Constructor por defecto.
     */
    public MapNode() {
        this.connections = new ArrayList<>();
        this.enemyId = 0;
        this.treasureId = 0;
    }
    
    /**
     * Constructor con parámetros principales.
     */
    public MapNode(String name, String description, NodeType type, int posX, int posY) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.connections = new ArrayList<>();
        this.enemyId = 0;
        this.treasureId = 0;
    }
    
    /**
     * Constructor completo incluyendo campos adicionales.
     */
    public MapNode(String name, String description, NodeType type, int posX, int posY,
                  String backgroundImage, int enemyId, int treasureId, String eventScript) {
        this(name, description, type, posX, posY);
        this.backgroundImage = backgroundImage;
        this.enemyId = enemyId;
        this.treasureId = treasureId;
        this.eventScript = eventScript;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public NodeType getType() {
        return type;
    }
    
    public void setType(NodeType type) {
        this.type = type;
    }
    
    // Alias para compatibilidad con MapNodeDAO
    public NodeType getNodeType() {
        return type;
    }
    
    public void setNodeType(NodeType nodeType) {
        this.type = nodeType;
    }
    
    public int getPosX() {
        return posX;
    }
    
    public void setPosX(int posX) {
        this.posX = posX;
    }
    
    public int getPosY() {
        return posY;
    }
    
    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    // Alias para compatibilidad con MapNodeDAO
    public int getX() {
        return posX;
    }
    
    public void setX(int x) {
        this.posX = x;
    }
    
    public int getY() {
        return posY;
    }
    
    public void setY(int y) {
        this.posY = y;
    }
    
    public List<MapConnection> getConnections() {
        return connections;
    }
    
    public void setConnections(List<MapConnection> connections) {
        this.connections = connections;
    }
    
    /**
     * Añade una conexión a este nodo.
     * @param connection La conexión a añadir
     */
    public void addConnection(MapConnection connection) {
        this.connections.add(connection);
    }
    
    // Nuevos getters y setters para los campos adicionales
    
    public String getBackgroundImage() {
        return backgroundImage;
    }
    
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
    
    public int getEnemyId() {
        return enemyId;
    }
    
    public void setEnemyId(int enemyId) {
        this.enemyId = enemyId;
    }
    
    public int getTreasureId() {
        return treasureId;
    }
    
    public void setTreasureId(int treasureId) {
        this.treasureId = treasureId;
    }
    
    public String getEventScript() {
        return eventScript;
    }
    
    public void setEventScript(String eventScript) {
        this.eventScript = eventScript;
    }
    
    public int getMapId() {
        return mapId;
    }
    
    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
    
    @Override
    public String toString() {
        return "MapNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", posX=" + posX +
                ", posY=" + posY +
                '}';
    }
}