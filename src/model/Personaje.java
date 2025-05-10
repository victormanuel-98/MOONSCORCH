package model;

public class Personaje {
    private int id;
    private String nombre;
    private int nivel;
    private int salud;
    private int fuerza;

    // Constructor sin ID (para cuando insertas un nuevo personaje)
    public Personaje(String nombre, int nivel, int salud, int fuerza) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.salud = salud;
        this.fuerza = fuerza;
    }

    // Constructor con ID (para leer de la base de datos)
    public Personaje(int id, String nombre, int nivel, int salud, int fuerza) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
        this.salud = salud;
        this.fuerza = fuerza;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public int getSalud() { return salud; }
    public void setSalud(int salud) { this.salud = salud; }

    public int getFuerza() { return fuerza; }
    public void setFuerza(int fuerza) { this.fuerza = fuerza; }

    @Override
    public String toString() {
        return "Personaje{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nivel=" + nivel +
                ", salud=" + salud +
                ", fuerza=" + fuerza +
                '}';
    }
}
