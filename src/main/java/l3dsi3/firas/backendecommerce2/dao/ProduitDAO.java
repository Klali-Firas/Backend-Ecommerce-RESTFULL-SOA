package l3dsi3.firas.backendecommerce2.dao;

import l3dsi3.firas.backendecommerce2.config.Singleton;
import l3dsi3.firas.backendecommerce2.entites.Categorie;
import l3dsi3.firas.backendecommerce2.entites.Produit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO {
    private final Connection connection = Singleton.getConnection();

    public List<Produit> findAll() {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT p.id, p.designation, p.prix, p.photo, c.id as categorie_id, c.nom as categorie_nom, c.photo as categorie_photo " +
                "FROM produits p JOIN categories c ON p.categorie_id = c.id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Categorie categorie = new Categorie(rs.getLong("categorie_id"), rs.getString("categorie_nom"), rs.getString("categorie_photo"));
                Produit produit = new Produit(categorie.getId(), rs.getLong("id"), rs.getString("designation"),
                        rs.getDouble("prix"), rs.getString("photo"));
                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }

    public Produit findById(Long id) {
        Produit produit = null;
        String query = "SELECT p.id, p.designation, p.prix, p.photo, c.id as categorie_id, c.nom as categorie_nom, c.photo as categorie_photo " +
                "FROM produits p JOIN categories c ON p.categorie_id = c.id WHERE p.id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Categorie categorie = new Categorie(rs.getLong("categorie_id"), rs.getString("categorie_nom"), rs.getString("categorie_photo"));
                produit = new Produit(categorie.getId(), rs.getLong("id"), rs.getString("designation"),
                        rs.getDouble("prix"), rs.getString("photo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produit;
    }

    public Produit create(Produit produit) {
        String query = "INSERT INTO produits (designation, prix, photo, categorie_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, produit.getDesignation());
            pstmt.setDouble(2, produit.getPrix());
            pstmt.setString(3, produit.getPhoto());
            pstmt.setLong(4, produit.getCategorie());
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) produit.setId(keys.getLong(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produit;
    }

    public Produit update(Produit produit) {
        String query = "UPDATE produits SET designation = ?, prix = ?, photo = ?, categorie_id = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, produit.getDesignation());
            pstmt.setDouble(2, produit.getPrix());
            pstmt.setString(3, produit.getPhoto());
            pstmt.setLong(4, produit.getCategorie());
            pstmt.setLong(5, produit.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produit;
    }

    public boolean delete(Long id) {
        try (PreparedStatement pstmt = connection.prepareStatement("DELETE FROM produits WHERE id = ?")) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
