package model;

/**
 * Representa un objeto del juego.
 */
public class GameItem {
    /**
     * Tipos de ítems disponibles en el juego.
     */
    public enum ItemType {
        WEAPON(1, "Arma"),
        ARMOR(2, "Armadura"),
        POTION(3, "Poción"),
        KEY(4, "Llave"),
        TREASURE(5, "Tesoro"),
        QUEST(6, "Objeto de misión"),
        MISC(7, "Misceláneo");
        
        private final int id;
        private final String displayName;
        
        ItemType(int id, String displayName) {
            this.id = id;
            this.displayName = displayName;
        }
        
        public int getId() {
            return id;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public static ItemType fromId(int id) {
            for (ItemType type : values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return MISC; // Valor predeterminado
        }
        
        @Override
        public String toString() {
            return displayName;
        }
    }
    
    private int id;
    private String name;
    private int itemTypeId;
    private ItemType type; // Nuevo campo para el tipo de ítem como enum
    private String description;
    private int value;
    private int atkMod;
    private int defMod;
    private int evaMod;
    private int hpMod;
    private int mpMod;
    private int lukMod;
    private boolean isEquippable;
    private boolean isConsumable;
    private boolean isKeyItem;
    private String imagePath; // Nuevo campo para la ruta de la imagen
    
    /**
     * Constructor por defecto.
     */
    public GameItem() {
    }
    
    /**
     * Constructor con parámetros principales.
     */
    public GameItem(String name, int itemTypeId, String description, int value) {
        this.name = name;
        this.itemTypeId = itemTypeId;
        this.type = ItemType.fromId(itemTypeId);
        this.description = description;
        this.value = value;
        this.atkMod = 0;
        this.defMod = 0;
        this.evaMod = 0;
        this.hpMod = 0;
        this.mpMod = 0;
        this.lukMod = 0;
    }
    
    /**
     * Constructor completo incluyendo imagePath.
     */
    public GameItem(String name, int itemTypeId, String description, int value, String imagePath) {
        this(name, itemTypeId, description, value);
        this.imagePath = imagePath;
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
    
    public int getItemTypeId() {
        return itemTypeId;
    }
    
    public void setItemTypeId(int itemTypeId) {
        this.itemTypeId = itemTypeId;
        this.type = ItemType.fromId(itemTypeId);
    }
    
    /**
     * Obtiene el tipo de ítem como enum.
     * 
     * @return Tipo de ítem
     */
    public ItemType getType() {
        if (type == null) {
            type = ItemType.fromId(itemTypeId);
        }
        return type;
    }
    
    /**
     * Establece el tipo de ítem.
     * 
     * @param type Tipo de ítem
     */
    public void setType(ItemType type) {
        this.type = type;
        this.itemTypeId = type.getId();
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    /**
     * Obtiene el valor del efecto del ítem.
     * Para compatibilidad con el código que espera getEffectValue().
     * 
     * @return Valor del efecto principal del ítem (basado en su tipo)
     */
    public int getEffectValue() {
        switch (getType()) {
            case WEAPON:
                return atkMod;
            case ARMOR:
                return defMod;
            case POTION:
                return hpMod > 0 ? hpMod : mpMod;
            default:
                return 0;
        }
    }
    
    /**
     * Obtiene el valor en oro del ítem.
     * Para compatibilidad con el código que espera getGoldValue().
     * 
     * @return Valor en oro del ítem
     */
    public int getGoldValue() {
        return value;
    }
    
    /**
     * Establece el valor en oro del ítem.
     * 
     * @param goldValue Valor en oro
     */
    public void setGoldValue(int goldValue) {
        this.value = goldValue;
    }
    
    public int getAtkMod() {
        return atkMod;
    }
    
    public void setAtkMod(int atkMod) {
        this.atkMod = atkMod;
    }
    
    public int getDefMod() {
        return defMod;
    }
    
    public void setDefMod(int defMod) {
        this.defMod = defMod;
    }
    
    public int getEvaMod() {
        return evaMod;
    }
    
    public void setEvaMod(int evaMod) {
        this.evaMod = evaMod;
    }
    
    public int getHpMod() {
        return hpMod;
    }
    
    public void setHpMod(int hpMod) {
        this.hpMod = hpMod;
    }
    
    public int getMpMod() {
        return mpMod;
    }
    
    public void setMpMod(int mpMod) {
        this.mpMod = mpMod;
    }
    
    public int getLukMod() {
        return lukMod;
    }
    
    public void setLukMod(int lukMod) {
        this.lukMod = lukMod;
    }
    
    public boolean isEquippable() {
        return isEquippable;
    }
    
    public void setEquippable(boolean equippable) {
        isEquippable = equippable;
    }
    
    public boolean isConsumable() {
        return isConsumable;
    }
    
    public void setConsumable(boolean consumable) {
        isConsumable = consumable;
    }
    
    public boolean isKeyItem() {
        return isKeyItem;
    }
    
    public void setKeyItem(boolean keyItem) {
        isKeyItem = keyItem;
    }
    
    /**
     * Obtiene la ruta de la imagen del ítem.
     * 
     * @return Ruta de la imagen o una ruta predeterminada si es null
     */
    public String getImagePath() {
        // Si no hay ruta de imagen, devolver una ruta predeterminada basada en el tipo
        if (imagePath == null || imagePath.isEmpty()) {
            return "resources/images/items/" + getType().name().toLowerCase() + "_default.png";
        }
        return imagePath;
    }
    
    /**
     * Establece la ruta de la imagen del ítem.
     * 
     * @param imagePath Ruta de la imagen
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    @Override
    public String toString() {
        return name;
    }
}