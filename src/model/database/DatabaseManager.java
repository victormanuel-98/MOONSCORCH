package model.database;

import model.GameState;
import model.Jugador;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    
    // Configuración de la conexión
    private static final String DB_URL = "jdbc:mysql://localhost:3306/moonscorch";
    private static final String DB_USER = "root"; // Cambia esto a tu usuario de MySQL
    private static final String DB_PASSWORD = "guerragalaxias"; // Cambia esto a tu contraseña de MySQL
    
    private static Connection connection;
    
    // Inicializar la conexión a la base de datos
    public static void inicializarDB() {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establecer conexión
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Conexión a la base de datos MySQL establecida.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver MySQL no encontrado. " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Cerrar la conexión
    public static void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión a la base de datos cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    
    // Registrar un nuevo usuario
    public static int registrarUsuario(String username, String password) {
        if (connection == null) {
            // Modo sin conexión
            return 1; // ID ficticio
        }
        
        try {
            // Verificar si el usuario ya existe
            String sqlCheck = "SELECT id FROM usuarios WHERE username = ?";
            PreparedStatement pstmtCheck = connection.prepareStatement(sqlCheck);
            pstmtCheck.setString(1, username);
            ResultSet rs = pstmtCheck.executeQuery();
            
            if (rs.next()) {
                // El usuario ya existe
                return -1;
            }
            
            // Insertar el nuevo usuario
            String sqlInsert = "INSERT INTO usuarios (username, password) VALUES (?, ?)";
            PreparedStatement pstmtInsert = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            pstmtInsert.setString(1, username);
            pstmtInsert.setString(2, password);
            
            int filasAfectadas = pstmtInsert.executeUpdate();
            
            if (filasAfectadas > 0) {
                // Obtener el ID generado
                ResultSet generatedKeys = pstmtInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
            
            return -1; // Error al insertar
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return -1;
        }
    }
    
    // Verificar login
    public static int verificarLogin(String username, String password) {
        String sql = "SELECT id FROM usuarios WHERE username = ? AND password = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password); // En una aplicación real, deberías verificar el hash
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            return -1; // Usuario no encontrado
        } catch (SQLException e) {
            System.err.println("Error al verificar login: " + e.getMessage());
            return -1;
        }
    }
    
    // Guardar un personaje
    public static boolean guardarPersonaje(int idUsuario, Jugador jugador) {
        // Primero verificamos si el personaje ya existe
        String checkSql = "SELECT id FROM personajes WHERE id_usuario = ? AND nombre = ?";
        String insertSql = "INSERT INTO personajes (id_usuario, nombre, clase, nivel, hp_maximo, hp_actual, ataque, defensa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE personajes SET nivel = ?, hp_maximo = ?, hp_actual = ?, ataque = ?, defensa = ?, ultima_sesion = CURRENT_TIMESTAMP WHERE id = ?";
        
        try {
            // Verificar si el personaje existe
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setInt(1, idUsuario);
            checkStmt.setString(2, jugador.nombre);
            
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                // El personaje existe, actualizarlo
                int idPersonaje = rs.getInt("id");
                
                PreparedStatement updateStmt = connection.prepareStatement(updateSql);
                updateStmt.setInt(1, jugador.nivel);
                updateStmt.setInt(2, jugador.getHPMaximo());
                updateStmt.setInt(3, jugador.getHPActual());
                updateStmt.setInt(4, jugador.getAtaque());
                updateStmt.setInt(5, jugador.getDefensa());
                updateStmt.setInt(6, idPersonaje);
                
                int filasActualizadas = updateStmt.executeUpdate();
                
                // Actualizar inventario
                actualizarInventario(idPersonaje, jugador.getInventario());
                
                return filasActualizadas > 0;
            } else {
                // El personaje no existe, crearlo
                PreparedStatement insertStmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                insertStmt.setInt(1, idUsuario);
                insertStmt.setString(2, jugador.nombre);
                insertStmt.setString(3, jugador.clase);
                insertStmt.setInt(4, jugador.nivel);
                insertStmt.setInt(5, jugador.getHPMaximo());
                insertStmt.setInt(6, jugador.getHPActual());
                insertStmt.setInt(7, jugador.getAtaque());
                insertStmt.setInt(8, jugador.getDefensa());
                
                int filasInsertadas = insertStmt.executeUpdate();
                
                if (filasInsertadas > 0) {
                    // Obtener el ID generado
                    ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int idPersonaje = generatedKeys.getInt(1);
                        
                        // Guardar inventario
                        guardarInventario(idPersonaje, jugador.getInventario());
                        
                        // Inicializar estadísticas
                        inicializarEstadisticas(idPersonaje);
                        
                        // Inicializar progreso
                        inicializarProgreso(idPersonaje);
                        
                        return true;
                    }
                }
                
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar personaje: " + e.getMessage());
            return false;
        }
    }
    
    // Guardar inventario
    private static void guardarInventario(int idPersonaje, Map<String, Integer> inventario) {
        String sql = "INSERT INTO inventario (id_personaje, objeto, cantidad) VALUES (?, ?, ?)";
        
        try {
            // Primero eliminamos el inventario existente
            eliminarInventario(idPersonaje);
            
            // Luego insertamos el nuevo inventario
            PreparedStatement pstmt = connection.prepareStatement(sql);
            
            for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                pstmt.setInt(1, idPersonaje);
                pstmt.setString(2, entry.getKey());
                pstmt.setInt(3, entry.getValue());
                pstmt.addBatch();
            }
            
            pstmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error al guardar inventario: " + e.getMessage());
        }
    }
    
    // Actualizar inventario
    private static void actualizarInventario(int idPersonaje, Map<String, Integer> inventario) {
        // Simplemente eliminamos el inventario existente y guardamos el nuevo
        guardarInventario(idPersonaje, inventario);
    }
    
    // Eliminar inventario
    private static void eliminarInventario(int idPersonaje) {
        String sql = "DELETE FROM inventario WHERE id_personaje = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idPersonaje);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar inventario: " + e.getMessage());
        }
    }
    
    // Inicializar estadísticas
    private static void inicializarEstadisticas(int idPersonaje) {
        String sql = "INSERT INTO estadisticas (id_personaje) VALUES (?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idPersonaje);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al inicializar estadísticas: " + e.getMessage());
        }
    }
    
    // Inicializar progreso
    private static void inicializarProgreso(int idPersonaje) {
        String sql = "INSERT INTO progreso (id_personaje, mapa, zona) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idPersonaje);
            pstmt.setString(2, "PISO_1.txt");
            pstmt.setString(3, "PRIMERA ZONA: calles abandonadas");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al inicializar progreso: " + e.getMessage());
        }
    }
    
    // Cargar un personaje
    public static Jugador cargarPersonaje(int idPersonaje) {
        String sql = "SELECT * FROM personajes WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idPersonaje);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String clase = rs.getString("clase");
                int nivel = rs.getInt("nivel");
                int hpMaximo = rs.getInt("hp_maximo");
                int hpActual = rs.getInt("hp_actual");
                int ataque = rs.getInt("ataque");
                int defensa = rs.getInt("defensa");
                
                // Crear el jugador
                Jugador jugador = new Jugador(nombre, clase, nivel, hpMaximo, hpActual, ataque, defensa);
                
                // Cargar inventario
                Map<String, Integer> inventario = cargarInventario(idPersonaje);
                for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                    jugador.agregarObjeto(entry.getKey(), entry.getValue());
                }
                
                return jugador;
            }
            
            return null;
        } catch (SQLException e) {
            System.err.println("Error al cargar personaje: " + e.getMessage());
            return null;
        }
    }
    
    // Cargar inventario
    private static Map<String, Integer> cargarInventario(int idPersonaje) {
        String sql = "SELECT objeto, cantidad FROM inventario WHERE id_personaje = ?";
        Map<String, Integer> inventario = new HashMap<>();
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idPersonaje);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String objeto = rs.getString("objeto");
                int cantidad = rs.getInt("cantidad");
                
                inventario.put(objeto, cantidad);
            }
            
            return inventario;
        } catch (SQLException e) {
            System.err.println("Error al cargar inventario: " + e.getMessage());
            return inventario;
        }
    }
    
    // Cargar progreso
    public static Map<String, Object> cargarProgreso(int idPersonaje) {
        String sql = "SELECT * FROM progreso WHERE id_personaje = ?";
        Map<String, Object> progreso = new HashMap<>();
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idPersonaje);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                progreso.put("mapa", rs.getString("mapa"));
                progreso.put("nodoActual", rs.getInt("nodo_actual"));
                progreso.put("zona", rs.getString("zona"));
                progreso.put("completado", rs.getBoolean("completado"));
            }
            
            return progreso;
        } catch (SQLException e) {
            System.err.println("Error al cargar progreso: " + e.getMessage());
            return progreso;
        }
    }
    
    // Guardar progreso
    public static boolean guardarProgreso(int idPersonaje, String mapa, int nodoActual, String zona, boolean completado) {
        String sql = "UPDATE progreso SET mapa = ?, nodo_actual = ?, zona = ?, completado = ? WHERE id_personaje = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, mapa);
            pstmt.setInt(2, nodoActual);
            pstmt.setString(3, zona);
            pstmt.setBoolean(4, completado);
            pstmt.setInt(5, idPersonaje);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar progreso: " + e.getMessage());
            return false;
        }
    }
    
    // Actualizar estadísticas
    public static boolean actualizarEstadisticas(int idPersonaje, String tipo, int cantidad) {
        String sql = "UPDATE estadisticas SET " + tipo + " = " + tipo + " + ? WHERE id_personaje = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cantidad);
            pstmt.setInt(2, idPersonaje);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estadísticas: " + e.getMessage());
            return false;
        }
    }
    
    // Obtener lista de personajes de un usuario
    public static List<Map<String, Object>> obtenerPersonajesUsuario(int idUsuario) {
        String sql = "SELECT id, nombre, clase, nivel FROM personajes WHERE id_usuario = ?";
        List<Map<String, Object>> personajes = new ArrayList<>();
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> personaje = new HashMap<>();
                personaje.put("id", rs.getInt("id"));
                personaje.put("nombre", rs.getString("nombre"));
                personaje.put("clase", rs.getString("clase"));
                personaje.put("nivel", rs.getInt("nivel"));
                
                personajes.add(personaje);
            }
            
            return personajes;
        } catch (SQLException e) {
            System.err.println("Error al obtener personajes: " + e.getMessage());
            return personajes;
        }
    }
    
    // Probar la conexión a la base de datos
    public static boolean probarConexion() {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Intentar establecer conexión
            Connection testConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // Si llegamos aquí, la conexión fue exitosa
            testConnection.close();
            System.out.println("Conexión a la base de datos MySQL probada con éxito.");
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver MySQL no encontrado. " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos MySQL: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}