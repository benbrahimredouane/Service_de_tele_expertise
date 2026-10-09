
package ma.youcode.teleexpertise.resource;

import java.util.List;
import java.util.Map;

import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import ma.youcode.teleexpertise.config.JpaLifecycle;
import ma.youcode.teleexpertise.dto.CreerDemandeRequest;
import ma.youcode.teleexpertise.model.DemandeExpertise;
import ma.youcode.teleexpertise.repository.jpa.JpaDemandeExpertiseRepository;
import ma.youcode.teleexpertise.repository.jpa.JpaUtilisateurRepository;
import ma.youcode.teleexpertise.repository.SpecialisteRepository;
import ma.youcode.teleexpertise.model.Specialiste;
import ma.youcode.teleexpertise.model.Utilisateur;
import ma.youcode.teleexpertise.service.DemandeService;

@Path("/demandes")
@Consumes(MediaType.APPLICATION_JSON)
@jakarta.ws.rs.Produces(MediaType.APPLICATION_JSON)
public class DemandeResource {

    @Context
    private ServletContext servletContext;

    @Context
    private SecurityContext securityContext;

    @POST
    @RolesAllowed("GENERALISTE")
    public Response creer(CreerDemandeRequest request) {
        EntityManagerFactory emf =
                (EntityManagerFactory) servletContext.getAttribute(
                        JpaLifecycle.EMF_KEY
                );

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
    @RolesAllowed({"SPECIALISTE", "GENERALISTE"})
    public Response consulter(
            @QueryParam("statut") String statut,
            @QueryParam("consultationId") Integer consultationId
    ) {
        EntityManagerFactory emf =
                (EntityManagerFactory) servletContext.getAttribute(
                        JpaLifecycle.EMF_KEY
                );

        DemandeService service = new DemandeService(
                new JpaDemandeExpertiseRepository(emf)
        );

       
        if (securityContext.isUserInRole("SPECIALISTE")) {

            SpecialisteRepository specialisteRepository =
                    new SpecialisteRepository(emf);

            String email = securityContext.getUserPrincipal().getName();

            JpaUtilisateurRepository utilisateurRepository =
                    new JpaUtilisateurRepository(emf);

            Utilisateur utilisateur = utilisateurRepository
                    .findByEmail(email)
                    .orElseThrow(() ->
                            new jakarta.ws.rs.NotFoundException(
                                    "Utilisateur introuvable"
                            )
                    );

            Specialiste specialiste = specialisteRepository
                    .findByUtilisateurId(utilisateur.getId())
                    .orElseThrow(() ->
                            new jakarta.ws.rs.NotFoundException(
                                    "Profil spécialiste introuvable"
                            )
                    );

            List<DemandeExpertise> demandes =
                    service.listerParSpecialiste(
                            specialiste.getId(),
                            statut
                    );

            return Response.ok(demandes).build();
        }

      
        if (securityContext.isUserInRole("GENERALISTE")) {

            if (consultationId == null) {
                throw new BadRequestException(
                        "Le paramètre consultationId est obligatoire"
                );
            }

            if (consultationId <= 0) {
                throw new BadRequestException(
                        "consultationId doit être un entier positif"
                );
            }

            DemandeExpertise demande =
                    service.trouverParConsultation(consultationId);

            return Response.ok(demande).build();
        }

        return Response.status(Response.Status.FORBIDDEN)
                .entity(Map.of("message", "Accès interdit"))
                .build(); 
    }
}