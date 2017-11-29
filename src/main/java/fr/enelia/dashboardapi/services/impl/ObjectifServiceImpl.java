package fr.enelia.dashboardapi.services.impl;

import fr.enelia.dashboardapi.entities.Employe;
import fr.enelia.dashboardapi.entities.Objectif;
import fr.enelia.dashboardapi.repositories.EmployeRepository;
import fr.enelia.dashboardapi.repositories.ObjectifRepository;
import fr.enelia.dashboardapi.services.ObjectifService;
import fr.enelia.dashboardapi.services.PeriodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("objectifService")
public class ObjectifServiceImpl implements ObjectifService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectifServiceImpl.class);

    @Autowired
    private ObjectifRepository objectifRepository;
    @Autowired
    private PeriodeService periodeService;

    @Override
    public Objectif createObjectif(Objectif objectif) {
        LOGGER.info("createObjectif");
        return objectifRepository.save(objectif);
    }

    @Override
    public Objectif updateObjectif(Objectif objectif) {
        LOGGER.info("updateObjectif");
        return objectifRepository.save(objectif);
    }

    @Override
    public Objectif getObjectifById(Long id) {
        LOGGER.info("getObjectifById");
        return objectifRepository.findOne(id);
    }

    @Override
    public Objectif getLatestObjectifOfEmployeById(Employe employe) {
        LOGGER.info("getLatestObjectifOfEmployeById");
        Objectif objectif = objectifRepository.findObjectifByEmployeAndPeriode(employe, periodeService.getLatestPeriode());
        objectif.setEmploye(null);
        objectif.getPeriode().setObjectifs(null);
        return objectif;
    }

    @Override
    public Iterable<Objectif> getObjectifs() {
        return null;
    }
}
