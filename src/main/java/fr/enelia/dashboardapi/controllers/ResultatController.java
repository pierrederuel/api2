package fr.enelia.dashboardapi.controllers;

import fr.enelia.dashboardapi.dto.EmployeTV;
import fr.enelia.dashboardapi.dto.ResultatsTV;
import fr.enelia.dashboardapi.entities.Resultat;
import fr.enelia.dashboardapi.services.CommercialService;
import fr.enelia.dashboardapi.services.ResultatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class ResultatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultatController.class);

    @Autowired
    private ResultatService resultatService;
    @Autowired
    private CommercialService commercialService;

    @PostMapping(value = "resultat")
    public Resultat createResultat(@RequestBody Resultat resultat) {
        resultat = resultatService.createResultat(resultat);
        return resultat;
    }

    @PutMapping("resultat")
    public Resultat updateResultat(@RequestBody Resultat resultat) {
        resultat = resultatService.updateResultat(resultat);
        return resultat;
    }

    @GetMapping(value = "resultat")
    public Resultat getResultatById(@RequestParam Long id) {
        return resultatService.getResultatById(id);
    }

    @GetMapping(value = "resultats")
    public Iterable<Resultat> getResultats() {
        return resultatService.getResultats();
    }

    @GetMapping(value = "resultats-commerciaux-en-cours")
    public Iterable<Resultat> getResultatsCommerciauxMoisEncous() {
        return resultatService.getResultatsCommerciauxMoisEnCours();
    }

    @GetMapping(value = "resultats-prospecteurs-en-cours")
    public Iterable<Resultat> getResultatsProspecteursMoisEncous() {
        return resultatService.getResultatsProspecteursMoisEnCours();
    }

    @GetMapping(value = "resultats-commerciaux-precedents")
    public Iterable<Resultat> getResultatsCommerciauxMoisPrecedent() {
        return resultatService.getResultatsCommerciauxMoisPrecedent();
    }

    @GetMapping(value = "resultats-prospecteurs-precedents")
    public Iterable<Resultat> getResultatsProspecteursMoisPrecedent() {
        return resultatService.getResultatsProspecteursMoisPrecedent();
    }

    @GetMapping(value = "resultats-tv")
    public ResultatsTV getResultatsTV() {
        ResultatsTV resultatsTV = new ResultatsTV();
        List<EmployeTV> listCommerciaux = new ArrayList<>();
        Iterable<Resultat> resultatsCommerciaux = resultatService.getResultatsCommerciauxMoisEnCours();

        Iterator<Resultat> itCommerciaux = resultatsCommerciaux.iterator();
        while (itCommerciaux.hasNext()) {
            Resultat current = itCommerciaux.next();
            EmployeTV employe = new EmployeTV();
            employe.setObjectif(current.getObjectif().getMontant());
            employe.setMontantVendu(current.getMontantVendu());
            employe.setNom(current.getCommercial().getNom());
            employe.setPrenom(current.getCommercial().getPrenom());
            employe.setPhoto(current.getCommercial().getPhoto());
            employe.setId(current.getCommercial().getId());
            listCommerciaux.add(employe);
            resultatsTV.setCaEnCours(resultatsTV.getCaEnCours() + current.getMontantVendu());
        }
        resultatsTV.setResultatsCommerciaux(listCommerciaux);

        //Commerciaux mois précédent
        List<EmployeTV> listCommerciauxPrecedent = new ArrayList<>();
        resultatsCommerciaux = resultatService.getResultatsCommerciauxMoisPrecedent();

        itCommerciaux = resultatsCommerciaux.iterator();
        while (itCommerciaux.hasNext()) {
            Resultat current = itCommerciaux.next();
            EmployeTV employe = new EmployeTV();
            employe.setObjectif(current.getObjectif().getMontant());
            employe.setMontantVendu(current.getMontantVendu());
            employe.setNom(current.getCommercial().getNom());
            employe.setPrenom(current.getCommercial().getPrenom());
            employe.setPhoto(current.getCommercial().getPhoto());
            employe.setId(current.getCommercial().getId());
            listCommerciauxPrecedent.add(employe);
            resultatsTV.setCaPrecedent(resultatsTV.getCaPrecedent() + current.getMontantVendu());
        }
        resultatsTV.setResultatsCommerciauxPrecedent(listCommerciauxPrecedent);

        //Prospecteur mois en cours
        List<EmployeTV> listProspecteurs = new ArrayList<>();
        resultatsCommerciaux = resultatService.getResultatsProspecteursMoisEnCours();

        itCommerciaux = resultatsCommerciaux.iterator();
        while (itCommerciaux.hasNext()) {
            Resultat current = itCommerciaux.next();
            EmployeTV employe = new EmployeTV();
            employe.setObjectif(current.getObjectif().getMontant());
            employe.setMontantVendu(current.getMontantVendu());
            employe.setNom(current.getProspecteur().getNom());
            employe.setPrenom(current.getProspecteur().getPrenom());
            employe.setPhoto(current.getProspecteur().getPhoto());
            employe.setId(current.getProspecteur().getId());
            listProspecteurs.add(employe);
        }
        resultatsTV.setResultatsProspecteurs(listProspecteurs);

        //Prospecteur mois précédent
        List<EmployeTV> listProspecteursPrecedent = new ArrayList<>();
        resultatsCommerciaux = resultatService.getResultatsProspecteursMoisPrecedent();

        itCommerciaux = resultatsCommerciaux.iterator();
        while (itCommerciaux.hasNext()) {
            Resultat current = itCommerciaux.next();
            EmployeTV employe = new EmployeTV();
            employe.setObjectif(current.getObjectif().getMontant());
            employe.setMontantVendu(current.getMontantVendu());
            employe.setNom(current.getProspecteur().getNom());
            employe.setPrenom(current.getProspecteur().getPrenom());
            employe.setPhoto(current.getProspecteur().getPhoto());
            employe.setId(current.getProspecteur().getId());
            listProspecteursPrecedent.add(employe);
        }
        resultatsTV.setResultatsProspecteursPrecedent(listProspecteursPrecedent);

        return resultatsTV;
    }
}
