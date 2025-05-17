
package com.moonscorch.dao;

/**
 * Fábrica para obtener instancias de los diferentes DAOs.
 * Implementa el patrón Singleton para cada tipo de DAO.
 */
public class DAOFactory {
    
    // Instancias únicas de cada DAO
    private static PlayerDAO playerDAO;
    private static CharacterClassDAO characterClassDAO;
    private static SavedGameDAO savedGameDAO;
    private static GameItemDAO gameItemDAO;
    private static InventoryItemDAO inventoryItemDAO;
    private static MapNodeDAO mapNodeDAO;
    private static MapProgressDAO mapProgressDAO;
    private static EnemyDAO enemyDAO;
    private static SkillDAO skillDAO;
    private static TreasureDAO treasureDAO;
    
    /**
     * Constructor privado para evitar instanciación directa.
     */
    private DAOFactory() {
        // Constructor privado para evitar instanciación
    }
    
    /**
     * Obtiene una instancia del DAO de jugadores.
     * 
     * @return Instancia única de PlayerDAO
     */
    public static synchronized PlayerDAO getPlayerDAO() {
        if (playerDAO == null) {
            playerDAO = new PlayerDAO();
        }
        return playerDAO;
    }
    
    /**
     * Obtiene una instancia del DAO de clases de personajes.
     * 
     * @return Instancia única de CharacterClassDAO
     */
    public static synchronized CharacterClassDAO getCharacterClassDAO() {
        if (characterClassDAO == null) {
            characterClassDAO = new CharacterClassDAO();
        }
        return characterClassDAO;
    }
    
    /**
     * Obtiene una instancia del DAO de partidas guardadas.
     * 
     * @return Instancia única de SavedGameDAO
     */
    public static synchronized SavedGameDAO getSavedGameDAO() {
        if (savedGameDAO == null) {
            savedGameDAO = new SavedGameDAO();
        }
        return savedGameDAO;
    }
    
    /**
     * Obtiene una instancia del DAO de objetos del juego.
     * 
     * @return Instancia única de GameItemDAO
     */
    public static synchronized GameItemDAO getGameItemDAO() {
        if (gameItemDAO == null) {
            gameItemDAO = new GameItemDAO();
        }
        return gameItemDAO;
    }
    
    /**
     * Obtiene una instancia del DAO de inventario.
     * 
     * @return Instancia única de InventoryItemDAO
     */
    public static synchronized InventoryItemDAO getInventoryItemDAO() {
        if (inventoryItemDAO == null) {
            inventoryItemDAO = new InventoryItemDAO();
        }
        return inventoryItemDAO;
    }
    
    /**
     * Obtiene una instancia del DAO de nodos del mapa.
     * 
     * @return Instancia única de MapNodeDAO
     */
    public static synchronized MapNodeDAO getMapNodeDAO() {
        if (mapNodeDAO == null) {
            mapNodeDAO = new MapNodeDAO();
        }
        return mapNodeDAO;
    }
    
    /**
     * Obtiene una instancia del DAO de progreso en el mapa.
     * 
     * @return Instancia única de MapProgressDAO
     */
    public static synchronized MapProgressDAO getMapProgressDAO() {
        if (mapProgressDAO == null) {
            mapProgressDAO = new MapProgressDAO();
        }
        return mapProgressDAO;
    }
    
    /**
     * Obtiene una instancia del DAO de enemigos.
     * 
     * @return Instancia única de EnemyDAO
     */
    public static synchronized EnemyDAO getEnemyDAO() {
        if (enemyDAO == null) {
            enemyDAO = new EnemyDAO();
        }
        return enemyDAO;
    }
    
    /**
     * Obtiene una instancia del DAO de habilidades.
     * 
     * @return Instancia única de SkillDAO
     */
    public static synchronized SkillDAO getSkillDAO() {
        if (skillDAO == null) {
            skillDAO = new SkillDAO();
        }
        return skillDAO;
    }
    
    /**
     * Obtiene una instancia del DAO de tesoros.
     * 
     * @return Instancia única de TreasureDAO
     */
    public static synchronized TreasureDAO getTreasureDAO() {
        if (treasureDAO == null) {
            treasureDAO = new TreasureDAO();
        }
        return treasureDAO;
    }
}
