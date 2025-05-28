package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD { // Datos necesarios para enlazar con la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/moonscorch";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "guerragalaxias";

    public static Connection conectar()  {
        try {
        	
        	// Cargamos el driver para la conexión
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

	public static Statement getInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}
