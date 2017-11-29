package fr.enelia.dashboardapi.services.impl;

import fr.enelia.dashboardapi.entities.Agence;
import fr.enelia.dashboardapi.repositories.AgenceRepository;
import fr.enelia.dashboardapi.services.AgenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("agenceService")
public class AgenceServiceImpl implements AgenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgenceServiceImpl.class);

    @Autowired
    private AgenceRepository agenceRepository;

    public Agence createAgence(Agence agence) {
        LOGGER.info("createAgence");
        agence = agenceRepository.save(agence);
        return agence;
    }

    public Agence updateAgence(Agence agence) {
        LOGGER.info("updateAgence");
        agence = agenceRepository.save(agence);
        return agence;
    }

    public Agence getAgenceById(Long id) {
        LOGGER.info("getAgenceById");
        return agenceRepository.findOne(id);
    }

    public Iterable<Agence> getAgences() {
        LOGGER.info("getAgences");
        return agenceRepository.findAll();
    }
}
