package fr.enelia.dashboardapi.services.impl;

import fr.enelia.dashboardapi.entities.Employe;
import fr.enelia.dashboardapi.entities.Periode;
import fr.enelia.dashboardapi.entities.StatistiquesAnnuelles;
import fr.enelia.dashboardapi.entities.StatistiquesMensuelles;
import fr.enelia.dashboardapi.repositories.EmployeRepository;
import fr.enelia.dashboardapi.repositories.StatistiquesAnnuellesRepository;
import fr.enelia.dashboardapi.repositories.StatistiquesMensuellesRepository;
import fr.enelia.dashboardapi.services.StatistiquesAnnuellesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("statistiquesAnnuellesService")
public class StatistiquesAnnuellesServiceImpl implements StatistiquesAnnuellesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatistiquesAnnuellesServiceImpl.class);

    @Autowired
    private StatistiquesAnnuellesRepository statistiquesAnnuellesRepository;
    @Autowired
    private EmployeRepository employeRepository;
    @Override
    public StatistiquesAnnuelles createStatistiquesAnnuelles(StatistiquesAnnuelles statistiquesMensuelles) {
        LOGGER.info("createStatistiquesAnnuelles");
        return statistiquesAnnuellesRepository.save(statistiquesMensuelles);
    }

    @Override
    public StatistiquesAnnuelles updateStatistiquesAnnuelles(StatistiquesAnnuelles statistiquesMensuelles) {
        LOGGER.info("updateStatistiquesAnnuelles");
        return statistiquesAnnuellesRepository.save(statistiquesMensuelles);
    }

    @Override
    public Iterable<StatistiquesAnnuelles> getStatistiquesAnnuellesByUserId(Long id) {
        LOGGER.info("getStatistiquesAnnuellesByUserId");
        Employe employe = employeRepository.findOne(id);
        return statistiquesAnnuellesRepository.getStatistiquesAnnuellesByEmploye(employe);
    }

    @Override
    public StatistiquesAnnuelles getStatistiquesAnnuellesByUserIdAndPeriode(Long id, Periode periode) {
        LOGGER.info("getStatistiquesAnnuellesByUserIdAndPeriode");
        Employe employe = employeRepository.findOne(id);
        return statistiquesAnnuellesRepository.getStatsAnnuellesByEmploye(employe);
    }
}
