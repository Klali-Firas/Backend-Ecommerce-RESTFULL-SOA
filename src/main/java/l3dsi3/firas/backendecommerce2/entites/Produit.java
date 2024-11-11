package l3dsi3.firas.backendecommerce2.entites;

public class Produit {
    private Long categorie;
    private Long id;
    private String designation;
    private double prix;
    private String photo;

    public Produit() {
    }

    public Produit(Long categorie, Long id, String designation, double prix, String photo) {
        this.categorie = categorie;
        this.id = id;
        this.designation = designation;
        this.prix = prix;
        this.photo = photo;
    }

    public Long getCategorie() {
        return categorie;
    }

    public void setCategorie(Long categorie) {
        this.categorie = categorie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
