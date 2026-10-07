package ma.youcode.teleexpertise.service;

import java.util.Comparator;
import java.util.List;

import ma.youcode.teleexpertise.model.Specialiste;
import ma.youcode.teleexpertise.model.Specialite;
import ma.youcode.teleexpertise.repository.SpecialisteRepository;

public class SpecialisteService {

    private final SpecialisteRepository repository;

    public SpecialisteService(SpecialisteRepository repository) {
        this.repository = repository;
    }
    public List<Specialiste> ListerAll(){
        return repository.findAll();
    }

    public List<Specialiste> listerParSpecialite(Specialite specialite) {
        if (specialite == null ) {
            throw new IllegalArgumentException("La spécialité est obligatoire");
        }

        return repository.findAll()
                .stream()
                .filter(s -> s.getSpecialite() == specialite)
                .sorted(Comparator.comparing(Specialiste::getTarif))
                .toList();
                
    }
}