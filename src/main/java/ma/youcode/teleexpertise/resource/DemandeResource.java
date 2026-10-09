package ma.youcode.teleexpertise.resource;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import ma.youcode.teleexpertise.config.JpaLifecycle;
import ma.youcode.teleexpertise.dto.CreerDemandeRequest;
import ma.youcode.teleexpertise.model.DemandeExpertise;
import ma.youcode.teleexpertise.model.Specialiste;
import ma.youcode.teleexpertise.model.Utilisateur;
import ma.youcode.teleexpertise.repository.SpecialisteRepository;
import ma.youcode.teleexpertise.repository.jpa.JpaDemandeExpertiseRepository;
import ma.youcode.teleexpertise.repository.jpa.JpaUtilisateurRepository;
import ma.youcode.teleexpertise.service.DemandeService;

import jakarta.annotation.security.RolesAllowed;

@Path("/demandes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DemandeResource {

    @Context
    private ServletContext servletContext;
    @Context
    private SecurityContext securityContext;

    @POST
    @RolesAllowed("GENERALISTE")
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
    
@GET
@RolesAllowed("SPECIALISTE")
public Response lister(
        @QueryParam("statut") String statut
) {
    EntityManagerFactory emf =
            (EntityManagerFactory) servletContext.getAttribute(
                    JpaLifecycle.EMF_KEY
            );

    Principal principal = securityContext.getUserPrincipal();

    if (principal == null) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(Map.of("message", "Authentification requise"))
                .build();
    }

    String email = principal.getName();

    JpaUtilisateurRepository utilisateurRepository =
            new JpaUtilisateurRepository(emf);

    Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
            .orElseThrow(() -> new jakarta.ws.rs.NotFoundException(
                    "Utilisateur introuvable"
            ));

    SpecialisteRepository specialisteRepository =
            new SpecialisteRepository(emf);

    Specialiste specialiste = specialisteRepository
            .findByUtilisateurId(utilisateur.getId())
            .orElseThrow(() -> new jakarta.ws.rs.NotFoundException(
                    "Profil spécialiste introuvable"
            ));

    DemandeService service = new DemandeService(
            new JpaDemandeExpertiseRepository(emf)
    );

    List<DemandeExpertise> demandes =
            service.listerParSpecialiste(specialiste.getId(), statut);

    return Response.ok(demandes).build();
}
}