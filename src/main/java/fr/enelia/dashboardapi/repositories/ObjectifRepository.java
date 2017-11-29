package fr.enelia.dashboardapi.repositories;

import fr.enelia.dashboardapi.entities.Employe;
import fr.enelia.dashboardapi.entities.Objectif;
import fr.enelia.dashboardapi.entities.Periode;
import org.springframework.data.repository.CrudRepository;

public interface ObjectifRepository extends CrudRepository<Objectif, Long> {

    Objectif findObjectifByEmployeAndPeriode(Employe employe, Periode periodeId);

}
