package dao;

import model.CharacterClass;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterClassDAO {

    public List<CharacterClass> getAllCharacterClasses() {
        List<CharacterClass> classes = new ArrayList<>();
        String sql = "SELECT id, name, description, image_path, base_atk, base_def, base_eva, base_hp, base_mp, base_luk FROM character_class";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CharacterClass cc = new CharacterClass();
                cc.setId(rs.getInt("id"));
                cc.setName(rs.getString("name"));
                cc.setDescription(rs.getString("description"));
                cc.setImagePath(rs.getString("image_path"));
                cc.setBaseAtk(rs.getInt("base_atk"));
                cc.setBaseDef(rs.getInt("base_def"));
                cc.setBaseEva(rs.getInt("base_eva"));
                cc.setBaseHp(rs.getInt("base_hp"));
                cc.setBaseMp(rs.getInt("base_mp"));
                cc.setBaseLuk(rs.getInt("base_luk"));
                classes.add(cc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classes;
    }

    public CharacterClass getCharacterClassById(int id) {
        String sql = "SELECT id, name, description, image_path, base_atk, base_def, base_eva, base_hp, base_mp, base_luk FROM character_class WHERE id = ?";
        CharacterClass cc = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cc = new CharacterClass();
                    cc.setId(rs.getInt("id"));
                    cc.setName(rs.getString("name"));
                    cc.setDescription(rs.getString("description"));
                    cc.setImagePath(rs.getString("image_path"));
                    cc.setBaseAtk(rs.getInt("base_atk"));
                    cc.setBaseDef(rs.getInt("base_def"));
                    cc.setBaseEva(rs.getInt("base_eva"));
                    cc.setBaseHp(rs.getInt("base_hp"));
                    cc.setBaseMp(rs.getInt("base_mp"));
                    cc.setBaseLuk(rs.getInt("base_luk"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cc;
    }
}