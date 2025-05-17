
package com.moonscorch.model;

/**
 * Clase que representa un nodo en el mapa del juego.
 */
public class MapNode {
    private int id;
    private String name;
    private String description;
    private String nodeType; // ciudad, mazmorra, bosque, etc.
    private int xCoord; // coordenada X en el mapa
    private int yCoord; // coordenada Y en el mapa
    private String region; // región a la que pertenece
    private boolean hasTreasure; // si contiene tesoro
    private boolean hasEnemy; // si contiene enemigos
    private int difficultyLevel; // nivel de dificultad (1-5)
    
    public MapNode() {
    }
    
    public MapNode(String name, String description, String nodeType, int xCoord, int yCoord, String region, boolean hasTreasure, boolean hasEnemy, int difficultyLevel) {
        this.name = name;
        this.description = description;
        this.nodeType = nodeType;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.region = region;
        this.hasTreasure = hasTreasure;
        this.hasEnemy = hasEnemy;
        this.difficultyLevel = difficultyLevel;
    }
    
    // Getters y setters
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
    
    public String getNodeType() {
        return nodeType;
    }
    
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
    
    public int getXCoord() {
        return xCoord;
    }
    
    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }
    
    public int getYCoord() {
        return yCoord;
    }
    
    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public boolean isHasTreasure() {
        return hasTreasure;
    }
    
    public void setHasTreasure(boolean hasTreasure) {
        this.hasTreasure = hasTreasure;
    }
    
    public boolean isHasEnemy() {
        return hasEnemy;
    }
    
    public void setHasEnemy(boolean hasEnemy) {
        this.hasEnemy = hasEnemy;
    }
    
    public int getDifficultyLevel() {
        return difficultyLevel;
    }
    
    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
    
    @Override
    public String toString() {
        return "MapNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", nodeType='" + nodeType + '\'' +
                ", xCoord=" + xCoord +
                ", yCoord=" + yCoord +
                ", region='" + region + '\'' +
                ", hasTreasure=" + hasTreasure +
                ", hasEnemy=" + hasEnemy +
                ", difficultyLevel=" + difficultyLevel +
                '}';
    }
}
