package fr.enelia.dashboardapi.controllers;

import fr.enelia.dashboardapi.dto.EmployeStats;
import fr.enelia.dashboardapi.entities.Prospecteur;
import fr.enelia.dashboardapi.services.ProspecteurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

@RestController
public class ProspecteurController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProspecteurController.class);

    @Autowired
    private ProspecteurService prospecteurService;

    @PostMapping(value = "prospecteur")
    public Prospecteur createProspecteur(@RequestBody Prospecteur prospecteur) {
        LOGGER.info(prospecteur.getPrenom());
        prospecteur = prospecteurService.createProspecteur(prospecteur);
        return prospecteur;
    }

    @PutMapping("prospecteur")
    public Prospecteur updateProspecteur(@RequestBody Prospecteur prospecteur) {
        prospecteur = prospecteurService.updateProspecteur(prospecteur);
        return prospecteur;
    }

    @GetMapping(value = "prospecteur/{userId}")
    public Prospecteur getProspecteurById(@PathVariable Long userId) {
        return prospecteurService.getProspecteurById(userId);
    }

    @GetMapping(value = "prospecteur-stats/{userId}")
    public EmployeStats getProspecteurAvecStatsById(@PathVariable Long userId) {
        return prospecteurService.getProspecteurAvecStatsById(userId);
    }

    @GetMapping(value = "prospecteurs")
    public Iterable<Prospecteur> getProspecteurs() {
        Iterable<Prospecteur> results = prospecteurService.getProspecteurs();
        Iterator<Prospecteur> itProspecteurs = results.iterator();
        while (itProspecteurs.hasNext()) {
            Prospecteur prospecteur = itProspecteurs.next();
            prospecteur.setObjectifs(null);
        }
        return results;
    }
}
