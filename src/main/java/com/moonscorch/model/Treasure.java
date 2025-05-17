
package com.moonscorch.model;

/**
 * Clase que representa un tesoro en el juego.
 */
public class Treasure {
    private int id;
    private int nodeId; // nodo del mapa donde se encuentra
    private String name;
    private String description;
    private int goldAmount; // cantidad de oro
    private int rarity; // rareza del tesoro (1-5)
    private boolean requiresKey; // si requiere llave para abrirlo
    
    public Treasure() {
    }
    
    public Treasure(int nodeId, String name, String description, int goldAmount, int rarity, boolean requiresKey) {
        this.nodeId = nodeId;
        this.name = name;
        this.description = description;
        this.goldAmount = goldAmount;
        this.rarity = rarity;
        this.requiresKey = requiresKey;
    }
    
    // Getters y setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getNodeId() {
        return nodeId;
    }
    
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
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
    
    public int getGoldAmount() {
        return goldAmount;
    }
    
    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }
    
    public int getRarity() {
        return rarity;
    }
    
    public void setRarity(int rarity) {
        this.rarity = rarity;
    }
    
    public boolean isRequiresKey() {
        return requiresKey;
    }
    
    public void setRequiresKey(boolean requiresKey) {
        this.requiresKey = requiresKey;
    }
    
    @Override
    public String toString() {
        return "Treasure{" +
                "id=" + id +
                ", nodeId=" + nodeId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", goldAmount=" + goldAmount +
                ", rarity=" + rarity +
                ", requiresKey=" + requiresKey +
                '}';
    }
}
