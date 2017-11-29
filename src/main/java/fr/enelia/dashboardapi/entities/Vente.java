package fr.enelia.dashboardapi.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="vente")
public class Vente implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(mappedBy = "vente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Commission> commisions;
    @OneToOne(optional = true)
    private Prospecteur prospecteur;
    private String client;
    private long montantTotal;
    @ManyToOne
    private Periode periode;
    private LocalDate dateVente;
    private LocalDate dateAssise;
    private LocalDate dateEcoHabitant;
    private LocalDate dateVisiteTechnique;
    private LocalDate dateFinancement;
    private LocalDate dateAnnulation;
    private String commentaires;
    private boolean assise;
    private boolean ecoHabitant;
    private boolean visiteTechnique;
    private boolean financement;
    private boolean annulation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Commission> getCommisions() {
        return commisions;
    }

    public void setCommisions(List<Commission> commisions) {
        this.commisions = commisions;
    }

    public Prospecteur getProspecteur() {
        return prospecteur;
    }

    public void setProspecteur(Prospecteur prospecteur) {
        this.prospecteur = prospecteur;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public long getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(long montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Periode getPeriode() {
        return periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public LocalDate getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDate dateVente) {
        this.dateVente = dateVente;
    }

    public LocalDate getDateAssise() {
        return dateAssise;
    }

    public void setDateAssise(LocalDate dateAssise) {
        this.dateAssise = dateAssise;
    }

    public LocalDate getDateEcoHabitant() {
        return dateEcoHabitant;
    }

    public void setDateEcoHabitant(LocalDate dateEcoHabitant) {
        this.dateEcoHabitant = dateEcoHabitant;
    }

    public LocalDate getDateVisiteTechnique() {
        return dateVisiteTechnique;
    }

    public void setDateVisiteTechnique(LocalDate dateVisiteTechnique) {
        this.dateVisiteTechnique = dateVisiteTechnique;
    }

    public LocalDate getDateAnnulation() {
        return dateAnnulation;
    }

    public void setDateAnnulation(LocalDate dateAnnulation) {
        this.dateAnnulation = dateAnnulation;
    }

    public LocalDate getDateFinancement() {
        return dateFinancement;
    }

    public void setDateFinancement(LocalDate dateFinancement) {
        this.dateFinancement = dateFinancement;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public boolean isAssise() {
        return assise;
    }

    public void setAssise(boolean assise) {
        this.assise = assise;
    }

    public boolean isEcoHabitant() {
        return ecoHabitant;
    }

    public void setEcoHabitant(boolean ecoHabitant) {
        this.ecoHabitant = ecoHabitant;
    }

    public boolean isVisiteTechnique() {
        return visiteTechnique;
    }

    public void setVisiteTechnique(boolean visiteTechnique) {
        this.visiteTechnique = visiteTechnique;
    }

    public boolean isFinancement() {
        return financement;
    }

    public void setFinancement(boolean financement) {
        this.financement = financement;
    }

    public boolean isAnnulation() {
        return annulation;
    }

    public void setAnnulation(boolean annulation) {
        this.annulation = annulation;
    }
}
