package ma.youcode.teleexpertise.repository;

import java.util.Optional;
import ma.youcode.teleexpertise.model.Utilisateur;

public interface UtilisateurRepository {
    Optional<Utilisateur> findByEmail(String email);
}