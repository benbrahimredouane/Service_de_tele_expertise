package ma.youcode.teleexpertise.resource;

import java.util.Map;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import ma.youcode.teleexpertise.config.JpaLifecycle;
import ma.youcode.teleexpertise.dto.CreerDemandeRequest;
import ma.youcode.teleexpertise.model.DemandeExpertise;
import ma.youcode.teleexpertise.repository.jpa.JpaDemandeExpertiseRepository;
import ma.youcode.teleexpertise.service.DemandeService;

@Path("/demandes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DemandeResource {

    @Context
    private ServletContext servletContext;

    @POST
    public Response creer(CreerDemandeRequest request) {
        EntityManagerFactory emf = (EntityManagerFactory)
                servletContext.getAttribute(JpaLifecycle.EMF_KEY);

        DemandeService service = new DemandeService(
                new JpaDemandeExpertiseRepository(emf)
        );

        DemandeExpertise demande = service.creer(request);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of(
                        "id", demande.getId(),
                        "statut", demande.getStatut().name()
                ))
                .build();
    }
}