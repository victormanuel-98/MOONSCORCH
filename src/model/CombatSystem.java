
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Sistema de combate por turnos con dados.
 */
public class CombatSystem {
    private GameState gameState;
    private List<Enemy> enemies;
    private int currentTurn;
    private boolean playerTurn;
    private DiceRoller diceRoller;
    
    /**
     * Constructor por defecto.
     */
    public CombatSystem() {
        this.enemies = new ArrayList<>();
        this.currentTurn = 0;
        this.playerTurn = true;
        this.diceRoller = new DiceRoller();
    }
    
    /**
     * Constructor con parámetros principales.
     */
    public CombatSystem(GameState gameState) {
        this.gameState = gameState;
        this.enemies = new ArrayList<>();
        this.currentTurn = 0;
        this.playerTurn = true;
        this.diceRoller = new DiceRoller();
    }
    
    // Getters y Setters
    public GameState getGameState() {
        return gameState;
    }
    
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    
    public List<Enemy> getEnemies() {
        return enemies;
    }
    
    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }
    
    public int getCurrentTurn() {
        return currentTurn;
    }
    
    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }
    
    public boolean isPlayerTurn() {
        return playerTurn;
    }
    
    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }
    
    public DiceRoller getDiceRoller() {
        return diceRoller;
    }
    
    public void setDiceRoller(DiceRoller diceRoller) {
        this.diceRoller = diceRoller;
    }
    
    /**
     * Inicia un combate con los enemigos especificados.
     * @param enemies Lista de enemigos
     */
    public void startCombat(List<Enemy> enemies) {
        this.enemies = enemies;
        this.currentTurn = 1;
        this.playerTurn = true;
    }
    
    /**
     * Realiza un ataque básico del jugador a un enemigo.
     * @param targetIndex Índice del enemigo objetivo
     * @return Daño causado
     */
    public int playerAttack(int targetIndex) {
        if (!playerTurn || targetIndex >= enemies.size()) {
            return 0;
        }
        
        SavedGame player = gameState.getCurrentGame();
        Enemy target = enemies.get(targetIndex);
        
        // Tirada de ataque (d20 + modificador de ATK)
        int attackRoll = diceRoller.rollDice(20) + player.getCurrentAtk();
        
        // Tirada de defensa (d20 + modificador de DEF)
        int defenseRoll = diceRoller.rollDice(20) + target.getDef();
        
        // Tirada de evasión (d100 vs probabilidad de evasión)
        int evasionRoll = diceRoller.rollDice(100);
        if (evasionRoll <= target.getEva()) {
            // Ataque evadido
            endPlayerTurn();
            return 0;
        }
        
        int damage = 0;
        if (attackRoll > defenseRoll) {
            // Éxito en el ataque
            damage = Math.max(1, player.getCurrentAtk() - target.getDef() / 2);
            
            // Crítico (d20 = 20 o tirada de suerte)
            if (diceRoller.rollDice(20) == 20 || diceRoller.rollDice(100) <= player.getCurrentLuk()) {
                damage *= 2;
            }
            
            // Aplicar daño
            target.setHp(Math.max(0, target.getHp() - damage));
        }
        
        endPlayerTurn();
        return damage;
    }
    
    /**
     * Realiza un ataque de un enemigo al jugador.
     * @param enemyIndex Índice del enemigo que ataca
     * @return Daño causado
     */
    public int enemyAttack(int enemyIndex) {
        if (playerTurn || enemyIndex >= enemies.size()) {
            return 0;
        }
        
        SavedGame player = gameState.getCurrentGame();
        Enemy attacker = enemies.get(enemyIndex);
        
        // Tirada de ataque (d20 + modificador de ATK)
        int attackRoll = diceRoller.rollDice(20) + attacker.getAtk();
        
        // Tirada de defensa (d20 + modificador de DEF)
        int defenseRoll = diceRoller.rollDice(20) + player.getCurrentDef();
        
        // Tirada de evasión (d100 vs probabilidad de evasión)
        int evasionRoll = diceRoller.rollDice(100);
        if (evasionRoll <= player.getCurrentEva()) {
            // Ataque evadido
            return 0;
        }
        
        int damage = 0;
        if (attackRoll > defenseRoll) {
            // Éxito en el ataque
            damage = Math.max(1, attacker.getAtk() - player.getCurrentDef() / 2);
            
            // Crítico (d20 = 20 o tirada de suerte)
            if (diceRoller.rollDice(20) == 20 || diceRoller.rollDice(100) <= attacker.getLuk()) {
                damage *= 2;
            }
            
            // Aplicar daño
            player.setCurrentHp(Math.max(0, player.getCurrentHp() - damage));
        }
        
        return damage;
    }
    
    /**
     * Usa una habilidad del jugador.
     * @param skillId ID de la habilidad
     * @param targetIndex Índice del objetivo (enemigo o jugador)
     * @return true si se usó correctamente, false en caso contrario
     */
    public boolean useSkill(int skillId, int targetIndex) {
        // Implementación básica
        // En una implementación real, se buscaría la habilidad por ID y se aplicarían sus efectos
        return false;
    }
    
    /**
     * Finaliza el turno del jugador.
     */
    public void endPlayerTurn() {
        playerTurn = false;
        
        // Comprobar si todos los enemigos están derrotados
        boolean allEnemiesDefeated = true;
        for (Enemy enemy : enemies) {
            if (enemy.getHp() > 0) {
                allEnemiesDefeated = false;
                break;
            }
        }
        
        if (allEnemiesDefeated) {
            endCombat(true);
        }
    }
    
    /**
     * Finaliza el turno de los enemigos.
     */
    public void endEnemyTurn() {
        playerTurn = true;
        currentTurn++;
        
        // Actualizar efectos de estado
        gameState.updateStatusEffects();
        
        // Comprobar si el jugador está derrotado
        if (gameState.getCurrentGame().getCurrentHp() <= 0) {
            endCombat(false);
        }
    }
    
    /**
     * Finaliza el combate.
     * @param victory true si el jugador ganó, false si perdió
     */
    public void endCombat(boolean victory) {
        if (victory) {
            // Calcular recompensas
            int totalExp = 0;
            int totalGold = 0;
            
            for (Enemy enemy : enemies) {
                totalExp += enemy.getExpReward();
                totalGold += enemy.getGoldReward();
            }
            
            // Aplicar recompensas
            SavedGame player = gameState.getCurrentGame();
            player.setCurrentExp(player.getCurrentExp() + totalExp);
            player.setGold(player.getGold() + totalGold);
            
            // Comprobar subida de nivel
            checkLevelUp();
            
            // Marcar el nodo actual como completado
            int currentNodeId = player.getCurrentMapNodeId();
            if (gameState.getMapProgress().containsKey(currentNodeId)) {
                gameState.getMapProgress().get(currentNodeId).clear();
            }
        } else {
            // Implementar lógica de derrota
        }
        
        // Limpiar estado de combate
        enemies.clear();
        currentTurn = 0;
    }
    
    /**
     * Comprueba si el jugador sube de nivel.
     */
    private void checkLevelUp() {
        SavedGame player = gameState.getCurrentGame();
        int currentLevel = player.getCurrentLevel();
        int currentExp = player.getCurrentExp();
        
        // Fórmula simple para el exp necesario para subir de nivel
        int expNeeded = currentLevel * 100;
        
        if (currentExp >= expNeeded) {
            // Subir de nivel
            player.setCurrentLevel(currentLevel + 1);
            player.setCurrentExp(currentExp - expNeeded);
            
            // Aumentar estadísticas
            player.setCurrentHp(player.getCurrentHp() + 5);
            player.setCurrentMp(player.getCurrentMp() + 3);
            player.setCurrentAtk(player.getCurrentAtk() + 1);
            player.setCurrentDef(player.getCurrentDef() + 1);
            player.setCurrentEva(player.getCurrentEva() + 1);
            player.setCurrentLuk(player.getCurrentLuk() + 1);
            
            // Comprobar si hay más subidas de nivel pendientes
            checkLevelUp();
        }
    }
}
