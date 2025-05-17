
package model;
/**
 * Representa una conexión entre dos nodos del mapa.
 */
public class MapConnection {
    private int id;
    private int sourceNodeId;
    private int targetNodeId;
    private boolean isLocked;
    private int requiredItemId; // ID del objeto necesario para desbloquear (si está bloqueado)
    
    /**
     * Constructor por defecto.
     */
    public MapConnection() {
    }
    
    /**
     * Constructor con parámetros principales.
     */
    public MapConnection(int sourceNodeId, int targetNodeId, boolean isLocked) {
        this.sourceNodeId = sourceNodeId;
        this.targetNodeId = targetNodeId;
        this.isLocked = isLocked;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getSourceNodeId() {
        return sourceNodeId;
    }
    
    public void setSourceNodeId(int sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }
    
    public int getTargetNodeId() {
        return targetNodeId;
    }
    
    public void setTargetNodeId(int targetNodeId) {
        this.targetNodeId = targetNodeId;
    }
    
    public boolean isLocked() {
        return isLocked;
    }
    
    public void setLocked(boolean locked) {
        isLocked = locked;
    }
    
    public int getRequiredItemId() {
        return requiredItemId;
    }
    
    public void setRequiredItemId(int requiredItemId) {
        this.requiredItemId = requiredItemId;
    }
    
    /**
     * Desbloquea la conexión.
     */
    public void unlock() {
        this.isLocked = false;
    }
    
    @Override
    public String toString() {
        return "MapConnection{" +
                "id=" + id +
                ", sourceNodeId=" + sourceNodeId +
                ", targetNodeId=" + targetNodeId +
                ", isLocked=" + isLocked +
                ", requiredItemId=" + requiredItemId +
                '}';
    }
}
