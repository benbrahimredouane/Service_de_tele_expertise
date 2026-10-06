package ma.youcode.teleexpertise.model;

import jakarta.persistence.*;

@Entity
@Table(name = "utilisateurs")
public class Utilisateur {

    public enum Role {
        INFIRMIER,
        GENERALISTE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    private String email;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Utilisateur() {
    }

    public Utilisateur(String nom, String email, String motDePasse, Role role) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}