package controller;

import java.sql.Connection;

public class PruebaConexion {
    public static void main(String[] args) {
        // Llamamos a la clase ConexionBD para probar la conexión
        Connection conexion = ConexionBD.conectar();
        
        if (conexion != null) {
            System.out.println("Conexión exitosa.");
        } else {
            System.out.println("Error al conectar a la base de datos.");
        }
    }
}
