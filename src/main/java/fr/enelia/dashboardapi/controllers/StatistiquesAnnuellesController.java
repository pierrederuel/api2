package fr.enelia.dashboardapi.controllers;

import fr.enelia.dashboardapi.entities.StatistiquesAnnuelles;
import fr.enelia.dashboardapi.services.StatistiquesAnnuellesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatistiquesAnnuellesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatistiquesAnnuellesController.class);

    @Autowired
    private StatistiquesAnnuellesService statistiquesAnnuellesService;

    @GetMapping(value = "stats-annuelles-by-user-id/{userId}")
    public Iterable<StatistiquesAnnuelles> getStatsMensuellesByUserId(@PathVariable Long id) {
        return statistiquesAnnuellesService.getStatistiquesAnnuellesByUserId(id);
    }
}