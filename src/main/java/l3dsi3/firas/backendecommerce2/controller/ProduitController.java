package l3dsi3.firas.backendecommerce2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import l3dsi3.firas.backendecommerce2.entites.Produit;
import l3dsi3.firas.backendecommerce2.metier.ProduitMetier;

import java.io.IOException;
import java.util.List;

@Path("/produits")
public class ProduitController {

    private final ProduitMetier produitMetier = new ProduitMetier();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProduits() {
        List<Produit> produits = produitMetier.findAll();
        return Response.ok(produits).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduitById(@PathParam("id") Long id) {
        Produit produit = produitMetier.findById(id);
        if (produit == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        }
        return Response.ok(produit).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduit(String json) throws IOException {
        try {
            Produit produit = objectMapper.readValue(json, Produit.class);
            Produit createdProduit = produitMetier.createProduit(produit);
            return Response.ok(createdProduit).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduit(String json) throws IOException {
        try {
            Produit produit = objectMapper.readValue(json, Produit.class);
            Produit updatedProduit = produitMetier.updateProduit(produit);
            return Response.ok(updatedProduit).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduit(@PathParam("id") Long id) {
        boolean result = produitMetier.deleteProduit(id);
        if (!result) {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        }
        return Response.ok("{\"success\":" + result + "}").build();
    }
}