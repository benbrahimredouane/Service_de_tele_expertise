package ma.youcode.teleexpertise.resource;

import java.util.HashMap;
import java.util.Map;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {

    @GET
    public Map<String, String> ping() {
        return Map.of("status", "OK");
    }
    @Context
private SecurityContext securityContext;

@GET
@Path("/me")
@Produces(MediaType.APPLICATION_JSON)
public Map<String, String> me() {
    Map<String, String> result = new HashMap<>();

    result.put(
        "principal",
        securityContext.getUserPrincipal().getName()
    );

    result.put(
        "isGeneraliste",
        String.valueOf(
            securityContext.isUserInRole("GENERALISTE")
        )
    );

    return result;
}
}
