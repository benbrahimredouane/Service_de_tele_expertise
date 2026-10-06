package ma.youcode.teleexpertise.repository.jpa;

import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import ma.youcode.teleexpertise.model.DemandeExpertise;
import ma.youcode.teleexpertise.repository.DemandeExpertiseRepository;
import ma.youcode.teleexpertise.model.Consultation;
import ma.youcode.teleexpertise.model.Specialiste;

public class JpaDemandeExpertiseRepository implements DemandeExpertiseRepository {

    private final EntityManagerFactory entityManagerFactory;

    public JpaDemandeExpertiseRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public DemandeExpertise save(DemandeExpertise demande) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(demande);
            transaction.commit();
            return demande;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<DemandeExpertise> findByConsultationId(int consultationId) {
        EntityManager em = entityManagerFactory.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT d FROM DemandeExpertise d WHERE d.consultationId = :id",
                    DemandeExpertise.class
                )
                .setParameter("id", consultationId)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
        } finally {
            em.close();
        }
    }

         @Override
        public boolean existsConsultationById(int consultationId) {
            EntityManager em = entityManagerFactory.createEntityManager();
            try {
                return em.find(Consultation.class, consultationId) != null;
            } finally {
                em.close();
            }
        }

        @Override
        public boolean existsSpecialisteById(int specialisteId) {
            EntityManager em = entityManagerFactory.createEntityManager();
            try {
                return em.find(Specialiste.class, specialisteId) != null;
            } finally {
                em.close();
            }
        }
}