package ma.youcode.teleexpertise.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import ma.youcode.teleexpertise.repository.jpa.JpaUtilisateurRepository;
import ma.youcode.teleexpertise.service.AuthService;

@WebListener
public class JpaLifecycle implements ServletContextListener {

    public static final String EMF_KEY = "teleexpertiseEntityManagerFactory";

    @Override
    public void contextInitialized(ServletContextEvent event) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("teleexpertisePU");

        event.getServletContext().setAttribute(EMF_KEY, emf);

        AuthService authService =
                new AuthService(new JpaUtilisateurRepository(emf));

        event.getServletContext().setAttribute("authService", authService);
    }
    

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        EntityManagerFactory emf = (EntityManagerFactory)
                event.getServletContext().getAttribute(EMF_KEY);

        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}