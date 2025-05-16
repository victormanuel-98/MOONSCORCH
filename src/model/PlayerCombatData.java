package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa los datos del jugador necesarios para el combate.
 */
public class PlayerCombatData {

    public String nombre;
    public String clase;
    public int nivel;
    public int[] stats; // Orden: ATK, DEF, EVA, HP, MP, LUK

    // Inventario simulado con cantidades
    private final Map<String, Integer> inventario = new HashMap<>();

    public PlayerCombatData(String nombre, String clase, int nivel, int[] stats) {
        this.nombre = nombre;
        this.clase = clase;
        this.nivel = nivel;
        this.stats = stats;
    }

    // Getters de estadísticas
    public int getATK() { return stats[0]; }
    public int getDEF() { return stats[1]; }
    public int getEVA() { return stats[2]; }
    public int getHP()  { return stats[3]; }
    public int getMP()  { return stats[4]; }
    public int getLUK() { return stats[5]; }

    // Modificar suerte
    public void incrementarLUK(int cantidad) {
        stats[5] += cantidad;
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

    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                ", clase='" + clase + '\'' +
                ", nivel=" + nivel +
                ", stats=[ATK=" + getATK() + ", DEF=" + getDEF() + ", EVA=" + getEVA() +
                ", HP=" + getHP() + ", MP=" + getMP() + ", LUK=" + getLUK() + "]" +
                ", inventario=" + inventario +
                '}';
    }
}
