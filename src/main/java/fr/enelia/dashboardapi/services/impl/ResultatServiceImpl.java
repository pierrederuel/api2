package fr.enelia.dashboardapi.services.impl;

import fr.enelia.dashboardapi.entities.*;
import fr.enelia.dashboardapi.repositories.ResultatRepository;
import fr.enelia.dashboardapi.services.PeriodeService;
import fr.enelia.dashboardapi.services.ResultatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service("resultatService")
public class ResultatServiceImpl implements ResultatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultatServiceImpl.class);

    @Autowired
    private ResultatRepository resultatRepository;
    @Autowired
    private PeriodeService periodeService;

    @Override
    public Resultat createResultat(Resultat resultat) {
        LOGGER.info("createResultat");
        resultat = resultatRepository.save(resultat);
        return resultat;
    }

    @Override
    public Resultat updateResultat(Resultat resultat) {
        LOGGER.info("updateResultat");
        resultat = resultatRepository.save(resultat);
        return resultat;
    }

    @Override
    public void addVenteToResultat(Vente vente) {
        LOGGER.info("addVenteToResultat");
        Periode currentPeriode = vente.getPeriode();
        // Ajout des commissions aux commerciaux
        for (int i = 0; i < vente.getCommisions().size(); i++) {
            Commission currentCommission = vente.getCommisions().get(i);
            Commercial currentCommercial = currentCommission.getCommercial();
            Resultat currentResultat = resultatRepository.findResultatByCommercialAndPeriode(currentCommercial, currentPeriode);
            currentResultat.setMontantVendu(currentResultat.getMontantVendu() + currentCommission.getMontant());
            this.updateResultat(currentResultat);
        }
        //Ajout de la vente au prospecteur
        Prospecteur currentProspecteur = vente.getProspecteur();
        if (currentProspecteur != null) {
            Resultat currentResultat = resultatRepository.findResultatByProspecteurAndPeriode(currentProspecteur, currentPeriode);
            currentResultat.setMontantVendu(currentResultat.getMontantVendu() + vente.getMontantTotal());
            this.updateResultat(currentResultat);
        }
    }

    @Override
    public Resultat getResultatById(Long id) {
        LOGGER.info("getResultatById");
        return resultatRepository.findOne(id);
    }

    @Override
    public Iterable<Resultat> getResultats() {
        LOGGER.info("getResultats");
        return resultatRepository.findAll();
    }

    @Override
    public Iterable<Resultat> getResultatsCommerciauxMoisEnCours() {
        LOGGER.info("getResultatsCommerciauxMoisEnCours");
        Iterable<Resultat> resultats = resultatRepository.findResultatCommerciauxByPeriodeId(periodeService.getLatestPeriode().getId());
        Iterator<Resultat> itResultats = resultats.iterator();
        while (itResultats.hasNext()) {
            Resultat resultat = itResultats.next();
            resultat.getObjectif();
            //resultat.getCommercial().setObjectifs(null);
            //resultat.getCommercial().setCommissions(null);
        }
        return resultats;
    }

    @Override
    public Iterable<Resultat> getResultatsProspecteursMoisEnCours() {
        LOGGER.info("getResultatsProspecteursMoisEnCours");
        Iterable<Resultat> resultats = resultatRepository.findResultatProspecteursByPeriodeId(periodeService.getLatestPeriode().getId());
        Iterator<Resultat> itResultats = resultats.iterator();
        while (itResultats.hasNext()) {
            Resultat resultat = itResultats.next();
            resultat.getObjectif();
            //resultat.getCommercial().setObjectifs(null);
            //resultat.getCommercial().setCommissions(null);
        }
        return resultats;
    }

    @Override
    public Iterable<Resultat> getResultatsCommerciauxMoisPrecedent() {
        LOGGER.info("getResultatsCommerciauxMoisPrecedent");
        Iterable<Resultat> resultats = resultatRepository.findResultatCommerciauxByPeriodeId(periodeService.getPeriodeBeforeLast().getId());
        Iterator<Resultat> itResultats = resultats.iterator();
        while (itResultats.hasNext()) {
            Resultat resultat = itResultats.next();
            //resultat.getCommercial().setObjectifs(null);
            //resultat.getCommercial().setCommissions(null);
        }
        return resultats;
    }

    @Override
    public Iterable<Resultat> getResultatsProspecteursMoisPrecedent() {
        LOGGER.info("getResultatsProspecteursMoisPrecedent");
        Iterable<Resultat> resultats = resultatRepository.findResultatProspecteursByPeriodeId(periodeService.getPeriodeBeforeLast().getId());
        Iterator<Resultat> itResultats = resultats.iterator();
        while (itResultats.hasNext()) {
            Resultat resultat = itResultats.next();
            //resultat.getCommercial().setObjectifs(null);
            //resultat.getCommercial().setCommissions(null);
        }
        return resultats;
    }

    @Override
    public Resultat getResultatByCommercialAndPeriode(Commercial commercial, Periode periode) {
        LOGGER.info("getResultatByCommercialAndPeriode");
        return resultatRepository.findResultatByCommercialAndPeriode(commercial, periode);
    }

    @Override
    public Resultat getResultatByProspecteurAndPeriode(Prospecteur prospecteur, Periode periode) {
        LOGGER.info("getResultatByProspecteurAndPeriode");
        return resultatRepository.findResultatByProspecteurAndPeriode(prospecteur, periode);
    }
}
