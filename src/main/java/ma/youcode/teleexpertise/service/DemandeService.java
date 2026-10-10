package ma.youcode.teleexpertise.service;

import java.util.Objects;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

import ma.youcode.teleexpertise.dto.CreerDemandeRequest;
import ma.youcode.teleexpertise.dto.RepondreDemandeRequest;
import ma.youcode.teleexpertise.model.DemandeExpertise;
import ma.youcode.teleexpertise.model.Priorite;
import ma.youcode.teleexpertise.model.Specialiste;
import ma.youcode.teleexpertise.model.StatutDemande;
import ma.youcode.teleexpertise.repository.DemandeExpertiseRepository;
import ma.youcode.teleexpertise.repository.SpecialisteRepository;

public class DemandeService {

    private final DemandeExpertiseRepository repository;
    private final SpecialisteRepository specialisteRepository;

    public DemandeService(
            DemandeExpertiseRepository repository,
            SpecialisteRepository specialisteRepository) {
        this.repository = repository;
        this.specialisteRepository = specialisteRepository;
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

    public DemandeExpertise repondre(
            int demandeId,
            String emailConnecte,
            RepondreDemandeRequest reponse) {

        if (reponse == null
                || reponse.getAvis() == null
                || reponse.getAvis().isBlank()
                || reponse.getRecommandations() == null
                || reponse.getRecommandations().isBlank()) {
            throw new BadRequestException(
                    "L'avis et les recommandations sont obligatoires"
            );
        }

        DemandeExpertise demande = repository.findById(demandeId)
                .orElseThrow(
                        () -> new NotFoundException("Demande introuvable")
                );

        Specialiste specialiste = specialisteRepository
                .findByUtilisateurEmail(emailConnecte)
                .orElseThrow(
                        () -> new ForbiddenException("Spécialiste introuvable")
                );

        if (!Objects.equals(
                demande.getSpecialisteId(),
                specialiste.getId())) {
            throw new ForbiddenException(
                    "Cette demande est attribuée à un autre spécialiste"
            );
        }

        demande.setAvis(reponse.getAvis().trim());
        demande.setRecommandations(reponse.getRecommandations().trim());
        demande.setStatut(StatutDemande.TERMINEE);

        return repository.update(demande);
    }
}