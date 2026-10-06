package ma.youcode.teleexpertise.service;

import ma.youcode.teleexpertise.dto.CreerDemandeRequest;
import ma.youcode.teleexpertise.model.Priorite;

public class DemandeService {

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
}