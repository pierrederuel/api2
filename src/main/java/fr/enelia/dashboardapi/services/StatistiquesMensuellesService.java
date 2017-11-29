package fr.enelia.dashboardapi.services;

import fr.enelia.dashboardapi.entities.Periode;
import fr.enelia.dashboardapi.entities.StatistiquesMensuelles;

public interface StatistiquesMensuellesService {

    StatistiquesMensuelles createStatistiquesMensuelles(StatistiquesMensuelles statistiquesMensuelles);
    StatistiquesMensuelles updateStatistiquesMensuelles(StatistiquesMensuelles statistiquesMensuelles);
    Iterable<StatistiquesMensuelles> getStatistiquesMensuellesByUserId(Long id);
    StatistiquesMensuelles getStatistiquesMensuellesByUserIdAndPeriode(Long id, Periode periode);
}
