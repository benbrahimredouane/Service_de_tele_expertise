package ma.youcode.teleexpertise.resource;

import java.util.Map;

import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import ma.youcode.teleexpertise.config.JpaLifecycle;
import ma.youcode.teleexpertise.dto.CreerDemandeRequest;
import ma.youcode.teleexpertise.dto.RepondreDemandeRequest;
import ma.youcode.teleexpertise.model.DemandeExpertise;
import ma.youcode.teleexpertise.repository.SpecialisteRepository;
import ma.youcode.teleexpertise.repository.jpa.JpaDemandeExpertiseRepository;
import ma.youcode.teleexpertise.service.DemandeService;

@Path("/demandes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DemandeResource {

    @Context
    private ServletContext servletContext;

    private DemandeService creerService() {
        EntityManagerFactory emf = (EntityManagerFactory)
                servletContext.getAttribute(JpaLifecycle.EMF_KEY);

        return new DemandeService(
                new JpaDemandeExpertiseRepository(emf),
                new SpecialisteRepository(emf)
        );
    }

    @POST
    @RolesAllowed("GENERALISTE")
    public Response creer(CreerDemandeRequest request) {
        DemandeExpertise demande = creerService().creer(request);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of(
                        "id", demande.getId(),
                        "statut", demande.getStatut().name()
                ))
                .build();
    }

    @PUT
    @Path("/{id}/reponse")
    @RolesAllowed("SPECIALISTE")
    public Response repondre(
            @PathParam("id") int id,
            RepondreDemandeRequest request,
            @Context SecurityContext securityContext) {

        String emailConnecte =
                securityContext.getUserPrincipal().getName();

        DemandeExpertise demande =
                creerService().repondre(id, emailConnecte, request);

        return Response.ok(Map.of(
                "id", demande.getId(),
                "statut", demande.getStatut().name()
        )).build();
    }
}