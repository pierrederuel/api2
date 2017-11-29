package fr.enelia.dashboardapi.controllers;

import fr.enelia.dashboardapi.dto.EmployeStats;
import fr.enelia.dashboardapi.entities.Commercial;
import fr.enelia.dashboardapi.services.CommercialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
public class CommercialController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommercialController.class);

    @Autowired
    CommercialService commercialService;

    @PostMapping(value = "commercial")
    public Commercial createCommercial(@RequestBody Commercial commercial) {
        LOGGER.error("createCommercial");
        commercial = commercialService.createCommercial(commercial);
        return commercial;
    }

    @PutMapping("commercial")
    public Commercial updateCommercial(@RequestBody Commercial commercial) {
        commercial = commercialService.updateCommercial(commercial);
        return commercial;
    }

    @GetMapping(value = "commercial/{userId}")
    public Commercial getCommercialById(@PathVariable("userId") Long userId) {
        return commercialService.getCommercialById(userId);
    }

    @GetMapping(value = "commercial-stats/{userId}")
    public EmployeStats getCommercialAvecStatsById(@PathVariable("userId") Long userId) {
        return commercialService.getCommercialAvecStatsById(userId);
    }

    @GetMapping(value="commerciaux")
    public Iterable<Commercial> getAllCommerciaux() {
        Iterable<Commercial> results = commercialService.getCommerciaux();
        Iterator<Commercial> itCommerciaux = results.iterator();
        while (itCommerciaux.hasNext()) {
            Commercial commercial = itCommerciaux.next();
            commercial.setObjectifs(null);
        }
        return results;
    }
}
