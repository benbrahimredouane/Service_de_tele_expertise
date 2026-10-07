package ma.youcode.teleexpertise.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class JpaLifecycle implements ServletContextListener {

    public static final String EMF_KEY = "teleexpertiseEntityManagerFactory";

    @Override
    public void contextInitialized(ServletContextEvent event) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("teleexpertisePU");

        event.getServletContext().setAttribute(EMF_KEY, emf);
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