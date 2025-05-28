package model;

import java.util.HashMap;
import java.util.Map;

public class Jugador {
    public String nombre;
    public String clase;
    public int nivel;
    private int hpMaximo;
    private int hpActual;
    private int ataque;
    private int defensa;
    private Map<String, Integer> inventario;
    
    public Jugador(String nombre, String clase) {
        this.nombre = nombre;
        this.clase = clase;
        this.nivel = 1;
        this.inventario = new HashMap<>();
        
        // Establecer estadísticas base según la clase
        switch (clase) {
            case "Ladrón":
                this.hpMaximo = 100;
                this.ataque = 15;
                this.defensa = 5;
                break;
            case "Caballero":
                this.hpMaximo = 100;
                this.ataque = 10;
                this.defensa = 10;
                break;
            case "Clérigo":
                this.hpMaximo = 1;
                this.ataque = 1;
                this.defensa = 1;
                break;
            default:
                this.hpMaximo = 100;
                this.ataque = 10;
                this.defensa = 5;
                break;
        }
        
        this.hpActual = this.hpMaximo;
    }
    
    public Jugador(String nombre, String clase, int nivel, int hpMaximo, int hpActual, int ataque, int defensa) {
        this.nombre = nombre;
        this.clase = clase;
        this.nivel = nivel;
        this.hpMaximo = hpMaximo;
        this.hpActual = hpActual;
        this.ataque = ataque;
        this.defensa = defensa;
        this.inventario = new HashMap<>();
    }
    
    public int getHPMaximo() {
        return hpMaximo;
    }
    
    public void setHPMaximo(int hpMaximo) {
        this.hpMaximo = hpMaximo;
    }
    
    public int getHPActual() {
        return hpActual;
    }
    
    public void setHPActual(int hpActual) {
        this.hpActual = hpActual;
    }
    
    public int getAtaque() {
        return ataque;
    }
    
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }
    
    public int getDefensa() {
        return defensa;
    }
    
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }
    
    public void agregarObjeto(String objeto) {
        inventario.put(objeto, inventario.getOrDefault(objeto, 0) + 1);
    }
    
    public void agregarObjeto(String objeto, int cantidad) {
        inventario.put(objeto, inventario.getOrDefault(objeto, 0) + cantidad);
    }
    
    public boolean usarObjeto(String objeto) {
        if (inventario.containsKey(objeto) && inventario.get(objeto) > 0) {
            inventario.put(objeto, inventario.get(objeto) - 1);
            if (inventario.get(objeto) <= 0) {
                inventario.remove(objeto);
            }
            return true;
        }
        return false;
    }
    
    public Map<String, Integer> getInventario() {
        return new HashMap<>(inventario);
    }
    
    public void subirNivel() {
        this.nivel++;
        
        // Aumentar estadísticas según la clase
        switch (this.clase) {
            case "Ladrón":
                this.hpMaximo += 8;
                this.ataque += 3;
                this.defensa += 1;
                break;
            case "Caballero":
                this.hpMaximo += 12;
                this.ataque += 2;
                this.defensa += 2;
                break;
            case "Clérigo":
                this.hpMaximo += 10;
                this.ataque += 1;
                this.defensa += 1;
                break;
            default:
                this.hpMaximo += 10;
                this.ataque += 2;
                this.defensa += 1;
                break;
        }
        
        this.hpActual = this.hpMaximo; // Recuperar toda la vida al subir de nivel
    }
    
    public void curar(int cantidad) {
        this.hpActual = Math.min(this.hpActual + cantidad, this.hpMaximo);
    }
    
    public void recibirDaño(int cantidad) {
        int dañoReal = Math.max(1, cantidad - this.defensa);
        this.hpActual = Math.max(0, this.hpActual - dañoReal);
    }
    
    public boolean estaVivo() {
        return this.hpActual > 0;
    }
    
    public int calcularDaño() {
        // Daño base + variación aleatoria
        return this.ataque + (int)(Math.random() * 5);
    }

	public Object getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Jugador getCharacterClass() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getGold() {
		// TODO Auto-generated method stub
		return null;
	}
}