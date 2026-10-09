package ma.youcode.teleexpertise.service;

import ma.youcode.teleexpertise.dto.CreerDemandeRequest;
import ma.youcode.teleexpertise.model.Priorite;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.ws.rs.NotFoundException;
import ma.youcode.teleexpertise.repository.DemandeExpertiseRepository;
import ma.youcode.teleexpertise.model.DemandeExpertise;
import ma.youcode.teleexpertise.model.StatutDemande;

public class DemandeService {

    private final DemandeExpertiseRepository repository;

    public DemandeService(DemandeExpertiseRepository repository) {
        this.repository = repository;
    }

    public void verifierReferences(CreerDemandeRequest demande) {
        if (demande.getConsultationId() == null
                || demande.getSpecialisteId() == null) {
            throw new IllegalArgumentException(
                    "La consultation et le spécialiste sont obligatoires"
            );
        }

        if (!repository.existsConsultationById(demande.getConsultationId())) {
            throw new NotFoundException("Consultation introuvable");
        }

        if (!repository.existsSpecialisteById(demande.getSpecialisteId())) {
            throw new NotFoundException("Spécialiste introuvable");
        }
    }

    public Priorite valider(CreerDemandeRequest demande) {
        if (demande == null) {
            throw new IllegalArgumentException("La demande est obligatoire");
        }

        if (demande.getQuestion() == null
                || demande.getQuestion().isBlank()) {
            throw new IllegalArgumentException("La question est obligatoire");
        }

        if (demande.getPriorite() == null) {
            throw new IllegalArgumentException("La priorité est obligatoire");
        }

        try {
            return Priorite.valueOf(demande.getPriorite());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Priorité inconnue");
        }
    }
            public DemandeExpertise creer(CreerDemandeRequest request) {

            Priorite priorite = valider(request);
            verifierReferences(request);

            DemandeExpertise demande = new DemandeExpertise();
            demande.setConsultationId(request.getConsultationId());
            demande.setSpecialisteId(request.getSpecialisteId());
            demande.setQuestion(request.getQuestion().trim());
            demande.setPriorite(priorite);
            demande.setStatut(StatutDemande.EN_ATTENTE);

            return repository.save(demande);
        }
        
public List<DemandeExpertise> listerParSpecialiste(
        int specialisteId,
        String statutDemande
) {
    List<DemandeExpertise> demandes =
            repository.findBySpecialisteId(specialisteId);

    StatutDemande statut = null;

    if (statutDemande != null && !statutDemande.isBlank()) {
        try {
            statut = StatutDemande.valueOf(
                    statutDemande.trim().toUpperCase()
            );
        } catch (IllegalArgumentException e) {
            throw new jakarta.ws.rs.BadRequestException(
                    "Statut invalide. Valeurs acceptées : EN_ATTENTE, TERMINEE"
            );
        }
    }

    final StatutDemande statutFiltre = statut;

    return demandes.stream()
            .filter(d -> statutFiltre == null
                    || d.getStatut() == statutFiltre)
            .sorted(Comparator.comparing(
                    d -> d.getPriorite() != Priorite.URGENTE
            ))
            .collect(Collectors.toList());
}

public DemandeExpertise trouverParConsultation(int consultationId) {
    return repository.findByConsultationId(consultationId)
            .orElseThrow(() -> new NotFoundException(
                    "Aucune demande trouvée pour cette consultation"
            ));
}
}   