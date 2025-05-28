package model;

import view.MapOverview;

import java.time.LocalDateTime;
import java.util.Map;

import javax.swing.JPanel;

public class GameState {

    public static PlayerCombatData jugadorActual;

    // Mantiene la instancia del mapa actual para evitar reinicios
    
    public static MapOverview mapaActual;

    public static EnemyCombatData enemigoActual;

    // Número del piso actual que se está jugando (por defecto 1)
    
    public static int numeroPisoActual = 1;
    
    // Variables para la base de datos
    
    public static int idUsuarioActual = -1;
    public static int idPersonajeActual = -1;
    public static String tipoEnemigoActual;
    
    // Nombre del mapa y zona actual para guardar progreso
    
    public static String nombreMapaActual = "";
    public static String nombreZonaActual = "";
    public static int nodoActual = 0;

    // Simular objetos iniciales al comenzar la partida
    
    public static void asignarObjetosIniciales() {
        if (jugadorActual != null) {
            jugadorActual.agregarObjeto("Poción de Vida", 1); 
        }
    }

    public static boolean hayPartidaGuardada() {
        
        return idUsuarioActual > 0 && idPersonajeActual > 0;
    }

    public static EnemyCombatData generarEnemigo() {
        String nombre = "Enemigo"; // Valor por defecto

        switch (tipoEnemigoActual) {
            case "ENEMIGO":
                nombre = "Hombre calcinado";
                break;
            case "ENEMIGO2":
                nombre = "Masa calcinada";
                break;
            case "ENEMIGO3":
                nombre = "Esqueleto";
                break;
            case "ENEMIGO4":
                nombre = "Cuervo Desollador";
                break;
            case "ENEMIGO5":
                nombre = "Eco de Gro-Goroth";
                break;
            case "ENEMIGO6":
                nombre = "Bestia lunar";
                break;
            case "JEFE FINAL":
                nombre = "Rey Amarillo";
                break;
            default:
                nombre = "Espectro";
        }

        // Puedes ajustar las stats si quieres que varíen según el enemigo
        return new EnemyCombatData(nombre, 1, new int[]{5, 3, 4, 50, 1});
    }


    // Simula guardar el progreso actual del jugador al morir
    
    public static void guardarProgresoSimulado() {
        if (jugadorActual != null) {
            System.out.println("Guardando progreso simulado...");
            System.out.println("Nombre: " + jugadorActual.nombre);
            System.out.println("Clase: " + jugadorActual.clase);
            System.out.println("Nivel: " + jugadorActual.nivel);
            System.out.println("Stats: ");
            int[] stats = jugadorActual.stats;
            String[] statNames = {"ATK", "DEF", "EVA", "HP", "MP", "LUK"};
            for (int i = 0; i < stats.length; i++) {
                System.out.println("  " + statNames[i] + ": " + stats[i]);
            }
            System.out.println("Objetos: " + jugadorActual.getInventario());
            
            // Si hay un personaje activo en la base de datos, guardar el progreso real
            
            if (idPersonajeActual > 0) {
                guardarProgresoReal();
            }
        }
    }
    
    // Guarda el progreso real en la base de datos
    
    public static void guardarProgresoReal() {
        if (idPersonajeActual > 0 && jugadorActual != null) {
           
            Jugador jugador = new Jugador(
                jugadorActual.nombre,
                jugadorActual.clase,
                jugadorActual.nivel,
                jugadorActual.stats[3], 
                jugadorActual.stats[3], 
                jugadorActual.stats[0], 
                jugadorActual.stats[1]  
            );
            
            // Copiar inventario
            for (Map.Entry<String, Integer> entry : jugadorActual.getInventario().entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    jugador.agregarObjeto(entry.getKey());
                }
            }
            
            // Guardar el personaje
            model.database.DatabaseManager.guardarPersonaje(idUsuarioActual, jugador);
            
            // Guardar progreso del mapa
            if (!nombreMapaActual.isEmpty()) {
                model.database.DatabaseManager.guardarProgreso(
                    idPersonajeActual,
                    nombreMapaActual,
                    nodoActual,
                    nombreZonaActual,
                    false // completado
                );
            }
        }
    }
    
    // Método para convertir PlayerCombatData a Jugador
    public static Jugador convertirAJugador(PlayerCombatData playerData) {
        if (playerData == null) return null;
        
        Jugador jugador = new Jugador(
            playerData.nombre,
            playerData.clase,
            playerData.nivel,
            playerData.stats[3], // HP máximo
            playerData.stats[3], // HP actual
            playerData.stats[0], // ATK
            playerData.stats[1]  // DEF
        );
        
        // Copiar inventario
        for (Map.Entry<String, Integer> entry : playerData.getInventario().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                jugador.agregarObjeto(entry.getKey());
            }
        }
        
        return jugador;
    }
    
 // Método para convertir Jugador a PlayerCombatData
    public static PlayerCombatData convertirAPlayerCombatData(Jugador jugador) {
        if (jugador == null) return null;
        
        
        int[] stats = new int[6];
        stats[0] = jugador.getAtaque();    
        stats[1] = jugador.getDefensa();   
        stats[2] = 5;                      
        stats[3] = jugador.getHPMaximo();  
        stats[4] = 10;                     
        stats[5] = 5;                      
        
        
        PlayerCombatData playerData = new PlayerCombatData(
            jugador.nombre,
            jugador.clase,
            jugador.nivel,
            stats
        );
        
        // Copiar inventario
        
        for (Map.Entry<String, Integer> entry : jugador.getInventario().entrySet()) {
            playerData.agregarObjeto(entry.getKey(), entry.getValue());
        }
        
        return playerData;
    }

	public Jugador getJugador() {
		// TODO Auto-generated method stub
		return null;
	}

	public LocalDateTime getGameStartTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getEnemiesDefeated() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getTreasuresFound() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPotionsUsed() {
		// TODO Auto-generated method stub
		return null;
	}

	public JPanel getMapOverview() {
		// TODO Auto-generated method stub
		return null;
	}
}