package ma.youcode.teleexpertise.repository;

import java.util.List;
import java.util.Optional;
import ma.youcode.teleexpertise.model.DemandeExpertise;

public interface DemandeExpertiseRepository {

    DemandeExpertise save(DemandeExpertise demande);

    Optional<DemandeExpertise> findByConsultationId(int consultationId);

    boolean existsConsultationById(int consultationId);

    boolean existsSpecialisteById(int specialisteId);

    List<DemandeExpertise> findBySpecialisteId(int specialisteId);
    
}