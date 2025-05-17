
package com.moonscorch.model;

/**
 * Clase que representa un objeto en el inventario de un personaje.
 */
public class InventoryItem {
    private int id;
    private int characterId;
    private int itemId;
    private int quantity;
    private boolean equipped;
    
    public InventoryItem() {
    }
    
    public InventoryItem(int characterId, int itemId, int quantity, boolean equipped) {
        this.characterId = characterId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.equipped = equipped;
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
    
    public int getItemId() {
        return itemId;
    }
    
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public boolean isEquipped() {
        return equipped;
    }
    
    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
    
    @Override
    public String toString() {
        return "InventoryItem{" +
                "id=" + id +
                ", characterId=" + characterId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                ", equipped=" + equipped +
                '}';
    }
}
