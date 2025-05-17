
package com.moonscorch.model;

/**
 * Clase que representa un objeto del juego.
 */
public class GameItem {
    private int id;
    private String name;
    private String description;
    private String itemType; // poción, arma, armadura, etc.
    private int effectValue; // valor del efecto (daño, curación, etc.)
    private int rarity; // rareza del objeto (1-5)
    private boolean usableInCombat; // si se puede usar en combate
    
    public GameItem() {
    }
    
    public GameItem(String name, String description, String itemType, int effectValue, int rarity, boolean usableInCombat) {
        this.name = name;
        this.description = description;
        this.itemType = itemType;
        this.effectValue = effectValue;
        this.rarity = rarity;
        this.usableInCombat = usableInCombat;
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
    
    public String getItemType() {
        return itemType;
    }
    
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    
    public int getEffectValue() {
        return effectValue;
    }
    
    public void setEffectValue(int effectValue) {
        this.effectValue = effectValue;
    }
    
    public int getRarity() {
        return rarity;
    }
    
    public void setRarity(int rarity) {
        this.rarity = rarity;
    }
    
    public boolean isUsableInCombat() {
        return usableInCombat;
    }
    
    public void setUsableInCombat(boolean usableInCombat) {
        this.usableInCombat = usableInCombat;
    }
    
    @Override
    public String toString() {
        return "GameItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", itemType='" + itemType + '\'' +
                ", effectValue=" + effectValue +
                ", rarity=" + rarity +
                ", usableInCombat=" + usableInCombat +
                '}';
    }
}
