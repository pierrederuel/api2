package fr.enelia.dashboardapi.controllers;

import fr.enelia.dashboardapi.entities.Agence;
import fr.enelia.dashboardapi.services.AgenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class AgenceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgenceController.class);

    @Autowired
    private AgenceService agenceService;

    @PostMapping(value = "agence")
    public Agence createAgence(@RequestBody Agence agence) {
        agence = agenceService.createAgence(agence);
        return agence;
    }

    @PutMapping("agence")
    public Agence updateAgence(@RequestBody Agence agence) {
        agence = agenceService.updateAgence(agence);
        return agence;
    }

    @GetMapping(value = "agence")
    public Agence getAgenceById(@RequestParam Long id) {
        return agenceService.getAgenceById(id);
    }

    @PreAuthorize("#oauth2.hasScope('trust')")
    @GetMapping(value = "agences")
    public Iterable<Agence> getAgences() {
        return agenceService.getAgences();
    }
}
