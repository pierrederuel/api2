package fr.enelia.dashboardapi.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Commission implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Commercial commercial;
    private double montant;
    @ManyToOne
    private Periode periode;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Vente vente;

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

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Periode getPeriode() {
        return periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }
}
