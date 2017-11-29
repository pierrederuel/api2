package fr.enelia.dashboardapi.dto;

import fr.enelia.dashboardapi.entities.Objectif;

import java.util.List;

public class EmployeStats {
    private Long id;
    private String nom;
    private String prenom;
    private String photo;
    private String password;
    private boolean actif;
    private Objectif objectif;
    private List<StatistiquesMensuellesDTO> statsMensuelles;
    private List<StatistiquesAnnuellesDTO> statsAnnuelles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public Objectif getObjectif() {
        return objectif;
    }

    public void setObjectif(Objectif objectif) {
        this.objectif = objectif;
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

    public List<StatistiquesMensuellesDTO> getStatsMensuelles() {
        return statsMensuelles;
    }

    public void setStatsMensuelles(List<StatistiquesMensuellesDTO> statsMensuelles) {
        this.statsMensuelles = statsMensuelles;
    }

    public List<StatistiquesAnnuellesDTO> getStatsAnnuelles() {
        return statsAnnuelles;
    }

    public void setStatsAnnuelles(List<StatistiquesAnnuellesDTO> statsAnnuelles) {
        this.statsAnnuelles = statsAnnuelles;
    }
}
