package ma.youcode.teleexpertise.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.Optional;

import ma.youcode.teleexpertise.model.Utilisateur;
import ma.youcode.teleexpertise.repository.UtilisateurRepository;

public class JpaUtilisateurRepository implements UtilisateurRepository {

    private final EntityManagerFactory emf;

    public JpaUtilisateurRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Optional<Utilisateur> findByEmail(String email) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.email = :email",
                    Utilisateur.class
            )
            .setParameter("email", email)
            .setMaxResults(1)
            .getResultList()
            .stream()
            .findFirst();
        } finally {
            em.close();
        }
    }
}