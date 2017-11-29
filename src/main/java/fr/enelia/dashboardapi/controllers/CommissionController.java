package fr.enelia.dashboardapi.controllers;

import fr.enelia.dashboardapi.entities.Commission;
import fr.enelia.dashboardapi.services.CommissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommissionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommissionController.class);

    @Autowired
    private CommissionService commissionService;

    @PostMapping(value = "commission")
    public Commission createCommission(@RequestBody Commission commission) {
        commission = commissionService.createCommission(commission);
        return commission;
    }

    @PutMapping("commission")
    public Commission updateCommission(@RequestBody Commission commission) {
        commission = commissionService.updateCommission(commission);
        return commission;
    }

    @GetMapping(value = "commission")
    public Commission getCommissionById(@RequestParam Long id) {
        return commissionService.getCommissionById(id);
    }

    @GetMapping(value = "commissions")
    public Iterable<Commission> getCommissions() {
        return commissionService.getCommissions();
    }

}
