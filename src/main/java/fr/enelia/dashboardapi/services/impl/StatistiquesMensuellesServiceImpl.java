package fr.enelia.dashboardapi.services.impl;

import fr.enelia.dashboardapi.controllers.StatistiquesMensuellesController;
import fr.enelia.dashboardapi.entities.Employe;
import fr.enelia.dashboardapi.entities.Periode;
import fr.enelia.dashboardapi.entities.StatistiquesMensuelles;
import fr.enelia.dashboardapi.repositories.EmployeRepository;
import fr.enelia.dashboardapi.repositories.StatistiquesMensuellesRepository;
import fr.enelia.dashboardapi.services.StatistiquesMensuellesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("statistiquesMensuellesService")
public class StatistiquesMensuellesServiceImpl implements StatistiquesMensuellesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatistiquesMensuellesServiceImpl.class);

    @Autowired
    private StatistiquesMensuellesRepository statistiquesMensuellesRepository;
    @Autowired
    private EmployeRepository employeRepository;

    @Override
    public StatistiquesMensuelles createStatistiquesMensuelles(StatistiquesMensuelles statistiquesMensuelles) {
        LOGGER.info("createStatistiquesMensuelles");
        return statistiquesMensuellesRepository.save(statistiquesMensuelles);
    }

    @Override
    public StatistiquesMensuelles updateStatistiquesMensuelles(StatistiquesMensuelles statistiquesMensuelles) {
        LOGGER.info("updateStatistiquesMensuelles");
        return statistiquesMensuellesRepository.save(statistiquesMensuelles);
    }

    @Override
    public Iterable<StatistiquesMensuelles> getStatistiquesMensuellesByUserId(Long id) {
        LOGGER.info("getStatistiquesMensuellesByUserId");
        Employe employe = employeRepository.findOne(id);
        return statistiquesMensuellesRepository.getStatistiquesMensuellesByEmploye(employe);
    }

    @Override
    public StatistiquesMensuelles getStatistiquesMensuellesByUserIdAndPeriode(Long id, Periode periode) {
        LOGGER.info("getStatistiquesMensuellesByUserIdAndPeriode");
        Employe employe = employeRepository.findOne(id);
        return statistiquesMensuellesRepository.getStatistiquesMensuellesByEmployeAndPeriode(employe, periode);
    }
}
