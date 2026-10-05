package ma.youcode.teleexpertise.config;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import ma.youcode.teleexpertise.error.ApiExceptionMapper;
import ma.youcode.teleexpertise.resource.PingResource;

@ApplicationPath("/api")
public class ApiApplication extends ResourceConfig {

    public ApiApplication() {
        register(JacksonFeature.class);
        register(PingResource.class);
        register(ApiExceptionMapper.class);
    }
}