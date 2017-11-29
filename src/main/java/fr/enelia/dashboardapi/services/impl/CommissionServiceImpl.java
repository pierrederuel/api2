package fr.enelia.dashboardapi.services.impl;

import fr.enelia.dashboardapi.entities.Commercial;
import fr.enelia.dashboardapi.entities.Commission;
import fr.enelia.dashboardapi.entities.Periode;
import fr.enelia.dashboardapi.repositories.CommissionRepository;
import fr.enelia.dashboardapi.services.CommissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("commissionService")
public class CommissionServiceImpl implements CommissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommissionServiceImpl.class);

    @Autowired
    private CommissionRepository commissionRepository;

    @Override
    public Commission createCommission(Commission commission) {
        LOGGER.info("createCommission");
        commission = commissionRepository.save(commission);
        return commission;
    }

    @Override
    public Commission updateCommission(Commission commission) {
        LOGGER.info("updateCommission");
        commission = commissionRepository.save(commission);
        return commission;
    }

    @Override
    public Commission getCommissionById(Long id) {
        LOGGER.info("getCommissionById");
        return commissionRepository.findOne(id);
    }

    @Override
    public Iterable<Commission> getCommissionsByCommercial(Commercial commercial) {
        LOGGER.info("getCommissionByCommercial");
        return commissionRepository.findCommissionsByCommercial(commercial);
    }

    @Override
    public Iterable<Commission> getCommissions() {
        LOGGER.info("getCommissions");
        return commissionRepository.findAll();
    }
}
