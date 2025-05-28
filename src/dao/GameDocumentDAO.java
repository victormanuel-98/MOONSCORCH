package dao;

import model.GameDocument;
import controller.ConexionBD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Código para gestionar los documentos del juego

public class GameDocumentDAO {
    
    private Connection conn;
    
//Esto inicializa la conexión a la base de datos
    
    public GameDocumentDAO() {
        try {
            conn = ConexionBD.getInstance().getConnection();
            createTableIfNotExists();
        } catch (Exception e) {
            System.err.println("Error al inicializar GameDocumentDAO: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
 //Crea la tabla de documentos si no existe
    
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS game_documents (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "title VARCHAR(100) NOT NULL, " +
                "content TEXT, " +
                "type VARCHAR(20) NOT NULL, " +
                "is_read BOOLEAN DEFAULT FALSE, " +
                "is_collected BOOLEAN DEFAULT FALSE, " +
                "discovered_at TIMESTAMP NULL, " +
                "location VARCHAR(100), " +
                "author VARCHAR(100), " +
                "file_path VARCHAR(255)" +
                ")";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al crear tabla game_documents: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Guarda un documento en la base de datos.
     * 
     * @param document Documento a guardar
     * @return ID del documento guardado
     */
    public int save(GameDocument document) {
        String sql = "INSERT INTO game_documents (title, content, type, is_read, is_collected, " +
                "discovered_at, location, author) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, document.getTitle());
            pstmt.setString(2, document.getContent());
            pstmt.setString(3, document.getType().name());
            pstmt.setBoolean(4, document.isRead());
            pstmt.setBoolean(5, document.isCollected());
            
            if (document.getDiscoveredAt() != null) {
                pstmt.setTimestamp(6, Timestamp.valueOf(document.getDiscoveredAt()));
            } else {
                pstmt.setNull(6, Types.TIMESTAMP);
            }
            
            pstmt.setString(7, document.getLocation());
            pstmt.setString(8, document.getAuthor());
            
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    document.setId(generatedKeys.getInt(1));
                    return document.getId();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar documento: " + e.getMessage());
            e.printStackTrace();
        }
        
        return -1;
    }
    
    /**
     * Actualiza un documento existente.
     * 
     * @param document Documento a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean update(GameDocument document) {
        String sql = "UPDATE game_documents SET title = ?, content = ?, type = ?, is_read = ?, " +
                "is_collected = ?, discovered_at = ?, location = ?, author = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, document.getTitle());
            pstmt.setString(2, document.getContent());
            pstmt.setString(3, document.getType().name());
            pstmt.setBoolean(4, document.isRead());
            pstmt.setBoolean(5, document.isCollected());
            
            if (document.getDiscoveredAt() != null) {
                pstmt.setTimestamp(6, Timestamp.valueOf(document.getDiscoveredAt()));
            } else {
                pstmt.setNull(6, Types.TIMESTAMP);
            }
            
            pstmt.setString(7, document.getLocation());
            pstmt.setString(8, document.getAuthor());
            pstmt.setInt(9, document.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar documento: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Marca un documento como leído.
     * 
     * @param documentId ID del documento
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean markAsRead(int documentId) {
        String sql = "UPDATE game_documents SET is_read = TRUE WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, documentId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al marcar documento como leído: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Marca un documento como recolectado.
     * 
     * @param documentId ID del documento
     * @param location Ubicación donde se recolectó
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean collectDocument(int documentId, String location) {
        String sql = "UPDATE game_documents SET is_collected = TRUE, discovered_at = ?, location = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(2, location);
            pstmt.setInt(3, documentId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al recolectar documento: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Obtiene un documento por su ID.
     * 
     * @param id ID del documento
     * @return Documento encontrado o null si no existe
     */
    public GameDocument getById(int id) {
        String sql = "SELECT * FROM game_documents WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDocument(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener documento por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Obtiene todos los documentos recolectados.
     * 
     * @return Lista de documentos recolectados
     */
    public List<GameDocument> getAllCollected() {
        String sql = "SELECT * FROM game_documents WHERE is_collected = TRUE ORDER BY discovered_at DESC";
        List<GameDocument> documents = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                documents.add(mapResultSetToDocument(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener documentos recolectados: " + e.getMessage());
            e.printStackTrace();
        }
        
        return documents;
    }
    
    /**
     * Obtiene todos los documentos no leídos.
     * 
     * @return Lista de documentos no leídos
     */
    public List<GameDocument> getUnreadDocuments() {
        String sql = "SELECT * FROM game_documents WHERE is_collected = TRUE AND is_read = FALSE";
        List<GameDocument> documents = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                documents.add(mapResultSetToDocument(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener documentos no leídos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return documents;
    }
    
    /**
     * Obtiene documentos por tipo.
     * 
     * @param type Tipo de documento
     * @return Lista de documentos del tipo especificado
     */
    public List<GameDocument> getByType(GameDocument.DocumentType type) {
        String sql = "SELECT * FROM game_documents WHERE type = ? AND is_collected = TRUE";
        List<GameDocument> documents = new ArrayList<>();
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type.name());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    documents.add(mapResultSetToDocument(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener documentos por tipo: " + e.getMessage());
            e.printStackTrace();
        }
        
        return documents;
    }
    
    /**
     * Carga documentos desde archivos de texto.
     * 
     * @param directoryPath Directorio donde se encuentran los archivos
     * @param type Tipo de documento a cargar
     * @return Número de documentos cargados
     */
    public int loadDocumentsFromFiles(String directoryPath, GameDocument.DocumentType type) {
        int count = 0;
        
        try {
            Files.list(Paths.get(directoryPath))
                .filter(path -> path.toString().endsWith(".txt"))
                .forEach(path -> {
                    try {
                        String title = path.getFileName().toString().replace(".txt", "");
                        String content = new String(Files.readAllBytes(path));
                        
                        GameDocument document = new GameDocument(0, title, content, type);
                        document.setLocation("Archivo: " + path.toString());
                        
                        save(document);
                    } catch (IOException e) {
                        System.err.println("Error al leer archivo: " + path + " - " + e.getMessage());
                    }
                });
            
            count = (int) Files.list(Paths.get(directoryPath))
                .filter(path -> path.toString().endsWith(".txt"))
                .count();
        } catch (IOException e) {
            System.err.println("Error al listar archivos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return count;
    }
    
    /**
     * Mapea un ResultSet a un objeto GameDocument.
     * 
     * @param rs ResultSet a mapear
     * @return Objeto GameDocument
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private GameDocument mapResultSetToDocument(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String content = rs.getString("content");
        GameDocument.DocumentType type = GameDocument.DocumentType.valueOf(rs.getString("type"));
        
        GameDocument document = new GameDocument(id, title, content, type);
        document.setRead(rs.getBoolean("is_read"));
        document.setCollected(rs.getBoolean("is_collected"));
        
        Timestamp discoveredAt = rs.getTimestamp("discovered_at");
        if (discoveredAt != null) {
            document.setDiscoveredAt(discoveredAt.toLocalDateTime());
        }
        
        document.setLocation(rs.getString("location"));
        document.setAuthor(rs.getString("author"));
        
        return document;
    }
}