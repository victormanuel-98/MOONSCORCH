package model;

import java.time.LocalDateTime;

/**
 * Representa un documento o texto que el jugador puede encontrar y leer.
 */
public class GameDocument {
    
    public enum DocumentType {
        SCROLL("Pergamino"),
        BOOK("Libro"),
        NOTE("Nota"),
        LETTER("Carta"),
        DIARY("Diario"),
        SIGN("Cartel"),
        RUNE("Runa"),
        RECIPE("Receta"),
        MAP("Mapa"),
        CERTIFICATE("Certificado");
        
        private final String displayName;
        
        DocumentType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    private int id;
    private String title;
    private String content;
    private DocumentType type;
    private boolean isRead;
    private boolean isCollected;
    private LocalDateTime discoveredAt;
    private String location;
    private String author;
    
    // Constructor
    public GameDocument(int id, String title, String content, DocumentType type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
        this.isRead = false;
        this.isCollected = false;
        this.discoveredAt = null;
        this.location = "";
        this.author = "";
    }
    
    // Getters y setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public DocumentType getType() {
        return type;
    }
    
    public void setType(DocumentType type) {
        this.type = type;
    }
    
    public boolean isRead() {
        return isRead;
    }
    
    public void setRead(boolean read) {
        isRead = read;
    }
    
    public boolean isCollected() {
        return isCollected;
    }
    
    public void setCollected(boolean collected) {
        isCollected = collected;
        if (collected && discoveredAt == null) {
            discoveredAt = LocalDateTime.now();
        }
    }
    
    public LocalDateTime getDiscoveredAt() {
        return discoveredAt;
    }
    
    public void setDiscoveredAt(LocalDateTime discoveredAt) {
        this.discoveredAt = discoveredAt;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    /**
     * Marca el documento como leído.
     */
    public void markAsRead() {
        this.isRead = true;
    }
    
    /**
     * Obtiene el nombre para mostrar del tipo de documento.
     * 
     * @return Nombre para mostrar del tipo de documento
     */
    public String getTypeDisplayName() {
        return type.getDisplayName();
    }
    
    /**
     * Obtiene una descripción corta del documento.
     * 
     * @return Descripción corta
     */
    public String getShortDescription() {
        return type.getDisplayName() + ": " + title;
    }
}