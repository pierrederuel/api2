package fr.enelia.dashboardapi.controllers;

import fr.enelia.dashboardapi.entities.Objectif;
import fr.enelia.dashboardapi.services.ObjectifService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ObjectifController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectifController.class);

    @Autowired
    private ObjectifService objectifService;

    @PostMapping(value = "objectif")
    public Objectif createObjectif(@RequestBody Objectif objectif) {
        objectif = objectifService.createObjectif(objectif);
        return objectif;
    }

    @PutMapping("objectif")
    public Objectif updateObjectif(@RequestBody Objectif objectif) {
        Objectif result = objectifService.getObjectifById(objectif.getId());
        result.setMontant(objectif.getMontant());
        result = objectifService.updateObjectif(result);
        return result;
    }

    @GetMapping(value = "objectif")
    public Objectif getObjectifById(@RequestParam Long id) {
        return objectifService.getObjectifById(id);
    }

    @GetMapping(value = "objectifs")
    public Iterable<Objectif> getObjectifs() {
        return objectifService.getObjectifs();
    }
}
