package model;

/*Clase que representa los datos del jugador necesarios para el combate.*/

public class PlayerCombatData {

	public String nombre;
	public String clase;
	public int nivel;
	public int[] stats; // Orden: ATK, DEF, EVA, HP, MP, LUK

	public PlayerCombatData(String nombre, String clase, int nivel, int[] stats) {
		this.nombre = nombre;
		this.clase = clase;
		this.nivel = nivel;
		this.stats = stats;
	}

	// Métodos auxiliares (si los necesitas)
	public int getATK() {
		return stats[0];
	}

	public int getDEF() {
		return stats[1];
	}

	public int getEVA() {
		return stats[2];
	}

	public int getHP() {
		return stats[3];
	}

	public int getMP() {
		return stats[4];
	}

	public int getLUK() {
		return stats[5];
	}

	@Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + "''" +
                ", clase='" + clase + "''" +
                ", nivel=" + nivel +
                ", stats=[ATK=" + getATK() + ", DEF=" + getDEF() + ", EVA=" + getEVA() +
                ", HP=" + getHP() + ", MP=" + getMP() + ", LUK=" + getLUK() + "]" +
                '}';
    }
}
