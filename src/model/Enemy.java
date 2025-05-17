package model;

/**
 * Representa un enemigo en el juego.
 */
public class Enemy {
    private int id;
    private String name;
    private String description;
    private int level;
    private int hp;
    private int mp;
    private int atk;
    private int def;
    private int eva;
    private int luk;
    private int expReward;
    private int goldReward;
    private String imagePath; // Nuevo campo para la ruta de la imagen
    
    /**
     * Constructor por defecto.
     */
    public Enemy() {
    }
    
    /**
     * Constructor con parámetros principales.
     */
    public Enemy(String name, String description, int level, int hp, int mp, 
                int atk, int def, int eva, int luk, int expReward, int goldReward) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.hp = hp;
        this.mp = mp;
        this.atk = atk;
        this.def = def;
        this.eva = eva;
        this.luk = luk;
        this.expReward = expReward;
        this.goldReward = goldReward;
    }
    
    /**
     * Constructor completo incluyendo imagePath.
     */
    public Enemy(String name, String description, int level, int hp, int mp, 
                int atk, int def, int eva, int luk, int expReward, int goldReward,
                String imagePath) {
        this(name, description, level, hp, mp, atk, def, eva, luk, expReward, goldReward);
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
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
    
    public int getLuk() {
        return luk;
    }
    
    public void setLuk(int luk) {
        this.luk = luk;
    }
    
    public int getExpReward() {
        return expReward;
    }
    
    public void setExpReward(int expReward) {
        this.expReward = expReward;
    }
    
    public int getGoldReward() {
        return goldReward;
    }
    
    public void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
    }
    
    /**
     * Obtiene la ruta de la imagen del enemigo.
     * 
     * @return Ruta de la imagen o una ruta predeterminada si es null
     */
    public String getImagePath() {
        // Si no hay ruta de imagen, devolver una ruta predeterminada
        return imagePath != null ? imagePath : "resources/images/enemies/default.png";
    }
    
    /**
     * Establece la ruta de la imagen del enemigo.
     * 
     * @param imagePath Ruta de la imagen
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    @Override
    public String toString() {
        return name + " (Nivel " + level + ")";
    }
}