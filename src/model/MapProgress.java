package model;

/**
 * Representa el progreso del jugador en el mapa.
 */
public class MapProgress {
    private int id;
    private int savedGameId;
    private int mapNodeId;
    private boolean isVisited;
    private boolean isCleared;
    private String notes; // Nuevo campo para notas
    
    /**
     * Constructor por defecto.
     */
    public MapProgress() {
    }
    
    /**
     * Constructor con parámetros principales.
     */
    public MapProgress(int savedGameId, int mapNodeId) {
        this.savedGameId = savedGameId;
        this.mapNodeId = mapNodeId;
        this.isVisited = false;
        this.isCleared = false;
    }
    
    /**
     * Constructor completo incluyendo estado de visita y completado.
     */
    public MapProgress(int savedGameId, int mapNodeId, boolean isVisited, boolean isCleared) {
        this.savedGameId = savedGameId;
        this.mapNodeId = mapNodeId;
        this.isVisited = isVisited;
        this.isCleared = isCleared;
    }
    
    /**
     * Constructor completo incluyendo notas.
     */
    public MapProgress(int savedGameId, int mapNodeId, boolean isVisited, boolean isCleared, String notes) {
        this.savedGameId = savedGameId;
        this.mapNodeId = mapNodeId;
        this.isVisited = isVisited;
        this.isCleared = isCleared;
        this.notes = notes;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getSavedGameId() {
        return savedGameId;
    }
    
    public void setSavedGameId(int savedGameId) {
        this.savedGameId = savedGameId;
    }
    
    // Alias para compatibilidad con MapProgressDAO
    public int getSaveId() {
        return savedGameId;
    }
    
    public void setSaveId(int saveId) {
        this.savedGameId = saveId;
    }
    
    public int getMapNodeId() {
        return mapNodeId;
    }
    
    public void setMapNodeId(int mapNodeId) {
        this.mapNodeId = mapNodeId;
    }
    
    // Alias para compatibilidad con MapProgressDAO
    public int getNodeId() {
        return mapNodeId;
    }
    
    public void setNodeId(int nodeId) {
        this.mapNodeId = nodeId;
    }
    
    public boolean isVisited() {
        return isVisited;
    }
    
    public void setVisited(boolean visited) {
        isVisited = visited;
    }
    
    public boolean isCleared() {
        return isCleared;
    }
    
    public void setCleared(boolean cleared) {
        isCleared = cleared;
    }
    
    // Alias para compatibilidad con MapProgressDAO
    public boolean isCompleted() {
        return isCleared;
    }
    
    public void setCompleted(boolean completed) {
        isCleared = completed;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    /**
     * Marca el nodo como visitado.
     */
    public void visit() {
        this.isVisited = true;
    }
    
    /**
     * Marca el nodo como completado/despejado.
     */
    public void clear() {
        this.isCleared = true;
    }
    
    @Override
    public String toString() {
        return "MapProgress{" +
                "id=" + id +
                ", savedGameId=" + savedGameId +
                ", mapNodeId=" + mapNodeId +
                ", isVisited=" + isVisited +
                ", isCleared=" + isCleared +
                ", notes=" + (notes != null ? "'" + notes + "'" : "null") +
                '}';
    }
}