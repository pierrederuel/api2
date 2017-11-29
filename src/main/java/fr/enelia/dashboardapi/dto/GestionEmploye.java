package fr.enelia.dashboardapi.dto;

import java.util.ArrayList;
import java.util.List;

public class GestionEmploye {

    private long id;
    private long idEmploye;
    private String nom;
    private String prenom;
    private String photo;
    private double montantVendu;
    private double objectif;
    private List<String> roles = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(long idEmploye) {
        this.idEmploye = idEmploye;
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

    public double getMontantVendu() {
        return montantVendu;
    }

    public void setMontantVendu(double montantVendu) {
        this.montantVendu = montantVendu;
    }

    public double getObjectif() {
        return objectif;
    }

    public void setObjectif(double objectif) {
        this.objectif = objectif;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
