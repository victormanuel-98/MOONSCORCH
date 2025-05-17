// Archivo: src/dao/DAOFactory.java

package dao;

/**
 * Fábrica para crear instancias de DAOs.
 * Implementa el patrón Factory para centralizar la creación de DAOs.
 */
public class DAOFactory {
    
    private static DAOFactory instance;
    
    /**
     * Constructor privado para evitar instanciación directa (patrón Singleton).
     */
    private DAOFactory() {
    }
    
    /**
     * Obtiene la única instancia de la fábrica de DAOs.
     * 
     * @return Instancia única de DAOFactory
     */
    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }
    
    /**
     * Crea un DAO para jugadores.
     * 
     * @return Instancia de PlayerDAO
     */
    public PlayerDAO createPlayerDAO() {
        return new PlayerDAO();
    }
    
    /**
     * Crea un DAO para clases de personajes.
     * 
     * @return Instancia de CharacterClassDAO
     */
    public CharacterClassDAO createCharacterClassDAO() {
        return new CharacterClassDAO();
    }
    
    /**
     * Crea un DAO para partidas guardadas.
     * 
     * @return Instancia de SavedGameDAO
     */
    public SavedGameDAO createSavedGameDAO() {
        return new SavedGameDAO();
    }
    
    /**
     * Crea un DAO para ítems del juego.
     * 
     * @return Instancia de GameItemDAO
     */
    public GameItemDAO createGameItemDAO() {
        return new GameItemDAO();
    }
    
    /**
     * Crea un DAO para ítems de inventario.
     * 
     * @return Instancia de InventoryItemDAO
     */
    public InventoryItemDAO createInventoryItemDAO() {
        return new InventoryItemDAO();
    }
    
    /**
     * Crea un DAO para enemigos.
     * 
     * @return Instancia de EnemyDAO
     */
    public EnemyDAO createEnemyDAO() {
        return new EnemyDAO();
    }
    
    /**
     * Crea un DAO para nodos del mapa.
     * 
     * @return Instancia de MapNodeDAO
     */
    public MapNodeDAO createMapNodeDAO() {
        return new MapNodeDAO();
    }
    
    /**
     * Crea un DAO para el progreso en el mapa.
     * 
     * @return Instancia de MapProgressDAO
     */
    public MapProgressDAO createMapProgressDAO() {
        return new MapProgressDAO();
    }
    
    /**
     * Crea un DAO para habilidades.
     * 
     * @return Instancia de SkillDAO
     */
    public SkillDAO createSkillDAO() {
        return new SkillDAO();
    }
    
    /**
     * Crea un DAO para tesoros.
     * 
     * @return Instancia de TreasureDAO
     */
    public TreasureDAO createTreasureDAO() {
        return new TreasureDAO();
    }
}