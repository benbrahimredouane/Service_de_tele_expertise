package ma.youcode.teleexpertise.model;

import java.math.BigDecimal;

import jakarta.persistence.*;



@Entity
@Table(name = "specialistes")
public class Specialiste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "utilisateur_id", nullable = false, unique = true)
    private Utilisateur utilisateur;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Specialite specialite;

    @Column(nullable = false, precision = 10, scale = 2)
    private Long tarif;
    
    public Long getTarif() {
        return tarif;
    }

     public void setTarif(Long tarif) {
         this.tarif = tarif;
     }

    public Specialiste() {
    }

    public Specialiste(Utilisateur utilisateur, Specialite specialite) {
        this.utilisateur = utilisateur;
        this.specialite = specialite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }
}