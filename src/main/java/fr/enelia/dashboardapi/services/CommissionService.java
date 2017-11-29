package fr.enelia.dashboardapi.services;

import fr.enelia.dashboardapi.entities.Commercial;
import fr.enelia.dashboardapi.entities.Commission;
import fr.enelia.dashboardapi.entities.Periode;
import org.springframework.stereotype.Service;

@Service
public interface CommissionService {
    public Commission createCommission(Commission commission);
    public Commission updateCommission(Commission commission);
    public Commission getCommissionById(Long id);
    public Iterable<Commission> getCommissionsByCommercial(Commercial commercial);
    public Iterable<Commission> getCommissions();
}
