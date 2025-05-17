package model;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Representa una partida guardada en el juego.
 */
public class SavedGame {
    private int id;
    private int playerId;
    private String saveName;
    private Date saveDate;
    private int characterClassId;
    private int currentLevel;
    private int currentExp;
    private int currentHp;
    private int currentMp;
    private int currentAtk;
    private int currentDef;
    private int currentEva;
    private int currentLuk;
    private int gold;
    private int currentMapNodeId;
    
    // Campos adicionales para compatibilidad con SavedGameDAO
    private String characterName;
    private int currentMaxHp;
    private int currentMaxMp;
    private int experiencePoints;
    private int level;
    private int playTimeSeconds;
    private Timestamp savedAt;
    
    /**
     * Constructor por defecto.
     */
    public SavedGame() {
    }
    
    /**
     * Constructor con parámetros principales.
     */
    public SavedGame(int playerId, String saveName, int characterClassId) {
        this.playerId = playerId;
        this.saveName = saveName;
        this.saveDate = new Date();
        this.characterClassId = characterClassId;
        this.currentLevel = 1;
        this.currentExp = 0;
        this.gold = 0;
        this.playTimeSeconds = 0;
        this.savedAt = new Timestamp(System.currentTimeMillis());
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getPlayerId() {
        return playerId;
    }
    
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    
    public String getSaveName() {
        return saveName;
    }
    
    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }
    
    public Date getSaveDate() {
        return saveDate;
    }
    
    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }
    
    public int getCharacterClassId() {
        return characterClassId;
    }
    
    public void setCharacterClassId(int characterClassId) {
        this.characterClassId = characterClassId;
    }
    
    public int getCurrentLevel() {
        return currentLevel;
    }
    
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
    
    public int getCurrentExp() {
        return currentExp;
    }
    
    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }
    
    public int getCurrentHp() {
        return currentHp;
    }
    
    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }
    
    public int getCurrentMp() {
        return currentMp;
    }
    
    public void setCurrentMp(int currentMp) {
        this.currentMp = currentMp;
    }
    
    public int getCurrentAtk() {
        return currentAtk;
    }
    
    public void setCurrentAtk(int currentAtk) {
        this.currentAtk = currentAtk;
    }
    
    public int getCurrentDef() {
        return currentDef;
    }
    
    public void setCurrentDef(int currentDef) {
        this.currentDef = currentDef;
    }
    
    public int getCurrentEva() {
        return currentEva;
    }
    
    public void setCurrentEva(int currentEva) {
        this.currentEva = currentEva;
    }
    
    public int getCurrentLuk() {
        return currentLuk;
    }
    
    public void setCurrentLuk(int currentLuk) {
        this.currentLuk = currentLuk;
    }
    
    public int getGold() {
        return gold;
    }
    
    public void setGold(int gold) {
        this.gold = gold;
    }
    
    public int getCurrentMapNodeId() {
        return currentMapNodeId;
    }
    
    public void setCurrentMapNodeId(int currentMapNodeId) {
        this.currentMapNodeId = currentMapNodeId;
    }
    
    // Métodos adicionales para compatibilidad con SavedGameDAO
    
    /**
     * Obtiene el nombre del personaje.
     * @return Nombre del personaje
     */
    public String getCharacterName() {
        return characterName != null ? characterName : saveName;
    }
    
    /**
     * Establece el nombre del personaje.
     * @param characterName Nombre del personaje
     */
    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
    
    /**
     * Obtiene el HP máximo actual.
     * @return HP máximo actual
     */
    public int getCurrentMaxHp() {
        return currentMaxHp;
    }
    
    /**
     * Establece el HP máximo actual.
     * @param currentMaxHp HP máximo actual
     */
    public void setCurrentMaxHp(int currentMaxHp) {
        this.currentMaxHp = currentMaxHp;
    }
    
    /**
     * Obtiene el MP máximo actual.
     * @return MP máximo actual
     */
    public int getCurrentMaxMp() {
        return currentMaxMp;
    }
    
    /**
     * Establece el MP máximo actual.
     * @param currentMaxMp MP máximo actual
     */
    public void setCurrentMaxMp(int currentMaxMp) {
        this.currentMaxMp = currentMaxMp;
    }
    
    /**
     * Obtiene los puntos de experiencia.
     * @return Puntos de experiencia
     */
    public int getExperiencePoints() {
        return experiencePoints != 0 ? experiencePoints : currentExp;
    }
    
    /**
     * Establece los puntos de experiencia.
     * @param experiencePoints Puntos de experiencia
     */
    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
        this.currentExp = experiencePoints; // Mantener sincronizado con currentExp
    }
    
    /**
     * Obtiene el nivel.
     * @return Nivel
     */
    public int getLevel() {
        return level != 0 ? level : currentLevel;
    }
    
    /**
     * Establece el nivel.
     * @param level Nivel
     */
    public void setLevel(int level) {
        this.level = level;
        this.currentLevel = level; // Mantener sincronizado con currentLevel
    }
    
    /**
     * Obtiene el tiempo de juego en segundos.
     * @return Tiempo de juego en segundos
     */
    public int getPlayTimeSeconds() {
        return playTimeSeconds;
    }
    
    /**
     * Establece el tiempo de juego en segundos.
     * @param playTimeSeconds Tiempo de juego en segundos
     */
    public void setPlayTimeSeconds(int playTimeSeconds) {
        this.playTimeSeconds = playTimeSeconds;
    }
    
    /**
     * Obtiene el ID del nodo actual.
     * @return ID del nodo actual
     */
    public int getCurrentNodeId() {
        return currentMapNodeId;
    }
    
    /**
     * Establece el ID del nodo actual.
     * @param currentNodeId ID del nodo actual
     */
    public void setCurrentNodeId(int currentNodeId) {
        this.currentMapNodeId = currentNodeId;
    }
    
    /**
     * Obtiene la fecha de guardado como Timestamp.
     * @return Fecha de guardado
     */
    public Timestamp getSavedAt() {
        if (savedAt != null) {
            return savedAt;
        } else if (saveDate != null) {
            return new Timestamp(saveDate.getTime());
        } else {
            return new Timestamp(System.currentTimeMillis());
        }
    }
    
    /**
     * Establece la fecha de guardado.
     * @param savedAt Fecha de guardado
     */
    public void setSavedAt(Timestamp savedAt) {
        this.savedAt = savedAt;
        this.saveDate = new Date(savedAt.getTime()); // Mantener sincronizado con saveDate
    }
    
    /**
     * Formatea el tiempo de juego en formato horas:minutos:segundos.
     * 
     * @return Tiempo de juego formateado
     */
    public String getFormattedPlayTime() {
        int hours = playTimeSeconds / 3600;
        int minutes = (playTimeSeconds % 3600) / 60;
        int seconds = playTimeSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    
    /**
     * Incrementa el tiempo de juego en segundos.
     * 
     * @param seconds Segundos a añadir
     */
    public void addPlayTime(int seconds) {
        this.playTimeSeconds += seconds;
    }
    
    @Override
    public String toString() {
        return "SavedGame{" +
                "id=" + id +
                ", playerId=" + playerId +
                ", saveName='" + saveName + '\'' +
                ", saveDate=" + saveDate +
                ", characterClassId=" + characterClassId +
                ", currentLevel=" + currentLevel +
                ", currentExp=" + currentExp +
                ", gold=" + gold +
                ", currentMapNodeId=" + currentMapNodeId +
                '}';
    }
}