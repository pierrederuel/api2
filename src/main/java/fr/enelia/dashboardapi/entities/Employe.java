package fr.enelia.dashboardapi.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="employe")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Employe implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String prenom;
    private String photo;
    @ManyToOne
    private Agence agence;
    @OneToMany(mappedBy = "employe", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Objectif> objectifs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public List<Objectif> getObjectifs() {
        return objectifs;
    }

    public void setObjectifs(List<Objectif> objectifs) {
        this.objectifs = objectifs;
    }
}
