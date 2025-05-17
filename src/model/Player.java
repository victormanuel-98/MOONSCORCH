package model;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Representa a un jugador/usuario del juego.
 */
public class Player {
    private int id;
    private String username;
    private String password;
    private String email;
    private Date registrationDate;
    private Date lastLogin;
    private Timestamp createdAt; // Nuevo campo para compatibilidad con PlayerDAO
    
    /**
     * Constructor por defecto.
     */
    public Player() {
    }
    
    /**
     * Constructor con parámetros principales.
     */
    public Player(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.registrationDate = new Date();
        this.lastLogin = new Date();
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Date getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public Date getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    /**
     * Obtiene la fecha de creación del jugador en la base de datos.
     * @return Timestamp de creación
     */
    public Timestamp getCreatedAt() {
        // Si createdAt es null pero registrationDate no lo es, convertir registrationDate a Timestamp
        if (createdAt == null && registrationDate != null) {
            return new Timestamp(registrationDate.getTime());
        }
        return createdAt;
    }
    
    /**
     * Establece la fecha de creación del jugador en la base de datos.
     * También actualiza registrationDate para mantener la consistencia.
     * @param createdAt Timestamp de creación
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        // Actualizar también registrationDate para mantener la consistencia
        if (createdAt != null) {
            this.registrationDate = new Date(createdAt.getTime());
        }
    }
    
    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", registrationDate=" + registrationDate +
                ", lastLogin=" + lastLogin +
                ", createdAt=" + createdAt +
                '}';
    }
}