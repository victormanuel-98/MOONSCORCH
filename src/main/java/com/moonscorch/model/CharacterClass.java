
package com.moonscorch.model;

/**
 * Clase que representa una clase de personaje en el juego.
 */
public class CharacterClass {
    private int id;
    private String name;
    private String description;
    private int baseAtk;
    private int baseDef;
    private int baseEva;
    private int baseHp;
    private int baseMp;
    private int baseLuk;
    
    public CharacterClass() {
    }
    
    public CharacterClass(String name, String description, int baseAtk, int baseDef, int baseEva, int baseHp, int baseMp, int baseLuk) {
        this.name = name;
        this.description = description;
        this.baseAtk = baseAtk;
        this.baseDef = baseDef;
        this.baseEva = baseEva;
        this.baseHp = baseHp;
        this.baseMp = baseMp;
        this.baseLuk = baseLuk;
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
    
    @Override
    public String toString() {
        return "CharacterClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", baseAtk=" + baseAtk +
                ", baseDef=" + baseDef +
                ", baseEva=" + baseEva +
                ", baseHp=" + baseHp +
                ", baseMp=" + baseMp +
                ", baseLuk=" + baseLuk +
                '}';
    }
}
