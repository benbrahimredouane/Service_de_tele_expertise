package ma.youcode.teleexpertise.repository;

import ma.youcode.teleexpertise.model.Specialiste;

import java.util.List;
import java.util.Optional;

public interface SpecialisteRepository {


    public void save(Specialiste specialiste);

    public Optional<Specialiste> findById(int id) ;

    public List<Specialiste> findAll();

    public Optional<Specialiste> findByUtilisateurId(int utilisateurId);

    public Optional<Specialiste> findByUtilisateurEmail(String email);
}
