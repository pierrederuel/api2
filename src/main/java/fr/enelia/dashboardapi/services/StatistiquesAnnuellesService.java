package fr.enelia.dashboardapi.services;

import fr.enelia.dashboardapi.entities.Periode;
import fr.enelia.dashboardapi.entities.StatistiquesAnnuelles;

public interface StatistiquesAnnuellesService {

    StatistiquesAnnuelles createStatistiquesAnnuelles(StatistiquesAnnuelles statistiquesMensuelles);
    StatistiquesAnnuelles updateStatistiquesAnnuelles(StatistiquesAnnuelles statistiquesMensuelles);
    Iterable<StatistiquesAnnuelles> getStatistiquesAnnuellesByUserId(Long id);
    StatistiquesAnnuelles getStatistiquesAnnuellesByUserIdAndPeriode(Long id, Periode periode);
}
