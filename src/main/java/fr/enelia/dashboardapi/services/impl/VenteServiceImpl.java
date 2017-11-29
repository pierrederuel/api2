package fr.enelia.dashboardapi.services.impl;

import fr.enelia.dashboardapi.dto.InputVenteDTO;
import fr.enelia.dashboardapi.entities.*;
import fr.enelia.dashboardapi.repositories.VenteRepository;
import fr.enelia.dashboardapi.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("venteService")
public class VenteServiceImpl implements VenteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VenteServiceImpl.class);

    @Autowired
    private VenteRepository venteRepository;
    @Autowired
    private CommercialService commercialService;
    @Autowired
    private PeriodeService periodeService;
    @Autowired
    private StatistiquesMensuellesService statistiquesMensuellesService;
    @Autowired
    private StatistiquesAnnuellesService statistiquesAnnuellesService;
    @Autowired
    private CommissionService commissionService;
    @Autowired
    private ResultatService resultatService;

    @Override
    public Vente createVente(InputVenteDTO vente) {
        LOGGER.info("createVente");

        StatistiquesMensuelles statistiquesMensuelles = null;
        StatistiquesAnnuelles statistiquesAnnuelles = null;

        if (vente.getCommerciaux() != null && vente.getCommerciaux().size() > 0) {
            List<Commission> commissions = new ArrayList<Commission>();
            //Gestion du cas plusieurs commerciaux, division de la commission
            double montantDivise = vente.getVente().getMontantTotal();
            if (vente.getCommerciaux().size() > 1) {
                montantDivise = vente.getVente().getMontantTotal() / vente.getCommerciaux().size();
            }
            for (int i = 0; i < vente.getCommerciaux().size(); i++) {
                Commission commission = new Commission();
                Commercial commercial = commercialService.getCommercialById(vente.getCommerciaux().get(i).getId());
                if (commercial.getId() == 0) {
                    commercial = commercialService.getCommercialByNomAndPrenom(commercial.getNom(), commercial.getPrenom());
                }
                commission.setCommercial(commercial);
                commission.setMontant(montantDivise);
                commission.setPeriode(periodeService.getLatestPeriode());
                commission.setVente(vente.getVente());
                commissions.add(commission);
                //On gère les stats pour chaque commercial
                statistiquesMensuelles = statistiquesMensuellesService.getStatistiquesMensuellesByUserIdAndPeriode(commercial.getId(), commission.getPeriode());
                statistiquesMensuelles.setCaTotal(statistiquesMensuelles.getCaTotal() + montantDivise);
                statistiquesMensuelles.setCaReel(statistiquesMensuelles.getCaReel() + montantDivise);
                statistiquesMensuelles.setNbVentes(statistiquesMensuelles.getNbVentes()+1);
                statistiquesMensuellesService.updateStatistiquesMensuelles(statistiquesMensuelles);

                statistiquesAnnuelles = statistiquesAnnuellesService.getStatistiquesAnnuellesByUserIdAndPeriode(commercial.getId(), commission.getPeriode());
                statistiquesAnnuelles.setCaTotal(statistiquesAnnuelles.getCaTotal() + montantDivise);
                statistiquesAnnuelles.setCaReel(statistiquesAnnuelles.getCaReel() + montantDivise);
                statistiquesAnnuelles.setNbVentes(statistiquesAnnuelles.getNbVentes()+1);
                statistiquesAnnuellesService.updateStatistiquesAnnuelles(statistiquesAnnuelles);
            }
            vente.getVente().setCommisions(commissions);
        }
        vente.getVente().setDateVente(LocalDate.now());
        vente.getVente().setPeriode(periodeService.getLatestPeriode());

        Vente resultVente = venteRepository.save(vente.getVente());

        //On gère les stats pour le prospecteur
        if (resultVente.getProspecteur() != null && resultVente.getProspecteur().getId() != 0) {
            statistiquesMensuelles = statistiquesMensuellesService.getStatistiquesMensuellesByUserIdAndPeriode(resultVente.getProspecteur().getId(), periodeService.getLatestPeriode());
            statistiquesMensuelles.setCaTotal(statistiquesMensuelles.getCaTotal() + resultVente.getMontantTotal());
            statistiquesMensuelles.setCaReel(statistiquesMensuelles.getCaReel() + resultVente.getMontantTotal());
            statistiquesMensuelles.setNbVentes(statistiquesMensuelles.getNbVentes() + 1);
            statistiquesMensuellesService.updateStatistiquesMensuelles(statistiquesMensuelles);

            statistiquesAnnuelles = statistiquesAnnuellesService.getStatistiquesAnnuellesByUserIdAndPeriode(resultVente.getProspecteur().getId(), periodeService.getLatestPeriode());
            statistiquesAnnuelles.setCaTotal(statistiquesAnnuelles.getCaTotal() + resultVente.getMontantTotal());
            statistiquesAnnuelles.setCaReel(statistiquesAnnuelles.getCaReel() + resultVente.getMontantTotal());
            statistiquesAnnuelles.setNbVentes(statistiquesAnnuelles.getNbVentes() + 1);
            statistiquesAnnuellesService.updateStatistiquesAnnuelles(statistiquesAnnuelles);
        }

        return resultVente;
    }

    @Override
    public Vente updateVente(Vente vente) {
        LOGGER.info("updateVente");
        vente = venteRepository.save(vente);
        return vente;
    }

    @Override
    public Vente updateVenteEmploye(Vente vente) {
        LOGGER.info("updateVenteEmploye");
        Vente current = getVenteById(vente.getId());

        if (!current.isAssise() && vente.isAssise()) {
            current.setAssise(vente.isAssise());
            current.setDateAssise(LocalDate.now());
            //Prospecteur
            //Gestion des stats mensuelles
            if (current.getProspecteur() != null) {
                StatistiquesMensuelles statistiquesMensuelles = statistiquesMensuellesService.getStatistiquesMensuellesByUserIdAndPeriode(current.getProspecteur().getId(), current.getPeriode());
                statistiquesMensuelles.setNbAssises(statistiquesMensuelles.getNbAssises() + 1);
                statistiquesMensuellesService.updateStatistiquesMensuelles(statistiquesMensuelles);
                //Gestion des stats annuelles
                StatistiquesAnnuelles statistiquesAnnuelles = statistiquesAnnuellesService.getStatistiquesAnnuellesByUserIdAndPeriode(current.getProspecteur().getId(), current.getPeriode());
                statistiquesAnnuelles.setNbAssises(statistiquesAnnuelles.getNbAssises() + 1);
                statistiquesAnnuellesService.updateStatistiquesAnnuelles(statistiquesAnnuelles);
            }
            //Commerciaux
            Iterator<Commission> itCommissions = current.getCommisions().iterator();
            while (itCommissions.hasNext()) {
                Commission currentCommission = itCommissions.next();
                StatistiquesMensuelles statistiquesMensuellesCommercial = statistiquesMensuellesService.getStatistiquesMensuellesByUserIdAndPeriode(currentCommission.getCommercial().getId(), current.getPeriode());
                statistiquesMensuellesCommercial.setNbAssises(statistiquesMensuellesCommercial.getNbAssises() + 1);
                statistiquesMensuellesService.updateStatistiquesMensuelles(statistiquesMensuellesCommercial);
                StatistiquesAnnuelles statistiquesAnnuellesCommercial = statistiquesAnnuellesService.getStatistiquesAnnuellesByUserIdAndPeriode(currentCommission.getCommercial().getId(), current.getPeriode());
                statistiquesAnnuellesCommercial.setNbAssises(statistiquesAnnuellesCommercial.getNbAssises() + 1);
                statistiquesAnnuellesService.updateStatistiquesAnnuelles(statistiquesAnnuellesCommercial);
            }
        }

        vente = updateVente(current);
        return vente;
    }

    @Override
    public Vente updateVenteManager(Vente vente) {
        LOGGER.info("updateVenteManager");
        Vente current = getVenteById(vente.getId());

        if (!current.isAssise() && vente.isAssise()) {
            current.setAssise(vente.isAssise());
            current.setDateAssise(LocalDate.now());
            //Prospecteur
            //Gestion des stats mensuelles
            if (current.getProspecteur() != null) {
                StatistiquesMensuelles statistiquesMensuelles = statistiquesMensuellesService.getStatistiquesMensuellesByUserIdAndPeriode(current.getProspecteur().getId(), current.getPeriode());
                StatistiquesAnnuelles statistiquesAnnuelles = statistiquesAnnuellesService.getStatistiquesAnnuellesByUserIdAndPeriode(current.getProspecteur().getId(), current.getPeriode());

                statistiquesMensuelles.setNbAssises(statistiquesMensuelles.getNbAssises() + 1);
                //Gestion des stats annuelles
                statistiquesAnnuelles.setNbAssises(statistiquesAnnuelles.getNbAssises() + 1);
                statistiquesMensuellesService.updateStatistiquesMensuelles(statistiquesMensuelles);
                statistiquesAnnuellesService.updateStatistiquesAnnuelles(statistiquesAnnuelles);
            }
            //Commerciaux
            Iterator<Commission> itCommissions = current.getCommisions().iterator();
            while (itCommissions.hasNext()) {
                Commission currentCommission = itCommissions.next();
                StatistiquesMensuelles statistiquesMensuellesCommercial = statistiquesMensuellesService.getStatistiquesMensuellesByUserIdAndPeriode(currentCommission.getCommercial().getId(), current.getPeriode());
                statistiquesMensuellesCommercial.setNbAssises(statistiquesMensuellesCommercial.getNbAssises() + 1);
                statistiquesMensuellesService.updateStatistiquesMensuelles(statistiquesMensuellesCommercial);
                StatistiquesAnnuelles statistiquesAnnuellesCommercial = statistiquesAnnuellesService.getStatistiquesAnnuellesByUserIdAndPeriode(currentCommission.getCommercial().getId(), current.getPeriode());
                statistiquesAnnuellesCommercial.setNbAssises(statistiquesAnnuellesCommercial.getNbAssises() + 1);
                statistiquesAnnuellesService.updateStatistiquesAnnuelles(statistiquesAnnuellesCommercial);
            }

        }
        if (!current.isEcoHabitant() && vente.isEcoHabitant()) {
            current.setEcoHabitant(vente.isEcoHabitant());
            current.setDateEcoHabitant(LocalDate.now());
            //Prospecteur
            //Gestion des stats mensuelles
            if (current.getProspecteur() != null) {
                StatistiquesMensuelles statistiquesMensuelles = statistiquesMensuellesService.getStatistiquesMensuellesByUserIdAndPeriode(current.getProspecteur().getId(), current.getPeriode());
                StatistiquesAnnuelles statistiquesAnnuelles = statistiquesAnnuellesService.getStatistiquesAnnuellesByUserIdAndPeriode(current.getProspecteur().getId(), current.getPeriode());

                statistiquesMensuelles.setEcoHabitant(statistiquesMensuelles.getEcoHabitant() + 1);
                //Gestion des stats annuelles
                statistiquesAnnuelles.setEcoHabitant(statistiquesAnnuelles.getEcoHabitant() + 1);
                statistiquesMensuellesService.updateStatistiquesMensuelles(statistiquesMensuelles);
                statistiquesAnnuellesService.updateStatistiquesAnnuelles(statistiquesAnnuelles);
            }
            //Commerciaux
            Iterator<Commission> itCommissions = current.getCommisions().iterator();
            while (itCommissions.hasNext()) {
                Commission currentCommission = itCommissions.next();
                StatistiquesMensuelles statistiquesMensuellesCommercial = statistiquesMensuellesService.getStatistiquesMensuellesByUserIdAndPeriode(currentCommission.getCommercial().getId(), current.getPeriode());
                statistiquesMensuellesCommercial.setEcoHabitant(statistiquesMensuellesCommercial.getEcoHabitant() + 1);
                statistiquesMensuellesService.updateStatistiquesMensuelles(statistiquesMensuellesCommercial);
                StatistiquesAnnuelles statistiquesAnnuellesCommercial = statistiquesAnnuellesService.getStatistiquesAnnuellesByUserIdAndPeriode(currentCommission.getCommercial().getId(), current.getPeriode());
                statistiquesAnnuellesCommercial.setEcoHabitant(statistiquesAnnuellesCommercial.getEcoHabitant() + 1);
                statistiquesAnnuellesService.updateStatistiquesAnnuelles(statistiquesAnnuellesCommercial);
            }
        }

        if (!current.isVisiteTechnique() && vente.isVisiteTechnique()) {
            current.setVisiteTechnique(vente.isVisiteTechnique());
            current.setDateVisiteTechnique(LocalDate.now());
            //Prospecteur
            //Gestion des stats mensuelles
            if (current.getProspecteur() != null) {
                StatistiquesMensuelles statistiquesMensuelles = statistiquesMensuellesService.getStatistiquesMensuellesByUserIdAndPeriode(current.getProspecteur().getId(), current.getPeriode());
                StatistiquesAnnuelles statistiquesAnnuelles = statistiquesAnnuellesService.getStatistiquesAnnuellesByUserIdAndPeriode(current.getProspecteur().getId(), current.getPeriode());

                statistiquesMensuelles.setVisiteTechnique(statistiquesMensuelles.getVisiteTechnique() + 1);
                //Gestion des stats annuelles
                statistiquesAnnuelles.setVisiteTechnique(statistiquesAnnuelles.getVisiteTechnique() + 1);
                statistiquesMensuellesService.updateStatistiquesMensuelles(statistiquesMensuelles);
                statistiquesAnnuellesService.updateStatistiquesAnnuelles(statistiquesAnnuelles);
            }
            //Commerciaux
            Iterator<Commission> itCommissions = current.getCommisions().iterator();
            while (itCommissions.hasNext()) {
                Commission currentCommission = itCommissions.next();
                StatistiquesMensuelles statistiquesMensuellesCommercial = statistiquesMensuellesService.getStatistiquesMensuellesByUserIdAndPeriode(currentCommission.getCommercial().getId(), current.getPeriode());
                statistiquesMensuellesCommercial.setVisiteTechnique(statistiquesMensuellesCommercial.getVisiteTechnique() + 1);
                statistiquesMensuellesService.updateStatistiquesMensuelles(statistiquesMensuellesCommercial);
                StatistiquesAnnuelles statistiquesAnnuellesCommercial = statistiquesAnnuellesService.getStatistiquesAnnuellesByUserIdAndPeriode(currentCommission.getCommercial().getId(), current.getPeriode());
                statistiquesAnnuellesCommercial.setVisiteTechnique(statistiquesAnnuellesCommercial.getVisiteTechnique() + 1);
                statistiquesAnnuellesService.updateStatistiquesAnnuelles(statistiquesAnnuellesCommercial);
            }
        }

        if (!current.isFinancement() && vente.isFinancement()) {
            current.setFinancement(vente.isFinancement());
            current.setDateFinancement(LocalDate.now());
            //Prospecteur
            //Gestion des stats mensuelles
            if (current.getProspecteur() != null) {
                StatistiquesMensuelles statistiquesMensuelles = statistiquesMensuellesService.getStatistiquesMensuellesByUserIdAndPeriode(current.getProspecteur().getId(), current.getPeriode());
                StatistiquesAnnuelles statistiquesAnnuelles = statistiquesAnnuellesService.getStatistiquesAnnuellesByUserIdAndPeriode(current.getProspecteur().getId(), current.getPeriode());

                statistiquesMensuelles.setNbFinancement(statistiquesMensuelles.getNbFinancement() + 1);
                //Gestion des stats annuelles
                statistiquesAnnuelles.setNbFinancement(statistiquesAnnuelles.getNbFinancement() + 1);
                statistiquesMensuellesService.updateStatistiquesMensuelles(statistiquesMensuelles);
                statistiquesAnnuellesService.updateStatistiquesAnnuelles(statistiquesAnnuelles);
            }
            //Commerciaux
            Iterator<Commission> itCommissions = current.getCommisions().iterator();
            while (itCommissions.hasNext()) {
                Commission currentCommission = itCommissions.next();
                StatistiquesMensuelles statistiquesMensuellesCommercial = statistiquesMensuellesService.getStatistiquesMensuellesByUserIdAndPeriode(currentCommission.getCommercial().getId(), current.getPeriode());
                statistiquesMensuellesCommercial.setNbFinancement(statistiquesMensuellesCommercial.getNbFinancement() + 1);
                statistiquesMensuellesService.updateStatistiquesMensuelles(statistiquesMensuellesCommercial);
                StatistiquesAnnuelles statistiquesAnnuellesCommercial = statistiquesAnnuellesService.getStatistiquesAnnuellesByUserIdAndPeriode(currentCommission.getCommercial().getId(), current.getPeriode());
                statistiquesAnnuellesCommercial.setNbFinancement(statistiquesAnnuellesCommercial.getNbFinancement() + 1);
                statistiquesAnnuellesService.updateStatistiquesAnnuelles(statistiquesAnnuellesCommercial);
            }

        }


        vente = updateVente(current);
        return vente;
    }

    @Override
    public Vente getVenteById(Long id) {
        LOGGER.info("updateVente");
        return venteRepository.findOne(id);
    }

    @Override
    public Iterable<Vente> getVentes() {
        LOGGER.info("updateVente");
        return venteRepository.findAll();
    }

    @Override
    public Iterable<Vente> getVentesByProspecteur(Prospecteur prospecteur) {
        LOGGER.info("getVentesByProspecteur");
        return venteRepository.findVentesByProspecteur(prospecteur);
    }

    @Override
    public Iterable<Vente> getVentesByCommercial(Commercial commercial) {
        LOGGER.info("getVentesByCommercial");
        return venteRepository.findVentesByCommercial(commercial);
    }

    @Override
    public Vente cancelVente(Vente vente) {
        LOGGER.info("cancelVente");
        //On gère le changement d'état avant l'annulation
        Vente result = updateVenteManager(vente);//this.getVenteById(vente.getId());
        result.getCommisions();

        //On gère l'annulation
        result.setDateAnnulation(LocalDate.now());
        result.setAnnulation(true);
        /*result.setCauseAnnulation(vente.getCauseAnnulation());*/

        //Gestion des commerciaux
        Iterable<Commission> commissions = result.getCommisions();

        Iterator<Commission> itCommissions = commissions.iterator();
        while (itCommissions.hasNext()) {
            Commission currentCommission = itCommissions.next();
            //Gestion du commercial
            //Gestion du resultat
            Resultat resultat = resultatService.getResultatByCommercialAndPeriode(currentCommission.getCommercial(), result.getPeriode());
            resultat.setMontantVendu(resultat.getMontantVendu() - currentCommission.getMontant());
            resultatService.updateResultat(resultat);
            //Gestion des stats mensuelles
            StatistiquesMensuelles statistiquesMensuelles = statistiquesMensuellesService.getStatistiquesMensuellesByUserIdAndPeriode(currentCommission.getCommercial().getId(), result.getPeriode());
            statistiquesMensuelles.setCaReel(statistiquesMensuelles.getCaReel() - currentCommission.getMontant());
            statistiquesMensuelles.setNbAnnulationClient(statistiquesMensuelles.getNbAnnulationClient() + 1);
            statistiquesMensuellesService.updateStatistiquesMensuelles(statistiquesMensuelles);
            //Gestion des stats annuelles
            StatistiquesAnnuelles statistiquesAnnuelles = statistiquesAnnuellesService.getStatistiquesAnnuellesByUserIdAndPeriode(currentCommission.getCommercial().getId(), result.getPeriode());
            statistiquesAnnuelles.setCaReel(statistiquesAnnuelles.getCaReel() - currentCommission.getMontant());
            statistiquesAnnuelles.setNbAnnulationClient(statistiquesAnnuelles.getNbAnnulationClient() + 1);
            statistiquesAnnuellesService.updateStatistiquesAnnuelles(statistiquesAnnuelles);
        }

        //Gestion du prospecteur
        //Gestion du resultat
        if (result.getProspecteur() != null) {
            Resultat resultat = resultatService.getResultatByProspecteurAndPeriode(result.getProspecteur(), result.getPeriode());
            resultat.setMontantVendu(resultat.getMontantVendu() - result.getMontantTotal());
            resultatService.updateResultat(resultat);
            //Gestion des stats mensuelles
            StatistiquesMensuelles statistiquesMensuelles = statistiquesMensuellesService.getStatistiquesMensuellesByUserIdAndPeriode(result.getProspecteur().getId(), result.getPeriode());
            statistiquesMensuelles.setCaReel(statistiquesMensuelles.getCaReel() - result.getMontantTotal());
            statistiquesMensuelles.setNbAnnulationClient(statistiquesMensuelles.getNbAnnulationClient() + 1);
            statistiquesMensuellesService.updateStatistiquesMensuelles(statistiquesMensuelles);
            //Gestion des stats annuelles
            StatistiquesAnnuelles statistiquesAnnuelles = statistiquesAnnuellesService.getStatistiquesAnnuellesByUserIdAndPeriode(result.getProspecteur().getId(), result.getPeriode());
            statistiquesAnnuelles.setCaReel(statistiquesAnnuelles.getCaReel() - result.getMontantTotal());
            statistiquesAnnuelles.setNbAnnulationClient(statistiquesAnnuelles.getNbAnnulationClient() + 1);
            statistiquesAnnuellesService.updateStatistiquesAnnuelles(statistiquesAnnuelles);
        }

        return this.updateVente(result);
    }
}
