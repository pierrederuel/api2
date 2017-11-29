package fr.enelia.dashboardapi.repositories;

import fr.enelia.dashboardapi.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
