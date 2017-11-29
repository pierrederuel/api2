package fr.enelia.dashboardapi.repositories;

import fr.enelia.dashboardapi.entities.Commercial;
import org.springframework.data.repository.CrudRepository;

public interface CommercialRepository extends CrudRepository<Commercial, Long> {
    Commercial findCommercialByNomAndPrenom(String nom, String prenom);
}
