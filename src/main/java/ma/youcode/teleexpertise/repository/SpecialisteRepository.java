package ma.youcode.teleexpertise.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ma.youcode.teleexpertise.model.Specialiste;

import java.util.List;
import java.util.Optional;

public class SpecialisteRepository {

    private final EntityManagerFactory entityManagerFactory;

    public SpecialisteRepository() {
        this.entityManagerFactory =
                Persistence.createEntityManagerFactory("teleexpertisePU");
    }

    public void save(Specialiste specialiste) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            entityManager.persist(specialiste);

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Optional<Specialiste> findById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Specialiste specialiste =
                    entityManager.find(Specialiste.class, id);

            return Optional.ofNullable(specialiste);
        } finally {
            entityManager.close();
        }
    }

    public List<Specialiste> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager
                    .createQuery("SELECT s FROM Specialiste s", Specialiste.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public Optional<Specialiste> findByUtilisateurId(int utilisateurId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager
                    .createQuery(
                            "SELECT s FROM Specialiste s " +
                            "WHERE s.utilisateur.id = :utilisateurId",
                            Specialiste.class
                    )
                    .setParameter("utilisateurId", utilisateurId)
                    .getResultStream()
                    .findFirst();
        } finally {
            entityManager.close();
        }
    }
}