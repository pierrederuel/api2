package fr.enelia.dashboardapi.repositories;

import fr.enelia.dashboardapi.entities.Commercial;
import fr.enelia.dashboardapi.entities.Commission;
import org.springframework.data.repository.CrudRepository;

public interface CommissionRepository extends CrudRepository<Commission, Long> {

    Iterable<Commission> findCommissionsByCommercial(Commercial commercial);


}
