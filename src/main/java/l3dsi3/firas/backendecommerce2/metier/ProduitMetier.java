package l3dsi3.firas.backendecommerce2.metier;

import l3dsi3.firas.backendecommerce2.dao.CategorieDAO;
import l3dsi3.firas.backendecommerce2.dao.ProduitDAO;
import l3dsi3.firas.backendecommerce2.entites.Categorie;
import l3dsi3.firas.backendecommerce2.entites.Produit;

import java.util.List;

public class ProduitMetier {
    private final ProduitDAO produitDAO = new ProduitDAO();
    private final CategorieDAO categorieDAO = new CategorieDAO();

    public List<Produit> findAll() {
        return produitDAO.findAll();
    }

    public Produit findById(Long id) {
        return produitDAO.findById(id);
    }

    public Produit createProduit(Produit produit) {
        Categorie categorie = categorieDAO.findById(produit.getCategorie());
        if (categorie == null) {
            throw new IllegalArgumentException("Category does not exist");
        }
        return produitDAO.create(produit);
    }

    public Produit updateProduit(Produit produit) {
        Categorie categorie = categorieDAO.findById(produit.getCategorie());
        if (categorie == null) {
            throw new IllegalArgumentException("Category does not exist");
        }
        return produitDAO.update(produit);
    }

    public boolean deleteProduit(Long id) {
        return produitDAO.delete(id);
    }
}