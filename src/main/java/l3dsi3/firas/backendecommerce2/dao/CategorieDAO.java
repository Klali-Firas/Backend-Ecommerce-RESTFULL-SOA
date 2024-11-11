package l3dsi3.firas.backendecommerce2.dao;

import l3dsi3.firas.backendecommerce2.config.Singleton;
import l3dsi3.firas.backendecommerce2.entites.Categorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO {
    private final Connection connection = Singleton.getConnection();

    public List<Categorie> findAll() {
        List<Categorie> categories = new ArrayList<>();
        String query = "SELECT id, nom, photo FROM categories";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Categorie categorie = new Categorie(
                        rs.getLong("id"),
                        rs.getString("nom"),
                        rs.getString("photo")
                );
                categories.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public Categorie findById(Long id) {
        Categorie categorie = null;
        String query = "SELECT id, nom, photo FROM categories WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                categorie = new Categorie(
                        rs.getLong("id"),
                        rs.getString("nom"),
                        rs.getString("photo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorie;
    }

    public Categorie create(Categorie categorie) {
        String query = "INSERT INTO categories (nom, photo) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, categorie.getNom());
            pstmt.setString(2, categorie.getPhoto());
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                categorie.setId(keys.getLong(1)); // Set the generated ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorie;
    }

    public Categorie update(Categorie categorie) {
        String query = "UPDATE categories SET nom = ?, photo = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, categorie.getNom());
            pstmt.setString(2, categorie.getPhoto());
            pstmt.setLong(3, categorie.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorie;
    }

    public boolean delete(Long id) throws SQLException {
       PreparedStatement pstmt = connection.prepareStatement("DELETE FROM categories WHERE id = ?");
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;


    }
}
