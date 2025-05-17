
package model;
/**
 * Representa un tipo de objeto en el juego.
 */
public class ItemType {
    public enum Type {
        WEAPON("Arma"),
        ARMOR("Armadura"),
        POTION("Poción"),
        KEY_ITEM("Objeto Clave"),
        CONSUMABLE("Consumible");
        
        private final String displayName;
        
        Type(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    private int id;
    private Type type;
    private String description;
    
    /**
     * Constructor por defecto.
     */
    public ItemType() {
    }
    
    /**
     * Constructor con parámetros principales.
     */
    public ItemType(Type type, String description) {
        this.type = type;
        this.description = description;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Type getType() {
        return type;
    }
    
    public void setType(Type type) {
        this.type = type;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "ItemType{" +
                "id=" + id +
                ", type=" + type +
                ", description='" + description + '\'' +
                '}';
    }
}
