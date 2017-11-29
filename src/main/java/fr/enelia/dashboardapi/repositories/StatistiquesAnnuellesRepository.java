package fr.enelia.dashboardapi.repositories;

import fr.enelia.dashboardapi.entities.Employe;
import fr.enelia.dashboardapi.entities.Periode;
import fr.enelia.dashboardapi.entities.StatistiquesAnnuelles;
import org.springframework.data.repository.CrudRepository;

public interface StatistiquesAnnuellesRepository extends CrudRepository<StatistiquesAnnuelles, Long> {
    Iterable<StatistiquesAnnuelles> getStatistiquesAnnuellesByEmploye(Employe employe);
    StatistiquesAnnuelles getStatistiquesAnnuellesByEmployeAndPeriode(Employe employe, Periode periode);
    StatistiquesAnnuelles getStatsAnnuellesByEmploye(Employe employe);
}
