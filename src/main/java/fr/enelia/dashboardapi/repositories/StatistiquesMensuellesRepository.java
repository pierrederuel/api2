package fr.enelia.dashboardapi.repositories;

import fr.enelia.dashboardapi.entities.Employe;
import fr.enelia.dashboardapi.entities.Periode;
import fr.enelia.dashboardapi.entities.StatistiquesMensuelles;
import org.springframework.data.repository.CrudRepository;

public interface StatistiquesMensuellesRepository extends CrudRepository<StatistiquesMensuelles, Long> {

    Iterable<StatistiquesMensuelles> getStatistiquesMensuellesByEmploye(Employe employe);
    StatistiquesMensuelles getStatistiquesMensuellesByEmployeAndPeriode(Employe employe, Periode periode);

}
