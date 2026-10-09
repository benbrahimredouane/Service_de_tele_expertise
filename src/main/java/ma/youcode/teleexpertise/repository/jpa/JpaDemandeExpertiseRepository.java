package ma.youcode.teleexpertise.repository.jpa;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import ma.youcode.teleexpertise.model.DemandeExpertise;
import ma.youcode.teleexpertise.repository.DemandeExpertiseRepository;


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
        Number count = (Number) em.createNativeQuery(
                "SELECT COUNT(*) FROM consultations WHERE id = ?1"
        )
        .setParameter(1, consultationId)
        .getSingleResult();

        return count.longValue() > 0;
    } finally {
        em.close();
    }
}


@Override
public boolean existsSpecialisteById(int specialisteId) {
    EntityManager em = entityManagerFactory.createEntityManager();
    try {
        Number count = (Number) em.createNativeQuery(
                "SELECT COUNT(*) FROM specialistes WHERE id = ?1"
        )
        .setParameter(1, specialisteId)
        .getSingleResult();

        return count.longValue() > 0;
    } finally {
        em.close();
    }
}
@Override
public List<DemandeExpertise> findBySpecialisteId(int specialisteId) {
    EntityManager em = entityManagerFactory.createEntityManager();

    try {
        return em.createQuery(
                "SELECT d FROM DemandeExpertise d " +
                "WHERE d.specialisteId = :specialisteId",
                DemandeExpertise.class
            )
            .setParameter("specialisteId", specialisteId)
            .getResultList();
    } finally {
        em.close();
    }
}
}