// Archivo: src/model/InventoryItem.java

package model;

/**
 * Representa un ítem en el inventario de un personaje.
 */
public class InventoryItem {
    private int id;
    private int saveId;
    private int itemId;
    private int quantity;
    private boolean equipped;
    
    /**
     * Constructor por defecto.
     */
    public InventoryItem() {
    }
    
    /**
     * Constructor con parámetros principales.
     */
    public InventoryItem(int saveId, int itemId, int quantity, boolean equipped) {
        this.saveId = saveId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.equipped = equipped;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getSaveId() {
        return saveId;
    }
    
    public void setSaveId(int saveId) {
        this.saveId = saveId;
    }
    
    public int getItemId() {
        return itemId;
    }
    
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    
    /**
     * Obtiene el ID del objeto del juego (alias para getItemId).
     * @return ID del objeto del juego
     */
    public int getGameItemId() {
        return itemId;
    }
    
    /**
     * Establece el ID del objeto del juego (alias para setItemId).
     * @param gameItemId ID del objeto del juego
     */
    public void setGameItemId(int gameItemId) {
        this.itemId = gameItemId;
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
    
    /**
     * Aumenta la cantidad del objeto en el inventario.
     * 
     * @param amount Cantidad a aumentar
     */
    public void increaseQuantity(int amount) {
        if (amount > 0) {
            this.quantity += amount;
        }
    }
    
    /**
     * Disminuye la cantidad del objeto en el inventario.
     * 
     * @param amount Cantidad a disminuir
     * @return true si quedan objetos después de disminuir, false si la cantidad llega a 0
     */
    public boolean decreaseQuantity(int amount) {
        if (amount > 0) {
            this.quantity = Math.max(0, this.quantity - amount);
        }
        return this.quantity > 0;
    }
    
    /**
     * Verifica si hay suficiente cantidad del objeto.
     * 
     * @param amount Cantidad a verificar
     * @return true si hay suficiente cantidad, false en caso contrario
     */
    public boolean hasEnough(int amount) {
        return this.quantity >= amount;
    }
    
    /**
     * Equipa o desequipa el objeto.
     * 
     * @param equip true para equipar, false para desequipar
     */
    public void toggleEquipped(boolean equip) {
        this.equipped = equip;
    }
    
    @Override
    public String toString() {
        return "InventoryItem{" +
                "id=" + id +
                ", saveId=" + saveId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                ", equipped=" + equipped +
                '}';
    }
}