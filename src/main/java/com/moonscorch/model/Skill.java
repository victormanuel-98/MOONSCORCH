
package com.moonscorch.model;

/**
 * Clase que representa una habilidad en el juego.
 */
public class Skill {
    private int id;
    private String name;
    private String description;
    private int mpCost; // costo de MP para usar
    private String effectType; // daño, curación, buff, debuff, etc.
    private int effectValue; // valor del efecto
    private String targetType; // uno, todos, aliado, enemigo, etc.
    private int skillLevel; // nivel de la habilidad (1-5)
    
    public Skill() {
    }
    
    public Skill(String name, String description, int mpCost, String effectType, int effectValue, String targetType, int skillLevel) {
        this.name = name;
        this.description = description;
        this.mpCost = mpCost;
        this.effectType = effectType;
        this.effectValue = effectValue;
        this.targetType = targetType;
        this.skillLevel = skillLevel;
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
    
    public int getMpCost() {
        return mpCost;
    }
    
    public void setMpCost(int mpCost) {
        this.mpCost = mpCost;
    }
    
    public String getEffectType() {
        return effectType;
    }
    
    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }
    
    public int getEffectValue() {
        return effectValue;
    }
    
    public void setEffectValue(int effectValue) {
        this.effectValue = effectValue;
    }
    
    public String getTargetType() {
        return targetType;
    }
    
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
    
    public int getSkillLevel() {
        return skillLevel;
    }
    
    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }
    
    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", mpCost=" + mpCost +
                ", effectType='" + effectType + '\'' +
                ", effectValue=" + effectValue +
                ", targetType='" + targetType + '\'' +
                ", skillLevel=" + skillLevel +
                '}';
    }
}
