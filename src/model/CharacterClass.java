package model;

/**
 * Representa una clase de personaje en el juego (Ladrón, Caballero, Mago/Clérigo).
 */
public class CharacterClass {
    public enum ClassType {
        THIEF("Ladrón"),
        KNIGHT("Caballero"),
        MAGE("Mago/Clérigo");
        
        private final String displayName;
        
        ClassType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    private int id;
    private ClassType type;
    private int baseAtk;
    private int baseDef;
    private int baseEva;
    private int baseHp;
    private int baseMp;
    private int baseLuk;
    private String description;
    
    /**
     * Constructor por defecto.
     */
    public CharacterClass() {
    }
    
    /**
     * Constructor con parámetros principales.
     */
    public CharacterClass(ClassType type, int baseAtk, int baseDef, int baseEva, 
                          int baseHp, int baseMp, int baseLuk, String description) {
        this.type = type;
        this.baseAtk = baseAtk;
        this.baseDef = baseDef;
        this.baseEva = baseEva;
        this.baseHp = baseHp;
        this.baseMp = baseMp;
        this.baseLuk = baseLuk;
        this.description = description;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public ClassType getType() {
        return type;
    }
    
    public void setType(ClassType type) {
        this.type = type;
    }
    
    /**
     * Obtiene el nombre de la clase de personaje.
     * Este método se añade para compatibilidad con código existente.
     * 
     * @return Nombre de la clase de personaje
     */
    public String getName() {
        return type != null ? type.getDisplayName() : "Desconocida";
    }
    
    /**
     * Establece el tipo de clase a partir de un nombre.
     * Este método se añade para compatibilidad con código existente.
     * 
     * @param name Nombre de la clase
     */
    public void setName(String name) {
        for (ClassType classType : ClassType.values()) {
            if (classType.getDisplayName().equalsIgnoreCase(name)) {
                this.type = classType;
                return;
            }
        }
        // Si no se encuentra, establecer un valor predeterminado
        this.type = ClassType.KNIGHT;
    }
    
    public int getBaseAtk() {
        return baseAtk;
    }
    
    public void setBaseAtk(int baseAtk) {
        this.baseAtk = baseAtk;
    }
    
    public int getBaseDef() {
        return baseDef;
    }
    
    public void setBaseDef(int baseDef) {
        this.baseDef = baseDef;
    }
    
    public int getBaseEva() {
        return baseEva;
    }
    
    public void setBaseEva(int baseEva) {
        this.baseEva = baseEva;
    }
    
    public int getBaseHp() {
        return baseHp;
    }
    
    public void setBaseHp(int baseHp) {
        this.baseHp = baseHp;
    }
    
    public int getBaseMp() {
        return baseMp;
    }
    
    public void setBaseMp(int baseMp) {
        this.baseMp = baseMp;
    }
    
    public int getBaseLuk() {
        return baseLuk;
    }
    
    public void setBaseLuk(int baseLuk) {
        this.baseLuk = baseLuk;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Devuelve una representación en cadena de la clase de personaje.
     * Modificado para mostrar el nombre de la clase en lugar del objeto completo.
     */
    @Override
    public String toString() {
        return getName();
    }
}