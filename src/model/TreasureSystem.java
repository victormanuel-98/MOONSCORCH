
package model;
import java.util.ArrayList;
import java.util.List;

/**
 * Sistema de tesoros para el juego.
 */
public class TreasureSystem {
    private GameState gameState;
    private DiceRoller diceRoller;
    
    /**
     * Constructor por defecto.
     */
    public TreasureSystem() {
        this.diceRoller = new DiceRoller();
    }
    
    /**
     * Constructor con parámetros principales.
     */
    public TreasureSystem(GameState gameState) {
        this.gameState = gameState;
        this.diceRoller = new DiceRoller();
    }
    
    // Getters y Setters
    public GameState getGameState() {
        return gameState;
    }
    
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    
    public DiceRoller getDiceRoller() {
        return diceRoller;
    }
    
    public void setDiceRoller(DiceRoller diceRoller) {
        this.diceRoller = diceRoller;
    }
    
    /**
     * Abre un tesoro.
     * @param treasure Tesoro a abrir
     * @return Lista de objetos obtenidos
     */
    public List<GameItem> openTreasure(Treasure treasure) {
        List<GameItem> items = new ArrayList<>();
        
        // Comprobar si el tesoro está bloqueado
        if (treasure.isLocked()) {
            // Comprobar si el jugador tiene el objeto requerido
            boolean hasKey = false;
            for (InventoryItem item : gameState.getInventory()) {
                if (item.getGameItemId() == treasure.getRequiredItemId() && item.getQuantity() > 0) {
                    hasKey = true;
                    // Consumir la llave
                    item.decreaseQuantity(1);
                    break;
                }
            }
            
            if (!hasKey) {
                // No se puede abrir el tesoro
                return items;
            }
            
            // Desbloquear el tesoro
            treasure.unlock();
        }
        
        // Añadir oro al jugador
        SavedGame player = gameState.getCurrentGame();
        player.setGold(player.getGold() + treasure.getGoldReward());
        
        // Añadir objetos al inventario
        // En una implementación real, se buscarían los objetos por ID en la base de datos
        // Aquí solo se simula la obtención de objetos
        
        return items;
    }
    
    /**
     * Genera un tesoro aleatorio según el nivel del jugador.
     * @param level Nivel del jugador
     * @param nodeId ID del nodo donde se genera el tesoro
     * @return Tesoro generado
     */
    public Treasure generateRandomTreasure(int level, int nodeId) {
        // Determinar tipo de tesoro según nivel y tirada de dados
        int roll = diceRoller.rollDice(100);
        Treasure.TreasureType type;
        
        if (roll <= 5 + level) {
            type = Treasure.TreasureType.LEGENDARY;
        } else if (roll <= 15 + level * 2) {
            type = Treasure.TreasureType.EPIC;
        } else if (roll <= 40 + level * 3) {
            type = Treasure.TreasureType.RARE;
        } else {
            type = Treasure.TreasureType.COMMON;
        }
        
        // Calcular oro según tipo y nivel
        int goldBase = switch (type) {
            case COMMON -> 10;
            case RARE -> 25;
            case EPIC -> 50;
            case LEGENDARY -> 100;
            default -> 5;
        };
        
        int gold = goldBase * level * (diceRoller.rollDice(4) + 1);
        
        // Crear tesoro
        Treasure treasure = new Treasure(
                "Tesoro " + type.getDisplayName(),
                "Un tesoro de tipo " + type.getDisplayName() + " encontrado en tu aventura.",
                type,
                nodeId,
                gold
        );
        
        // Determinar si está bloqueado
        if (diceRoller.rollDice(100) <= 20) {
            treasure.setLocked(true);
            // En una implementación real, se asignaría un ID de llave válido
            treasure.setRequiredItemId(1);
        }
        
        // Añadir objetos según tipo
        // En una implementación real, se seleccionarían objetos adecuados de la base de datos
        
        return treasure;
    }
    
    /**
     * Comprueba si un nodo contiene un tesoro.
     * @param nodeId ID del nodo a comprobar
     * @return true si hay un tesoro, false en caso contrario
     */
    public boolean nodeHasTreasure(int nodeId) {
        // Implementación básica
        // En una implementación real, se consultaría la base de datos
        MapNode node = gameState.getMapNodes().get(nodeId);
        return node != null && node.getType() == MapNode.NodeType.TREASURE;
    }
}
