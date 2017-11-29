package fr.enelia.dashboardapi.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="resultat")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Resultat.class)
public class Resultat implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Commercial commercial;
    @ManyToOne
    private Prospecteur prospecteur;
    @ManyToOne
    private Periode periode;
    @OneToOne
    private Objectif objectif;
    private double montantVendu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commercial getCommercial() {
        return commercial;
    }

    public void setCommercial(Commercial commercial) {
        this.commercial = commercial;
    }

    public Prospecteur getProspecteur() {
        return prospecteur;
    }

    public void setProspecteur(Prospecteur prospecteur) {
        this.prospecteur = prospecteur;
    }

    public Periode getPeriode() {
        return periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public Objectif getObjectif() {
        return objectif;
    }

    public void setObjectif(Objectif objectif) {
        this.objectif = objectif;
    }

    public double getMontantVendu() {
        return montantVendu;
    }

    public void setMontantVendu(double montantVendu) {
        this.montantVendu = montantVendu;
    }
}
