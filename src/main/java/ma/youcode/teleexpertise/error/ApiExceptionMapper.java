package ma.youcode.teleexpertise.error;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        Response original = null;
        int status = 500;

        if (exception instanceof WebApplicationException webException) {
            original = webException.getResponse();
            status = original.getStatus();
        } else if (exception instanceof IllegalArgumentException) {
            status = 400;
        }

        String error;
        String message;

        switch (status) {
            case 400 -> {
                error = "BAD_REQUEST";
                message = exception instanceof IllegalArgumentException
                        ? exception.getMessage()
                        : "Requête invalide";
            }
            case 401 -> {
                error = "UNAUTHORIZED";
                message = "Authentification requise";
            }
            case 403 -> {
                error = "FORBIDDEN";
                message = "Accès refusé";
            }
            case 404 -> {
                error = "NOT_FOUND";
                message = "Ressource introuvable";
            }
            default -> {
                error = "INTERNAL_SERVER_ERROR";
                message = "Erreur interne du serveur";
                status = 500;
            }
        }

        Response.ResponseBuilder builder = original == null
                ? Response.status(status)
                : Response.fromResponse(original).status(status);

        return builder
                .type(MediaType.APPLICATION_JSON)
                .entity(new ApiError(status, error, message))
                .build();
    }
}