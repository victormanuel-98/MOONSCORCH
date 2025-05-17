package model;
/**
 * Representa una habilidad en el juego.
 */
public class Skill {
    public enum SkillType {
        ATTACK("Ataque"),
        HEAL("Curación"),
        BUFF("Mejora"),
        DEBUFF("Perjuicio"),
        SPECIAL("Especial");
        
        private final String displayName;
        
        SkillType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    private int id;
    private String name;
    private String description;
    private SkillType type;
    private int mpCost;
    private int power;
    private int characterClassId; // -1 si es común a todas las clases
    private int requiredLevel;
    private boolean targetsSelf;
    private boolean targetsAll;
    private int statusEffectId; // 0 si no aplica ningún efecto
    private int statusEffectChance; // Probabilidad de aplicar el efecto (0-100)
    
    // Campos adicionales para compatibilidad con SkillDAO
    private SkillType skillType; // Alias para type
    private int effectValue; // Alias para power
    private String targetType; // Representación en texto del tipo de objetivo
    
    /**
     * Constructor por defecto.
     */
    public Skill() {
    }
    
    /**
     * Constructor con parámetros principales.
     */
    public Skill(String name, String description, SkillType type, int mpCost, int power, 
                int characterClassId, int requiredLevel) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.skillType = type; // Sincronizar con type
        this.mpCost = mpCost;
        this.power = power;
        this.effectValue = power; // Sincronizar con power
        this.characterClassId = characterClassId;
        this.requiredLevel = requiredLevel;
        this.targetsSelf = false;
        this.targetsAll = false;
        this.statusEffectId = 0;
        this.statusEffectChance = 0;
        updateTargetType(); // Inicializar targetType basado en targetsSelf y targetsAll
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
    
    public SkillType getType() {
        return type;
    }
    
    public void setType(SkillType type) {
        this.type = type;
        this.skillType = type; // Sincronizar con skillType
    }
    
    public int getMpCost() {
        return mpCost;
    }
    
    public void setMpCost(int mpCost) {
        this.mpCost = mpCost;
    }
    
    public int getPower() {
        return power;
    }
    
    public void setPower(int power) {
        this.power = power;
        this.effectValue = power; // Sincronizar con effectValue
    }
    
    public int getCharacterClassId() {
        return characterClassId;
    }
    
    public void setCharacterClassId(int characterClassId) {
        this.characterClassId = characterClassId;
    }
    
    public int getRequiredLevel() {
        return requiredLevel;
    }
    
    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }
    
    public boolean isTargetsSelf() {
        return targetsSelf;
    }
    
    public void setTargetsSelf(boolean targetsSelf) {
        this.targetsSelf = targetsSelf;
        updateTargetType(); // Actualizar targetType
    }
    
    public boolean isTargetsAll() {
        return targetsAll;
    }
    
    public void setTargetsAll(boolean targetsAll) {
        this.targetsAll = targetsAll;
        updateTargetType(); // Actualizar targetType
    }
    
    public int getStatusEffectId() {
        return statusEffectId;
    }
    
    public void setStatusEffectId(int statusEffectId) {
        this.statusEffectId = statusEffectId;
    }
    
    public int getStatusEffectChance() {
        return statusEffectChance;
    }
    
    public void setStatusEffectChance(int statusEffectChance) {
        this.statusEffectChance = statusEffectChance;
    }
    
    // Métodos adicionales para compatibilidad con SkillDAO
    
    /**
     * Obtiene el tipo de habilidad (alias para getType).
     * @return Tipo de habilidad
     */
    public SkillType getSkillType() {
        return type != null ? type : skillType;
    }
    
    /**
     * Establece el tipo de habilidad (alias para setType).
     * @param skillType Tipo de habilidad
     */
    public void setSkillType(SkillType skillType) {
        this.skillType = skillType;
        this.type = skillType; // Sincronizar con type
    }
    
    /**
     * Establece el tipo de habilidad a partir de una cadena.
     * @param skillTypeStr Cadena que representa el tipo de habilidad
     */
    public void setSkillType(String skillTypeStr) {
        try {
            SkillType type = SkillType.valueOf(skillTypeStr.toUpperCase());
            setSkillType(type);
        } catch (IllegalArgumentException e) {
            // Si la cadena no coincide con ningún valor de la enumeración, usar un valor por defecto
            setSkillType(SkillType.ATTACK);
        }
    }
    
    /**
     * Obtiene el valor del efecto (alias para getPower).
     * @return Valor del efecto
     */
    public int getEffectValue() {
        return power;
    }
    
    /**
     * Establece el valor del efecto (alias para setPower).
     * @param effectValue Valor del efecto
     */
    public void setEffectValue(int effectValue) {
        this.effectValue = effectValue;
        this.power = effectValue; // Sincronizar con power
    }
    
    /**
     * Obtiene el tipo de objetivo como cadena.
     * @return Tipo de objetivo
     */
    public String getTargetType() {
        if (targetType == null) {
            updateTargetType();
        }
        return targetType;
    }
    
    /**
     * Establece el tipo de objetivo a partir de una cadena.
     * @param targetType Tipo de objetivo
     */
    public void setTargetType(String targetType) {
        this.targetType = targetType;
        
        // Actualizar targetsSelf y targetsAll basado en targetType
        if (targetType != null) {
            if (targetType.contains("SELF")) {
                this.targetsSelf = true;
                this.targetsAll = false;
            } else if (targetType.contains("ALL")) {
                this.targetsAll = true;
                this.targetsSelf = false;
            } else if (targetType.contains("SINGLE")) {
                this.targetsSelf = false;
                this.targetsAll = false;
            }
        }
    }
    
    /**
     * Actualiza el campo targetType basado en targetsSelf y targetsAll.
     */
    private void updateTargetType() {
        if (targetsSelf) {
            targetType = "SELF";
        } else if (targetsAll) {
            targetType = "ALL";
        } else {
            targetType = "SINGLE";
        }
    }
    
    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", mpCost=" + mpCost +
                ", power=" + power +
                ", characterClassId=" + characterClassId +
                ", requiredLevel=" + requiredLevel +
                ", targetType='" + targetType + '\'' +
                '}';
    }
}