package model;

import view.MapOverview;

public class GameState {

    public static PlayerCombatData jugadorActual;

    // Mantiene la instancia del mapa actual para evitar reinicios
    public static MapOverview mapaActual;

    // En el futuro podrías tener:
    // public static Inventario inventario;
    // public static int pisoActual;
}