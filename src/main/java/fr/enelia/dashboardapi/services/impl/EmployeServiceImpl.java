package fr.enelia.dashboardapi.services.impl;

import fr.enelia.dashboardapi.entities.Employe;
import fr.enelia.dashboardapi.repositories.EmployeRepository;
import fr.enelia.dashboardapi.services.EmployeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("employeService")
public class EmployeServiceImpl implements EmployeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommercialServiceImpl.class);

    @Autowired
    EmployeRepository employeRepository;

    @Override
    public Employe updateEmploye(Employe employe) {
        LOGGER.info("updateEmploye");
        return employeRepository.save(employe);
    }

    @Override
    public Iterable<Employe> getAllEmployes() {
        LOGGER.info("getAllEmployes");
        return employeRepository.findAll();
    }

    @Override
    public Employe getEmployeById(Long id) {
        LOGGER.info("getEmployeById");
        return employeRepository.findOne(id);
    }
}
