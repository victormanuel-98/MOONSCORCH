package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa los datos del jugador necesarios para el combate.
 */
public class PlayerCombatData {

    private String name; // Cambiado de nombre a name para compatibilidad
    private String clase;
    private int level; // Cambiado de nivel a level para compatibilidad
    private int[] stats; // Orden: ATK, DEF, EVA, HP, MP, LUK
    
    // Campos adicionales para compatibilidad con GameState
    private int currentHp;
    private int maxHp;
    private int currentMp;
    private int maxMp;
    private int atk;
    private int def;
    private int eva;
    private int luk;
    private boolean isDefending;
    private boolean isCharging;

    // Inventario simulado con cantidades
    private final Map<String, Integer> inventario = new HashMap<>();

    /**
     * Constructor por defecto para compatibilidad con GameState.
     */
    public PlayerCombatData() {
        this.stats = new int[6]; // Inicializar array de stats
        this.isDefending = false;
        this.isCharging = false;
    }

    /**
     * Constructor original.
     */
    public PlayerCombatData(String nombre, String clase, int nivel, int[] stats) {
        this.name = nombre;
        this.clase = clase;
        this.level = nivel;
        this.stats = stats;
        
        // Inicializar campos adicionales
        this.currentHp = stats[3];
        this.maxHp = stats[3];
        this.currentMp = stats[4];
        this.maxMp = stats[4];
        this.atk = stats[0];
        this.def = stats[1];
        this.eva = stats[2];
        this.luk = stats[5];
        this.isDefending = false;
        this.isCharging = false;
    }

    // Getters y setters originales
    public String getNombre() { return name; }
    public void setNombre(String nombre) { this.name = nombre; }
    
    public String getClase() { return clase; }
    public void setClase(String clase) { this.clase = clase; }
    
    public int getNivel() { return level; }
    public void setNivel(int nivel) { this.level = nivel; }
    
    public int[] getStats() { return stats; }
    public void setStats(int[] stats) { this.stats = stats; }
    
    // Getters de estadísticas originales
    public int getATK() { return stats[0]; }
    public int getDEF() { return stats[1]; }
    public int getEVA() { return stats[2]; }
    public int getHP()  { return stats[3]; }
    public int getMP()  { return stats[4]; }
    public int getLUK() { return stats[5]; }

    // Modificar suerte
    public void incrementarLUK(int cantidad) {
        stats[5] += cantidad;
        this.luk += cantidad; // Actualizar también el campo luk
    }

    // Inventario
    public Map<String, Integer> getInventario() {
        return inventario;
    }

    public void agregarObjeto(String item) {
        inventario.put(item, inventario.getOrDefault(item, 0) + 1);
    }

    public void eliminarObjeto(String item) {
        if (inventario.containsKey(item)) {
            int cantidad = inventario.get(item);
            if (cantidad <= 1) {
                inventario.remove(item);
            } else {
                inventario.put(item, cantidad - 1);
            }
        }
    }
    
    // Métodos adicionales para compatibilidad con GameState
    
    /**
     * Obtiene el nombre del jugador.
     * @return Nombre del jugador
     */
    public String getName() {
        return name;
    }
    
    /**
     * Establece el nombre del jugador.
     * @param name Nombre del jugador
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Obtiene el nivel del jugador.
     * @return Nivel del jugador
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * Establece el nivel del jugador.
     * @param level Nivel del jugador
     */
    public void setLevel(int level) {
        this.level = level;
    }
    
    /**
     * Obtiene el HP actual del jugador.
     * @return HP actual
     */
    public int getCurrentHp() {
        return currentHp;
    }
    
    /**
     * Establece el HP actual del jugador.
     * @param currentHp HP actual
     */
    public void setCurrentHp(int currentHp) {
        this.currentHp = Math.max(0, Math.min(currentHp, maxHp));
        // Actualizar también el array de stats
        if (stats != null && stats.length >= 4) {
            stats[3] = this.currentHp;
        }
    }
    
    /**
     * Obtiene el HP máximo del jugador.
     * @return HP máximo
     */
    public int getMaxHp() {
        return maxHp;
    }
    
    /**
     * Establece el HP máximo del jugador.
     * @param maxHp HP máximo
     */
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
        // Asegurarse de que el HP actual no exceda el máximo
        if (currentHp > maxHp) {
            currentHp = maxHp;
        }
    }
    
    /**
     * Obtiene el MP actual del jugador.
     * @return MP actual
     */
    public int getCurrentMp() {
        return currentMp;
    }
    
    /**
     * Establece el MP actual del jugador.
     * @param currentMp MP actual
     */
    public void setCurrentMp(int currentMp) {
        this.currentMp = Math.max(0, Math.min(currentMp, maxMp));
        // Actualizar también el array de stats
        if (stats != null && stats.length >= 5) {
            stats[4] = this.currentMp;
        }
    }
    
    /**
     * Obtiene el MP máximo del jugador.
     * @return MP máximo
     */
    public int getMaxMp() {
        return maxMp;
    }
    
    /**
     * Establece el MP máximo del jugador.
     * @param maxMp MP máximo
     */
    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
        // Asegurarse de que el MP actual no exceda el máximo
        if (currentMp > maxMp) {
            currentMp = maxMp;
        }
    }
    
    /**
     * Obtiene el ataque del jugador.
     * @return Ataque
     */
    public int getAtk() {
        return atk;
    }
    
    /**
     * Establece el ataque del jugador.
     * @param atk Ataque
     */
    public void setAtk(int atk) {
        this.atk = Math.max(1, atk); // Asegurar que el ataque no sea menor que 1
        // Actualizar también el array de stats
        if (stats != null && stats.length >= 1) {
            stats[0] = this.atk;
        }
    }
    
    /**
     * Obtiene la defensa del jugador.
     * @return Defensa
     */
    public int getDef() {
        return def;
    }
    
    /**
     * Establece la defensa del jugador.
     * @param def Defensa
     */
    public void setDef(int def) {
        this.def = Math.max(0, def); // Asegurar que la defensa no sea negativa
        // Actualizar también el array de stats
        if (stats != null && stats.length >= 2) {
            stats[1] = this.def;
        }
    }
    
    /**
     * Obtiene la evasión del jugador.
     * @return Evasión
     */
    public int getEva() {
        return eva;
    }
    
    /**
     * Establece la evasión del jugador.
     * @param eva Evasión
     */
    public void setEva(int eva) {
        this.eva = Math.max(0, Math.min(eva, 95)); // Limitar la evasión entre 0 y 95%
        // Actualizar también el array de stats
        if (stats != null && stats.length >= 3) {
            stats[2] = this.eva;
        }
    }
    
    /**
     * Obtiene la suerte del jugador.
     * @return Suerte
     */
    public int getLuk() {
        return luk;
    }
    
    /**
     * Establece la suerte del jugador.
     * @param luk Suerte
     */
    public void setLuk(int luk) {
        this.luk = Math.max(1, luk); // Asegurar que la suerte no sea menor que 1
        // Actualizar también el array de stats
        if (stats != null && stats.length >= 6) {
            stats[5] = this.luk;
        }
    }
    
    /**
     * Verifica si el jugador está defendiendo.
     * @return true si está defendiendo, false en caso contrario
     */
    public boolean isDefending() {
        return isDefending;
    }
    
    /**
     * Establece si el jugador está defendiendo.
     * @param defending true si está defendiendo, false en caso contrario
     */
    public void setDefending(boolean defending) {
        isDefending = defending;
    }
    
    /**
     * Verifica si el jugador está cargando un ataque.
     * @return true si está cargando, false en caso contrario
     */
    public boolean isCharging() {
        return isCharging;
    }
    
    /**
     * Establece si el jugador está cargando un ataque.
     * @param charging true si está cargando, false en caso contrario
     */
    public void setCharging(boolean charging) {
        isCharging = charging;
    }
    
    /**
     * Verifica si el jugador está vivo.
     * @return true si el jugador tiene HP mayor que 0, false en caso contrario
     */
    public boolean isAlive() {
        return currentHp > 0;
    }
    
    /**
     * Calcula el porcentaje de HP actual.
     * @return Porcentaje de HP actual (0-100)
     */
    public int getHpPercentage() {
        return (int) ((double) currentHp / maxHp * 100);
    }
    
    /**
     * Calcula el porcentaje de MP actual.
     * @return Porcentaje de MP actual (0-100)
     */
    public int getMpPercentage() {
        return (int) ((double) currentMp / maxMp * 100);
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + name + '\'' +
                ", clase='" + clase + '\'' +
                ", nivel=" + level +
                ", stats=[ATK=" + getATK() + ", DEF=" + getDEF() + ", EVA=" + getEVA() +
                ", HP=" + getCurrentHp() + "/" + getMaxHp() + 
                ", MP=" + getCurrentMp() + "/" + getMaxMp() + 
                ", LUK=" + getLUK() + "]" +
                ", inventario=" + inventario +
                '}';
    }
}