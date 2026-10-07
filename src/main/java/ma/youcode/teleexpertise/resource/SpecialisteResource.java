package ma.youcode.teleexpertise.resource;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import ma.youcode.teleexpertise.config.JpaLifecycle;
import ma.youcode.teleexpertise.model.Specialite;
import ma.youcode.teleexpertise.repository.SpecialisteRepository;
import ma.youcode.teleexpertise.service.SpecialisteService;

@Path("/specialistes")
@Produces(MediaType.APPLICATION_JSON)
public class SpecialisteResource {

    @Context
    private ServletContext servletContext;


    @GET
    public Response lister(@QueryParam("specialite") Specialite specialite) {
        EntityManagerFactory emf = (EntityManagerFactory)
                servletContext.getAttribute(JpaLifecycle.EMF_KEY);

        SpecialisteService service = new SpecialisteService(
                new SpecialisteRepository(emf)
        );

        if(specialite != null){
            return Response.ok(service.listerParSpecialite(specialite)).build();
        }
        else{
            return Response.ok(service.ListerAll()).build();
        }
        
    }
}