package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

import dao.DAOFactory;
import dao.GameItemDAO;
import dao.InventoryItemDAO;
import dao.MapNodeDAO;
import dao.MapProgressDAO;
import dao.SavedGameDAO;

/**
 * Representa el estado global del juego.
 * Gestiona la partida actual, el jugador, el inventario y el progreso en el mapa.
 */
public class GameState {
    private static final Logger LOGGER = Logger.getLogger(GameState.class.getName());
    
    private SavedGame currentGame;
    private Player currentPlayer;
    private CharacterClass characterClass;
    private MapNode currentNode;
    private List<InventoryItem> inventory;
    private Map<Integer, GameItem> itemsCache;
    private Map<Integer, MapNode> mapNodes;
    private Map<Integer, MapProgress> mapProgress;
    private List<StatusEffect> activeStatusEffects;
    private CombatSystem combatSystem;
    private JPanel mapaActual; // Para compatibilidad con GameFrame
    private PlayerCombatData jugadorActual; // Para compatibilidad con GameFrame
    
    // DAOs para interactuar con la base de datos
    private final DAOFactory daoFactory;
    private final SavedGameDAO savedGameDAO;
    private final InventoryItemDAO inventoryItemDAO;
    private final MapNodeDAO mapNodeDAO;
    private final MapProgressDAO mapProgressDAO;
    private final GameItemDAO gameItemDAO;
    
    /**
     * Constructor por defecto.
     * Inicializa las colecciones y los DAOs.
     */
    public GameState() {
        this.inventory = new ArrayList<>();
        this.itemsCache = new HashMap<>();
        this.mapNodes = new HashMap<>();
        this.mapProgress = new HashMap<>();
        this.activeStatusEffects = new ArrayList<>();
        this.combatSystem = new CombatSystem();
        
        // Inicializar DAOs
        this.daoFactory = DAOFactory.getInstance();
        this.savedGameDAO = daoFactory.createSavedGameDAO();
        this.inventoryItemDAO = daoFactory.createInventoryItemDAO();
        this.mapNodeDAO = daoFactory.createMapNodeDAO();
        this.mapProgressDAO = daoFactory.createMapProgressDAO();
        this.gameItemDAO = daoFactory.createGameItemDAO();
    }
    
    /**
     * Constructor con parámetros principales.
     * 
     * @param currentGame Partida guardada actual
     * @param currentPlayer Jugador actual
     * @param characterClass Clase de personaje
     */
    public GameState(SavedGame currentGame, Player currentPlayer, CharacterClass characterClass) {
        this();
        this.currentGame = currentGame;
        this.currentPlayer = currentPlayer;
        this.characterClass = characterClass;
        
        // Cargar datos iniciales si hay una partida
        if (currentGame != null && currentGame.getId() > 0) {
            loadGameData();
        }
    }
    
    /**
     * Carga los datos de la partida desde la base de datos.
     * Incluye inventario, nodo actual y progreso del mapa.
     */
    private void loadGameData() {
        try {
            // Cargar inventario
            List<InventoryItem> items = inventoryItemDAO.findBySaveId(currentGame.getId());
            this.inventory = items;
            
            // Cargar ítems en caché
            for (InventoryItem item : items) {
                if (!itemsCache.containsKey(item.getItemId())) {
                    GameItem gameItem = gameItemDAO.findById(item.getItemId());
                    if (gameItem != null) {
                        itemsCache.put(gameItem.getId(), gameItem);
                    }
                }
            }
            
            // Cargar nodo actual
            if (currentGame.getCurrentNodeId() > 0) {
                this.currentNode = mapNodeDAO.findById(currentGame.getCurrentNodeId());
                if (currentNode != null) {
                    mapNodes.put(currentNode.getId(), currentNode);
                }
            }
            
            // Cargar progreso del mapa
            List<MapProgress> progressList = mapProgressDAO.findBySaveId(currentGame.getId());
            for (MapProgress progress : progressList) {
                mapProgress.put(progress.getNodeId(), progress);
                
                // Cargar nodos visitados
                if (!mapNodes.containsKey(progress.getNodeId())) {
                    MapNode node = mapNodeDAO.findById(progress.getNodeId());
                    if (node != null) {
                        mapNodes.put(node.getId(), node);
                    }
                }
            }
            
            // Cargar nodos conectados al actual
            if (currentNode != null) {
                List<MapNode> connectedNodes = mapNodeDAO.findConnectedNodes(currentNode.getId());
                for (MapNode node : connectedNodes) {
                    mapNodes.put(node.getId(), node);
                }
            }
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar datos de la partida", e);
        }
    }
    
    // Getters y Setters
    public SavedGame getCurrentGame() {
        return currentGame;
    }
    
    public void setCurrentGame(SavedGame currentGame) {
        this.currentGame = currentGame;
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    
    public CharacterClass getCharacterClass() {
        return characterClass;
    }
    
    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }
    
    public MapNode getCurrentNode() {
        return currentNode;
    }
    
    public void setCurrentNode(MapNode currentNode) {
        this.currentNode = currentNode;
        if (currentNode != null && currentGame != null) {
            currentGame.setCurrentNodeId(currentNode.getId());
        }
    }
    
    public List<InventoryItem> getInventory() {
        return inventory;
    }
    
    public void setInventory(List<InventoryItem> inventory) {
        this.inventory = inventory;
    }
    
    public Map<Integer, MapNode> getMapNodes() {
        return mapNodes;
    }
    
    public void setMapNodes(Map<Integer, MapNode> mapNodes) {
        this.mapNodes = mapNodes;
    }
    
    public Map<Integer, MapProgress> getMapProgress() {
        return mapProgress;
    }
    
    public void setMapProgress(Map<Integer, MapProgress> mapProgress) {
        this.mapProgress = mapProgress;
    }
    
    public List<StatusEffect> getActiveStatusEffects() {
        return activeStatusEffects;
    }
    
    public void setActiveStatusEffects(List<StatusEffect> activeStatusEffects) {
        this.activeStatusEffects = activeStatusEffects;
    }
    
    public CombatSystem getCombatSystem() {
        return combatSystem;
    }
    
    /**
     * Obtiene un ítem del juego por su ID.
     * Si no está en caché, lo carga desde la base de datos.
     * 
     * @param itemId ID del ítem
     * @return Ítem del juego o null si no existe
     */
    public GameItem getGameItem(int itemId) {
        if (itemsCache.containsKey(itemId)) {
            return itemsCache.get(itemId);
        } else {
            GameItem item = gameItemDAO.findById(itemId);
            if (item != null) {
                itemsCache.put(itemId, item);
            }
            return item;
        }
    }
    
    /**
     * Añade un objeto al inventario.
     * Si el objeto ya existe, incrementa su cantidad.
     * 
     * @param item Objeto a añadir
     * @param quantity Cantidad a añadir
     * @return true si se añadió correctamente, false en caso contrario
     */
    public boolean addItemToInventory(GameItem item, int quantity) {
        if (item == null || quantity <= 0 || currentGame == null) {
            return false;
        }
        
        // Añadir a caché si no existe
        if (!itemsCache.containsKey(item.getId())) {
            itemsCache.put(item.getId(), item);
        }
        
        // Buscar si ya existe en el inventario
        for (InventoryItem inventoryItem : inventory) {
            if (inventoryItem.getItemId() == item.getId()) {
                // Incrementar cantidad
                inventoryItem.setQuantity(inventoryItem.getQuantity() + quantity);
                
                // Actualizar en base de datos
                try {
                    return inventoryItemDAO.update(inventoryItem);
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Error al actualizar ítem en inventario", e);
                    return false;
                }
            }
        }
        
        // Si no existe, crear nuevo
        InventoryItem newItem = new InventoryItem();
        newItem.setSaveId(currentGame.getId());
        newItem.setItemId(item.getId());
        newItem.setQuantity(quantity);
        newItem.setEquipped(false);
        
        // Guardar en base de datos
        try {
            boolean saved = inventoryItemDAO.save(newItem);
            if (saved) {
                inventory.add(newItem);
            }
            return saved;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al guardar ítem en inventario", e);
            return false;
        }
    }
    
    /**
     * Elimina una cantidad de un objeto del inventario.
     * 
     * @param itemId ID del objeto a eliminar
     * @param quantity Cantidad a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean removeItemFromInventory(int itemId, int quantity) {
        if (quantity <= 0 || currentGame == null) {
            return false;
        }
        
        for (InventoryItem inventoryItem : inventory) {
            if (inventoryItem.getItemId() == itemId) {
                if (inventoryItem.getQuantity() <= quantity) {
                    // Eliminar completamente
                    inventory.remove(inventoryItem);
                    return inventoryItemDAO.delete(inventoryItem.getId());
                } else {
                    // Reducir cantidad
                    inventoryItem.setQuantity(inventoryItem.getQuantity() - quantity);
                    return inventoryItemDAO.update(inventoryItem);
                }
            }
        }
        
        return false; // Ítem no encontrado
    }
    
    /**
     * Equipa o desequipa un ítem.
     * 
     * @param inventoryItemId ID del ítem en el inventario
     * @param equip true para equipar, false para desequipar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean equipItem(int inventoryItemId, boolean equip) {
        for (InventoryItem item : inventory) {
            if (item.getId() == inventoryItemId) {
                item.setEquipped(equip);
                return inventoryItemDAO.update(item);
            }
        }
        return false;
    }
    
    /**
     * Mueve al jugador a un nuevo nodo del mapa.
     * Actualiza el nodo actual y el progreso del mapa.
     * 
     * @param nodeId ID del nodo destino
     * @return true si el movimiento fue exitoso, false en caso contrario
     */
    public boolean moveToNode(int nodeId) {
        // Verificar si el nodo existe o cargarlo
        MapNode targetNode = mapNodes.get(nodeId);
        if (targetNode == null) {
            targetNode = mapNodeDAO.findById(nodeId);
            if (targetNode != null) {
                mapNodes.put(nodeId, targetNode);
            } else {
                return false; // Nodo no encontrado
            }
        }
        
        // Actualizar nodo actual
        currentNode = targetNode;
        currentGame.setCurrentNodeId(nodeId);
        
        // Actualizar progreso del mapa
        MapProgress progress = mapProgress.get(nodeId);
        if (progress == null) {
            progress = new MapProgress();
            progress.setSaveId(currentGame.getId());
            progress.setNodeId(nodeId);
            progress.setVisited(true);
            progress.setCompleted(false);
            
            try {
                if (mapProgressDAO.save(progress)) {
                    mapProgress.put(nodeId, progress);
                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error al guardar progreso del mapa", e);
            }
        } else if (!progress.isVisited()) {
            progress.setVisited(true);
            mapProgressDAO.update(progress);
        }
        
        // Cargar nodos conectados
        try {
            List<MapNode> connectedNodes = mapNodeDAO.findConnectedNodes(nodeId);
            for (MapNode node : connectedNodes) {
                if (!mapNodes.containsKey(node.getId())) {
                    mapNodes.put(node.getId(), node);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al cargar nodos conectados", e);
        }
        
        // Guardar cambios en la partida
        savedGameDAO.update(currentGame);
        
        return true;
    }
    
    /**
     * Marca un nodo como completado.
     * 
     * @param nodeId ID del nodo a marcar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean completeNode(int nodeId) {
        MapProgress progress = mapProgress.get(nodeId);
        if (progress != null) {
            progress.setCompleted(true);
            return mapProgressDAO.update(progress);
        } else if (mapNodes.containsKey(nodeId)) {
            progress = new MapProgress();
            progress.setSaveId(currentGame.getId());
            progress.setNodeId(nodeId);
            progress.setVisited(true);
            progress.setCompleted(true);
            
            boolean saved = mapProgressDAO.save(progress);
            if (saved) {
                mapProgress.put(nodeId, progress);
            }
            return saved;
        }
        return false;
    }
    
    /**
     * Añade un efecto de estado al personaje.
     * 
     * @param effect Efecto a añadir
     */
    public void addStatusEffect(StatusEffect effect) {
        // Verificar si ya existe un efecto del mismo tipo
        for (StatusEffect existingEffect : activeStatusEffects) {
            if (existingEffect.getEffectType() == effect.getEffectType()) {
                // Reemplazar si el nuevo es más fuerte o tiene mayor duración
                if (effect.getDuration() > existingEffect.getDuration() || 
                    Math.abs(effect.getHpEffect()) > Math.abs(existingEffect.getHpEffect()) ||
                    Math.abs(effect.getMpEffect()) > Math.abs(existingEffect.getMpEffect())) {
                    activeStatusEffects.remove(existingEffect);
                    activeStatusEffects.add(effect);
                }
                return;
            }
        }
        
        // Si no existe, añadir
        activeStatusEffects.add(effect);
    }
    
    /**
     * Actualiza los efectos de estado (reduce duración, aplica efectos).
     */
    public void updateStatusEffects() {
        List<StatusEffect> expiredEffects = new ArrayList<>();
        
        for (StatusEffect effect : activeStatusEffects) {
            // Aplicar efectos por turno
            if (effect.getHpEffect() != 0) {
                int newHp = currentGame.getCurrentHp() + effect.getHpEffect();
                currentGame.setCurrentHp(Math.max(1, Math.min(newHp, currentGame.getCurrentMaxHp())));
            }
            
            if (effect.getMpEffect() != 0) {
                int newMp = currentGame.getCurrentMp() + effect.getMpEffect();
                currentGame.setCurrentMp(Math.max(0, Math.min(newMp, currentGame.getCurrentMaxMp())));
            }
            
            // Aplicar modificadores temporales de estadísticas
            // (Implementación según necesidades)
            
            // Reducir duración
            effect.setDuration(effect.getDuration() - 1);
            if (effect.getDuration() <= 0) {
                expiredEffects.add(effect);
            }
        }
        
        // Eliminar efectos expirados
        activeStatusEffects.removeAll(expiredEffects);
    }
    
    /**
     * Inicia un combate con el enemigo del nodo actual.
     * 
     * @return Datos de combate inicializados o null si no hay enemigo
     */
    public PlayerCombatData initiateCombat() {
        if (currentNode == null || currentNode.getEnemyId() <= 0) {
            return null;
        }
        
        // Crear datos de combate del jugador
        PlayerCombatData combatData = new PlayerCombatData();
        combatData.setName(currentGame.getCharacterName());
        combatData.setCurrentHp(currentGame.getCurrentHp());
        combatData.setMaxHp(currentGame.getCurrentMaxHp());
        combatData.setCurrentMp(currentGame.getCurrentMp());
        combatData.setMaxMp(currentGame.getCurrentMaxMp());
        combatData.setAtk(currentGame.getCurrentAtk());
        combatData.setDef(currentGame.getCurrentDef());
        combatData.setEva(currentGame.getCurrentEva());
        combatData.setLuk(currentGame.getCurrentLuk());
        
        // Aplicar efectos de estado activos
        for (StatusEffect effect : activeStatusEffects) {
            if (effect.getAtkModifier() != 0) {
                combatData.setAtk(combatData.getAtk() + effect.getAtkModifier());
            }
            if (effect.getDefModifier() != 0) {
                combatData.setDef(combatData.getDef() + effect.getDefModifier());
            }
            if (effect.getEvaModifier() != 0) {
                combatData.setEva(combatData.getEva() + effect.getEvaModifier());
            }
            if (effect.getLukModifier() != 0) {
                combatData.setLuk(combatData.getLuk() + effect.getLukModifier());
            }
        }
        
        return combatData;
    }
    
    /**
     * Actualiza las estadísticas del jugador después de un combate.
     * 
     * @param combatData Datos de combate actualizados
     */
    public void updateAfterCombat(PlayerCombatData combatData) {
        if (combatData == null || currentGame == null) {
            return;
        }
        
        currentGame.setCurrentHp(combatData.getCurrentHp());
        currentGame.setCurrentMp(combatData.getCurrentMp());
        
        // Guardar cambios
        savedGameDAO.update(currentGame);
    }
    
    /**
     * Guarda el estado actual del juego en la base de datos.
     * 
     * @return true si se guardó correctamente, false en caso contrario
     */
    public boolean saveGame() {
        if (currentGame == null) {
            return false;
        }
        
        try {
            // Actualizar tiempo de juego, etc.
            currentGame.setSavedAt(new java.sql.Timestamp(System.currentTimeMillis()));
            
            // Guardar partida
            boolean saved = savedGameDAO.update(currentGame);
            
            // Guardar inventario y progreso del mapa se hace en sus respectivos métodos
            
            return saved;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al guardar partida", e);
            return false;
        }
    }
    
    /**
     * Crea una nueva partida.
     * 
     * @param player Jugador
     * @param characterClass Clase de personaje
     * @param characterName Nombre del personaje
     * @param startingNodeId ID del nodo inicial
     * @return true si se creó correctamente, false en caso contrario
     */
    public boolean createNewGame(Player player, CharacterClass characterClass, String characterName, int startingNodeId) {
        try {
            // Crear nueva partida
            SavedGame newGame = new SavedGame();
            newGame.setPlayerId(player.getId());
            newGame.setCharacterClassId(characterClass.getId());
            newGame.setCharacterName(characterName);
            
            // Establecer estadísticas iniciales
            newGame.setCurrentAtk(characterClass.getBaseAtk());
            newGame.setCurrentDef(characterClass.getBaseDef());
            newGame.setCurrentEva(characterClass.getBaseEva());
            newGame.setCurrentHp(characterClass.getBaseHp());
            newGame.setCurrentMaxHp(characterClass.getBaseHp());
            newGame.setCurrentMp(characterClass.getBaseMp());
            newGame.setCurrentMaxMp(characterClass.getBaseMp());
            newGame.setCurrentLuk(characterClass.getBaseLuk());
            
            // Valores iniciales
            newGame.setExperiencePoints(0);
            newGame.setLevel(1);
            newGame.setGold(0);
            newGame.setPlayTimeSeconds(0);
            newGame.setCurrentNodeId(startingNodeId);
            newGame.setSavedAt(new java.sql.Timestamp(System.currentTimeMillis()));
            
            // Guardar partida
            boolean saved = savedGameDAO.save(newGame);
            if (saved) {
                // Actualizar estado
                this.currentGame = newGame;
                this.currentPlayer = player;
                this.characterClass = characterClass;
                
                // Cargar nodo inicial
                this.currentNode = mapNodeDAO.findById(startingNodeId);
                if (currentNode != null) {
                    mapNodes.put(currentNode.getId(), currentNode);
                }
                
                // Crear progreso inicial
                MapProgress initialProgress = new MapProgress();
                initialProgress.setSaveId(newGame.getId());
                initialProgress.setNodeId(startingNodeId);
                initialProgress.setVisited(true);
                initialProgress.setCompleted(false);
                
                mapProgressDAO.save(initialProgress);
                mapProgress.put(startingNodeId, initialProgress);
                
                // Cargar nodos conectados
                List<MapNode> connectedNodes = mapNodeDAO.findConnectedNodes(startingNodeId);
                for (MapNode node : connectedNodes) {
                    mapNodes.put(node.getId(), node);
                }
            }
            
            return saved;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al crear nueva partida", e);
            return false;
        }
    }
    
    /**
     * Carga una partida guardada.
     * 
     * @param saveId ID de la partida guardada
     * @return true si se cargó correctamente, false en caso contrario
     */
    public boolean loadGame(int saveId) {
        try {
            // Cargar partida
            SavedGame game = savedGameDAO.findById(saveId);
            if (game == null) {
                return false;
            }
            
            // Actualizar estado
            this.currentGame = game;
            
            // Cargar datos relacionados
            loadGameData();
            
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar partida", e);
            return false;
        }
    }
    
    /**
     * Obtiene el mapa actual.
     * @return Mapa actual
     */
    public JPanel getMapOverview() {
        return mapaActual;
    }
    
    /**
     * Establece el mapa actual.
     * @param mapOverview Mapa actual
     */
    public void setMapOverview(JPanel mapOverview) {
        this.mapaActual = mapOverview;
    }
    
    /**
     * Obtiene los datos de combate del jugador.
     * @return Datos de combate del jugador
     */
    public PlayerCombatData getPlayerCombatData() {
        return jugadorActual;
    }
    
    /**
     * Establece los datos de combate del jugador.
     * @param playerCombatData Datos de combate del jugador
     */
    public void setPlayerCombatData(PlayerCombatData playerCombatData) {
        this.jugadorActual = playerCombatData;
    }
    
    /**
     * Asigna objetos iniciales al jugador según su clase.
     */
    public void asignarObjetosIniciales() {
        if (jugadorActual == null) {
            return;
        }
        
        String clase = jugadorActual.getClase();
        
        // Objetos comunes para todas las clases
        InventoryItem pocionVida = new InventoryItem();
        pocionVida.setSaveId(currentGame != null ? currentGame.getId() : 0);
        pocionVida.setItemId(1); // ID de poción de vida
        pocionVida.setQuantity(5);
        pocionVida.setEquipped(false);
        inventory.add(pocionVida);
        
        InventoryItem pocionMana = new InventoryItem();
        pocionMana.setSaveId(currentGame != null ? currentGame.getId() : 0);
        pocionMana.setItemId(2); // ID de poción de mana
        pocionMana.setQuantity(3);
        pocionMana.setEquipped(false);
        inventory.add(pocionMana);
        
        // Objetos específicos según la clase
        switch (clase) {
            case "Ladrón":
                InventoryItem daga = new InventoryItem();
                daga.setSaveId(currentGame != null ? currentGame.getId() : 0);
                daga.setItemId(10); // ID de daga
                daga.setQuantity(1);
                daga.setEquipped(true);
                inventory.add(daga);
                
                InventoryItem armaduraLigera = new InventoryItem();
                armaduraLigera.setSaveId(currentGame != null ? currentGame.getId() : 0);
                armaduraLigera.setItemId(20); // ID de armadura ligera
                armaduraLigera.setQuantity(1);
                armaduraLigera.setEquipped(true);
                inventory.add(armaduraLigera);
                
                InventoryItem bombasHumo = new InventoryItem();
                bombasHumo.setSaveId(currentGame != null ? currentGame.getId() : 0);
                bombasHumo.setItemId(30); // ID de bombas de humo
                bombasHumo.setQuantity(10);
                bombasHumo.setEquipped(false);
                inventory.add(bombasHumo);
                break;
                
            case "Caballero":
                InventoryItem espada = new InventoryItem();
                espada.setSaveId(currentGame != null ? currentGame.getId() : 0);
                espada.setItemId(11); // ID de espada
                espada.setQuantity(1);
                espada.setEquipped(true);
                inventory.add(espada);
                
                InventoryItem armaduraPesada = new InventoryItem();
                armaduraPesada.setSaveId(currentGame != null ? currentGame.getId() : 0);
                armaduraPesada.setItemId(21); // ID de armadura pesada
                armaduraPesada.setQuantity(1);
                armaduraPesada.setEquipped(true);
                inventory.add(armaduraPesada);
                
                InventoryItem escudo = new InventoryItem();
                escudo.setSaveId(currentGame != null ? currentGame.getId() : 0);
                escudo.setItemId(31); // ID de escudo
                escudo.setQuantity(1);
                escudo.setEquipped(false);
                inventory.add(escudo);
                break;
                
            case "Clérigo":
                InventoryItem baculo = new InventoryItem();
                baculo.setSaveId(currentGame != null ? currentGame.getId() : 0);
                baculo.setItemId(12); // ID de báculo
                baculo.setQuantity(1);
                baculo.setEquipped(true);
                inventory.add(baculo);
                
                InventoryItem tunica = new InventoryItem();
                tunica.setSaveId(currentGame != null ? currentGame.getId() : 0);
                tunica.setItemId(22); // ID de túnica
                tunica.setQuantity(1);
                tunica.setEquipped(true);
                inventory.add(tunica);
                
                InventoryItem pergaminoCuracion = new InventoryItem();
                pergaminoCuracion.setSaveId(currentGame != null ? currentGame.getId() : 0);
                pergaminoCuracion.setItemId(32); // ID de pergamino de curación
                pergaminoCuracion.setQuantity(5);
                pergaminoCuracion.setEquipped(false);
                inventory.add(pergaminoCuracion);
                break;
        }
        
        // Si hay una partida guardada, guardar los objetos en la base de datos
        if (currentGame != null && currentGame.getId() > 0) {
            for (InventoryItem item : inventory) {
                try {
                    inventoryItemDAO.save(item);
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Error al guardar ítem inicial", e);
                }
            }
        }
    }
}