package ma.youcode.teleexpertise.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Base64;
import java.util.Optional;

import jakarta.annotation.Priority;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import ma.youcode.teleexpertise.model.Utilisateur;
import ma.youcode.teleexpertise.service.AuthService;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class BasicAuthFilter implements ContainerRequestFilter {

    @Context
    private ServletContext servletContext;

    @Override
    public void filter(ContainerRequestContext request) throws IOException {

        String authorization = request.getHeaderString("Authorization");

        if (authorization == null || !authorization.startsWith("Basic ")) {
            refuser(request);
            return;
        }

        String identifiants;

        try {
            String texteEncode = authorization.substring(6);
            byte[] octets = Base64.getDecoder().decode(texteEncode);
            identifiants = new String(octets, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            refuser(request);
            return;
        }

        String[] parties = identifiants.split(":", 2);

        if (parties.length != 2) {
            refuser(request);
            return;
        }

        String email = parties[0];
        String motDePasse = parties[1];

        AuthService authService =
                (AuthService) servletContext.getAttribute("authService");

        Optional<Utilisateur> resultat =
                authService.authentifier(email, motDePasse);

        if (resultat.isEmpty()) {
            refuser(request);
            return;
        }

        Utilisateur utilisateur = resultat.get();
        SecurityContext ancienContext = request.getSecurityContext();

        request.setSecurityContext(new SecurityContext() {

            @Override
            public Principal getUserPrincipal() {
                return () -> utilisateur.getEmail();
            }

            @Override
            public boolean isUserInRole(String role) {
                return utilisateur.getRole() != null
                        && utilisateur.getRole().name().equals(role);
            }

            @Override
            public boolean isSecure() {
                return ancienContext.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return SecurityContext.BASIC_AUTH;
            }
        });
    }

    private void refuser(ContainerRequestContext request) {
    Response reponse = Response.status(Response.Status.UNAUTHORIZED)
            .header("WWW-Authenticate", "Basic realm=\"teleexpertise\"")
            .type(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
            .entity(new ma.youcode.teleexpertise.error.ApiError(
                    401,
                    "UNAUTHORIZED",
                    "Identifiants absents ou incorrects"
            ))
            .build();

    request.abortWith(reponse);
}
}