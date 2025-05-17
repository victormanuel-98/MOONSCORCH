package model;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un tesoro en el juego.
 */
public class Treasure {
    public enum TreasureType {
        COMMON("Común"),
        RARE("Raro"),
        EPIC("Épico"),
        LEGENDARY("Legendario"),
        QUEST("Misión");
        
        private final String displayName;
        
        TreasureType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    private int id;
    private String name;
    private String description;
    private TreasureType type;
    private int mapNodeId; // Nodo donde se encuentra el tesoro
    private int goldReward;
    private List<Integer> itemRewards; // IDs de los objetos que contiene
    private boolean isLocked;
    private int requiredItemId; // ID del objeto necesario para desbloquear (si está bloqueado)
    
    // Campos adicionales para compatibilidad con TreasureDAO
    private TreasureType treasureType; // Alias para type
    private int goldValue; // Alias para goldReward
    private int itemId; // Para almacenar un único ID de objeto
    private int quantity; // Cantidad del objeto
    
    /**
     * Constructor por defecto.
     */
    public Treasure() {
        this.itemRewards = new ArrayList<>();
    }
    
    /**
     * Constructor con parámetros principales.
     */
    public Treasure(String name, String description, TreasureType type, int mapNodeId, int goldReward) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.treasureType = type; // Sincronizar con type
        this.mapNodeId = mapNodeId;
        this.goldReward = goldReward;
        this.goldValue = goldReward; // Sincronizar con goldReward
        this.itemRewards = new ArrayList<>();
        this.isLocked = false;
        this.quantity = 1; // Valor por defecto
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
    
    public TreasureType getType() {
        return type;
    }
    
    public void setType(TreasureType type) {
        this.type = type;
        this.treasureType = type; // Sincronizar con treasureType
    }
    
    public int getMapNodeId() {
        return mapNodeId;
    }
    
    public void setMapNodeId(int mapNodeId) {
        this.mapNodeId = mapNodeId;
    }
    
    public int getGoldReward() {
        return goldReward;
    }
    
    public void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
        this.goldValue = goldReward; // Sincronizar con goldValue
    }
    
    public List<Integer> getItemRewards() {
        return itemRewards;
    }
    
    public void setItemRewards(List<Integer> itemRewards) {
        this.itemRewards = itemRewards;
        // Si hay al menos un objeto en la lista, actualizar itemId
        if (itemRewards != null && !itemRewards.isEmpty()) {
            this.itemId = itemRewards.get(0);
        }
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
     * Añade un objeto a la recompensa del tesoro.
     * @param itemId ID del objeto a añadir
     */
    public void addItemReward(int itemId) {
        this.itemRewards.add(itemId);
        // Si es el primer objeto, actualizar itemId
        if (this.itemRewards.size() == 1) {
            this.itemId = itemId;
        }
    }
    
    /**
     * Desbloquea el tesoro.
     */
    public void unlock() {
        this.isLocked = false;
    }
    
    // Métodos adicionales para compatibilidad con TreasureDAO
    
    /**
     * Obtiene el tipo de tesoro (alias para getType).
     * @return Tipo de tesoro
     */
    public TreasureType getTreasureType() {
        return type != null ? type : treasureType;
    }
    
    /**
     * Establece el tipo de tesoro (alias para setType).
     * @param treasureType Tipo de tesoro
     */
    public void setTreasureType(TreasureType treasureType) {
        this.treasureType = treasureType;
        this.type = treasureType; // Sincronizar con type
    }
    
    /**
     * Establece el tipo de tesoro a partir de una cadena.
     * @param treasureTypeStr Cadena que representa el tipo de tesoro
     */
    public void setTreasureType(String treasureTypeStr) {
        try {
            TreasureType type = TreasureType.valueOf(treasureTypeStr.toUpperCase());
            setTreasureType(type);
        } catch (IllegalArgumentException e) {
            // Si la cadena no coincide con ningún valor de la enumeración, usar un valor por defecto
            setTreasureType(TreasureType.COMMON);
        }
    }
    
    /**
     * Obtiene el valor en oro (alias para getGoldReward).
     * @return Valor en oro
     */
    public int getGoldValue() {
        return goldReward;
    }
    
    /**
     * Establece el valor en oro (alias para setGoldReward).
     * @param goldValue Valor en oro
     */
    public void setGoldValue(int goldValue) {
        this.goldValue = goldValue;
        this.goldReward = goldValue; // Sincronizar con goldReward
    }
    
    /**
     * Obtiene el ID del objeto principal.
     * @return ID del objeto
     */
    public int getItemId() {
        // Si itemId está establecido, devolverlo
        if (itemId > 0) {
            return itemId;
        }
        // Si no, intentar obtener el primer objeto de la lista
        if (itemRewards != null && !itemRewards.isEmpty()) {
            return itemRewards.get(0);
        }
        // Si no hay objetos, devolver 0
        return 0;
    }
    
    /**
     * Establece el ID del objeto principal.
     * @param itemId ID del objeto
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
        // Actualizar también la lista de recompensas si está vacía o si el primer elemento es diferente
        if (itemRewards == null) {
            itemRewards = new ArrayList<>();
        }
        if (itemRewards.isEmpty()) {
            itemRewards.add(itemId);
        } else if (itemRewards.get(0) != itemId) {
            itemRewards.set(0, itemId);
        }
    }
    
    /**
     * Obtiene la cantidad del objeto.
     * @return Cantidad del objeto
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * Establece la cantidad del objeto.
     * @param quantity Cantidad del objeto
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return "Treasure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", mapNodeId=" + mapNodeId +
                ", goldReward=" + goldReward +
                ", itemRewards=" + itemRewards +
                ", isLocked=" + isLocked +
                '}';
    }
}