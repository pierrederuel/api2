package fr.enelia.dashboardapi.repositories;

import fr.enelia.dashboardapi.entities.Employe;
import org.springframework.data.repository.CrudRepository;

public interface EmployeRepository extends CrudRepository<Employe, Long> {
}
