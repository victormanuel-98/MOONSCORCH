
package model;

import java.util.Random;

/**
 * Sistema de dados para el juego.
 */
public class DiceRoller {
    private Random random;
    
    /**
     * Constructor por defecto.
     */
    public DiceRoller() {
        this.random = new Random();
    }
    
    /**
     * Constructor con semilla específica (útil para pruebas).
     */
    public DiceRoller(long seed) {
        this.random = new Random(seed);
    }
    
    /**
     * Lanza un dado de n caras.
     * @param sides Número de caras del dado
     * @return Resultado de la tirada (1-sides)
     */
    public int rollDice(int sides) {
        return random.nextInt(sides) + 1;
    }
    
    /**
     * Lanza múltiples dados de n caras.
     * @param numDice Número de dados a lanzar
     * @param sides Número de caras de cada dado
     * @return Suma de los resultados
     */
    public int rollMultipleDice(int numDice, int sides) {
        int total = 0;
        for (int i = 0; i < numDice; i++) {
            total += rollDice(sides);
        }
        return total;
    }
    
    /**
     * Lanza dados con notación estándar (ej: "2d6+3").
     * @param notation Notación de dados (XdY+Z)
     * @return Resultado de la tirada
     */
    public int rollDiceNotation(String notation) {
        // Implementación básica para notación simple
        String[] parts = notation.split("d|\\+|-");
        
        int numDice = Integer.parseInt(parts[0]);
        int sides = Integer.parseInt(parts[1]);
        int modifier = 0;
        
        if (notation.contains("+")) {
            modifier = Integer.parseInt(parts[2]);
        } else if (notation.contains("-")) {
            modifier = -Integer.parseInt(parts[2]);
        }
        
        return rollMultipleDice(numDice, sides) + modifier;
    }
    
    /**
     * Realiza una tirada de habilidad (d20 + modificador).
     * @param modifier Modificador a aplicar
     * @return Resultado de la tirada
     */
    public int rollSkillCheck(int modifier) {
        return rollDice(20) + modifier;
    }
    
    /**
     * Realiza una tirada con ventaja (lanza 2d20 y toma el mayor).
     * @param modifier Modificador a aplicar
     * @return Resultado de la tirada
     */
    public int rollWithAdvantage(int modifier) {
        int roll1 = rollDice(20);
        int roll2 = rollDice(20);
        return Math.max(roll1, roll2) + modifier;
    }
    
    /**
     * Realiza una tirada con desventaja (lanza 2d20 y toma el menor).
     * @param modifier Modificador a aplicar
     * @return Resultado de la tirada
     */
    public int rollWithDisadvantage(int modifier) {
        int roll1 = rollDice(20);
        int roll2 = rollDice(20);
        return Math.min(roll1, roll2) + modifier;
    }
}
