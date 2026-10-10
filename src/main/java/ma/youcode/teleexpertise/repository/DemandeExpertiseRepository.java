package ma.youcode.teleexpertise.repository;

import java.util.Optional;
import ma.youcode.teleexpertise.model.DemandeExpertise;


public interface DemandeExpertiseRepository {

    DemandeExpertise save(DemandeExpertise demande);

    Optional<DemandeExpertise> findByConsultationId(int consultationId);

    boolean existsConsultationById(int consultationId);

    boolean existsSpecialisteById(int specialisteId);
    Optional<DemandeExpertise> findById(int id);

    DemandeExpertise update(DemandeExpertise demande);

}