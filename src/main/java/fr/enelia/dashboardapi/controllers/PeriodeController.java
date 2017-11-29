package fr.enelia.dashboardapi.controllers;

import fr.enelia.dashboardapi.entities.Periode;
import fr.enelia.dashboardapi.services.PeriodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
public class PeriodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodeController.class);

    @Autowired
    private PeriodeService periodeService;

    @PostMapping(value = "periode")
    public Periode createPeriode(@RequestBody Periode periode) {
        periode = periodeService.createPeriode(periode);
        return periode;
    }

    @PutMapping("periode")
    public Periode updatePeriode(@RequestBody Periode periode) {
        periode = periodeService.updatePeriode(periode);
        return periode;
    }

    @GetMapping(value = "periode")
    public Periode getPeriodeById(@RequestParam Long id) {
        return periodeService.getPeriodeById(id);
    }

    @GetMapping(value = "periodes")
    public Iterable<Periode> getPeriodes() {
        return periodeService.getPeriodes();
    }
}
