package model;

import view.MapOverview;

public class GameState {

    public static PlayerCombatData jugadorActual;

    // Mantiene la instancia del mapa actual para evitar reinicios
    public static MapOverview mapaActual;

    // Simular objetos iniciales al comenzar la partida
    public static void asignarObjetosIniciales() {
        if (jugadorActual != null) {
            jugadorActual.agregarObjeto("Poción de Vida");
            jugadorActual.agregarObjeto("Poción de Maná");
        }
    }
}