package fr.enelia.dashboardapi.services.impl;

import fr.enelia.dashboardapi.dto.EmployeStats;
import fr.enelia.dashboardapi.dto.StatistiquesMensuellesDTO;
import fr.enelia.dashboardapi.dto.StatistiquesAnnuellesDTO;
import fr.enelia.dashboardapi.entities.*;
import fr.enelia.dashboardapi.repositories.ProspecteurRepository;
import fr.enelia.dashboardapi.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("prospecteurService")
public class ProspecteurServiceImpl implements ProspecteurService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProspecteurServiceImpl.class);

    @Autowired
    private ProspecteurRepository prospecteurRepository;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private PeriodeService periodeService;
    @Autowired
    private ResultatService resultatService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ObjectifService objectifService;
    @Autowired
    private StatistiquesMensuellesService statistiquesMensuellesService;
    @Autowired
    private StatistiquesAnnuellesService statistiquesAnnuellesService;

    @Override
    public Prospecteur createProspecteur(Prospecteur prospecteur) {
        LOGGER.info("createProspecteur");
        if (prospecteur.getPhoto() == null || "".equals(prospecteur.getPhoto())) {
            prospecteur.setPhoto("https://enelia.ddns.net/img/users/avatar.png");
        }
        //On créé un nouvel objectif pour le prospecteur
        Periode lastPeriode = periodeService.getLatestPeriode();
        prospecteur.getObjectifs().get(0).setPeriode(lastPeriode);
        prospecteur.getObjectifs().get(0).setEmploye(prospecteur);
        prospecteur = prospecteurRepository.save(prospecteur);


        //Création de l'utilisateur pour la connexion
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmploye(prospecteur);
        utilisateur.setPassword("password");
        utilisateur.setUsername(prospecteur.getPrenom().toLowerCase() + "." + prospecteur.getNom().toLowerCase());
        List<Role> roles = new ArrayList<Role>();
        Role role = roleService.getRoleById(3l);
        role.getUtilisateurs().add(utilisateur);
        roles.add(role);//TODO: changer la manière de récupérer le role
        //utilisateur.setRoles(roles);

        utilisateur = utilisateurService.createUtilisateur(utilisateur);

        //Création du résultat
        Resultat resultat = new Resultat();
        resultat.setMontantVendu(0);
        resultat.setPeriode(periodeService.getLatestPeriode());
        resultat.setProspecteur(prospecteur);
        resultat.setObjectif(prospecteur.getObjectifs().get(0));
        resultatService.createResultat(resultat);

        return (Prospecteur) utilisateur.getEmploye();
    }

    @Override
    public Prospecteur updateProspecteur(Prospecteur prospecteur) {
        LOGGER.info("updateProspecteur");
        prospecteur = prospecteurRepository.save(prospecteur);
        return prospecteur;
    }

    @Override
    public Prospecteur getProspecteurById(Long id) {
        LOGGER.info("getProspecteurById");
        return prospecteurRepository.findOne(id);
    }

    @Override
    public EmployeStats getProspecteurAvecStatsById(Long id) {
        LOGGER.info("getProspecteurAvecStatsById");
        List<StatistiquesMensuellesDTO> listStatsMensuelles = new ArrayList<>();
        List<StatistiquesAnnuellesDTO> listStatsAnnuelles = new ArrayList<>();
        Prospecteur temp = prospecteurRepository.findOne(id);
        Objectif objectif = objectifService.getLatestObjectifOfEmployeById(temp);
        Utilisateur user = utilisateurService.getUtilisateurByUsername(temp.getPrenom() + "." + temp.getNom());

        Iterable<StatistiquesMensuelles> statsMensuelles = statistiquesMensuellesService.getStatistiquesMensuellesByUserId(id);
        Iterator<StatistiquesMensuelles> itStatsMensuelles = statsMensuelles.iterator();
        while(itStatsMensuelles.hasNext()) {
            StatistiquesMensuelles current  = itStatsMensuelles.next();
            StatistiquesMensuellesDTO stat = new StatistiquesMensuellesDTO();
            double[] currentTab = new double[6];

            currentTab[0] = current.getNbVentes();
            currentTab[1] = current.getNbAnnulationClient();
            currentTab[2] = current.getNbAssises();
            currentTab[3] = current.getVisiteTechnique();
            currentTab[4] = current.getEcoHabitant();
            currentTab[5] = current.getNbFinancement();

            stat.setNbVentes(current.getNbVentes());
            stat.setNbAnnulationClient(current.getNbAnnulationClient());
            stat.setNbAssises(current.getNbAssises());
            stat.setVisiteTechnique(current.getVisiteTechnique());
            stat.setEcoHabitant(current.getEcoHabitant());
            stat.setNbFinancement(current.getNbFinancement());
            stat.setCaTotal(current.getCaTotal());
            stat.setCaReel(current.getCaReel());
            stat.setDateDebut(current.getPeriode().getDateDebut());
            stat.setDateFin(current.getPeriode().getDateFin());
            stat.setStats(currentTab);

            listStatsMensuelles.add(stat);
        }

        Iterable<StatistiquesAnnuelles> statsAnnuelles = statistiquesAnnuellesService.getStatistiquesAnnuellesByUserId(id);
        Iterator<StatistiquesAnnuelles> itStatsAnnuelles = statsAnnuelles.iterator();
        while(itStatsAnnuelles.hasNext()) {
            StatistiquesAnnuelles current  = itStatsAnnuelles.next();
            StatistiquesAnnuellesDTO stat = new StatistiquesAnnuellesDTO();
            double[] currentTab = new double[6];

            currentTab[0] = current.getNbVentes();
            currentTab[1] = current.getNbAnnulationClient();
            currentTab[2] = current.getNbAssises();
            currentTab[3] = current.getVisiteTechnique();
            currentTab[4] = current.getEcoHabitant();
            currentTab[5] = current.getNbFinancement();

            stat.setNbVentes(current.getNbVentes());
            stat.setNbAnnulationClient(current.getNbAnnulationClient());
            stat.setNbAssises(current.getNbAssises());
            stat.setVisiteTechnique(current.getVisiteTechnique());
            stat.setEcoHabitant(current.getEcoHabitant());
            stat.setNbFinancement(current.getNbFinancement());
            stat.setCaTotal(current.getCaTotal());
            stat.setCaReel(current.getCaReel());
            stat.setAnnee(String.valueOf(current.getPeriode().getDateDebut().getYear()));
            stat.setStats(currentTab);

            listStatsAnnuelles.add(stat);
        }

        EmployeStats result = new EmployeStats();
        result.setId(temp.getId());
        result.setNom(temp.getNom());
        result.setPhoto(temp.getPhoto());
        result.setPrenom(temp.getPrenom());
        result.setPassword(user.getPassword());
        result.setActif(user.isActive());
        result.setObjectif(objectif);
        result.setStatsMensuelles(listStatsMensuelles);
        result.setStatsAnnuelles(listStatsAnnuelles);

        return result;
    }

    @Override
    public Iterable<Prospecteur> getProspecteurs() {
        LOGGER.info("getProspecteurs");
        return prospecteurRepository.findAll();
    }
}
