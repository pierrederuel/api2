package fr.enelia.dashboardapi.controllers;

import fr.enelia.dashboardapi.entities.*;
import fr.enelia.dashboardapi.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.Calendar;
import java.util.Iterator;

@RestController
public class Scheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    private PeriodeService periodeService;
    @Autowired
    private ObjectifService objectifService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private ResultatService resultatService;
    @Autowired
    private CommercialService commercialService;
    @Autowired
    private ProspecteurService prospecteurService;
    @Autowired
    private StatistiquesMensuellesService statistiquesMensuellesService;
    @Autowired
    private StatistiquesAnnuellesService statistiquesAnnuellesService;

    //@Scheduled(cron="0 0 0 1 1/1 *") //Chaque 1er du mois à 00h01, on crée la nouvelle période
    @GetMapping(value = "init-data")
    public void initData() {

        Periode periode =  periodeService.getPeriodeById(1l);
        if (periode != null) {
            //TODO: Modifier l'appel pour récupérer l'ensemble des commerciaux / prospecteurs encore actifs
            //On créé ensuite l'objectif de chaque commercial / prospecteur en reprenant les chiffres du mois précédent
            Iterable<Utilisateur> utilisateurs = utilisateurService.getUtilisateursActifs();
            Iterator<Utilisateur> itUtilisateurs = utilisateurs.iterator();
            while (itUtilisateurs.hasNext()) {
                Utilisateur currentUtilisateur = itUtilisateurs.next();currentUtilisateur.getRoles();
                if (currentUtilisateur.isActive()
                        && currentUtilisateur.getEmploye() != null
                        && (currentUtilisateur.getRoles().get(0).getNom().equals("ROLE_COMMERCIAL") || currentUtilisateur.getRoles().get(0).getNom().equals("ROLE_PROSPECTEUR") )) {
                    //On récupère l'objectif de la période précédente, on créé un nouvel objectif avec le même montant
                    Objectif lastObjectif = null;

                    Objectif objectif = new Objectif();
                    objectif.setMontant(20000);//lastObjectif.getMontant());
                    objectif.setPeriode(periode);
                    objectif.setEmploye(currentUtilisateur.getEmploye());
                    currentUtilisateur.getEmploye().getObjectifs().add(objectif);
                    utilisateurService.updateUtilisateur(currentUtilisateur);

                    //On créé un Resultat pour gérer le classement
                    Resultat resultat = new Resultat();
                    if (currentUtilisateur.getRoles().get(0).getNom().equals("ROLE_COMMERCIAL")) {
                        resultat.setCommercial(commercialService.getCommercialById(currentUtilisateur.getEmploye().getId()));
                    } else if (currentUtilisateur.getRoles().get(0).getNom().equals("ROLE_PROSPECTEUR")) {
                        resultat.setProspecteur(prospecteurService.getProspecteurById(currentUtilisateur.getEmploye().getId()));
                    }
                    resultat.setMontantVendu(0);
                    resultat.setObjectif(objectif);
                    resultat.setPeriode(periode);

                    resultatService.createResultat(resultat);

                    StatistiquesMensuelles statistiquesMensuelles = new StatistiquesMensuelles();
                    statistiquesMensuelles.setPeriode(periode);
                    statistiquesMensuelles.setEmploye(currentUtilisateur.getEmploye());

                    statistiquesMensuellesService.createStatistiquesMensuelles(statistiquesMensuelles);

                    StatistiquesAnnuelles statistiquesAnnuelles = new StatistiquesAnnuelles();
                    statistiquesAnnuelles.setPeriode(periode);
                    statistiquesAnnuelles.setEmploye(currentUtilisateur.getEmploye());

                    statistiquesAnnuellesService.createStatistiquesAnnuelles(statistiquesAnnuelles);

                }

            }
        }
    }



    //@Scheduled(cron="0 0 0 1 1/1 *") //Chaque 1er du mois à 00h01, on crée la nouvelle période
    @GetMapping(value = "init-new-periode")
    public void createNewPeriode() {

        Periode periode =  periodeService.generatePeriode(); //periodeService.getPeriodeById(1l);
        if (periode != null) {
            //TODO: Modifier l'appel pour récupérer l'ensemble des commerciaux / prospecteurs encore actifs
            //On créé ensuite l'objectif de chaque commercial / prospecteur en reprenant les chiffres du mois précédent
            Iterable<Utilisateur> utilisateurs = utilisateurService.getUtilisateursActifs();
            Iterator<Utilisateur> itUtilisateurs = utilisateurs.iterator();
            while (itUtilisateurs.hasNext()) {
                Utilisateur currentUtilisateur = itUtilisateurs.next();currentUtilisateur.getRoles();
                if (currentUtilisateur.isActive()
                        && currentUtilisateur.getEmploye() != null
                        && (currentUtilisateur.getRoles().get(0).getNom().equals("ROLE_COMMERCIAL") || currentUtilisateur.getRoles().get(0).getNom().equals("ROLE_PROSPECTEUR") )) {
                    //On récupère l'objectif de la période précédente, on créé un nouvel objectif avec le même montant
                    Objectif lastObjectif = null;

                    Objectif objectif = new Objectif();
                    objectif.setMontant(20000);//lastObjectif.getMontant());
                    objectif.setPeriode(periode);
                    objectif.setEmploye(currentUtilisateur.getEmploye());
                    currentUtilisateur.getEmploye().getObjectifs().add(objectif);
                    utilisateurService.updateUtilisateur(currentUtilisateur);

                    //On créé un Resultat pour gérer le classement
                    Resultat resultat = new Resultat();
                    if (currentUtilisateur.getRoles().get(0).getNom().equals("ROLE_COMMERCIAL")) {
                        resultat.setCommercial(commercialService.getCommercialById(currentUtilisateur.getEmploye().getId()));
                    } else if (currentUtilisateur.getRoles().get(0).getNom().equals("ROLE_PROSPECTEUR")) {
                        resultat.setProspecteur(prospecteurService.getProspecteurById(currentUtilisateur.getEmploye().getId()));
                    }
                    resultat.setMontantVendu(0);
                    resultat.setObjectif(objectif);
                    resultat.setPeriode(periode);

                    resultatService.createResultat(resultat);

                    StatistiquesMensuelles statistiquesMensuelles = new StatistiquesMensuelles();
                    statistiquesMensuelles.setPeriode(periode);
                    statistiquesMensuelles.setEmploye(currentUtilisateur.getEmploye());

                    statistiquesMensuellesService.createStatistiquesMensuelles(statistiquesMensuelles);
                }

            }
        }
    }

}
