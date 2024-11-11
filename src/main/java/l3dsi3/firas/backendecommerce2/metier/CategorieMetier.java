package l3dsi3.firas.backendecommerce2.metier;

import l3dsi3.firas.backendecommerce2.dao.CategorieDAO;
import l3dsi3.firas.backendecommerce2.entites.Categorie;
import org.postgresql.util.PSQLException;

import java.util.List;

public class CategorieMetier {

    private final CategorieDAO categorieDAO = new CategorieDAO();

    public List<Categorie> findAll() {
        return categorieDAO.findAll();
    }

    public Categorie findById(Long id) {
        return categorieDAO.findById(id);
    }

    public Categorie createCategorie(Categorie categorie) {
        
        return categorieDAO.create(categorie);
    }

    public Categorie updateCategorie(Categorie categorie) {
        if (categorieDAO.findById(categorie.getId()) == null) {
            throw new IllegalArgumentException("Category does not exist");
        }
        return categorieDAO.update(categorie);
    }

    public boolean deleteCategorie(Long id) {
        if (categorieDAO.findById(id) == null) {
            throw new IllegalArgumentException("Category does not exist");
        }
        try {
            return categorieDAO.delete(id);
        } catch (PSQLException e) {
            throw new RuntimeException("Cannot delete category due to foreign key constraint: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }
}