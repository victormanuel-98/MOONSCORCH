package model;

public class CharacterClass {
    private int id;
    private String name;
    private String description;
    private String imagePath; // Ruta a la imagen representativa
    private int baseAtk;
    private int baseDef;
    private int baseEva;
    private int baseHp;
    private int baseMp;
    private int baseLuk;

    public CharacterClass() {
    }

    public CharacterClass(int id, String name, String description, String imagePath,
                          int baseAtk, int baseDef, int baseEva,
                          int baseHp, int baseMp, int baseLuk) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.baseAtk = baseAtk;
        this.baseDef = baseDef;
        this.baseEva = baseEva;
        this.baseHp = baseHp;
        this.baseMp = baseMp;
        this.baseLuk = baseLuk;
    }

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
}