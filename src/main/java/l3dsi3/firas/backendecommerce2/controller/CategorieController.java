package l3dsi3.firas.backendecommerce2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import l3dsi3.firas.backendecommerce2.entites.Categorie;
import l3dsi3.firas.backendecommerce2.metier.CategorieMetier;

import java.io.IOException;
import java.util.List;

@Path("/categories")
public class CategorieController {

    private final CategorieMetier categorieMetier = new CategorieMetier();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategories() {
        List<Categorie> categories = categorieMetier.findAll();
        return Response.ok(categories).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategorieById(@PathParam("id") Long id) {
        Categorie categorie = categorieMetier.findById(id);
        if (categorie == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Category not found").build();
        }
        return Response.ok(categorie).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCategorie(String json) throws IOException {
        try {
            Categorie categorie = objectMapper.readValue(json, Categorie.class);
            Categorie createdCategorie = categorieMetier.createCategorie(categorie);
            return Response.ok(createdCategorie).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategorie(String json) throws IOException {
        try {
            Categorie categorie = objectMapper.readValue(json, Categorie.class);
            Categorie updatedCategorie = categorieMetier.updateCategorie(categorie);
            return Response.ok(updatedCategorie).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategorie(@PathParam("id") Long id) {
        try {
            boolean result = categorieMetier.deleteCategorie(id);
            return Response.ok("{\"success\":" + result + "}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}