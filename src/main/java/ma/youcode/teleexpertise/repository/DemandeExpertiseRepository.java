package ma.youcode.teleexpertise.repository;

import java.util.Optional;
import ma.youcode.teleexpertise.model.DemandeExpertise;

public interface DemandeExpertiseRepository {

    DemandeExpertise save(DemandeExpertise demande);

    Optional<DemandeExpertise> findByConsultationId(int consultationId);
}