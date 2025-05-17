
package com.moonscorch.model;

import java.sql.Timestamp;

/**
 * Clase que representa una partida guardada.
 */
public class SavedGame {
    private int id;
    private int playerId;
    private int characterId;
    private String saveName;
    private Timestamp saveDate;
    private String gameData; // JSON con el estado del juego
    private int playTime; // Tiempo de juego en segundos
    
    public SavedGame() {
    }
    
    public SavedGame(int playerId, int characterId, String saveName, String gameData, int playTime) {
        this.playerId = playerId;
        this.characterId = characterId;
        this.saveName = saveName;
        this.gameData = gameData;
        this.playTime = playTime;
    }
    
    // Getters y setters
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
    
    public int getCharacterId() {
        return characterId;
    }
    
    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }
    
    public String getSaveName() {
        return saveName;
    }
    
    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }
    
    public Timestamp getSaveDate() {
        return saveDate;
    }
    
    public void setSaveDate(Timestamp saveDate) {
        this.saveDate = saveDate;
    }
    
    public String getGameData() {
        return gameData;
    }
    
    public void setGameData(String gameData) {
        this.gameData = gameData;
    }
    
    public int getPlayTime() {
        return playTime;
    }
    
    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }
    
    @Override
    public String toString() {
        return "SavedGame{" +
                "id=" + id +
                ", playerId=" + playerId +
                ", characterId=" + characterId +
                ", saveName='" + saveName + '\'' +
                ", saveDate=" + saveDate +
                ", playTime=" + playTime + " seconds" +
                '}';
    }
}
