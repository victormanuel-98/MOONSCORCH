package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/rpg_victor";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "guerragalaxias";

    public static Connection conectar()  {
        try {
        	
        	// Cargamos el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("✅ Conexión exitosa a la base de datos.");
            return conexion;
        }  
        catch(ClassNotFoundException | SQLException  e)
        {
            System.err.println("❌ Error al conectar: " + e.getMessage());
            return null;
        }
    }
}
