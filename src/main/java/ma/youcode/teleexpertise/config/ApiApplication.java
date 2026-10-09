package ma.youcode.teleexpertise.config;

import jakarta.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import ma.youcode.teleexpertise.error.ApiExceptionMapper;
import ma.youcode.teleexpertise.filter.BasicAuthFilter;
import ma.youcode.teleexpertise.resource.DemandeResource;
import ma.youcode.teleexpertise.resource.PingResource;

@ApplicationPath("/api")
public class ApiApplication extends ResourceConfig {

    public ApiApplication() {
        register(JacksonFeature.class);
        register(PingResource.class);
        register(DemandeResource.class);
        register(ApiExceptionMapper.class);

        register(BasicAuthFilter.class);
        register(RolesAllowedDynamicFeature.class);
    }
}