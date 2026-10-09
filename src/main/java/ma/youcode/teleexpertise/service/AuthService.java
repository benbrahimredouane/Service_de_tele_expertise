package ma.youcode.teleexpertise.service;

import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;

import ma.youcode.teleexpertise.model.Utilisateur;
import ma.youcode.teleexpertise.repository.UtilisateurRepository;

public class AuthService {

    private final UtilisateurRepository utilisateurRepository;

    public AuthService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public Optional<Utilisateur> authentifier(String email,
                                               String motDePasse) {
        Optional<Utilisateur> utilisateur =
                utilisateurRepository.findByEmail(email);

        if (utilisateur.isEmpty()) {
            return Optional.empty();
        }

        boolean motDePasseCorrect = BCrypt.checkpw(
                motDePasse,
                utilisateur.get().getMotDePasse()
        );

        return motDePasseCorrect
                ? utilisateur
                : Optional.empty();
    }
}