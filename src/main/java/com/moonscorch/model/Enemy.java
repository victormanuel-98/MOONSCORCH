
package com.moonscorch.model;

/**
 * Clase que representa un enemigo en el juego.
 */
public class Enemy {
    private int id;
    private String name;
    private String description;
    private String enemyType; // humanoide, bestia, no-muerto, etc.
    private int atk;
    private int def;
    private int eva;
    private int hp;
    private int mp;
    private int luk;
    private int difficultyLevel; // nivel de dificultad (1-5)
    private int xpReward; // experiencia al derrotar
    private int goldReward; // oro al derrotar
    
    public Enemy() {
    }
    
    public Enemy(String name, String description, String enemyType, int atk, int def, int eva, int hp, int mp, int luk, int difficultyLevel, int xpReward, int goldReward) {
        this.name = name;
        this.description = description;
        this.enemyType = enemyType;
        this.atk = atk;
        this.def = def;
        this.eva = eva;
        this.hp = hp;
        this.mp = mp;
        this.luk = luk;
        this.difficultyLevel = difficultyLevel;
        this.xpReward = xpReward;
        this.goldReward = goldReward;
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
    
    public String getEnemyType() {
        return enemyType;
    }
    
    public void setEnemyType(String enemyType) {
        this.enemyType = enemyType;
    }
    
    public int getAtk() {
        return atk;
    }
    
    public void setAtk(int atk) {
        this.atk = atk;
    }
    
    public int getDef() {
        return def;
    }
    
    public void setDef(int def) {
        this.def = def;
    }
    
    public int getEva() {
        return eva;
    }
    
    public void setEva(int eva) {
        this.eva = eva;
    }
    
    public int getHp() {
        return hp;
    }
    
    public void setHp(int hp) {
        this.hp = hp;
    }
    
    public int getMp() {
        return mp;
    }
    
    public void setMp(int mp) {
        this.mp = mp;
    }
    
    public int getLuk() {
        return luk;
    }
    
    public void setLuk(int luk) {
        this.luk = luk;
    }
    
    public int getDifficultyLevel() {
        return difficultyLevel;
    }
    
    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
    
    public int getXpReward() {
        return xpReward;
    }
    
    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }
    
    public int getGoldReward() {
        return goldReward;
    }
    
    public void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
    }
    
    @Override
    public String toString() {
        return "Enemy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", enemyType='" + enemyType + '\'' +
                ", atk=" + atk +
                ", def=" + def +
                ", eva=" + eva +
                ", hp=" + hp +
                ", mp=" + mp +
                ", luk=" + luk +
                ", difficultyLevel=" + difficultyLevel +
                ", xpReward=" + xpReward +
                ", goldReward=" + goldReward +
                '}';
    }
}
