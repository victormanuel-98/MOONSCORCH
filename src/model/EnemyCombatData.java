package model;

import java.util.Random;

public class EnemyCombatData {

    private final String nombre;
    private int nivel;
    private int[] stats; // ATK, DEF, EVA, HP, LUK
    private int hpActual;

    public EnemyCombatData(String nombre, int nivel, int[] stats) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.stats = stats;
        this.hpActual = stats[3];
    }

    public int getATK() {
        return stats[0];
    }

    public int getDEF() {
        return stats[1];
    }

    public int getEVA() {
        return stats[2];
    }

    public int getHPMaximo() {
        return stats[3];
    }

    public int getHPActual() {
        return hpActual;
    }

    public int getLUK() {
        return stats[4];
    }

    public boolean estaVivo() {
        return hpActual > 0;
    }

    public void recibirDanio(int cantidad) {
        hpActual = Math.max(0, hpActual - cantidad);
    }

    public void curar(int cantidad) {
        hpActual = Math.min(getHPMaximo(), hpActual + cantidad);
    }

    public String getNombre() {
        return nombre;
    }
}
