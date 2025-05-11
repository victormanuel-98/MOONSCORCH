/*package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import controller.ConexionBD;
import model.Personaje;

public class PersonajeDAO {

    public void insertar(Personaje p) {
        String sql = "INSERT INTO personaje (nombre, nivel, salud, fuerza) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setInt(2, p.getNivel());
            stmt.setInt(3, p.getSalud());
            stmt.setInt(4, p.getFuerza());

            stmt.executeUpdate();
            System.out.println("✅ Personaje insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar personaje: " + e.getMessage());
        }
    }

    public List<Personaje> listar() {
        List<Personaje> personajes = new ArrayList<>();
        String sql = "SELECT * FROM personaje";

        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Personaje p = new Personaje(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("nivel"),
                        rs.getInt("salud"),
                        rs.getInt("fuerza")
                );
                personajes.add(p);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar personajes: " + e.getMessage());
        }

        return personajes;
    }

    public void actualizar(Personaje p) {
        String sql = "UPDATE personaje SET nombre=?, nivel=?, salud=?, fuerza=? WHERE id=?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setInt(2, p.getNivel());
            stmt.setInt(3, p.getSalud());
            stmt.setInt(4, p.getFuerza());
            stmt.setInt(5, p.getId());

            stmt.executeUpdate();
            System.out.println("✅ Personaje actualizado.");
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar personaje: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM personaje WHERE id=?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("🗑️ Personaje eliminado.");
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar personaje: " + e.getMessage());
        }
    }
}*/

/* Cambios a futuro*/
package dao;


