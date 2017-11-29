package fr.enelia.dashboardapi.controllers;

import fr.enelia.dashboardapi.entities.StatistiquesMensuelles;
import fr.enelia.dashboardapi.services.StatistiquesMensuellesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatistiquesMensuellesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatistiquesMensuellesController.class);

    @Autowired
    private StatistiquesMensuellesService statistiquesMensuellesService;

    @GetMapping(value = "stats-mensuelles-by-user-id/{userId}")
    public Iterable<StatistiquesMensuelles> getStatsMensuellesByUserId(@PathVariable Long id) {
        return statistiquesMensuellesService.getStatistiquesMensuellesByUserId(id);
    }
}