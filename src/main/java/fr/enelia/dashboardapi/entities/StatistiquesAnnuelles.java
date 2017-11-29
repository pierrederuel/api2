package fr.enelia.dashboardapi.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="statistiques_annuelles")
public class StatistiquesAnnuelles implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Periode periode;
    @ManyToOne
    private Employe employe;
    private double caTotal;
    private double caReel;
    private int nbVentes;
    private int nbAssises;
    private int nbAnnulationClient;
    private int ecoHabitant;
    private int visiteTechnique;
    private int nbFinancement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCaTotal() {
        return caTotal;
    }

    public void setCaTotal(double caTotal) {
        this.caTotal = caTotal;
    }

    public double getCaReel() {
        return caReel;
    }

    public void setCaReel(double caReel) {
        this.caReel = caReel;
    }

    public int getNbVentes() {
        return nbVentes;
    }

    public Periode getPeriode() {
        return periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public void setNbVentes(int nbVentes) {
        this.nbVentes = nbVentes;
    }

    public int getNbAssises() {
        return nbAssises;
    }

    public void setNbAssises(int nbAssises) {
        this.nbAssises = nbAssises;
    }

    public int getNbAnnulationClient() {
        return nbAnnulationClient;
    }

    public void setNbAnnulationClient(int nbAnnulationClient) {
        this.nbAnnulationClient = nbAnnulationClient;
    }

    public int getEcoHabitant() {
        return ecoHabitant;
    }

    public void setEcoHabitant(int ecoHabitant) {
        this.ecoHabitant = ecoHabitant;
    }

    public int getVisiteTechnique() {
        return visiteTechnique;
    }

    public void setVisiteTechnique(int visiteTechnique) {
        this.visiteTechnique = visiteTechnique;
    }

    public int getNbFinancement() {
        return nbFinancement;
    }

    public void setNbFinancement(int nbFinancement) {
        this.nbFinancement = nbFinancement;
    }
}
