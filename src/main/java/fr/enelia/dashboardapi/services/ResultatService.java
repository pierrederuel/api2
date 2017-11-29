package fr.enelia.dashboardapi.services;

import fr.enelia.dashboardapi.entities.*;
import org.springframework.stereotype.Service;

@Service
public interface ResultatService {
    public Resultat createResultat(Resultat resultat);
    public Resultat updateResultat(Resultat resultat);
    public void addVenteToResultat(Vente vente);
    public Resultat getResultatById(Long id);
    public Iterable<Resultat> getResultats();
    public Iterable<Resultat> getResultatsCommerciauxMoisEnCours();
    public Iterable<Resultat> getResultatsProspecteursMoisEnCours();
    public Iterable<Resultat> getResultatsCommerciauxMoisPrecedent();
    public Iterable<Resultat> getResultatsProspecteursMoisPrecedent();
    public Resultat getResultatByCommercialAndPeriode(Commercial commercial, Periode periode);
    public Resultat getResultatByProspecteurAndPeriode(Prospecteur prospecteur, Periode periode);
}
