package model;

import java.util.HashMap;
import java.util.Map;

public class PlayerCombatData {

    public final String nombre;
    public final String clase;
    public int nivel;
    public int[] stats;               // ATK, DEF, EVA, HP, MP=0, LUK
    private int hpActual;
    private int puntosEspecial;

    private final Map<String,Integer> inventario = new HashMap<>();

    // Experiencia y progresión
    private int experiencia = 0;
    private final int EXP_POR_NIVEL = 100;

    public PlayerCombatData(String nombre,String clase,int nivel,int[] stats){
        this.nombre = nombre;
        this.clase  = clase;
        this.nivel  = nivel;
        this.stats  = stats;
        this.hpActual = stats[3];
    }

    /* ---------- Vida / Daño ---------- */
    public void recibirDanio(int d){ hpActual = Math.max(0,hpActual-d); }
    public void curar(int c){ hpActual = Math.min(getHPMaximo(), hpActual + c); }
    public int  getHPMaximo(){ return stats[3]; }
    public int  getHPActual(){ return hpActual; }
    public boolean estaVivo(){ return hpActual>0; }

    /* ---------- Stats básicos ---------- */
    public int getATK(){ return stats[0]; }
    public int getDEF(){ return stats[1]; }
    public int getEVA(){ return stats[2]; }
    public int getLUK(){ return stats[5]; }

    /* ---------- Especial ---------- */
    public int  getPuntosEspecial(){ return puntosEspecial; }
    public void cargarEspecial(){ puntosEspecial=Math.min(2,puntosEspecial+1); }
    public void usarEspecial(int coste){ puntosEspecial=Math.max(0,puntosEspecial-coste); }

    /* ---------- Inventario ---------- */
    public Map<String,Integer> getInventario(){ return inventario; }

    public void agregarObjeto(String nombre,int cant){
        if(nombre==null||nombre.isBlank()||cant<=0) return;
        inventario.put(nombre, inventario.getOrDefault(nombre,0)+cant);
    }

    /** Reduce cantidad o elimina el objeto si queda 0 */
    public void reducirCantidadObjeto(String nombre){
        inventario.computeIfPresent(nombre,(k,v)-> v>1? v-1 : null);
    }

    /** Elimina por completo la entrada del inventario */
    public void eliminarObjeto(String nombre){
        inventario.remove(nombre);
    }

    public void usarObjeto(String nombre){
        switch (nombre.toUpperCase()){
            case "POCION DE VIDA" -> curar((int)(getHPMaximo()*0.3));
        }
        reducirCantidadObjeto(nombre);
    }

    /* ---------- Experiencia ---------- */
    public void ganarExperiencia(int cantidad) {
        experiencia += cantidad;
        while (experiencia >= EXP_POR_NIVEL) {
            experiencia -= EXP_POR_NIVEL;
            nivel++;
            stats[3] += 10; // HP Max +10
            stats[1] += 1;  // DEF +1
        }
    }

    public int getExperiencia() {
        return experiencia;
    }

    public int getNivel() {
        return nivel;
    }

    /* ---------- Restaurar vida al máximo ---------- */
    public void restaurarVidaTotal() {
        this.hpActual = getHPMaximo();
    }
}
