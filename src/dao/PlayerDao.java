package dao;

import model.PlayerCombatData;

public class PlayerDao {

    private static PlayerCombatData jugadorGuardado;

    
     
// Guarda el jugador actual en la "base de datos"
    
  public static void guardarJugador(PlayerCombatData jugador) {
      jugadorGuardado = jugador;
      System.out.println("[PlayerDAO] Jugador guardado correctamente:");
      System.out.println(jugador);}

    
     
// Carga al jugador por nombre
  
  public static PlayerCombatData cargarJugadorPorNombre(String nombre) {
      if (jugadorGuardado != null && jugadorGuardado.nombre.equalsIgnoreCase(nombre)) {
          System.out.println("[PlayerDAO] Jugador encontrado: " + nombre);
          return jugadorGuardado;} else {
          System.out.println("[PlayerDAO] No se encontró jugador con nombre: " + nombre);
          return null;}}

     
// Elimina el jugador simulado.
  
  public static void borrarJugador() {
      jugadorGuardado = null;
      System.out.println("[PlayerDAO] Jugador eliminado de la memoria simulada.");}



	public static Object getGold() {
		// TODO Auto-generated method stub
		return null;
	}
}