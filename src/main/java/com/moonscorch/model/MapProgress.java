
package com.moonscorch.model;

import java.sql.Timestamp;

/**
 * Clase que representa el progreso de un personaje en un nodo del mapa.
 */
public class MapProgress {
    private int id;
    private int characterId;
    private int nodeId;
    private boolean visited;
    private boolean treasureLooted;
    private boolean enemyDefeated;
    private Timestamp visitDate;
    
    public MapProgress() {
    }
    
    public MapProgress(int characterId, int nodeId, boolean visited, boolean treasureLooted, boolean enemyDefeated) {
        this.characterId = characterId;
        this.nodeId = nodeId;
        this.visited = visited;
        this.treasureLooted = treasureLooted;
        this.enemyDefeated = enemyDefeated;
    }
    
    // Getters y setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getCharacterId() {
        return characterId;
    }
    
    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }
    
    public int getNodeId() {
        return nodeId;
    }
    
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }
    
    public boolean isVisited() {
        return visited;
    }
    
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    public boolean isTreasureLooted() {
        return treasureLooted;
    }
    
    public void setTreasureLooted(boolean treasureLooted) {
        this.treasureLooted = treasureLooted;
    }
    
    public boolean isEnemyDefeated() {
        return enemyDefeated;
    }
    
    public void setEnemyDefeated(boolean enemyDefeated) {
        this.enemyDefeated = enemyDefeated;
    }
    
    public Timestamp getVisitDate() {
        return visitDate;
    }
    
    public void setVisitDate(Timestamp visitDate) {
        this.visitDate = visitDate;
    }
    
    @Override
    public String toString() {
        return "MapProgress{" +
                "id=" + id +
                ", characterId=" + characterId +
                ", nodeId=" + nodeId +
                ", visited=" + visited +
                ", treasureLooted=" + treasureLooted +
                ", enemyDefeated=" + enemyDefeated +
                ", visitDate=" + visitDate +
                '}';
    }
}
